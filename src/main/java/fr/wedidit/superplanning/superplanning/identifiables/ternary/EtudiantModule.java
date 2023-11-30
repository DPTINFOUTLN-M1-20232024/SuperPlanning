package fr.wedidit.superplanning.superplanning.identifiables.ternary;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class EtudiantModule implements Identifiable {
    private final long id;
    private final Student student;
    private final Module module;

    private EtudiantModule(long id, Student student, Module module) {
        this.id = id;
        this.student = student;
        this.module = module;
    }

    public static EtudiantModule of(long id, Student student, Module module) {
        return new EtudiantModule(id, student, module);
    }

}
