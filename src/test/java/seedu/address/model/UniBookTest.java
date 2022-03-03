package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalUniBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class UniBookTest {

    private final UniBook uniBook = new UniBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), uniBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyUniBook_replacesData() {
        UniBook newData = getTypicalUniBook();
        uniBook.resetData(newData);
        assertEquals(newData, uniBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        UniBookStub newData = new UniBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> uniBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInUniBook_returnsFalse() {
        assertFalse(uniBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInUniBook_returnsTrue() {
        uniBook.addPerson(ALICE);
        assertTrue(uniBook.hasPerson(ALICE));
    }

    @Test
<<<<<<< HEAD:src/test/java/seedu/address/model/AddressBookTest.java
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
=======
    public void hasPerson_personWithSameIdentityFieldsInUniBook_returnsTrue() {
        uniBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
>>>>>>> c1a634abff28b20650784c00cb64152a609d174e:src/test/java/seedu/address/model/UniBookTest.java
                .build();
        assertTrue(uniBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyUniBook whose persons list can violate interface constraints.
     */
    private static class UniBookStub implements ReadOnlyUniBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        UniBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
