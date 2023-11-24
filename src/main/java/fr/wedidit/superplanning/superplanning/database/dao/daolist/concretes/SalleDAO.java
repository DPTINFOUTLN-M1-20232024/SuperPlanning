package fr.wedidit.superplanning.superplanning.database.dao.daolist.concretes;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Batiment;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Salle;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class SalleDAO extends AbstractDAO<Salle> {

    public SalleDAO() throws DataAccessException {
        super(TableType.SALLE, new String[] {"NAME", "ID_BATIMENT"});
    }

    @Override
    protected Salle instantiateEntity(ResultSet resultSet) throws SQLException {

        // Load batiment
        long idBatiment = resultSet.getLong("ID_BATIMENT");
        Batiment batiment;
        try (BatimentDAO batimentDAO = new BatimentDAO()) {
            batiment = batimentDAO.find(idBatiment).orElseThrow(() -> new IdentifiableNotFoundException(idBatiment));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        // Create the salle
        return Salle.of(resultSet.getLong("ID"), resultSet.getString("NAME"), batiment);
    }

    @Override
    public Salle persist(Salle salle) throws DataAccessException {
        try {
            psPersist.setString(1, salle.getNom());
            psPersist.setLong(2, salle.getBatiment().getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(SalleDAO.class,
                    sqlException,
                    salle,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Salle salle) throws DataAccessException {
        try {
            psUpdate.setString(1, salle.getNom());
            psUpdate.setLong(2, salle.getBatiment().getId());
            psUpdate.setLong(3, salle.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(SalleDAO.class,
                    sqlException,
                    salle,
                    "Unable to update");
        }
        super.update();
    }
}
