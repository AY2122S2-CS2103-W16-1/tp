package unibook.logic.parser;

import static java.util.Objects.requireNonNull;
import static unibook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unibook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static unibook.logic.parser.CliSyntax.PREFIX_MODULE;
import static unibook.logic.parser.CliSyntax.PREFIX_NAME;
import static unibook.logic.parser.CliSyntax.PREFIX_NEWMOD;
import static unibook.logic.parser.CliSyntax.PREFIX_OPTION;
import static unibook.logic.parser.CliSyntax.PREFIX_PHONE;
import static unibook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import unibook.commons.core.LogsCenter;
import unibook.commons.core.index.Index;
import unibook.logic.LogicManager;
import unibook.logic.commands.EditCommand;
import unibook.logic.commands.EditCommand.EditModuleDescriptor;
import unibook.logic.commands.EditCommand.EditPersonDescriptor;
import unibook.logic.parser.exceptions.ParseException;
import unibook.model.module.Module;
import unibook.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OPTION,
                                                 PREFIX_NAME,
                                                 PREFIX_PHONE,
                                                 PREFIX_EMAIL,
                                                 PREFIX_TAG,
                                                 PREFIX_MODULE,
                                                 PREFIX_NEWMOD);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            if (argMultimap.getValue(PREFIX_OPTION).get().equals("person")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                        EditCommand.PERSON_MESSAGE_USAGE), pe);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                        EditCommand.MODULE_MESSAGE_USAGE), pe);
            }
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();

        if (argMultimap.getValue(PREFIX_OPTION).get().equals("person")) {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            }

            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
            parseModulesForEdit(argMultimap.getAllValues(PREFIX_NEWMOD)).ifPresent(editPersonDescriptor::setModules);
            if (!editPersonDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }

            return new EditCommand(index, editPersonDescriptor);
        } else {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editModuleDescriptor.setModuleName(ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
                editModuleDescriptor.setModuleCode(ParserUtil.parseModuleCode(argMultimap
                                                                              .getValue(PREFIX_MODULE).get()));
            }

            if (!editModuleDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditCommand(index, editModuleDescriptor);
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> modules} into a {@code Set<Module>} if {@code modules} is non-empty.
     * If {@code modules} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Module>} containing zero modules.
     */
    private Optional<Set<Module>> parseModulesForEdit(Collection<String> modules) throws ParseException {
        assert modules != null;

        if (modules.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> moduleSet = modules.size() == 1 && modules.contains("") ? Collections.emptySet() : modules;
        return Optional.of(ParserUtil.parseModules(moduleSet));
    }
}
