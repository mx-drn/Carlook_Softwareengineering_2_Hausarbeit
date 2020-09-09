package org.model.dao;

import org.control.exception.DataBaseException;
import org.control.exception.NoSuchAutoException;
import org.model.entity.Auto;
import org.services.db.DBConnection;
import org.services.db.QueryContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        return auto;
    }

    public Auto getAuto(int id) throws DataBaseException, NoSuchAutoException {
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

        return auto;
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

    public ArrayList<Auto> searchAuto(String[] request) {
        ArrayList<Auto> result = new ArrayList<>();
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

}
