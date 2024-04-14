package model;

public class Flight {

    int id;
    String hat;
    String ucak;
    String kalkisyeri;
    String varisyeri;
    String kalkisTarihi;
    String varisTarihi;
    String kalkisZamani;
    String varisZamani;
    String durum;
    String ucusNo;

    public Flight(int id, String hat, String ucak, String kalkisyeri, String varisyeri, String kalkisTarihi,
            String varisTarihi, String kalkisZamani, String varisZamani, String durum, String ucusNo) {
        super();
        this.id = id;
        this.hat = hat;
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

    public String getHat() {
        return hat;
    }

    public void setHat(String hat) {
        this.hat = hat;
    }

    public String getUcak() {
        return ucak;
    }

    public void setUcak(String ucak) {
        this.ucak = ucak;
    }

    public String getKalkisyeri() {
        return kalkisyeri;
    }

    public void setKalkisyeri(String kalkisyeri) {
        this.kalkisyeri = kalkisyeri;
    }

    public String getVarisyeri() {
        return varisyeri;
    }

    public void setVarisyeri(String varisyeri) {
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
