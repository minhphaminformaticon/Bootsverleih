import java.util.InputMismatchException;

public class RowingBoat extends Boat{
  private int numberOfSeats;

  public RowingBoat(int nameCount){
      this.nameCounter = nameCount;
  }
  @Override
  public void askAdditionalQuestions() {
    System.out.println("How many seats do you want, enter a number (max: 8)");
    int seats = 0;
    try {
      do {
        seats = keyboardInput.nextInt();
      } while (seats > 8);
    } catch (InputMismatchException e) {
      System.out.println("You must input a number");
      keyboardInput.nextLine();
    }
  setNumberOfSeats(seats);
  }

  @Override
  public void writeToFile() {
    String nameBoat = String.format("Rowing Boat %03d", nameCounter++);
    System.out.println(nameBoat);
    System.out.println("Number of seats: " + getNumberOfSeats());

  }

  public int getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }
}

