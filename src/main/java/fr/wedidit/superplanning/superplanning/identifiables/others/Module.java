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
    private final String nom;
    private final Promotion promotion;

    private Module(long id, String nom, Promotion promotion) {
        this.id = id;
        this.nom = nom;
        this.promotion = promotion;
    }

    public static Module of(long id, String nom, Promotion promotion) {
        return new Module(id, nom, promotion);
    }

}
