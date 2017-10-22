/**
 * Created by danielpclin on 2017/2/6.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jetbrains.annotations.Contract;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum;

public class Word {
    int rows = 19;
    int cols = 5;
    XWPFDocument doc;
    CTSectPr sectPr;

    public Word() {
    }

    public void createXWPFDoc() {
        doc = new XWPFDocument();
    }

    public void createsectPr() {
        sectPr = doc.getDocument().getBody().addNewSectPr();
    }

    public void PopulateTable(int num, String[] element) {
        try (FileOutputStream outStream = new FileOutputStream(new File("配方標籤紙-列印.docx"));){
            try {
                int index = 0;
                for(int h = 0; h < (int)Math.ceil((double)num / (double)(rows * cols)); ++h) {
                    XWPFTable table = doc.createTable(rows, cols);
                    table.getCTTbl().getTblPr().unsetTblBorders();

                    for(int i = 0; i < rows; ++i) {
                        XWPFTableRow row = table.getRow(i);
                        row.setHeight(mm_to_twip(14.0D));

                        for(int j = 0; j < cols; ++j) {
                            XWPFTableCell cell = row.getCell(j);
                            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2098L));
                            CTTcPr tcpr = cell.getCTTc().addNewTcPr();
                            CTVerticalJc va = tcpr.addNewVAlign();
                            va.setVal(STVerticalJc.CENTER);
                            XWPFParagraph p = (XWPFParagraph)cell.getParagraphs().get(0);
                            p.setAlignment(ParagraphAlignment.CENTER);
                            cell.setText(element[index]);
                            ++index;
                        }
                    }
                }
                doc.write(outStream);
            } catch (Exception e) {
                System.out.print("An Error Has Occurred!");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.print("An Error Has Occurred!");
            e.printStackTrace();
        }

    }

    @Contract(
            pure = true
    )
    private static int mm_to_twip(double mm) {
        return (int)(mm * 56.692913D);
    }

    public boolean setMargin(double left, double top, double right, double bottom) {
        int marLeft = mm_to_twip(left);
        int marTop = mm_to_twip(top);
        int marRight = mm_to_twip(right);
        int marBottom = mm_to_twip(bottom);

        try {
            if(!sectPr.isSetPgMar()) {
                sectPr.addNewPgMar();
            }

            CTPageMar pageMar = sectPr.getPgMar();
            pageMar.setLeft(BigInteger.valueOf((long)marLeft));
            pageMar.setTop(BigInteger.valueOf((long)marTop));
            pageMar.setRight(BigInteger.valueOf((long)marRight));
            pageMar.setBottom(BigInteger.valueOf((long)marBottom));
            return true;
        } catch (Exception var14) {
            var14.printStackTrace();
            return false;
        }
    }

    public boolean setPageSize(Enum orientation, double width, double height) {
        try {
            if(!sectPr.isSetPgSz()) {
                sectPr.addNewPgSz();
            }

            CTPageSz pageSize = sectPr.getPgSz();
            pageSize.setOrient(orientation);
            pageSize.setW(BigInteger.valueOf((long)mm_to_twip(width)));
            pageSize.setH(BigInteger.valueOf((long)mm_to_twip(height)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
