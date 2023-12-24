package fr.wedidit.superplanning.superplanning.identifiables.simples.humans;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;

public record StudentSimple(long id, String firstName, String lastName) implements Identifiable {
    public static StudentSimple of(long id, String firstname, String lastname) {
        return new StudentSimple(id, firstname, lastname);
    }

    @Override
    public long getId() {
        return id;
    }
}
