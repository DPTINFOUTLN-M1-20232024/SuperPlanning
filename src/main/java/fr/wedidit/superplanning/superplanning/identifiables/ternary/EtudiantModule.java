package fr.wedidit.superplanning.superplanning.identifiables.ternary;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Etudiant;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class EtudiantModule implements Identifiable {
    private final long id;
    private final Etudiant etudiant;
    private final Module module;

    private EtudiantModule(long id, Etudiant etudiant, Module module) {
        this.id = id;
        this.etudiant = etudiant;
        this.module = module;
    }

    public static EtudiantModule of(long id, Etudiant etudiant, Module module) {
        return new EtudiantModule(id, etudiant, module);
    }

}
