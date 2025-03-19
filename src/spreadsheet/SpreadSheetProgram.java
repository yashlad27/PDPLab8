package spreadsheet;

import java.io.InputStreamReader;

public class SpreadSheetProgram {
  public static void main(String []args) {
    SpreadSheet model = new SparseSpreadSheet();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    SpreadSheetController controller = new SpreadSheetController(model,rd,ap);
    controller.go();
  }
}
