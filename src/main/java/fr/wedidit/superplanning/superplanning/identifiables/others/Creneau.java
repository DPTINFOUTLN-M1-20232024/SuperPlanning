package fr.wedidit.superplanning.superplanning.identifiables.others;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Salle;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Intervenant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@EqualsAndHashCode
@ToString
public class Creneau implements Identifiable {

    private final long id;
    private final Date debut;
    private final Date fin;
    private final Module module;
    private final Intervenant intervenant;
    private final Salle salle;

    private Creneau(long id, Date debut, Date fin, Module module, Intervenant intervenant, Salle salle) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.module = module;
        this.intervenant = intervenant;
        this.salle = salle;
    }

    public static Creneau of(long id, Date debut, Date fin, Module module, Intervenant intervenant, Salle salle) {
        return new Creneau(id, debut, fin, module, intervenant, salle);
    }

}
