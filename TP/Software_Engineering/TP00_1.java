import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.*;

class Reservation {
  private String roomNum;
  private String userName;
  private LocalDateTime checkIn;
  private int id;
  private LocalDateTime checkOut;

  private static int counter = 1; // To use for generating unique IDs

  // Constructor for create
  public Reservation() {

  }

  // Constructor for update
  public Reservation(String roomNum, String userName, LocalDateTime checkIn, LocalDateTime checkOut) {
    setRoomNum(roomNum);
    setUserName(userName);
    setCheckIn(checkIn);
    setCheckOut(checkOut);
    this.id = counter++;
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
    return name.matches(".*[aeiouAEIOU].*") && name.matches(".*[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ].*");
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

  @Override
  public String toString() {
    return "Reservation [ID= " + id + ", roomNum=" + roomNum + ", userName=" + userName + ", checkIn=" + checkIn
        + ", checkOut="
        + checkOut + "]";
  }

}

public class TP00_1 {
  private static ArrayList<Reservation> reservation = new ArrayList<>();
  private static Scanner scanner = new Scanner(System.in);
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  public static void main(String[] args) {
    // Test Case 1: Valid Reservation
    // try {
    //   Reservation res1 = new Reservation("F-209", "Meng Hour", LocalDateTime.of(2024, 11, 1, 0, 0, 0),
    //       LocalDateTime.of(2024, 11, 3, 0, 0, 0));
    //   reservation.add(res1);
    //   System.out.println("Reservation created for: " + res1.getUserName() + " in room " + res1.getRoomNum());
    // } catch (Exception e) {
    //   System.out.println("Error: " + e.getMessage());
    // }

    // Test Case 2: Invalid Room Number
    // try {
    //   Reservation res2 = new Reservation("209", "Jane Doe", LocalDateTime.of(2024, 12, 1, 0, 0, 0),
    //       LocalDateTime.of(2024, 12, 2, 0, 0, 0));
    //   System.out.println("Reservation created for: " + res2.getUserName() + " in room " + res2.getRoomNum());
    // } catch (Exception e) {
    //   System.out.println("Error: " + e.getMessage());
    // }

    // Test Case 3: Empty User Name
    // try {
    //   Reservation res3 = new Reservation("J-704", "", LocalDateTime.of(2024, 12, 1, 0, 0, 0),
    //       LocalDateTime.of(2024, 12, 2, 0, 0, 0));
    //   System.out.println("Reservation created for: " + res3.getUserName() + " in room " + res3.getRoomNum());
    // } catch (Exception e) {
    //   System.out.println("Error: " + e.getMessage());
    // }

    // Test Case 4: User Name with No Vowel
    // try {
    //   Reservation res4 = new Reservation("J-704", "Rhythm", LocalDateTime.of(2024, 12, 1, 0, 0, 0),
    //       LocalDateTime.of(2024, 12, 2, 0, 0, 0));
    //   System.out.println("Reservation created for: " + res4.getUserName() + " in room " + res4.getRoomNum());
    // } catch (Exception e) {
    //   System.out.println("Error: " + e.getMessage());
    // }

    // Test Case 5: Check-in Time in the Past
    // try {
    //   Reservation res5 = new Reservation("G-305", "Anna Lee", LocalDateTime.of(2022, 11, 1, 0, 0, 0),
    //       LocalDateTime.of(2024, 11, 3, 0, 0, 0));
    //   System.out.println("Reservation created for: " + res5.getUserName() + " in room " + res5.getRoomNum());
    // } catch (Exception e) {
    //   System.out.println("Error: " + e.getMessage());
    // }

    // Test Case 6: Check-out Time Less Than 1 Hour After Check-in
    // try {
    //   Reservation res6 = new Reservation("H-101", "Steve Black", LocalDateTime.of(2024, 11, 1, 10, 0, 0),
    //       LocalDateTime.of(2024, 11, 1, 10, 30, 0)); // Less than 1 hour difference
    //   System.out.println("Reservation created for: " + res6.getUserName() + " in room " + res6.getRoomNum());
    // } catch (Exception e) {
    //   System.out.println("Error: " + e.getMessage());
    // }

    // listReservation();

    // addNewReservation();

    // listReservation();

    // removeReservation();

    // listReservation();

    // updateReservation();

    // listReservation();

    // swapRooms();

    // listReservation();

    while (true) {
      menu();

      System.out.println("Choose number from 1-6:");
      int input = scanner.nextInt();
      scanner.nextLine();

      if (input == 1) {
        listReservation();
      } else if (input == 2) {
        addNewReservation();
      } else if (input == 3) {
        removeReservation();
      } else if (input == 4) {
        updateReservation();
      } else if (input == 5) {
        swapRooms();
      } else if (input == 6) {
        System.out.println("Exiting...");
        break;
      } else {
        System.out.println("Number must be from 1-6");
      }
    }
  }

  public static void menu(){
    System.out.println("\n*** Menu ***");
    System.out.println("1. List all reservations");
    System.out.println("2. Add new resevations");
    System.out.println("3. Cancel/Remove reservation");
    System.out.println("4. Update reservation");
    System.out.println("5. Swap room between two reservations");
    System.out.println("6. Exit");
  }

