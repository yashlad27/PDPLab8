import org.junit.Before;

import java.io.StringWriter;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;

/**
 * Test class for SpreadSheetController.
 */
public class SpreadSheetControllerTest {

  private SpreadSheet sheet;
  private StringWriter output;

  @Before
  public void setUp() {
    sheet = new SparseSpreadSheet();
    output = new StringWriter();
  }

}