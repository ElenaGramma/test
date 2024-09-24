package bookHotel.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import bookHotel.entities.*;

public class BookHotel {
	private Map<String, Room> allRooms = new HashMap<>();// Ключ - название комнаты
	private Map<Room, Integer> roomsInventory = new HashMap<>();// Ключ - комната
	private TreeMap<LocalDate, Map<Room, Integer>> availability = new TreeMap<>();// Ключ - дата
	private Map<String, Client> clients = new HashMap<>();// Ключ - ID клиента
	private Map<String, Rental> rentals = new HashMap<>();// Ключ - ID клиента

	public boolean addRoomToRooms(Room room, int quantity) {
		String roomName = room.getRoomName();
		boolean added = allRooms.putIfAbsent(roomName, room) == null;
		if (added) {
			roomsInventory.put(room, quantity);
			LocalDate date = LocalDate.now();
			LocalDate end = date.plusMonths(3);
//			while (!date.isAfter(end)) {
//				
//						});
//				Map<Room, Integer> roomAv = availability.computeIfAbsent(date, d -> new HashMap<Room, Integer>());
//				roomAv.compute(room, new BiFunction<Room, Integer, Integer>() {
//
//					@Override
//					public Integer apply(Room t, Integer u) {
//						return quantity;
//					}
//				});
//				roomAv.compute(room, (r, count) -> quantity);
//				date = date.plusDays(1);
//			}
			date.datesUntil(end.plusDays(1)).forEach(currentDate->availability.computeIfAbsent(date, d -> new HashMap<>())
			.compute(room, (r, count) -> quantity));
		}
		return added;
	}

	public boolean removeRoomfromAllRooms(String roomName) {
		if (allRooms.containsKey(roomName))
			return false;
		Room roomToRemove = allRooms.remove(roomName);
		if (roomToRemove != null) {
			LocalDate dateofRemove = LocalDate.now();
//			for (LocalDate date : availability.keySet()) {
//				if (date.isEqual(dateofRemove) || date.isAfter(dateofRemove)) {
//					Map<Room, Integer> availableRooms = availability.get(date);
//					if (availableRooms.containsKey(roomToRemove))
//						availableRooms.remove(roomToRemove);
//				}
			availability.entrySet().stream().filter(e->e.getKey().isEqual(dateofRemove) || e.getKey().isAfter(dateofRemove))
			.forEach(e->e.getValue().remove(roomToRemove));
				roomsInventory.remove(dateofRemove);
				return false;
			}
		
		return false;
	}

	public boolean addRoomToRoomsInentory(String roomName, int numberOfRooms) {
		if (roomName == null || numberOfRooms <= 0 || roomName.isBlank())
			return false;
		Room roomToAdd = allRooms.get(roomName);
		if (roomToAdd == null)
			return false;
		int currentNumber = roomsInventory.getOrDefault(roomToAdd, 0);
		int newNumberOfRooms = currentNumber + numberOfRooms;
		roomsInventory.put(roomToAdd, newNumberOfRooms);
		LocalDate start = LocalDate.now();
		LocalDate end = start.plusMonths(3);
		boolean res = addRoomsToAvailability(start, end, newNumberOfRooms, roomToAdd);
		return res;
	}

	public boolean addRoomsToAvailability(LocalDate start, LocalDate end, int numbersrooms, Room room) {

		while (!start.isAfter(end)) {
			
			Map<Room, Integer> roomAv = availability.computeIfAbsent(start, x -> new HashMap<Room, Integer>());
		
			roomAv.compute(room, (t, count) -> count==null?numbersrooms:count+numbersrooms);
			start = start.plusDays(1);
		}

		return true;
	}

	public boolean addClient(Client client) {
		if (client == null)
			return false;
		String clientId = client.getClientId();
		if (clientId == null || clientId.isBlank() || clients.containsKey(clientId))
			return false;
		return clients.put(clientId, client) == null;
	}

	public boolean rentRoom(String rentalId, String roomName, LocalDate startDate, int numberOfDays, int numberOfRooms,
			String clientId) throws NoSuchElementExp {
		Room r = allRooms.get(roomName);
		if (r == null)
			return false;
		if (!clients.containsKey(clientId))
			throw new NoSuchElementExp();

		boolean isAvalaibale = areRoomsAvailable(roomName, startDate, numberOfDays, numberOfRooms);
		if (isAvalaibale) {
			rentals.put(rentalId, new Rental(rentalId, roomName, startDate, numberOfDays, numberOfRooms, clientId));
			updateRoomAvalaibale(roomName, startDate, numberOfDays, numberOfRooms, false);
			return true;
		}
		return false;
	}

	private boolean updateRoomAvalaibale(String roomName, LocalDate startDate, int numberOfDays, int numberOfRooms,
			boolean status) {
		Room room = allRooms.get(roomName);
		if (room == null)
			return false;
		LocalDate curDate = startDate;
		LocalDate endDate = startDate.plusDays(numberOfDays - 1);
		while (!curDate.isAfter(endDate)) {
//			availability.compute(curDate, new BiFunction<LocalDate, Map<Room, Integer>, Map<Room, Integer>>() {
//
//				@Override
//				public Map<Room, Integer> apply(LocalDate t, Map<Room, Integer> u) {
//					if (u == null)
//						u = new HashMap<Room, Integer>();
//					Integer curCount = u.getOrDefault(room, 0);
//					if (status)
//						u.put(room, curCount + numberOfRooms);
////					else if (curCount < numberOfRooms)
////						return u;
//					 if (curCount - numberOfRooms == 0)
//						u.remove(room);
//					else
//						u.put(room, curCount - numberOfRooms);
//					return u;
//				}
//
//			});
			availability.compute(curDate, (date, u) -> {
				if (u == null)
					u = new HashMap<>();
				Integer curCount = u.getOrDefault(room, 0);
				if (status)
					u.put(room, curCount + numberOfRooms);
				else if (curCount < numberOfRooms)
					return u;
				if (curCount - numberOfRooms == 0)
					u.remove(room);
				else
					u.put(room, curCount - numberOfRooms);
				return u;
			});
			curDate = curDate.plusDays(1);

		}
		return true;

	}

	private boolean areRoomsAvailable(String roomName, LocalDate startDate, int numberOfDays, int numberOfRooms) {
		LocalDate curDate = startDate;
		LocalDate endDate = startDate.plusDays(numberOfDays - 1);

		Room room = allRooms.get(roomName);
		while (curDate.isAfter(endDate)) {
			Map<Room, Integer> temp = availability.get(curDate);
			if (temp == null || temp.containsKey(room))
				return false;
			int avRooms = temp.get(room);
			if (avRooms < numberOfRooms)
				return false;
			curDate = curDate.plusDays(1);
		}
		return true;
	}
}
