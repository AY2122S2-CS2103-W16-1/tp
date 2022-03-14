package unibook.logic.commands;

import static java.util.Objects.requireNonNull;
import static unibook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static unibook.logic.parser.CliSyntax.PREFIX_MODULE;
import static unibook.logic.parser.CliSyntax.PREFIX_NAME;
import static unibook.logic.parser.CliSyntax.PREFIX_NEWMOD;
import static unibook.logic.parser.CliSyntax.PREFIX_OPTION;
import static unibook.logic.parser.CliSyntax.PREFIX_PHONE;
import static unibook.logic.parser.CliSyntax.PREFIX_TAG;
import static unibook.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static unibook.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import unibook.commons.core.LogsCenter;
import unibook.commons.core.Messages;
import unibook.commons.core.index.Index;
import unibook.commons.util.CollectionUtil;
import unibook.logic.LogicManager;
import unibook.logic.commands.exceptions.CommandException;
import unibook.model.Model;
import unibook.model.module.Module;
import unibook.model.module.ModuleCode;
import unibook.model.module.ModuleName;
import unibook.model.module.exceptions.ModuleNotFoundException;
import unibook.model.person.Email;
import unibook.model.person.Name;
import unibook.model.person.Office;
import unibook.model.person.Person;
import unibook.model.person.Phone;
import unibook.model.person.Professor;
import unibook.model.person.Student;
import unibook.model.tag.Tag;

/**
 * Edits the details of an existing person in the UniBook.
 */
public class EditCommand extends Command {


    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the UniBook.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the UniBook.";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_OPTION_NOT_FOUND = "Option tag must be provided. \n";
    public static final String MESSAGE_PERSON_NO_SUBTYPE = "Person must be a professor or student";

