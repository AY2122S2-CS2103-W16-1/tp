package unibook.ui.listpanels;

import static unibook.ui.util.CustomListChangeListeners.addBasicListChangeListener;
import static unibook.ui.util.CustomListChangeListeners.addIndexedListChangeListener;
import static unibook.ui.util.CustomVBoxListFiller.fillPaneFromList;

import java.util.function.BiFunction;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import unibook.commons.core.LogsCenter;
import unibook.model.person.Person;
import unibook.model.person.Professor;
import unibook.model.person.Student;
import unibook.model.person.exceptions.PersonNoSubtypeException;
import unibook.ui.UiPart;
import unibook.ui.cards.ModuleAndGroupMiniCard;
import unibook.ui.cards.ProfessorCard;
import unibook.ui.cards.StudentCard;
import unibook.model.module.Module;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "listpanels/PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private ObservableList<Person> personList;
    private ObservableList<Module> moduleList;

    @FXML
    private VBox personListView;
    @FXML
    private VBox moduleAndGroupListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<Module> moduleList) {
        super(FXML);
        logger.info("Instantiating person list");
        this.personList = personList;
        this.moduleList = moduleList;
        //set up the basic vbox list
        fillPaneFromList(personListView, personList, new BiFunction<Person, Integer, Node>() {
            @Override
            public Node apply(Person person, Integer index) {
                if (person instanceof Student) {
                    return new StudentCard((Student) person, index + 1).getRoot();
                } else if (person instanceof Professor) {
                    return new ProfessorCard((Professor) person, index + 1).getRoot();
                } else {
                    //Since a person must always be a Student or Professor, this
                    //should never run
                    throw new PersonNoSubtypeException();
                }
            }
        });

        //set up listener for changes to underlying personlist
        addIndexedListChangeListener(personListView, personList, new BiFunction<Person, Integer, Node>() {
            @Override
            public Node apply(Person person, Integer index) {
                if (person instanceof Student) {
                    return new StudentCard((Student) person, index + 1).getRoot();
                } else if (person instanceof Professor) {
                    return new ProfessorCard((Professor) person, index + 1).getRoot();
                } else {
                    //Since a person must always be a Student or Professor, this
                    //should never run
                    throw new PersonNoSubtypeException();
                }
            }
        });

        //fill in the moduleandgroup pane
        fillPaneFromList(moduleAndGroupListView, moduleList, module -> new ModuleAndGroupMiniCard(module).getRoot());

        //set up list event listener pane
        addBasicListChangeListener(moduleAndGroupListView, moduleList,
                module -> new ModuleAndGroupMiniCard(module).getRoot());
    }
}
