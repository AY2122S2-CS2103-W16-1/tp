package unibook.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import unibook.commons.core.GuiSettings;
import unibook.model.module.Module;
import unibook.model.person.Person;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' UniBook file path.
     */
    Path getUniBookFilePath();

    /**
     * Sets the user prefs' UniBook file path.
     */
    void setUniBookFilePath(Path uniBookFilePath);

    /**
     * Returns the UniBook
     */
    ReadOnlyUniBook getUniBook();

    /**
     * Replaces UniBook data with the data in {@code uniBook}.
     */
    void setUniBook(ReadOnlyUniBook uniBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the UniBook.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the UniBook.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the UniBook.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the UniBook.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the UniBook.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean hasModule(Module module);

    void deleteModule(Module target);


    void addModule(Module module);

    void setModule(Module target, Module editedModule);

    ObservableList<Module> getFilteredModuleList();

    void updateFilteredModuleList(Predicate<Module> predicate);
}
