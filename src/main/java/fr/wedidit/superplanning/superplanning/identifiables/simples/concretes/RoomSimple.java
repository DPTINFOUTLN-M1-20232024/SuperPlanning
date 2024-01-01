package fr.wedidit.superplanning.superplanning.identifiables.simples.concretes;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;

public record RoomSimple(long id, String name) implements Identifiable {
    public static RoomSimple of(long id, String name) {
        return new RoomSimple(id, name);
    }

    @Override
    public long getId() {
        return id;
    }
}