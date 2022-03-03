package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
<<<<<<< HEAD:src/main/java/seedu/address/model/person/Office.java
 * Represents a Professor's Office in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOffice(String)}
=======
 * Represents a Person's address in the UniBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
>>>>>>> c1a634abff28b20650784c00cb64152a609d174e:src/main/java/seedu/address/model/person/Address.java
 */
public class Office {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the office must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Office}.
     *
     * @param office A valid office.
     */
    public Office(String office) {
        requireNonNull(office);
        checkArgument(isValidOffice(office), MESSAGE_CONSTRAINTS);
        value = office;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidOffice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Office // instanceof handles nulls
                && value.equals(((Office) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
