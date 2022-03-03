package seedu.address.model.person;

import seedu.address.model.tag.Tag;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Professor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Professor extends Person {

    private final Office office;

    /**
     * Every field must be present and not null.
     */
    public Professor(Name name, Phone phone, Email email, Set<Tag> tags, Office office) {
        super(name, phone, email, tags);
        this.office = office;
    }

    public Office getOffice() {
        return this.office;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Professor)) {
            return false;
        }

        Professor otherPerson = (Professor) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getOffice().equals(getOffice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getTags(), office);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Office: ")
                .append(office);

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
