package fr.wedidit.superplanning.superplanning.identifiables.completes.concretes;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Room implements Identifiable {

    private final long id;
    private final String name;
    private final Building building;

    protected Room(long id, String name, Building building) {
        this.id = id;
        this.name = name;
        this.building = building;
    }

    public static Room of(long id, String name, Building building) {
        return new Room(id, name, building);
    }

}
