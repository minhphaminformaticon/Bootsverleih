import java.util.InputMismatchException;
import java.util.Scanner;

public class MotorBoat extends Boat{
  private int engineHorsePower;
  public MotorBoat(int nameCounter){
    this.nameCounter = nameCounter;
  }
  @Override
  public void askAdditionalQuestions() {
    System.out.println("How much Horse Power do you want, enter a number (max: 15)");
    int horsePower = 0;
    try {
      do {
        horsePower = keyboardInput.nextInt();
      } while (horsePower > 15);
    } catch (InputMismatchException e) {
      System.out.println("You must input a number");
      keyboardInput.nextLine();
    }
    setEngineHorsePower(horsePower);
  }

  @Override
  public void writeToFile() {
    String nameBoat = String.format("Motor Boat %03d", nameCounter++);
    System.out.println(nameBoat);
    System.out.println("Horsepower: " + getEngineHorsePower());


  }

  public int getEngineHorsePower() {
    return engineHorsePower;
  }

  public void setEngineHorsePower(int engineHorsePower) {
    this.engineHorsePower = engineHorsePower;
  }
}

