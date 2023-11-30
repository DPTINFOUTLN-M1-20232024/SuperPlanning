package fr.wedidit.superplanning.superplanning.database.dao.daolist.humans;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.GradeDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Student;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class StudentDAO extends AbstractDAO<Student> {

    public StudentDAO() throws DataAccessException {
        super(TableType.STUDENT, new String[]{"FIRSTNAME", "LASTNAME", "ID_GRADE"});
    }

    @Override
    protected Student instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String firstname = resultSet.getString("FIRSTNAME");
        String lastname = resultSet.getString("LASTNAME");

        long idGrade = resultSet.getLong("ID_GRADE");
        Grade grade;
        try (GradeDAO gradeDAO = new GradeDAO()) {
            grade = gradeDAO.find(idGrade).orElseThrow(() -> new IdentifiableNotFoundException(idGrade));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        return Student.of(id, firstname, lastname, grade);
    }

    @Override
    public Student persist(Student student) throws DataAccessException {
        try {
            psPersist.setString(1, student.getFirstname());
            psPersist.setString(2, student.getLastname());
            psPersist.setLong(3, student.getGrade().getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(StudentDAO.class,
                    sqlException,
                    student,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Student student) throws DataAccessException {
        try {
            psUpdate.setString(1, student.getFirstname());
            psUpdate.setString(2, student.getLastname());
            psUpdate.setLong(3, student.getGrade().getId());
            psUpdate.setLong(4, student.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(StudentDAO.class,
                    sqlException,
                    student,
                    "Unable to update");
        }
        super.update();
    }
}
