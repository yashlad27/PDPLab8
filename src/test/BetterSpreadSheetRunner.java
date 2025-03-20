
import java.io.InputStreamReader;

import spreadsheet.BetterSparseSpreadSheet;
import spreadsheet.BetterSpreadSheet;
import spreadsheet.BetterSpreadSheetController;

/**
 * Main program class for the better spreadsheet application.
 */
public class BetterSpreadSheetRunner {
  public static void main(String[] args) {
    // Create a better spreadsheet model
    BetterSpreadSheet model = new BetterSparseSpreadSheet();

    // Set up I/O
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;

    // Create and run the controller
    BetterSpreadSheetController controller = new BetterSpreadSheetController(model, rd, ap);
    controller.go();
  }
}