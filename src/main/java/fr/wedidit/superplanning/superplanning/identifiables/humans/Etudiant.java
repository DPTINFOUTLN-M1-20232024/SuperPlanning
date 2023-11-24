package fr.wedidit.superplanning.superplanning.identifiables.humans;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import fr.wedidit.superplanning.superplanning.identifiables.others.Promotion;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Etudiant implements Identifiable {

    private final long id;
    private final String firstname, lastname;
    private final Promotion promotion;

    private Etudiant(long id, String firstname, String lastname, Promotion promotion) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.promotion = promotion;
    }

    public static Etudiant of(long id, String firstname, String lastname, Promotion promotion) {
        return new Etudiant(id, firstname, lastname, promotion);
    }

}
