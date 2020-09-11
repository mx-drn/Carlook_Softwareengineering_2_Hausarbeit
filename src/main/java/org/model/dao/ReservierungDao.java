package org.model.dao;

import org.control.exception.DataBaseException;
import org.control.exception.NoSuchReservierungException;
import org.control.exception.NoSuchUserOrPasswordException;
import org.model.entity.Benutzer;
import org.model.entity.Reservierung;
import org.services.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservierungDao {
    private static ReservierungDao instance;
    private DBConnection dbConnection = null;

    private ReservierungDao() throws DataBaseException {
        try {
            this.dbConnection = new DBConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ReservierungDao getInstance() throws DataBaseException {
        if (instance == null) {
            instance = new ReservierungDao();
        }

        return instance;
    }

    public void delete (int id) {
        String sql = "DELETE FROM carlook.reservierung AS b WHERE b.id_reservierung='" + id + "'";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = dbConnection.getPreparedStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try{
                if(preparedStatement != null) preparedStatement.close();
                this.dbConnection.closeConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public Reservierung getReservierung(int autoId, int endnutzerId) throws DataBaseException, NoSuchReservierungException {
        String sql = "SELECT * FROM carlook.reservierung AS b WHERE b.id_auto= '" + autoId + "' AND b.id_endnutzer= '" + endnutzerId + "'";
        PreparedStatement preparedStatement = null;
        Reservierung reservierung = null;
        boolean failed = false;

        try {
            //this.dbConnection.openConnection();
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reservierung = new Reservierung();
                reservierung.setResId(resultSet.getInt("id_reservierung"));
                reservierung.setEndId(resultSet.getInt("id_endnutzer"));
                reservierung.setAutoId(resultSet.getInt("id_auto"));


            } else {
                throw new NoSuchReservierungException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                this.dbConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if (failed) throw new DataBaseException("Beim Laden der Reservierung ist ein Fehler aufgetreten!");
        }

        return reservierung;
    }

    public Reservierung getReservierung(int resId) throws DataBaseException, NoSuchReservierungException {
        String sql = "SELECT * FROM carlook.reservierung AS b WHERE b.id_reservierung= '" + resId + "'";
        PreparedStatement preparedStatement = null;
        Reservierung reservierung = null;
        boolean failed = false;

        try {
            //this.dbConnection.openConnection();
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reservierung = new Reservierung();
                reservierung.setResId(resultSet.getInt("id_reservierung"));
                reservierung.setEndId(resultSet.getInt("id_endnutzer"));
                reservierung.setAutoId(resultSet.getInt("id_auto"));


            } else {
                throw new NoSuchReservierungException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                this.dbConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if (failed) throw new DataBaseException("Beim Laden der Reservierung ist ein Fehler aufgetreten!");
        }

        return reservierung;
    }


    // TBD


    public void createReservierung(Reservierung reservierung) throws DataBaseException {
        String sql = "INSERT INTO carlook.reservierung (id_endnutzer, id_auto) VALUES (?,?)";
        PreparedStatement preparedStatement = null;
        boolean failed = false;
        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);

            // Benutzer speichern
            preparedStatement.setInt(1, reservierung.getEndId());
            preparedStatement.setInt(2, reservierung.getAutoId());

            preparedStatement.executeUpdate();

            // Vergebene Reservierungsid ermitteln
            this.setLatestResId(reservierung);

            this.dbConnection.commit();
        }
        // Fehlerhandling
        catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        }

        // Ressourcen schließen
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                this.dbConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            // Wenn ein Fehler aufgetreten ist
            if (failed) throw new DataBaseException("Beim Speichern der Reservierung ist ein Fehler aufgetreten!");
        }
    }

    //Private Methoden

    private void setLatestResId(Reservierung reservierung) throws DataBaseException {
        Statement statement = null;
        boolean failed = false;
        try {
            statement = this.dbConnection.getStatement();
            // Vergebene Reservierungsid ermitteln
            ResultSet result = statement.executeQuery("SELECT MAX(b.id_reservierung) FROM production.reservierung as b");
            result.next();
            reservierung.setResId(result.getInt(1));
        }
        catch(SQLException e) {
            e.printStackTrace();
            failed = true;
        }
        finally {
            try {
                if(statement != null) statement.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if (failed) throw new DataBaseException("");
        }
    }
}
