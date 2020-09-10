package org.model.dao;

import org.control.exception.DataBaseException;
import org.services.db.DBConnection;
import org.services.proxy.ProxySucheAutoAPI;

import java.sql.SQLException;

public class EndnutzerDao {

    private static EndnutzerDao instance;
    private DBConnection dbConnection = null;

    private EndnutzerDao() throws DataBaseException {
        try {
            this.dbConnection = new DBConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static EndnutzerDao getInstance() throws DataBaseException{
        if(instance == null) {
            instance = new EndnutzerDao();
        }

        return instance;
    }
/*
    public ArrayList<Endnutzer> getByAttributs(String[] columns, Object[] values, boolean permute) throws DataBaseException {
        ResultSet rs = super.select("SELECT * FROM " + super.table + " INNER JOIN production.benutzer ON " + super.table + ".studentenid = production.benutzer.benutzerid",
                true,
                columns,
                values,
                "OR",
                "AND",
                permute);
        ArrayList<Endnutzer> endnutzer = null;
        ArrayList<Endnutzer> benutzer = null;
        try {
            // Map endnutzer
            endnutzer = super.autoMap(rs, false);
            // reset iterator
            rs.beforeFirst();
            // Map unternemen
            benutzer = DaoFactory.getInstance().getBenutzerDao().autoMap(rs, true);
            // merge bentzer & endnutzer
            for(int i=0; i<endnutzer.size(); i++) {
                Endnutzer s = endnutzer.get(i);
                Benutzer b = benutzer.get(i);
                s.setBenutzerid(b.getBenutzerid());
                s.setRolle(b.getRolle());
                s.setPasswort(b.getPasswort());
                s.setBenutzername(b.getBenutzername());
                s.setBeschreibung(b.getBeschreibung());
                s.setAktiviert(b.getAktiviert());
            }
        }
        catch (SQLException e) {
            LOGGER.log( Level.SEVERE, e.toString(), e );
            throw new DataBaseException("Fehler beim Lesen aus der Datenbank!");
        }
        finally {
            try {
                this.dbConnection.closeConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return endnutzer;
    }

    public ArrayList<Endnutzer> getByAttribut(String column, Object value) throws DataBaseException {
        String[] columns = {column};
        Object[] obj = { value };
        return this.getByAttributs(columns, obj, false);
    }

    public Endnutzer getById(Object id) throws DataBaseException {
        ArrayList<Endnutzer> list = this.getByAttribut(this.idColumn, id);
        return list.size() > 0 ? list.get(0) : null;
    }
*/
}
