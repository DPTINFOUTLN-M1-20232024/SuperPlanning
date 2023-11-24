package fr.wedidit.superplanning.superplanning.identifiables.concretes;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class Batiment implements Identifiable {

    private final long id;
    private final String name;

    private Batiment(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Batiment of(long id, String name) {
        return new Batiment(id, name);
    }

}
