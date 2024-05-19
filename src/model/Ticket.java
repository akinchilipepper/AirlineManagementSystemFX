package model;

public class Ticket {

    private int id;
    private String pnr;
    private Passenger passenger;
    private Flight flight;
    private Seat seat;

    public Ticket(int id, String pnr, Passenger passenger, Flight flight, Seat seat) {
        super();
        this.id = id;
        this.pnr = pnr;
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
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

    public Passenger getUser() {
        return passenger;
    }

    public void setUser(Passenger passenger) {
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
}

