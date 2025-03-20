package spreadsheet;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents the controller for a BetterSpreadSheet application.
 * It extends the basic SpreadSheetController and adds support for bulk operations.
 */
public class BetterSpreadSheetController {

  private BetterSpreadSheet betterSheet;
  private Readable readable;
  private Appendable appendable;

  /**
   * Constructs a controller for a BetterSpreadSheet.
   *
   * @param sheet      the BetterSpreadSheet to use
   * @param readable   the source of user input
   * @param appendable the destination for output
   * @throws IllegalArgumentException if any argument is null
   */
  public BetterSpreadSheetController(BetterSpreadSheet sheet, Readable readable,
                                     Appendable appendable)
          throws IllegalArgumentException {
    if ((sheet == null) || (readable == null) || (appendable == null)) {
      throw new IllegalArgumentException("Sheet, readable or appendable is null");
    }
    this.betterSheet = sheet;
    this.readable = readable;
    this.appendable = appendable;
  }

  /**
   * Starts the controller, processing user input until the user quits.
   *
   * @throws IllegalStateException if there is an error writing output
   */
  public void go() throws IllegalStateException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;
    int row, col, startRow, startCol, endRow, endCol;
    double value;

    welcomeMessage();

    while (!quit) {
      writeMessage("Type instruction: ");
      String userInstruction = sc.next();

      switch (userInstruction) {
        case "assign-value":
          try {
            row = getRowNum(sc.next());
            col = sc.nextInt();
            value = sc.nextDouble();
            betterSheet.set(row, col - 1, value);
            writeMessage("Set cell (" + row + "," + (col - 1) + ") to "
                    + value + System.lineSeparator());
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "print-value":
          try {
            row = getRowNum(sc.next());
            col = sc.nextInt();
            writeMessage("Value: " + betterSheet.get(row, col - 1) + System.lineSeparator());
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "bulk-assign":
          try {
            startRow = getRowNum(sc.next());
            startCol = sc.nextInt();
            endRow = getRowNum(sc.next());
            endCol = sc.nextInt();
            value = sc.nextDouble();

            betterSheet.bulkSet(startRow, startCol - 1, endRow, endCol - 1, value);
            writeMessage("Bulk assigned " + value + " to region (" +
                    startRow + "," + (startCol - 1) + ") to (" +
                    endRow + "," + (endCol - 1) + ")" + System.lineSeparator());
          } catch (IllegalArgumentException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "menu":
          printMenu();
          break;
        case "q":
        case "quit":
          quit = true;
          break;
        default:
          writeMessage("Undefined instruction: " + userInstruction + System.lineSeparator());
          break;
      }
    }
    farewellMessage();
  }

  /**
   * Converts a row string (like "A", "B", "AA") to a row number starting with 0.
   *
   * @param rowLetters the row string to convert
   * @return the row number (0-based)
   * @throws IllegalArgumentException if the row string is invalid
   */
  private int getRowNum(String rowLetters) throws IllegalArgumentException {
    int rownumber = 0;

    for (int i = 0; i < rowLetters.length(); i++) {
      char c = rowLetters.charAt(i);
      if (!Character.isAlphabetic(c)) {
        throw new IllegalArgumentException("Invalid row");
      }
      rownumber = 26 * rownumber + ((int) Character.toLowerCase(c) - 'a' + 1);
    }
    return rownumber - 1;
  }

  /**
   * Writes a message to the appendable.
   *
   * @param message the message to write
   * @throws IllegalStateException if there is an error writing to the appendable
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Prints the menu of supported instructions.
   *
   * @throws IllegalStateException if there is an error writing to the appendable
   */
  private void printMenu() throws IllegalStateException {
    writeMessage("Supported user instructions are: " + System.lineSeparator());
    writeMessage("assign-value row-num col-num value (set a cell to a value)"
            + System.lineSeparator());
    writeMessage("print-value row-num col-num (print the value at a given cell)"
            + System.lineSeparator());
    writeMessage("bulk-assign start-row start-col end-row end-col value (set a region to a value)"
            + System.lineSeparator());
    writeMessage("menu (Print supported instruction list)" + System.lineSeparator());
    writeMessage("q or quit (quit the program) " + System.lineSeparator());
  }

  /**
   * Prints the welcome message.
   *
   * @throws IllegalStateException if there is an error writing to the appendable
   */
  private void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the better spreadsheet program!" + System.lineSeparator());
    printMenu();
  }

  /**
   * Prints the farewell message.
   *
   * @throws IllegalStateException if there is an error writing to the appendable
   */
  private void farewellMessage() throws IllegalStateException {
    writeMessage("Thank you for using this enhanced program!");
  }
}