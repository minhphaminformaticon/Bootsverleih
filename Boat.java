
import java.util.Scanner;

public abstract class Boat {
  Scanner keyboardInput = new Scanner(System.in);
  protected int nameCounter;
  public abstract void askAdditionalQuestions();

  public void saveFile(){
      writeToFile();
  }

  public abstract void writeToFile();

}
