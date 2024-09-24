package bookHotel.model;

public class NoSuchElementExp extends Exception {
 public NoSuchElementExp() {
	super("Client was not found");
}
 public NoSuchElementExp(String massege) {
	 super(massege);
 }

}
