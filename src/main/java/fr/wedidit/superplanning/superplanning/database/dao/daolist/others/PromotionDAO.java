package fr.wedidit.superplanning.superplanning.database.dao.daolist.others;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.concretes.BatimentDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.identifiables.others.Promotion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PromotionDAO extends AbstractDAO<Promotion> {

    public PromotionDAO() throws DataAccessException {
        super(TableType.PROMOTION, new String[] {"NAME"});
    }

    @Override
    protected Promotion instantiateEntity(ResultSet resultSet) throws SQLException {
        return Promotion.of(resultSet.getLong("ID"), resultSet.getString("NAME"));
    }

    @Override
    public Promotion persist(Promotion promotion) throws DataAccessException {
        try {
            psPersist.setString(1, promotion.getName());
        } catch (SQLException sqlException) {
            throw new DataAccessException(PromotionDAO.class,
                    sqlException,
                    promotion,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Promotion promotion) throws DataAccessException {
        try {
            psUpdate.setString(1, promotion.getName());
            psUpdate.setLong(2, promotion.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(PromotionDAO.class,
                    sqlException,
                    promotion,
                    "Unable to update");
        }
        super.update();
    }
}
