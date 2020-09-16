package org.model.dao;

import com.vaadin.ui.UI;
import org.control.exception.DataBaseException;
import org.control.exception.NoSuchAutoException;
import org.model.entity.Auto;
import org.services.db.DBConnection;
import org.services.db.QueryContext;
import org.ui.MainUI;

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

    public Auto getAuto(String marke, int baujahr, String beschreibung) throws DataBaseException, NoSuchAutoException {
        String sql = "SELECT * FROM carlook.auto AS a WHERE a.marke= '" + marke + "' AND a.baujahr= '" + baujahr + "' AND a.beschreibung= '" + beschreibung + "'";
        PreparedStatement preparedStatement = null;
        org.model.entity.Auto auto = null;
        boolean failed = false;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                auto = new org.model.entity.Auto();
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

        return auto;
    }

    public Auto getAuto(int id) throws DataBaseException, NoSuchAutoException {
        String sql = "SELECT * FROM carlook.auto AS a WHERE a.id_auto= '" + id + "'";
        PreparedStatement preparedStatement = null;
        org.model.entity.Auto auto = null;
        boolean failed = false;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                auto = new org.model.entity.Auto();
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
        return auto;
    }

    public void delete (int id) {
        String sql = "DELETE FROM carlook.auto AS b WHERE b.id_auto='" + id + "'";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
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

    public ArrayList<Auto> searchAuto(String[] request) {
        ArrayList<Auto> result = new ArrayList<>();
        if(request == null || request.length == 0) return result;
        String sql = "SELECT DISTINCT marke, baujahr, beschreibung FROM carlook.auto_suche " +
                "WHERE to_tsvector('german', COALESCE(marke,'') || ' ' || COALESCE(to_char(baujahr,'')) || ' ' || " +
                "COALESCE(beschreibung,'')) @@ to_tsquery('german', ?)";

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
                    Auto auto = new Auto();
                    auto.setMarke(resultSet.getString(1));
                    auto.setBaujahr(resultSet.getInt(2));
                    auto.setBeschreibung(resultSet.getString(3));
                    result.add(auto);
                }

                resultSet.beforeFirst();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    public void createAuto(Auto auto) throws DataBaseException {
        String sql = "INSERT INTO carlook.auto (marke, baujahr, beschreibung, id_vertriebler) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        boolean failed = false;
        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);

            // Benutzer speichern
            preparedStatement.setString(1, auto.getMarke());
            preparedStatement.setInt(2, auto.getBaujahr());
            preparedStatement.setString(3, auto.getBeschreibung());
            preparedStatement.setInt(4, ((MainUI) UI.getCurrent()).getBenutzer().getId());
            preparedStatement.executeUpdate();

            // Vergebene Benutzerid ermitteln
            this.setLastAutoId(auto);

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
            if (failed) throw new DataBaseException("Beim Speichern des Autos ist ein Fehler aufgetreten!");
        }
    }

    private void setLastAutoId(Auto auto) throws DataBaseException {
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
