package org.model.dao;

import org.control.exception.DataBaseException;
import org.control.exception.NoSuchAutoException;
import org.model.dto.AutoDTO;
import org.model.entity.Auto;
import org.model.entity.Benutzer;
import org.model.entity.Endnutzer;
import org.model.entity.Vertriebler;
import org.services.db.DBConnection;
import org.services.db.QueryContext;
import org.services.util.Rolle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AutoDao {
    private static AutoDao instance;
    private DBConnection dbConnection;

    private AutoDao() {
        try {
            this.dbConnection = new DBConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static AutoDao getInstance() throws DataBaseException {
        if (instance == null) {
            instance = new AutoDao();
        }
        return instance;
    }

    public AutoDTO getAuto(String marke, int baujahr, String beschreibung) throws DataBaseException, NoSuchAutoException {
        String sql = "SELECT * FROM carlook.auto AS a WHERE a.marke= '" + marke + "' AND a.baujahr= '" + baujahr + "' AND a.beschreibung= '" + beschreibung + "'";
        PreparedStatement preparedStatement = null;
        Auto auto = null;
        boolean failed = false;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                auto = new Auto();
                auto.setId(resultSet.getInt("id_auto"));
                auto.setMarke(resultSet.getString("marke"));
                auto.setBaujahr(resultSet.getInt("baujahr"));
                auto.setBeschreibung(resultSet.getString("beschreibung"));
                auto.setId_vertriebler(resultSet.getInt("id_vertriebler"));

            }else{
                throw new NoSuchAutoException();
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
            if(failed) throw new DataBaseException("Beim Laden der Autos ist ein Fehler aufgetreten!");
        }
        AutoDTO autoDTO = new AutoDTO(auto);

        return autoDTO;
    }

    public AutoDTO getAuto(int id) throws DataBaseException, NoSuchAutoException {
        String sql = "SELECT * FROM carlook.auto AS a WHERE a.id_auto= '" + id + "'";
        PreparedStatement preparedStatement = null;
        Auto auto = null;
        boolean failed = false;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                auto = new Auto();
                auto.setId(resultSet.getInt("id_auto"));
                auto.setMarke(resultSet.getString("marke"));
                auto.setBaujahr(resultSet.getInt("baujahr"));
                auto.setBeschreibung(resultSet.getString("beschreibung"));
                auto.setId_vertriebler(resultSet.getInt("id_vertriebler"));

            }else{
                throw new NoSuchAutoException();
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
            if(failed) throw new DataBaseException("Beim Laden der Autos ist ein Fehler aufgetreten!");
        }
        AutoDTO autoDTO = new AutoDTO(auto);

        return autoDTO;
    }

    public void delete (int id) {
        String sql = "DELETE FROM carlook.auto AS b WHERE b.id_auto='" + id + "'";
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

    public ArrayList<AutoDTO> searchAuto(String[] request) {
        ArrayList<AutoDTO> result = new ArrayList<>();
        if(request == null || request.length == 0) return result;
        String sql = "SELECT DISTINCT marke, baujahr, beschreibung FROM carlook.suche_auto " +
                "WHERE to_tsvector('german', COALESCE(marke,'') || ' ' || COALESCE(baujahr,'') || ' ' || " +
                "COALESCE(name,'')) @@ to_tsquery('german', ?)";

        String[] values = {request[0].equals("") ? "" : request[0] + ":*"};
        for (int i=1; i<request.length; i++) {
            values[0] += request[i].equals("") ? "" : " & " + request[i] + ":*";
        }

        Class[] types = { String.class };
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = dbConnection.buildStatement(sql, values, types, 1, QueryContext.TYPE_SCROLL_SENSITIVE);

            if (preparedStatement != null) {
                preparedStatement.closeOnCompletion();
                resultSet = preparedStatement.executeQuery();


                while(resultSet.next()) {
                    AutoDTO autoDTO = new AutoDTO();
                    autoDTO.setMarke(resultSet.getString(1));
                    autoDTO.setBaujahr(resultSet.getInt(2));
                    autoDTO.setBeschreibung(resultSet.getString(3));
                    result.add(autoDTO);
                }

                resultSet.beforeFirst();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    public void createAuto(AutoDTO autoDTO) throws DataBaseException {
        Auto auto = new Auto(autoDTO);
        String sql = "INSERT INTO carlook.auto (marke, baujahr, beschreibung) VALUES (?,?,?)";
        PreparedStatement preparedStatement = null;
        boolean failed = false;
        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);

            // Benutzer speichern
            preparedStatement.setString(1, auto.getMarke());
            preparedStatement.setInt(2, auto.getBaujahr());
            preparedStatement.setString(3, auto.getBeschreibung());
            preparedStatement.executeUpdate();

            // Vergebene Benutzerid ermitteln
            this.setLatestUserId(auto);

        }
        // Fehlerhandling
        catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        }

        // Ressourcen schließen
        finally {
            try {
                if(preparedStatement != null) preparedStatement.close();
                this.dbConnection.closeConnection();
            }
            catch(SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            // Wenn ein Fehler aufgetreten ist
            if (failed) throw new DataBaseException("Beim Speichern des Benutzers ist ein Fehler aufgetreten!");
        }
    }

    private void setLatestUserId(Auto auto) throws DataBaseException {
        Statement statement = null;
        boolean failed = false;
        try {
            statement = this.dbConnection.getStatement();
            // Vergebene Benutzerid ermitteln
            ResultSet result = statement.executeQuery("SELECT MAX(a.id_auto) FROM carlook.auto as a");
            result.next();
            auto.setId(result.getInt(1));
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

    public void closeConnection() {
        try {
            this.dbConnection.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
