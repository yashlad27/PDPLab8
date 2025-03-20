package spreadsheet;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents the controller of an interactive spreadsheet application.
 * This controller offers a simple text interface in which the user can
 * type instructions to manipulate a spreadsheet.
 *
 * <p>This controller works with any Readable to read its inputs and
 * any Appendable to transmit output. This controller directly uses
 * the Appendable object (i.e. there is no official "view")
 *
 * <p>A cell in the spreadsheet is referred to using a row-letter and a column number.
 * The row letter starts from A-Z and then AA-ZZ, then AAA-ZZZ and so on.
 * The column numbers begin with 1.
 *
 * <p>For example, the cell in the first row and column is A 1.
 * The cell in the 30th row and 26th column is AD 26.
 *
 * <p>In this way it tries to simulate how Microsoft Excel works (except that
 * it uses letters for rows, not columns).
 */
public class SpreadSheetController {
  private Readable readable;
  private Appendable appendable;
  private SpreadSheet sheet;

  /**
   * Constructs a SpreadSheetController with the specified spreadsheet model and I/O sources.
   *
   * @param sheet      the spreadsheet model to be controlled
   * @param readable   the source from which user input will be read
   * @param appendable the destination to which output will be written
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public SpreadSheetController(SpreadSheet sheet, Readable readable, Appendable appendable) {
    if ((sheet == null) || (readable == null) || (appendable == null)) {
      throw new IllegalArgumentException("Sheet, readable or appendable is null");
    }
    this.sheet = sheet;
    this.appendable = appendable;
    this.readable = readable;
  }

  /**
   * Starts the spreadsheet controller and runs the main program loop.
   * This method processes user input until the user quits the application.
   * It handles user commands, input validation, and manages interactions
   * between the user and the spreadsheet model.
   *
   * @throws IllegalStateException if there is an error writing to the output destination
   */
  public void executeProgram() throws IllegalStateException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;
    int row;
    int col;
    double value;

    this.welcomeMessage();

    while (!quit) {
      writeMessage("Type instruction: ");
      String userInstruction = sc.next();
      switch (userInstruction) {
        case "assign-value":
          try {
            row = getRowNum(sc.next());
            col = sc.nextInt();
            System.out.println("Setting cell (" + row + "," + (col - 1));
            sheet.set(row, col - 1, sc.nextDouble());
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "print-value":
          try {
            row = getRowNum(sc.next());
            col = sc.nextInt();
            writeMessage("Value: " + sheet.get(row, col - 1) + System.lineSeparator());
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "menu":
          welcomeMessage();
          break;
        case "q":
        case "quit":
          quit = true;
          break;
        default:
          writeMessage("Undefined instruction: " + userInstruction + System.lineSeparator());
      }
    }

    this.farewellMessage();

  }

  int getRowNum(String rowLetters) throws IllegalArgumentException {
    int rownumber = 0;

    for (int i = 0; i < rowLetters.length(); i = i + 1) {
      char c = rowLetters.charAt(i);
      if (!Character.isAlphabetic(c)) {
        throw new IllegalArgumentException("Invalid row");
      }
      rownumber = 26 * rownumber + ((int) Character.toLowerCase(c) - 'a' + 1);
    }
    return rownumber - 1;
  }


  private void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  void printMenu() throws IllegalStateException {
    writeMessage("Supported user instructions are: " + System.lineSeparator());
    writeMessage("assign-value row-num col-num value (set a cell to a value)"
            + System.lineSeparator());
    writeMessage("print-value row-num col-num (print the value at a given cell)"
            + System.lineSeparator());
    writeMessage("menu (Print supported instruction list)" + System.lineSeparator());
    writeMessage("q or quit (quit the program) " + System.lineSeparator());
  }

  private void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the spreadsheet program!" + System.lineSeparator());
    printMenu();
  }

  private void farewellMessage() throws IllegalStateException {
    writeMessage("Thank you for using this program!");
  }


}
