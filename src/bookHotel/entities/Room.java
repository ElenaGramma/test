package bookHotel.entities;

public class Room {
	private String roomName;
	private double rentalCostPerDay;
	
	public Room(String roomName, double rentalCostPerDay) {
		super();
		this.roomName = roomName;
		this.rentalCostPerDay = rentalCostPerDay;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public double getRentalCostPerDay() {
		return rentalCostPerDay;
	}

	public void setRentalCostPerDay(double rentalCostPerDay) {
		this.rentalCostPerDay = rentalCostPerDay;
	}

	@Override
	public String toString() {
		return "Room [roomName=" + roomName + ", rentalCostPerDay=" + rentalCostPerDay + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(rentalCostPerDay);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Room))
			return false;
		Room other = (Room) obj;
		if (Double.doubleToLongBits(rentalCostPerDay) != Double.doubleToLongBits(other.rentalCostPerDay))
			return false;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		return true;
	}
	
	

}
