package fr.wedidit.superplanning.superplanning.identifiables.completes.others;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Grade implements Identifiable {

    private final long id;
    private final String name;

    private Grade(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Grade of(long id, String name) {
        return new Grade(id, name);
    }

    public static Grade of(String name) {
        return new Grade(-1, name);
    }

}
