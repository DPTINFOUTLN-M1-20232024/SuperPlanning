package fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.others;

import fr.wedidit.superplanning.superplanning.database.DBUtils;
import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.humans.StudentDAO;
import fr.wedidit.superplanning.superplanning.database.datasources.DBCPDataSource;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.humans.Student;
import fr.wedidit.superplanning.superplanning.account.SessionConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class StudentConnectionDAO implements AutoCloseable {

    private Connection connection;
    private PreparedStatement psPersist;
    private PreparedStatement psFindStudent;
    private PreparedStatement psUpdate;
    private PreparedStatement psGetStudentIdFromMail;

    public StudentConnectionDAO() throws DataAccessException {

        String requestPersist = DBUtils.createPSPersist(TableType.STUDENT_CONNECTION.name(),
                StudentConnectionColumn.ID_STUDENT.name(),
                StudentConnectionColumn.MAIL.name(),
                StudentConnectionColumn.HASH_PASSWORD.name());

        String requestFindStudent = """
                SELECT %s.ID_STUDENT
                FROM %s
                WHERE %s.MAIL = ?
                AND %s.HASH_PASSWORD = ?;
                """.formatted(TableType.STUDENT_CONNECTION.name(),
                    TableType.STUDENT_CONNECTION.name(),
                    TableType.STUDENT_CONNECTION.name(),
                    TableType.STUDENT_CONNECTION.name());

        String requestUpdateFromIdStudent = """
                UPDATE %s SET
                %s=?
                WHERE %s=?;
                """.formatted(TableType.STUDENT_CONNECTION,
                    StudentConnectionColumn.HASH_PASSWORD,
                    StudentConnectionColumn.ID_STUDENT);

        String requestGetStudentIdFromMail = """
                SELECT %s.%s
                FROM %s
                WHERE %s.%s = ?;
                """.formatted(TableType.STUDENT_CONNECTION.name(),
                    StudentConnectionColumn.ID_STUDENT,
                    TableType.STUDENT_CONNECTION.name(),
                    TableType.STUDENT_CONNECTION.name(),
                    StudentConnectionColumn.MAIL);

        try {
            this.connection = DBCPDataSource.getConnection();
        } catch (SQLException sqlException) {
            log.error("Unable to get connection from DBCPDataSource");
        }

        try {
            this.psPersist = this.connection.prepareStatement(requestPersist);
            this.psFindStudent = this.connection.prepareStatement(requestFindStudent);
            this.psUpdate = this.connection.prepareStatement(requestUpdateFromIdStudent);
            this.psGetStudentIdFromMail = this.connection.prepareStatement(requestGetStudentIdFromMail);
        } catch (SQLException sqlException) {
            log.error("Request: %s".formatted(requestPersist));
            log.error(sqlException.getLocalizedMessage());
        }

    }

    /**
     *
     * @param sessionConnection sessionConnection
     * @param student student without id
     * @return student with id
     * @throws DataAccessException if error
     */
    public Student persist(SessionConnection sessionConnection, Student student) throws DataAccessException {

        Student studentWithID;
        try (StudentDAO studentDAO = new StudentDAO()){
            studentWithID = studentDAO.persist(student);
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to persist the student %s.".formatted(student));
            return null;
        }

        try {
            psPersist.setLong(StudentConnectionColumn.ID_STUDENT.colNumber(), studentWithID.getId());
            psPersist.setString(StudentConnectionColumn.MAIL.colNumber(), sessionConnection.getMail());
            psPersist.setString(StudentConnectionColumn.HASH_PASSWORD.colNumber(), sessionConnection.getHashPassword());
            psPersist.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DataAccessException(StudentConnectionDAO.class,
                    sqlException,
                    "Unable to persist");
        }

        return studentWithID;
    }

    public void update(String hashedPassword, long studentId) throws DataAccessException {
        try {
            psUpdate.setString(1, hashedPassword);
            psUpdate.setLong(2, studentId);
            psUpdate.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DataAccessException(StudentConnectionDAO.class,
                    sqlException,
                    "Unable to update");
        }
    }

    public Student getStudentFromConnection(SessionConnection sessionConnection) throws DataAccessException {
        long idStudent;
        try {
            psFindStudent.setString(1, sessionConnection.getMail());
            psFindStudent.setString(2, sessionConnection.getHashPassword());

            ResultSet rs = psFindStudent.executeQuery();
            if (rs.next()) {
                idStudent = rs.getLong(StudentConnectionColumn.ID_STUDENT.name());
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

    public Student getStudentFromMail(String mail) throws DataAccessException {
        long idStudent;
        try {
            psGetStudentIdFromMail.setString(1, mail);
            ResultSet rs = psGetStudentIdFromMail.executeQuery();
            if (rs.next()) {
                idStudent = rs.getLong(StudentConnectionColumn.ID_STUDENT.name());
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException(StudentConnectionDAO.class,
                    sqlException,
                    "Unable to get student from mail");
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

    private enum StudentConnectionColumn {
        ID_STUDENT,
        MAIL,
        HASH_PASSWORD;

        public int colNumber() {
            return ordinal() + 1;
        }
    }

}
