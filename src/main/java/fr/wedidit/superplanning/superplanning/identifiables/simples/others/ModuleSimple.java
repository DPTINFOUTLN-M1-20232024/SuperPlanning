package fr.wedidit.superplanning.superplanning.identifiables.simples.others;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;

public record ModuleSimple(long id, String name) implements Identifiable {
    public static ModuleSimple of(long id, String name) {
        return new ModuleSimple(id, name);
    }

    @Override
    public long getId() {
        return id;
    }
}