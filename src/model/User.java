package model;

public class User {

    private int id;
    private String ad;
    private String soyad;
    private String telno;
    private String e_posta;
    private String cinsiyet;
    private String dogumtarihi;
    private String parola;

    public User(int id, String ad, String soyad, String telno, String e_posta, String cinsiyet, String dogumtarihi, String parola) {
        this.ad = ad;
        this.soyad = soyad;
        this.telno = telno;
        this.e_posta = e_posta;
        this.cinsiyet = cinsiyet;
        this.dogumtarihi = dogumtarihi;
        this.parola = parola;
        this.id = id;
    }

    public User() {

    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getE_posta() {
        return e_posta;
    }

    public void setE_posta(String e_posta) {
        this.e_posta = e_posta;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getDogumtarihi() {
        return dogumtarihi;
    }

    public void setDogumtarihi(String dogumtarihi) {
        this.dogumtarihi = dogumtarihi;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
