package fr.wedidit.superplanning.superplanning.identifiables.others;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class Module implements Identifiable {

    private final long id;
    private final String name;
    private final Grade grade;

    private Module(long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public static Module of(long id, String name, Grade grade) {
        return new Module(id, name, grade);
    }

    public static Module of(String name, Grade grade) {
        return new Module(-1, name, grade);
    }

}
