package org.model.entity;

public class Benutzer {
    private String rolle = null;
    private Integer id;
    private String email;
    private String passwort;

    public Benutzer () {}

    public Benutzer (String rolle, Integer id, String email, String passwort) {
        this.setRolle(rolle);
        this.setId(id);
        this.setEmail(email);
        this.setPasswort(passwort);
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
