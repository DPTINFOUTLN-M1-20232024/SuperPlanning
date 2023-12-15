package fr.wedidit.superplanning.superplanning.database.dao.daolist.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.StudentDAO;
import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.StudentConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class StudentConnectionDAO implements AutoCloseable {

    private Connection connection;
    private PreparedStatement psPersist;
    private PreparedStatement psFindStudent;

    public StudentConnectionDAO() throws DataAccessException {

        String requestPersist = """
                INSERT INTO %s (ID_STUDENT, MAIL, HASH_PASSWORD) VALUES
                    (?, ?, ?);
                """.formatted(TableType.STUDENT_CONNECTION.name());

        String requestFindStudent = """
                SELECT %s.ID_STUDENT
                FROM %s
                WHERE %s.MAIL = ?
                AND %s.HASH_PASSWORD = ?;
                """.formatted(TableType.STUDENT_CONNECTION.name(),
                    TableType.STUDENT_CONNECTION.name(),
                    TableType.STUDENT_CONNECTION.name(),
                    TableType.STUDENT_CONNECTION.name());

        try {
            this.connection = DBCPDataSource.getConnection();
        } catch (SQLException sqlException) {
            log.error("Unable to get connection from DBCPDataSource");
        }

        try {
            this.psPersist = this.connection.prepareStatement(requestPersist);
        } catch (SQLException sqlException) {
            log.error("Unable to create prepared statement persist request from StudentConnectionDAO.");
            log.error("Request: %s".formatted(requestPersist));
        }

        try {
            this.psFindStudent = this.connection.prepareStatement(requestFindStudent);
        } catch (SQLException sqlException) {
            log.error("Unable to create prepared statement requestFindStudent request from StudentConnectionDAO.");
            log.error("Request: %s".formatted(requestFindStudent));
        }

    }

    /**
     *
     * @param studentConnection
     * @param student student without id
     * @return student with id
     * @throws DataAccessException
     */
    public Student persist(StudentConnection studentConnection, Student student) throws DataAccessException {

        Student studentWithID;
        try (StudentDAO studentDAO = new StudentDAO()){
            studentWithID = studentDAO.persist(student);
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to persist the student %s.".formatted(student));
            return null;
        }

        try {
            psPersist.setLong(1, studentWithID.getId());
            psPersist.setString(2, studentConnection.getMail());
            psPersist.setString(3, studentConnection.getHashPassword());
            psPersist.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DataAccessException(StudentConnectionDAO.class,
                    sqlException,
                    "Unable to persist");
        }

        return studentWithID;
    }

    public Student getStudentFromConnection(StudentConnection studentConnection) throws DataAccessException {
        long idStudent;
        try {
            psFindStudent.setString(1, studentConnection.getMail());
            psFindStudent.setString(2, studentConnection.getHashPassword());

            ResultSet rs = psFindStudent.executeQuery();
            if (rs.next()) {
                idStudent = rs.getLong("ID_STUDENT");
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException(StudentConnectionDAO.class,
                    sqlException,
                    "Unable to get student from connection student");
        }

        try (StudentDAO studentDAO = new StudentDAO()) {
            return studentDAO.find(idStudent).orElseThrow(() -> new IdentifiableNotFoundException(idStudent));
        }
    }

    @Override
    public void close() throws DataAccessException {
        try {
            this.connection.close();
        } catch (SQLException sqlException) {
            throw new DataAccessException(AbstractDAO.class,
                    sqlException,
                    "Unable to close");
        }
    }
}