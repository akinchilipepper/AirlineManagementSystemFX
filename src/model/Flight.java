package model;

public class Flight {

    int id;
    Plane ucak;
    Airport kalkisyeri;
    Airport varisyeri;
    String kalkisTarihi;
    String varisTarihi;
    String kalkisZamani;
    String varisZamani;
    String durum;
    String ucusNo;

    public Flight(int id, Plane ucak, Airport kalkisyeri, Airport varisyeri, String kalkisTarihi,
            String varisTarihi, String kalkisZamani, String varisZamani, String durum, String ucusNo) {
        super();
        this.id = id;
        this.ucak = ucak;
        this.kalkisyeri = kalkisyeri;
        this.varisyeri = varisyeri;
        this.kalkisTarihi = kalkisTarihi;
        this.varisTarihi = varisTarihi;
        this.kalkisZamani = kalkisZamani;
        this.varisZamani = varisZamani;
        this.durum = durum;
        this.ucusNo = ucusNo;
    }

    public Flight() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plane getUcak() {
        return ucak;
    }

    public void setUcak(Plane ucak) {
        this.ucak = ucak;
    }

    public Airport getKalkisyeri() {
        return kalkisyeri;
    }

    public void setKalkisyeri(Airport kalkisyeri) {
        this.kalkisyeri = kalkisyeri;
    }

    public Airport getVarisyeri() {
        return varisyeri;
    }

    public void setVarisyeri(Airport varisyeri) {
        this.varisyeri = varisyeri;
    }

    public String getKalkisTarihi() {
        return kalkisTarihi;
    }

    public void setKalkisTarihi(String kalkisTarihi) {
        this.kalkisTarihi = kalkisTarihi;
    }

    public String getVarisTarihi() {
        return varisTarihi;
    }

    public void setVarisTarihi(String varisTarihi) {
        this.varisTarihi = varisTarihi;
    }

    public String getKalkisZamani() {
        return kalkisZamani;
    }

    public void setKalkisZamani(String kalkisZamani) {
        this.kalkisZamani = kalkisZamani;
    }

    public String getVarisZamani() {
        return varisZamani;
    }

    public void setVarisZamani(String varisZamani) {
        this.varisZamani = varisZamani;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getUcusNo() {
        return ucusNo;
    }

    public void setUcusNo(String ucusNo) {
        this.ucusNo = ucusNo;
    }
}
