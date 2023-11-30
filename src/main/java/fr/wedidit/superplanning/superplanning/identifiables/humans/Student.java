package fr.wedidit.superplanning.superplanning.identifiables.humans;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Student implements Identifiable {

    private final long id;
    private final String firstname;
    private final String lastname;
    private final Grade grade;

    private Student(long id, String firstname, String lastname, Grade grade) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.grade = grade;
    }

    public static Student of(String firstname, String lastname, Grade grade) {
        return Student.of(-1, firstname, lastname, grade);
    }

    public static Student of(long id, String firstname, String lastname, Grade grade) {
        return new Student(id, firstname, lastname, grade);
    }

}
