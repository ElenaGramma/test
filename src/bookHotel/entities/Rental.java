package bookHotel.entities;

import java.time.LocalDate;

public class Rental {

	private String rentalId;
	private String roomName;
	private LocalDate startDate ;
	private int numberOfDays;
	private int numberOfRooms;
	private String clientId;
	private boolean status;
	public Rental(String rentalId, String roomName, LocalDate startDate, int numberOfDays, int numberOfRooms,
			String clientId) {
		super();
		this.rentalId = rentalId;
		this.roomName = roomName;
		this.startDate = startDate;
		this.numberOfDays = numberOfDays;
		this.numberOfRooms = numberOfRooms;
		this.clientId = clientId;
		this.status = true;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public int getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getRentalId() {
		return rentalId;
	}
	public String getClientId() {
		return clientId;
	}
	
	
	
}
