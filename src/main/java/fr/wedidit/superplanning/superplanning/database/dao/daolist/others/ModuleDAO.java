package fr.wedidit.superplanning.superplanning.database.dao.daolist.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.concretes.BatimentDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.concretes.Batiment;
import fr.wedidit.superplanning.superplanning.identifiables.others.Module;
import fr.wedidit.superplanning.superplanning.identifiables.others.Promotion;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class ModuleDAO extends AbstractDAO<Module> {

    public ModuleDAO() throws DataAccessException {
        super(TableType.MODULE, new String[] {"NOM", "ID_PROMOTION"});
    }

    @Override
    protected Module instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String nom = resultSet.getString("NOM");

        long idPromotion = resultSet.getLong("ID_PROMOTION");
        Promotion promotion;
        try (PromotionDAO promotionDAO = new PromotionDAO()) {
            promotion = promotionDAO.find(idPromotion).orElseThrow(() -> new IdentifiableNotFoundException(idPromotion));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        return Module.of(id, nom, promotion);
    }

    @Override
    public Module persist(Module module) throws DataAccessException {
        try {
            psPersist.setString(1, module.getNom());
            psPersist.setLong(2, module.getPromotion().getId());
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
            psUpdate.setString(1, module.getNom());
            psUpdate.setLong(2, module.getPromotion().getId());
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
