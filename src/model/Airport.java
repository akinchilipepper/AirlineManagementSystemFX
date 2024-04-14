package model;

public class Airport {

    private int id;
    private String sehir;
    private String havaalani;
    private String iatakodu;
    private String kullanim;

    public Airport(int id, String sehir, String havaalani, String iatakodu, String kullanim) {
        this.id = id;
        this.sehir = sehir;
        this.havaalani = havaalani;
        this.iatakodu = iatakodu;
        this.kullanim = kullanim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public String getHavaalani() {
        return havaalani;
    }

    public void setHavaalani(String havaalani) {
        this.havaalani = havaalani;
    }

    public String getIatakodu() {
        return iatakodu;
    }

    public void setIatakodu(String iatakodu) {
        this.iatakodu = iatakodu;
    }

    public String getKullanim() {
        return kullanim;
    }

    public void setKullanim(String kullanim) {
        this.kullanim = kullanim;
    }

}
