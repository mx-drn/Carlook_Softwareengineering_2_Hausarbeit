package org.model.entity;

public class Auto {
    private int id;
    private String marke;
    private int baujahr;
    private String beschreibung;
    private int id_vertriebler;

    public Auto () {}

    public Auto(int id, String marke, int baujahr, String beschreibung, int id_vertriebler) {
        this.id = id;
        this.marke = marke;
        this.baujahr = baujahr;
        this.beschreibung = beschreibung;
        this.id_vertriebler = id_vertriebler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(int baujahr) {
        this.baujahr = baujahr;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getId_vertriebler() {
        return id_vertriebler;
    }

    public void setId_vertriebler(int id_vertriebler) {
        this.id_vertriebler = id_vertriebler;
    }
}