    public static final String PERSON_MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_OPTION + "OPTION "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_OPTION + "person "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MODULE_MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the index number used in the displayed module list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_OPTION + "OPTION "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_MODULE + "MODULECODE] "
            + "[" + PREFIX_NEWMOD + "NEWMODULECODE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_OPTION + "module "
            + PREFIX_NAME + "Software Engineering "
            + PREFIX_MODULE + "CS2103T";



    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Index index;
    private EditPersonDescriptor editPersonDescriptor;
    private EditModuleDescriptor editModuleDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.editModuleDescriptor = null;
    }

    /**
     * @param index of the module in the filtered module list to edit
     * @param editModuleDescriptor details to edit the module with
     */
    public EditCommand(Index index, EditModuleDescriptor editModuleDescriptor) {
        requireNonNull(index);
        requireNonNull(editModuleDescriptor);

        this.index = index;
        this.editModuleDescriptor = new EditModuleDescriptor(editModuleDescriptor);
        this.editPersonDescriptor = null;
    }

    @Override
    public CommandResult execute(Model model, Boolean isPersonListShowing,
                                 Boolean isModuleListShowing) throws CommandException, ModuleNotFoundException {
        requireNonNull(model);

        // Edit person
        if (this.editModuleDescriptor == null) {
            if (!isPersonListShowing) {
                throw new CommandException(Messages.MESSAGE_CHANGE_TO_PERSON_PAGE);
            }
            List<Person> lastShownList = model.getFilteredPersonList();
            List<Module> latestModList = model.getFilteredModuleList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToEdit = lastShownList.get(index.getZeroBased());
            Module checkMod = null;

            // Checks if module that want to add to person is valid
            if (!editPersonDescriptor.getModules().equals(Optional.empty()) && latestModList.size() != 0) {
                checkMod = editPersonDescriptor.getModules().get().iterator().next();
                if (!latestModList.contains(checkMod)) {
                    throw new ModuleNotFoundException(checkMod.toString());
                }
            }

            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
            logger.info(String.valueOf(editedPerson instanceof Professor));
            logger.info(String.valueOf(editedPerson instanceof Student));

            // When adding new module with nm/, adds prof/student to person list in each mod
            if (checkMod != null) {
                int modIdx = latestModList.indexOf(checkMod);
                if (editedPerson instanceof Professor) {
                    latestModList.get(modIdx).addProfessor((Professor) editedPerson);
                } else if (editedPerson instanceof Student) {
                    latestModList.get(modIdx).addStudent((Student) editedPerson);
                } else {
                    throw new CommandException(MESSAGE_PERSON_NO_SUBTYPE);
                }
            }

            if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            // Change person in every module that has this person
            for (Module mod : latestModList) {
                if (editedPerson instanceof Professor) {
                    ObservableList<Professor> profList = mod.getProfessors();
                    if (profList.contains(personToEdit)) {
                        int profIdx = profList.indexOf(personToEdit);
                        profList.set(profIdx, (Professor) editedPerson);
                    }
                } else if (editedPerson instanceof Student) {
                    ObservableList<Student> studentList = mod.getStudents();
                    if (studentList.contains(personToEdit)) {
                        int studentIdx = studentList.indexOf(personToEdit);
                        studentList.set(studentIdx, (Student) editedPerson);
                    }
                } else {
                    throw new CommandException(MESSAGE_PERSON_NO_SUBTYPE);
                }
            }

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));

        } else {

            // Edit module

            if (!isModuleListShowing) {
                throw new CommandException(Messages.MESSAGE_CHANGE_TO_MODULE_PAGE);
            }

            List<Person> lastPersonList = model.getFilteredPersonList();
            List<Module> lastShownList = model.getFilteredModuleList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            }

            Module moduleToEdit = lastShownList.get(index.getZeroBased());
            Module editedModule = createEditedModule(moduleToEdit, editModuleDescriptor);

            if (!moduleToEdit.isSameModule(editedModule) && model.hasModule(editedModule)) {
                throw new CommandException(MESSAGE_DUPLICATE_MODULE);
            }

            // Find all profs and students with this module and will be edited
            for (Person person : lastPersonList) {
                Set<Module> moduleSet = person.getModulesModifiable();
                if (moduleSet.contains(moduleToEdit)) {
                    moduleSet.remove(moduleToEdit);
                    moduleSet.add(editedModule);
                    person.setModules(moduleSet);
                }
            }

            model.setModule(moduleToEdit, editedModule);
            model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
            return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());

        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Module> updatedModules = editPersonDescriptor.getModules().orElse(personToEdit.getModules());

        if (personToEdit instanceof Professor) {
            Office updatedOffice = ((Professor) personToEdit).getOffice();
            return new Professor(updatedName, updatedPhone, updatedEmail, updatedTags, updatedOffice, updatedModules);
        }
        return new Student(updatedName, updatedPhone, updatedEmail, updatedTags, updatedModules);
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit, EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        ModuleName updatedModuleName = editModuleDescriptor.getModuleName().orElse(moduleToEdit.getModuleName());
        ModuleCode updatedModuleCode = editModuleDescriptor.getModuleCode().orElse(moduleToEdit.getModuleCode());
        ObservableList<Professor> profs = moduleToEdit.getProfessors();
        ObservableList<Student> students = moduleToEdit.getStudents();

        return new Module(updatedModuleName, updatedModuleCode, profs, students);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Tag> tags;
        private Set<Module> modules;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setTags(toCopy.tags);
            setModules(toCopy.modules);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, tags, modules);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code modules} to this object's {@code modules}.
         * A defensive copy of {@code modules} is used internally.
         */
        public void setModules(Set<Module> modules) {
            if (modules == null) {
                this.modules = null;
            } else {
                Set<Module> modulesCopy = new HashSet<>(modules);
                modulesCopy.addAll(modules);
                this.modules = modulesCopy;
            }
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code modules} is null.
         */
        public Optional<Set<Module>> getModules() {
            return (modules != null) ? Optional.of(Collections.unmodifiableSet(modules)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getTags().equals(e.getTags())
                    && getModules().equals(e.getModules());
        }
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleDescriptor {
        private ModuleName moduleName;
        private ModuleCode moduleCode;

        public EditModuleDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setModuleName(toCopy.moduleName);
            setModuleCode(toCopy.moduleCode);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(moduleName, moduleCode);
        }

        public void setModuleName(ModuleName moduleName) {
            this.moduleName = moduleName;
        }

        public Optional<ModuleName> getModuleName() {
            return Optional.ofNullable(moduleName);
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleDescriptor)) {
                return false;
            }

            // state check
            EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getModuleName().equals(e.getModuleName())
                    && getModuleCode().equals(e.getModuleCode());
        }
    }
}
