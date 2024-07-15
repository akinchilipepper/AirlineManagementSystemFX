package com.flaaiairlines.model;

public class Aircraft {
	private int id;
	private String model;
	private int kapasite;
	
	public Aircraft(int id, String model, int kapasite) {
		this.id = id;
		this.model = model;
		this.kapasite = kapasite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getKapasite() {
		return kapasite;
	}

	public void setKapasite(int kapasite) {
		this.kapasite = kapasite;
	}
	
}
