package org.services.db;

import org.control.exception.DataBaseException;

import java.sql.*;

public class DBConnection {
    // Lokale Datenbankverbindung
    private Connection connection = null;

    // Connectionstring zur PostgreSQL Datenbank
    private static final String url = "jdbc:postgresql://dumbo.inf.h-brs.de:5432/mduere2s";

    // Benutzer der Datenbank
    private static final String user = "mduere2s";

    // Passwort des Benutzers
    private static final String password = DBpwd.PASSWORT;

    public DBConnection() throws SQLException {
        openConnection();
    }
    /**
     * Öffnet eine neue Verbindung zur spezifizierten Datenbank.
     * @throws SQLException Bei einem Datenbankfehler
     * @return void
     */
    public void openConnection() throws SQLException {
        if(this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(url, user, password);
        }
    }
    /**
     * Erzeugt ein java.sql.Statement mit Hilfe des lokalen java.sql.Connection Obejekts.
     * @throws SQLException Bei einem Datenbankfehler
     * @return Gibt ein fertiges java.sql.Statement zurück.
     */
    public Statement getStatement() throws SQLException {
        return this.connection.createStatement();
    }
    /**
     * Erzeugt ein java.sql.PreparedStatement mit Hilfe des lokalen java.sql.Connection Obejekts.
     * @param sql Ein prepared SQL-Query.
     * @throws SQLException Bei einem Datenbankfehler
     * @return Gibt ein fertiges java.sql.PreparedStatement zurück.
     */
    public PreparedStatement getPreparedStatement(String sql, QueryContext context) throws SQLException {
        PreparedStatement s = null;
        if(context == QueryContext.TYPE_SCROLL_SENSITIVE) {
            s = this.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        else if(context == QueryContext.RETURN_GENERATES_KEYS) {
            s = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        else {
            s = this.connection.prepareStatement(sql);
        }
        return s;
    }

    /**
     * @param sql
     * @param values
     * @param types
     * @param permutationFactor
     * @param context
     * @return
     * @throws DataBaseException
     */
    public PreparedStatement buildStatement(String sql, Object[] values, Class[] types, int permutationFactor, QueryContext context) throws DataBaseException {
        if (values != null && types != null && values.length != types.length) throw new IllegalArgumentException("Length of values and types have to be equal");
        PreparedStatement statement = null;
        try {
            this.openConnection();
            statement = this.getPreparedStatement(sql, context);
            if (statement != null
                    && values != null && values.length > 0
                    && types != null && types.length > 0) {
                for(int i=0; i< Math.max(1, permutationFactor) ; i++){
                    for (int j=0; j < values.length; j++) {
                        Class type = types[j];
                        Object value = values[j];
                        int parameterIndex = i*values.length+j+1;
                        if(int.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type)) statement.setInt(parameterIndex, (int)value);
                        else if(String.class.isAssignableFrom(type)) statement.setString(parameterIndex, (String)value);
                        else if(double.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type)) statement.setDouble(parameterIndex, (double)value);
                        else if(boolean.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type)) statement.setBoolean(parameterIndex, (boolean)value);
                        else if(int.class.isAssignableFrom(type) ||Integer.class.isAssignableFrom(type)) statement.setInt(parameterIndex, (int)value);
                    }
                }


            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        this.openConnection();
        return this.connection.prepareStatement(sql);
    }
    /**
     * Stellt das automatische Commiten der lokalen java.sql.Connection ab.
     * <p>
     *  * setzt das autocommit der lokal java.sql.Connection auf false.
     *  * Nachdem Änderungen an der DB vorgenommen wurden, sind diese zu commiten
     * </p>
     * @throws SQLException Bei einem Datenbankfehler
     * @return void
     */
    public void setAutoCommitOff() throws SQLException {
        this.connection.setAutoCommit(false);
    }
    public void setAutoCommitOn() throws SQLException {
        this.connection.setAutoCommit(true);
    }
    public boolean getAutoCommit() throws SQLException {
        return this.connection.getAutoCommit();
    }
    /**
     * Commitet die Änderungen der lokalen java.sql.Connection
     * <p>
     *  * Das Commiten ist nur nötig, wenn zuvor autoCommit auf false gelegt wurde.
     * </p>
     * @throws SQLException Bei einem Datenbankfehler
     * @return void
     */
    public void commit() throws SQLException {
        this.connection.commit();
    }
    /**
     * Schließt die lokale java.sql.Connection
     * <p>
     *  * Diese Methode sollte aufgerufen werden, nachdem alle Änderungen vorgenommen wurden.
     *  * Da die lokale java.sql.Connection zur DB geschlossen wird, ist das Objekt danach unbrauchbar.
     * </p>
     * @throws SQLException Bei einem Datenbankfehler
     * @return void
     */
    public void closeConnection() throws SQLException {
        this.connection.close();
    }
    /**
     * Prüft, ob die lokale java.sql.Connection noch geöffnet ist
     * @throws SQLException Bei einem Datenbankfehler
     * @return Einen Wahrheitswert, ob die Verbindung geschlossen ist.
     */
    public boolean connectionIsClosed() throws SQLException {
        boolean closed = false;
        closed = this.connection.isClosed();
        return closed;
    }

}
