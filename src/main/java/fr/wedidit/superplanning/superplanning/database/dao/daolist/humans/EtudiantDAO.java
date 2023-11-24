package fr.wedidit.superplanning.superplanning.database.dao.daolist.humans;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.dao.daolist.others.PromotionDAO;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.humans.Etudiant;
import fr.wedidit.superplanning.superplanning.identifiables.others.Promotion;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class EtudiantDAO extends AbstractDAO<Etudiant> {

    public EtudiantDAO() throws DataAccessException {
        super(TableType.ETUDIANT, new String[]{"FIRSTNAME", "LASTNAME", "ID_PROMOTION"});
    }

    @Override
    protected Etudiant instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String firstname = resultSet.getString("FIRSTNAME");
        String lastname = resultSet.getString("LASTNAME");
        long idPromotion = resultSet.getLong("ID_PROMOTION");
        Promotion promotion;
        try (PromotionDAO promotionDAO = new PromotionDAO()) {
            promotion = promotionDAO.find(idPromotion).orElseThrow(() -> new IdentifiableNotFoundException(idPromotion));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }
        return Etudiant.of(id, firstname, lastname, promotion);
    }

    @Override
    public Etudiant persist(Etudiant etudiant) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Etudiant etudiant) throws DataAccessException {

    }
}
