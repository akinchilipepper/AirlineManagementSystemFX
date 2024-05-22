package model;

public class Ticket {

    private int id;
    private String pnr;
    private Passenger passenger;
    private Flight flight;
    private Seat seat;
    private double ucret;

    public Ticket(int id, String pnr, Passenger passenger, Flight flight, Seat seat, double ucret) {
        super();
        this.id = id;
        this.pnr = pnr;
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        this.ucret = ucret;
    }
    
    public Ticket() {
    	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

	public double getUcret() {
		return ucret;
	}

	public void setUcret(double ucret) {
		this.ucret = ucret;
	}  
}

