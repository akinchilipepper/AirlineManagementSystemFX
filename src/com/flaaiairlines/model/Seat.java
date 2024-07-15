package com.flaaiairlines.model;

public class Seat {

    private int id;
    private String koltuknumarasi;
    private String koltukturu;
    private Flight flight;

    public Seat(int id, String koltuknumarasi, String koltukturu, Flight flight) {
        this.id = id;
        this.koltuknumarasi = koltuknumarasi;
        this.koltukturu = koltukturu;
        this.flight = flight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKoltuknumarasi() {
        return koltuknumarasi;
    }

    public void setKoltuknumarasi(String koltuknumarasi) {
        this.koltuknumarasi = koltuknumarasi;
    }

    public String getKoltukturu() {
        return koltukturu;
    }

    public void setKoltukturu(String koltukturu) {
        this.koltukturu = koltukturu;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}

