package fr.wedidit.superplanning.superplanning.database.dao.daolist.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
import fr.wedidit.superplanning.superplanning.identifiables.others.Grade;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ModuleDAO extends AbstractDAO<Module> {

    private final PreparedStatement psFindAllModuleFromGrade;

    public ModuleDAO() throws DataAccessException {
        super(TableType.MODULE, new String[] {"NAME", "ID_GRADE"});

        try {
            String request = """
                    SELECT MODULE.ID, MODULE.NAME 
                    FROM MODULE 
                    WHERE MODULE.ID_GRADE = ?;
                    """;
            psFindAllModuleFromGrade = connection.prepareStatement(request);
        } catch (SQLException sqlException) {
            throw new DataAccessException(ModuleDAO.class,
                    sqlException,
                    "Unable to create prepared statement in ModuleDAO class");
        }
    }

    public Set<Module> getModulesFromGrade(Grade grade) throws DataAccessException {
        Set<Module> modules = new HashSet<>();

        try {
            psFindAllModuleFromGrade.setLong(1, grade.getId());
            ResultSet rsFindAllModuleFromGrade = psFindAllModuleFromGrade.executeQuery();
            Module module;
            while (rsFindAllModuleFromGrade.next()) {
                long id = rsFindAllModuleFromGrade.getLong("ID");
                String name = rsFindAllModuleFromGrade.getString("NAME");
                module = Module.of(id, name, grade);
                modules.add(module);
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException(ModuleDAO.class,
                    sqlException,
                    "Unable to search the modules from grade");
        }

        return modules;
    }

    @Override
    protected Module instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String nom = resultSet.getString("NOM");

        long idGrade = resultSet.getLong("ID_GRADE");
        Grade grade;
        try (GradeDAO gradeDAO = new GradeDAO()) {
            grade = gradeDAO.find(idGrade).orElseThrow(() -> new IdentifiableNotFoundException(idGrade));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        return Module.of(id, nom, grade);
    }

    @Override
    public Module persist(Module module) throws DataAccessException {
        try {
            psPersist.setString(1, module.getName());
            psPersist.setLong(2, module.getGrade().getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(ModuleDAO.class,
                    sqlException,
                    module,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Module module) throws DataAccessException {
        try {
            psUpdate.setString(1, module.getName());
            psUpdate.setLong(2, module.getGrade().getId());
            psUpdate.setLong(2, module.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(ModuleDAO.class,
                    sqlException,
                    module,
                    "Unable to update");
        }
        super.update();
    }
}
