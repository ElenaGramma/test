package bookHotel.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bookHotel.entities.*;
import bookHotel.entities.Room;
import bookHotel.model.BookHotel;
import bookHotel.model.NoSuchElementExp;


	class BookHotelTest {

		private BookHotel bookHotel;

	    @BeforeEach
	    public void setUp() {
	        bookHotel = new BookHotel();
	        // Заполняем объект данными для тестов
	        Room room1 = new Room("Room1", 100.0);
	        Room room2 = new Room("Room2", 150.0);
	        bookHotel.addRoomToRooms(room1, 5);
	        bookHotel.addRoomToRooms(room2, 10);
	        Client client = new Client("Client1", "John Doe", "john@example.com", LocalDate.of(1990, 5, 15));
	        bookHotel.addClient(client);
	    }

	    @Test
	    public void testAddRoomToAllRooms() {
	        assertTrue(bookHotel.addRoomToRooms(new Room("Room3", 120.0), 8));
	        assertFalse(bookHotel.addRoomToRooms(new Room("Room1", 100.0), 7));
	    }

	    @Test
	    public void testRemoveRoomFromAllRooms() {
	        assertTrue(bookHotel.removeRoomfromAllRooms("Room2"));
	        assertFalse(bookHotel.removeRoomfromAllRooms("Room3"));
	    }

	    @Test
	    public void testAddRoomToRoomsInventory() {
	        assertTrue(bookHotel.addRoomToRoomsInentory("Room1", 3));
	        assertFalse(bookHotel.addRoomToRoomsInentory("Room3", 2));
	    }

	    @Test
	    public void testAddClient() {
	        assertTrue(bookHotel.addClient(new Client("Client2", "Alice Smith", "alice@example.com", LocalDate.of(1985, 8, 20))));
	        assertFalse(bookHotel.addClient(new Client("Client1", "Bob Johnson", "bob@example.com", LocalDate.of(1995, 3, 10))));
	    }

	    @Test
	    public void testRentRoom() throws NoSuchElementExp {
	        try {
	        	
				assertTrue(bookHotel.rentRoom("Rental1", "Room1", LocalDate.now(), 3, 2, "Client1"));
			} catch (NoSuchElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
	        	assertTrue(bookHotel.addClient(new Client("Client2", "Alice Smith", "alice@example.com", LocalDate.of(1985, 8, 20))));
				assertFalse(bookHotel.rentRoom("Rental2", "Room1", LocalDate.now(), 3, 10, "Client2"));
			} catch (NoSuchElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
