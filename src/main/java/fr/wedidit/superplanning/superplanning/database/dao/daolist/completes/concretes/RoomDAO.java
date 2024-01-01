package fr.wedidit.superplanning.superplanning.database.dao.daolist.completes.concretes;

import fr.wedidit.superplanning.superplanning.database.dao.AbstractDAO;
import fr.wedidit.superplanning.superplanning.database.dao.TableType;
import fr.wedidit.superplanning.superplanning.database.exceptions.DataAccessException;
import fr.wedidit.superplanning.superplanning.database.exceptions.IdentifiableNotFoundException;
import fr.wedidit.superplanning.superplanning.identifiables.completes.concretes.Building;
import fr.wedidit.superplanning.superplanning.identifiables.completes.concretes.Room;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class RoomDAO extends AbstractDAO<Room> {

    public RoomDAO() throws DataAccessException {
        super(TableType.ROOM, new String[] {"NAME", "ID_BUILDING"});
    }

    @Override
    protected Room instantiateEntity(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("ID");
        String name = resultSet.getString("NAME");

        // Load building
        long idBuilding = resultSet.getLong("ID_BUILDING");
        Building building;
        try (BuildingDAO buildingDAO = new BuildingDAO()) {
            building = buildingDAO.find(idBuilding).orElseThrow(() -> new IdentifiableNotFoundException(idBuilding));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getLocalizedMessage());
            return null;
        }

        return Room.of(id, name, building);
    }

    @Override
    public Room persist(Room room) throws DataAccessException {
        try {
            psPersist.setString(1, room.getName());
            psPersist.setLong(2, room.getBuilding().getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(RoomDAO.class,
                    sqlException,
                    room,
                    "Unable to persist");
        }
        return super.persist();
    }

    @Override
    public void update(Room room) throws DataAccessException {
        try {
            psUpdate.setString(1, room.getName());
            psUpdate.setLong(2, room.getBuilding().getId());
            psUpdate.setLong(3, room.getId());
        } catch (SQLException sqlException) {
            throw new DataAccessException(RoomDAO.class,
                    sqlException,
                    room,
                    "Unable to update");
        }
        super.update();
    }
}
