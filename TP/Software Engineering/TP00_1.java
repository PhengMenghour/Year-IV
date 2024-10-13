import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Reservation {
  private String roomNum;
  private String userName;
  private LocalDateTime checkIn;
  private int id;
  LocalDateTime checkOut;

  // Constructor for create
  public Reservation() {

  }

  // Constructor for update
  public Reservation(String roomNum, String userName, LocalDateTime checkIn, LocalDateTime checkOut) {
    setRoomNum(roomNum);
    setUserName(userName);
    setCheckIn(checkIn);
    setCheckOut(checkOut);
  }

  // Constructor for delete
  public Reservation(int id) {
    this.id = id;
  }

  private boolean validateRoom(String room) {
    Pattern pattern = Pattern.compile("^[A-Z]-\\d{3}$");
    Matcher matcher = pattern.matcher(room);
    return matcher.matches();
  }

  private boolean validateName(String name) {
    return name.matches("(?!).*[aeiou.*]") && name.matches("(?!).*[bcdfghjklmnpqrstvwxyz].*");
  }

  public String getRoomNum() {
    return roomNum;
  }

  public void setRoomNum(String roomNum) {
    if (roomNum == null || roomNum.isEmpty()) {
      throw new IllegalArgumentException("Room number must not be empty");
    }
    if (!validateRoom(roomNum)) {
      throw new IllegalArgumentException("Room number must follow the format Letter-###");
    }
    this.roomNum = roomNum;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    if (userName == null || userName.isEmpty()) {
      throw new IllegalArgumentException("User name must not be empty");
    }
    if (!validateName(userName)) {
      throw new IllegalArgumentException("User name must contain both vowels and consonants");
    }
    this.userName = userName;

  }

  public LocalDateTime getCheckIn() {
    return checkIn;
  }

  public void setCheckIn(LocalDateTime checkIn) {
    if (checkIn.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Check-in time must be in the future.");
    }
    this.checkIn = checkIn;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDateTime getCheckOut() {
    return checkOut;
  }

  public void setCheckOut(LocalDateTime checkOut) {
    if (checkIn == null) {
      throw new IllegalArgumentException("Check-in time must be set before check-out");
    }
    if (checkOut.isBefore(checkIn.plusHours(1))) {
      throw new IllegalArgumentException("Check out time must be atleast 1 hour after check in time");
    }
    this.checkOut = checkOut;
  }

}

public class TP00_1 {
  public static void main(String[] args) {
    // Test Case 1: Valid Reservation
    try {
      Reservation res1 = new Reservation("F-209", "John Doe", LocalDateTime.of(2024, 11, 1, 0, 0, 0),
          LocalDateTime.of(2024, 11, 3, 0, 0, 0));
      System.out.println("Reservation created for: " + res1.getUserName() + " in room " + res1.getRoomNum());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    // Test Case 2: Invalid Room Number
    try {
      Reservation res2 = new Reservation("209", "Jane Doe", LocalDateTime.of(2024, 12, 1, 0, 0, 0),
          LocalDateTime.of(2024, 12, 2, 0, 0, 0));
      System.out.println("Reservation created for: " + res2.getUserName() + " in room " + res2.getRoomNum());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    // Test Case 3: Empty User Name
    try {
      Reservation res3 = new Reservation("J-704", "", LocalDateTime.of(2024, 12, 1, 0, 0, 0),
          LocalDateTime.of(2024, 12, 2, 0, 0, 0));
      System.out.println("Reservation created for: " + res3.getUserName() + " in room " + res3.getRoomNum());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    // Test Case 4: User Name with No Vowel
    try {
      Reservation res4 = new Reservation("J-704", "Rhythm", LocalDateTime.of(2024, 12, 1, 0, 0, 0),
          LocalDateTime.of(2024, 12, 2, 0, 0, 0));
      System.out.println("Reservation created for: " + res4.getUserName() + " in room " + res4.getRoomNum());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    // Test Case 5: Check-in Time in the Past
    try {
      Reservation res5 = new Reservation("G-305", "Anna Lee", LocalDateTime.of(2022, 11, 1, 0, 0, 0),
          LocalDateTime.of(2024, 11, 3, 0, 0, 0));
      System.out.println("Reservation created for: " + res5.getUserName() + " in room " + res5.getRoomNum());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    // Test Case 6: Check-out Time Less Than 1 Hour After Check-in
    try {
      Reservation res6 = new Reservation("H-101", "Steve Black", LocalDateTime.of(2024, 11, 1, 10, 0, 0),
          LocalDateTime.of(2024, 11, 1, 10, 30, 0)); // Less than 1 hour difference
      System.out.println("Reservation created for: " + res6.getUserName() + " in room " + res6.getRoomNum());
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}