  public static void listReservation() {
    System.out.println("\nAll Reservation");
    if (reservation.isEmpty()) {
      System.out.println("No reservation found.");
    } else {
      for (Reservation res : reservation) {
        System.out.println(res);
      }
    }
  }

  public static void addNewReservation() {
    System.out.println("\n *** Add New Reservation ***");

    try {
      // Get room number
      System.out.println("Enter Room Number (format: Letter-###): ");
      String roomNum = scanner.nextLine();

      // Get user name
      System.out.println("Enter User Name: ");
      String userName = scanner.nextLine();

      // Get check-in time
      System.out.println("Enter Check-in Time (format: yyyy-MM-dd HH:mm)");
      String checkInStr = scanner.nextLine();
      LocalDateTime checkIn = LocalDateTime.parse(checkInStr, formatter);

      // Get check-out time
      System.out.println("Enter Check-out time (format: yyyy-MM-dd HH:mm)");
      String checkOutStr = scanner.nextLine();
      LocalDateTime checkOut = LocalDateTime.parse(checkOutStr, formatter);

      // Create the reservation
      Reservation newRes = new Reservation(roomNum, userName, checkIn, checkOut);
      reservation.add(newRes);
      System.out.println("Reservation addded for: " + newRes.getUserName() + " in room " + newRes.getRoomNum());

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void removeReservation(){
    System.out.println("\n*** Cancel/Remove Reservation *** ");
    try {
      // Get ID
      System.out.println("Enter Reservation ID to cancel:");
      int id = Integer.parseInt(scanner.nextLine());

      // Find and remove the reservation using the ID
      Reservation toRemove = null;
      for (Reservation res : reservation){
        if (res.getId() == id) {
          toRemove = res;
          break;
        }
      }

      if (toRemove != null) {
        reservation.remove(toRemove);
        System.out.println("Reservation with ID " + id + " has been removed.");
      } else {
        System.out.println("No reservation found with ID " + id);
      }

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void updateReservation(){
    System.out.println("\n*** Update Reservation ***");
    try {
      // Get ID
      System.out.println("Enter Reservation ID to update: ");
      int id = Integer.parseInt(scanner.nextLine());

      // Find reservation with the ID given
      Reservation toUpdate = null;
      for (Reservation res : reservation) {
        if (res.getId() == id) {
          toUpdate = res;
          break;
        }
      }

      if (toUpdate != null) {
        // Check the reservation has not started (check-in is in the future)
        if (toUpdate.getCheckIn().isAfter(LocalDateTime.now())) {
          // Allow user to update info
          System.out.println("Update reservation for: " + toUpdate.getUserName());

          // Get updated room number
          System.out.println("Enter new Room Number (format: Letter-### or press Enter to keep [" + toUpdate.getCheckIn() + "]: ");
          String roomNum = scanner.nextLine();
          if (!roomNum.isEmpty()) {
            toUpdate.setRoomNum(roomNum);
          }

          // Get updated user name
          System.out.println("Enter new User name or press Enter to keep [" + toUpdate.getUserName() + "]: ");
          String userName = scanner.nextLine();
          if (!userName.isEmpty()) {
            toUpdate.setUserName(userName);
          }

          // Get updated check-in time
          System.out.println("enter new Check-in or press Enter to keep [" + toUpdate.getCheckIn() + "]:");
          String checkInStr = scanner.nextLine();
          if (!checkInStr.isEmpty()) {
            LocalDateTime checkIn = LocalDateTime.parse(checkInStr,formatter);
            toUpdate.setCheckIn(checkIn);
          }

          // Get updated check-out time
          System.out.println("Enter new Check-out or press Enter to keep [" + toUpdate.getCheckOut() + "]:");
          String checkOutStr = scanner.nextLine();
          if (!checkOutStr.isEmpty()) {
            LocalDateTime checkOut = LocalDateTime.parse(checkOutStr, formatter);
            toUpdate.setCheckOut(checkOut);
          }

          System.out.println("Reservation updated: " + toUpdate);
        } else {
          System.out.println("Cannot update the reservation because it has already started.");
        }

      } else {
        System.out.println("No reservation found with ID " + id);
      }
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void swapRooms(){
    System.out.println("\n*** Swap Rooms Between Two Reservation");

    try {
      // Get the IDs of the two reservations
      System.out.println("Enter first reservation ID: ");
      int id1 = Integer.parseInt(scanner.nextLine());

      System.out.println("Enter second reservation ID: ");
      int id2 = Integer.parseInt(scanner.nextLine());

      // Find the reservations by ID
      Reservation res1 = null;
      Reservation res2 = null;

      for (Reservation res: reservation){
        if (res.getId() == id1) {
          res1 = res;
        } else if (res.getId() == id2) {
          res2 = res;
        }
      }

    
      // Check if both reservations have the same check-in and check-out times
      if (res1.getCheckIn().equals(res2.getCheckIn()) && res1.getCheckOut().equals(res2.getCheckOut())){
        // Swap room numbers
        String tempRoom = res1.getRoomNum();
        res1.setRoomNum(res2.getRoomNum());
        res2.setRoomNum(tempRoom);
        System.out.println("Rooms swapped between reservation "+ id1 + " and reservation " + id2);
      } else {
        System.out.println("Reservations do not have the same check-in and check-out times. Cannot swap rooms.");
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}