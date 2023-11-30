package fr.wedidit.superplanning.superplanning.identifiables.humans;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Instructor implements Identifiable {

    private final long id;
    private final String firstname;
    private final String lastname;

    private Instructor(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static Instructor of(long id, String firstname, String lastname) {
        return new Instructor(id, firstname, lastname);
    }


}
