package fr.wedidit.superplanning.superplanning.database.dao.daolist.ternary;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.humans.EtudiantDAO;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.ModuleDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Etudiant;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
import fr.wedidit.superplanning.superplanning.identifiables.ternary.EtudiantModule;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class EtudiantModuleDAO extends AbstractDAO<EtudiantModule> {

    protected EtudiantModuleDAO() throws DataAccessException {
        super(TableType.ETUDIANT_MODULE, new String[] {"ID_ETUDIANT", "ID_MODULE"});
    }

    @Override
    protected EtudiantModule instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");

        // Load the Etudiant
        long idEtudiant = resultSet.getLong("ID_ETUDIANT");
        Etudiant etudiant;
        try (EtudiantDAO etudiantDAO = new EtudiantDAO()) {
            etudiant = etudiantDAO.find(idEtudiant).orElseThrow(() -> new IdentifiableNotFoundException(idEtudiant));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        // Load the Etudiant
        long idModule = resultSet.getLong("ID_MODULE");
        Module module;
        try (ModuleDAO moduleDAO = new ModuleDAO()) {
            module = moduleDAO.find(idModule).orElseThrow(() -> new IdentifiableNotFoundException(idModule));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        return EtudiantModule.of(id, etudiant, module);
    }

    @Override
    public EtudiantModule persist(EtudiantModule etudiantModule) throws DataAccessException {
        throw new UnsupportedOperationException("EtudiantModule class is ternary");
    }

    @Override
    public void update(EtudiantModule etudiantModule) throws DataAccessException {
        throw new UnsupportedOperationException("EtudiantModule class is ternary");
    }
}
