package fr.wedidit.superplanning.superplanning.identifiables.others;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Promotion implements Identifiable {

    private final long id;
    private final String name;

    private Promotion(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Promotion of(long id, String name) {
        return new Promotion(id, name);
    }

    public static Promotion of(String name) {
        return new Promotion(-1, name);
    }

}
