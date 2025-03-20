package spreadsheet;

import java.io.InputStreamReader;

/**
 * Main program class for the better spreadsheet application.
 */
public class BetterSpreadSheetRunner {

  /**
   * Initializes the enhanced spreadsheet components and starts the application.
   * Creates a better spreadsheet model with advanced features, sets up input/output streams,
   * creates the controller, and begins processing user input.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    BetterSpreadSheet model = new BetterSparseSpreadSheet();

    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;

    BetterSpreadSheetController controller = new BetterSpreadSheetController(model, rd, ap);
    controller.go();
  }
}