package fr.wedidit.superplanning.superplanning.identifiables.completes.concretes;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class Building implements Identifiable {

    private final long id;
    private final String name;

    private Building(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Building of(long id, String name) {
        return new Building(id, name);
    }

    public static Building of(String name) {return new Building(-1, name);}

}
