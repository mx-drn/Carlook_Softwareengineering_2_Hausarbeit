package org.model.dao;

import org.control.exception.DataBaseException;
import org.control.exception.NoSuchUserOrPasswordException;
import org.model.entity.Benutzer;
import org.model.entity.Endnutzer;
import org.model.entity.Vertriebler;
import org.services.db.DBConnection;
import org.services.util.Rolle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void delete (int id) {
        String sql = "DELETE FROM carlook.benutzer AS b WHERE b.id_benutzer='" + id + "'";
        PreparedStatement preparedStatement = null;
        Boolean isEndnutzer = null;

        try {
            Benutzer benutzer = getBenutzer(id);
            if(benutzer.getRolle().equals("Endnutzer")) isEndnutzer = true;
        } catch (NoSuchUserOrPasswordException | DataBaseException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = dbConnection.getPreparedStatement(sql);
            if(isEndnutzer) {
                preparedStatement = dbConnection.getPreparedStatement("DELETE FROM carlook.endnutzer AS b WHERE b.id_endnutezr='" + id + "'");
            }else{
                preparedStatement = dbConnection.getPreparedStatement("DELETE FROM carlook.vertriebler AS b WHERE b.id_vbertriebler='" + id + "'");
            }
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

    public Benutzer getBenutzer(int id) throws NoSuchUserOrPasswordException, DataBaseException {
      String sql = "SELECT * FROM carlook.benutzer AS b WHERE b.id_benutzer= '" + id + "'";
        PreparedStatement preparedStatement = null;
        Benutzer benutzer = null;
        boolean failed = false;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
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

    public Benutzer getBenutzer(String email, String passwort) throws DataBaseException, NoSuchUserOrPasswordException {
        String sql = "SELECT * FROM carlook.benutzer AS b WHERE b.email= '" + email + "' AND b.passwort= '" + passwort + "'";
        PreparedStatement preparedStatement = null;
        Benutzer benutzer = null;
        boolean failed = false;

        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
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
                this.dbConnection.openConnection();
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

    public void createBenutzer(Benutzer benutzer) throws DataBaseException {
        String sql = "INSERT INTO carlook.benutzer (rolle, email, passwort) VALUES (?,?,?)";
        PreparedStatement preparedStatement = null;
        boolean failed = false;
        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);

            // Benutzer speichern
            preparedStatement.setString(1, benutzer.getRolle());
            preparedStatement.setString(2, benutzer.getEmail());
            preparedStatement.setString(3, benutzer.getPasswort());
            preparedStatement.executeUpdate();

            // Vergebene Benutzerid ermitteln
            this.setLatestUserId(benutzer);

            // Endnutzer o. Vertriebler speichern

            if(benutzer.getRolle().equals(Rolle.ENDNUTZER)) {
                this.createEndnutzer((Endnutzer) benutzer);
            }else if (benutzer.getRolle().equals(Rolle.VERTRIEBLER)) {
                this.createVertriebler((Vertriebler) benutzer);
            }

            //this.dbConnection.commit();
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
                this.dbConnection.openConnection();
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

    //Private Methoden

    private void createVertriebler(Vertriebler vertriebler) throws DataBaseException {
        String sql = "INSERT INTO carlook.vertriebler (id_vertriebler) VALUES (?)";
        PreparedStatement preparedStatement = null;
        boolean failed = false;
        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            preparedStatement.setInt(1, vertriebler.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            failed = true;
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if (failed) throw new DataBaseException("");
        }
    }

    private void createEndnutzer(Endnutzer endnutzer) throws DataBaseException {
        String sql = "INSERT INTO carlook.endnutzer (id_endnutzer) VALUES (?)";
        PreparedStatement preparedStatement = null;
        boolean failed = false;
        try {
            preparedStatement = this.dbConnection.getPreparedStatement(sql);
            preparedStatement.setInt(1, endnutzer.getId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            failed = true;
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if (failed) throw new DataBaseException("");
        }
    }

    private Benutzer getVertriebler(Benutzer benutzer) throws DataBaseException, NoSuchUserOrPasswordException  {
        Statement statement = null;
        boolean failed = false;
        Vertriebler vertriebler = null;

        try{
            statement = this.dbConnection.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM carlook.vertriebler AS v WHERE v.id_vertriebler='" + benutzer.getId() + "'");

            if(resultSet.next()) {
                vertriebler = new Vertriebler();
                vertriebler.setRolle(benutzer.getRolle());
                vertriebler.setEmail(benutzer.getEmail());
                vertriebler.setPasswort(benutzer.getPasswort());
                vertriebler.setId(benutzer.getId());
            }
            //Kein Vertriebler mit der BenutzerID gefunden
            else throw new NoSuchUserOrPasswordException();

        }catch (SQLException ex){

        }finally{
            try {
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if(failed) throw new DataBaseException("");
        }

        return vertriebler;
    }

    private Benutzer getEndnutzer(Benutzer benutzer) throws DataBaseException, NoSuchUserOrPasswordException {
        Statement statement = null;
        boolean failed = false;
        Endnutzer endnutzer = null;

        try{
            statement = this.dbConnection.getStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM carlook.endnutzer AS v WHERE v.id_endnutzer='" + benutzer.getId() + "'");

            if(resultSet.next()) {
                endnutzer = new Endnutzer();
                endnutzer.setRolle(benutzer.getRolle());
                endnutzer.setEmail(benutzer.getEmail());
                endnutzer.setPasswort(benutzer.getPasswort());
                endnutzer.setId(benutzer.getId());
            }
            //Kein Endnutzer mit der BenutzerID gefunden
            else {
                throw new NoSuchUserOrPasswordException();
            }

        }catch (SQLException ex){

        }finally{
            try {
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                failed = true;
            }
            if(failed) throw new DataBaseException("");
        }

        return endnutzer;
    }

    private void setLatestUserId(Benutzer benutzer) throws DataBaseException {
        Statement statement = null;
        boolean failed = false;
        try {
            statement = this.dbConnection.getStatement();
            // Vergebene Benutzerid ermitteln
            ResultSet result = statement.executeQuery("SELECT MAX(b.id_benutzer) FROM carlook.benutzer as b");
            result.next();
            benutzer.setId(result.getInt(1));
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
