package spreadsheet;

import java.io.InputStreamReader;

/**
 * Main entry point for the spreadsheet application.
 * This class initializes and starts a text-based spreadsheet program.
 */
public class SpreadSheetProgram {

  /**
   * The main method initializes the spreadsheet application and starts the program.
   *
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    SpreadSheet model = new SparseSpreadSheet();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    SpreadSheetController controller = new SpreadSheetController(model, rd, ap);
    controller.executeProgram();
  }
}
