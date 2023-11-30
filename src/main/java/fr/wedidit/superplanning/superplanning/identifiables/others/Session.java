package fr.wedidit.superplanning.superplanning.identifiables.others;

import fr.wedidit.superplanning.superplanning.identifiables.Identifiable;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Room;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Instructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@EqualsAndHashCode
@ToString
public class Session implements Identifiable {

    private final long id;
    private final Timestamp begin;
    private final Timestamp finish;
    private final Module module;
    private final Instructor instructor;
    private final Room room;

    private Session(long id, Timestamp begin, Timestamp finish, Module module, Instructor instructor, Room room) {
        this.id = id;
        this.begin = begin;
        this.finish = finish;
        this.module = module;
        this.instructor = instructor;
        this.room = room;
    }

    public static Session of(long id, Timestamp begin, Timestamp finish, Module module, Instructor instructor, Room room) {
        return new Session(id, begin, finish, module, instructor, room);
    }

}
