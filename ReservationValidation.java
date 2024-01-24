
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReservationValidation {
  static List<Boat> boats;
  public static PrintStream printingFile;

  public static void main(String [] args) {
    Scanner keyboardInput = new Scanner(System.in);
    boolean dayValid = false;
    boolean monthValid = false;
    boolean yearValid = false;
    boolean hourValid = false;
    boolean minuteValid = false;
    boolean nameValid = false;
    boolean emailValid = false;
    boolean numberValid = false;

    int day = 0;
    int month = 0;
    int year = 0;
    int daysInAMonth = 0;
    int recentYear = 2024;
    int hour = 0;
    int minute = 0;
    int boatChoice = 0;
    int nameCountMotorBoat = 0;
    int nameCountRowingBoat = 0;
    String name = "";
    String email;
    String number;

    boats = new ArrayList<Boat>();
    RowingBoat rowingBoat;
    MotorBoat motorBoat;

    do {
      try {
        System.out.println("Enter the month (MM):");
        month = keyboardInput.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("You must input a number!");
        keyboardInput.nextLine();
      }
      if (month > 0 && month <= 12) {
        switch (month) {
          case 1, 3, 5, 7, 8, 10, 12 -> {
            daysInAMonth = 31;
            monthValid = true;
          }
          case 2 -> {
            daysInAMonth = 28;
            monthValid = true;
          }
          case 4, 6, 9, 11 -> {
            daysInAMonth = 30;
            monthValid = true;
          }
          default -> {
          }
        }
      }
    } while (!monthValid);
    do {
      try {
        System.out.println("Enter the day (DD):");
        day = keyboardInput.nextInt();
        if (day <= daysInAMonth && day > 0) {
          dayValid = true;
        }
      } catch (InputMismatchException e) {
        System.out.println("You must input a number!");
        keyboardInput.nextLine();
      }
    } while (!dayValid);

    do {
      try {
        System.out.println("Enter the year (YYYY):");
        year = keyboardInput.nextInt();
        keyboardInput.nextLine();
        if (year >= recentYear) {
          yearValid = true;
        }
      } catch (InputMismatchException e) {
        System.out.println("You must input a number!");
        keyboardInput.nextLine();
      }
    } while (!yearValid);

    do {
      try {
        System.out.println("Enter the hour (HH:mm)");
        hour = keyboardInput.nextInt();
        if (hour <= 23) {
          hourValid = true;
        }
      } catch (InputMismatchException e) {
        System.out.println("You must input a number");
        keyboardInput.nextLine();
      }
    } while (!hourValid);
    do {
      try {
        System.out.println("Enter the minute (hh:MM)");
        minute = keyboardInput.nextInt();
        if (minute < 60 && minute % 15 == 0) {
          minuteValid = true;
        }
      } catch (InputMismatchException e) {
        System.out.println("You must input a number");
        keyboardInput.nextLine();
      }
    } while (!minuteValid);

    do {
      System.out.println("Enter your name");
      name = keyboardInput.nextLine();
      if (!name.isEmpty()) {
        nameValid = true;
      } else {
        System.out.println("You must enter your name");
      }
    } while (!nameValid);

    do {
      System.out.println("Enter your Email");
      email = keyboardInput.nextLine();
      if (email != null) {
        Pattern mailPattern = Pattern.compile("\\S+@\\S+(\\.ch|\\.com)");
        Matcher matcher = mailPattern.matcher(email);

        if (matcher.matches()) {
          emailValid = true;
        } else {
          System.out.println("Email is false");
        }
      } else {
        System.out.println("You need to put your email");
      }
    } while (!emailValid);

    do{
      System.out.println("Enter your Number");
      do {
        number = keyboardInput.nextLine();
      }while (number.length() < 12);
      String withoutSpaces = number;
      if (number.contains(" ")){
        withoutSpaces = number.replace(" ", "");
      }
      String shortenNumber = withoutSpaces.substring(0,12);
      if (shortenNumber.matches("\\+\\d+")) {
        numberValid = true;
      }
    } while (!numberValid);

    String date = String.format("%02d.%02d.%04d", day, month, year);
    String time = String.format("%02d:%02d", hour, minute);

    String directoryPath = "C:\\dev\\Bootsverleih\\Reservation\\";
    String filePath = directoryPath + name + ".txt";

    try {

      File file = new File(filePath);

      if (file.exists()){
        System.out.println("File (" + filePath + ") already exists. Do you want to overwrite this file? (y/n)");
        String input = keyboardInput.nextLine().trim().toLowerCase();

        if (!input.equals("y")) {
          System.out.println("File not overwritten. Exiting.");
          return;
        }
      }

      PrintStream console = System.out;

      // ---
      printingFile = new PrintStream(new FileOutputStream(filePath), true );
      System.setOut(printingFile);

      System.out.println();
      System.out.println("Name: " + name);
      System.out.println("Email: " + email);
      System.out.println("Time: " + time);
      System.out.println("Date: " + date);
      System.out.println("Number: " + number);
      System.out.println();

//      printingFile.close();

      System.setOut(console);

      System.out.println("File created");
      System.out.println(filePath);


      boolean reservationOver = false;
      do {
        boatChoice = 0;
        try {
          do {
            System.setOut(console);
            System.out.println();
            System.out.println("Press 1 for motorboat");
            System.out.println("Press 2 for rowing boat");
            boatChoice = keyboardInput.nextInt();
            if (keyboardInput.hasNextLine()) {
              keyboardInput.nextLine();
            }
          } while (boatChoice > 2 || boatChoice <= 0);
        } catch (InputMismatchException e) {
          System.out.println("You must input a number");
          keyboardInput.nextLine();
        }


        Boat boat;
        switch (boatChoice) {
          case 1 -> {
            nameCountMotorBoat++;
            boat = new MotorBoat(nameCountMotorBoat);
          }
          case 2 -> {
            nameCountRowingBoat++;
            boat = new RowingBoat(nameCountRowingBoat);
          }
          default -> boat = new RowingBoat(0);
        }
        boat.askAdditionalQuestions();
        boats.add(boat);


        System.out.println("Are you done with your reservation (y/n)");
        String endingReservation;
        endingReservation= keyboardInput.next();

        if (endingReservation.equals("y")) {
          reservationOver = true;
        }
      } while(!reservationOver);
      console.close();
      System.setOut(printingFile);
      // loop through boats, saveFile() on every boat.
      boats.forEach(Boat::saveFile);
    } catch (Exception e) {
      System.err.println("Error creating/writing to the file: " + e.getMessage());
    } finally {
      keyboardInput.close();
    }
  }
}

