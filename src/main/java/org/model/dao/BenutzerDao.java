package org.model.dao;

import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.control.exception.DataBaseException;
import org.control.exception.NoSuchUserOrPasswordException;
import org.model.entity.Benutzer;
import org.services.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BenutzerDao {
    private static BenutzerDao instance;
    private DBConnection dbConnection = null;

    private BenutzerDao() throws DataBaseException {
        try {
            this.dbConnection = new DBConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static BenutzerDao getInstance() throws DataBaseException{
        if(instance == null) {
            instance = new BenutzerDao();
        }

        return instance;
    }

    public Benutzer getBenutzer(String email, String passwort) throws DataBaseException, NoSuchUserOrPasswordException {
        String sql = "SELECT * FROM carlook.benutzer AS b WHERE b.email=? AND b.passwort=?";
        PreparedStatement preparedStatement = null;
        Benutzer benutzer = null;
        boolean failed = false;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwort);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                 benutzer = new Benutzer();
                 benutzer.setId(resultSet.getInt("id_benutzer"));
                 benutzer.setEmail(resultSet.getString("email"));
                 benutzer.setPasswort(resultSet.getString("passwort"));
                 benutzer.setRolle(resultSet.getString("rolle"));

                 //Endnutzer laden
                if (benutzer.getRolle().equals("Endnutzer")){
                    benutzer = this.getEndnutzer(benutzer);
                }else if(benutzer.getRolle().equals("Vertriebler")){
                    benutzer = this.getVertriebler(benutzer);
                }

            }else{
                throw new NoSuchUserOrPasswordException();
            }

        }catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        }finally {
            try{
                if(preparedStatement != null) preparedStatement.close();
                this.dbConnection.closeConnection();
            }
            catch(SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if(failed) throw new DataBaseException("Beim Laden der Benutzer ist ein Fehler aufgetreten!");
        }

        return benutzer;
    }

    private Benutzer getVertriebler(Benutzer benutzer) {

    }

    private Benutzer getEndnutzer(Benutzer benutzer) {

    }
}
