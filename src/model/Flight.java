package model;

public class Flight {

    int id;
    Plane ucak;
    Airport kalkisYeri;
    Airport varisYeri;
    String kalkisTarihi;
    String varisTarihi;
    String kalkisZamani;
    String varisZamani;
    String durum;
    String ucusNo;
    int biletFiyati;

    public Flight(int id, Plane ucak, Airport kalkisYeri, Airport varisYeri, String kalkisTarihi,
            String varisTarihi, String kalkisZamani, String varisZamani, 
            String durum, String ucusNo, int biletFiyati) {
        this.id = id;
        this.ucak = ucak;
        this.kalkisYeri = kalkisYeri;
        this.varisYeri = varisYeri;
        this.kalkisTarihi = kalkisTarihi;
        this.varisTarihi = varisTarihi;
        this.kalkisZamani = kalkisZamani;
        this.varisZamani = varisZamani;
        this.durum = durum;
        this.ucusNo = ucusNo;
        this.biletFiyati = biletFiyati;
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

    public Airport getKalkisYeri() {
        return kalkisYeri;
    }

    public void setKalkisyeri(Airport kalkisYeri) {
        this.kalkisYeri = kalkisYeri;
    }

    public Airport getVarisYeri() {
        return varisYeri;
    }

    public void setVarisyeri(Airport varisYeri) {
        this.varisYeri = varisYeri;
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

	public int getBiletFiyati() {
		return biletFiyati;
	}

	public void setBiletFiyati(int biletFiyati) {
		this.biletFiyati = biletFiyati;
	}
}
