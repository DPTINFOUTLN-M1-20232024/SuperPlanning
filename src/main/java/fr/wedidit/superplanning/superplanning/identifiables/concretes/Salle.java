package fr.wedidit.superplanning.superplanning.identifiables.concretes;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Salle implements Identifiable {

    private final long id;
    private final String nom;
    private final Batiment batiment;

    private Salle(long id, String nom, Batiment batiment) {
        this.id = id;
        this.nom = nom;
        this.batiment = batiment;
    }

    public static Salle of(long id, String nom, Batiment batiment) {
        return new Salle(id, nom, batiment);
    }

}
