import org.apache.poi.xwpf.usermodel.*;
import org.jetbrains.annotations.Contract;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

public class CreateTable {
    public CreateTable(){
        PopulateTable();
    }
    public static void PopulateTable(){
        try (FileOutputStream outStream = new FileOutputStream(new File("配方標籤紙-列印.docx"))){
            int rows = 19, cols = 5;
            //create Document
            XWPFDocument doc = new XWPFDocument();
            CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
            //set Page Size
            setPageSize(sectPr,STPageOrientation.PORTRAIT,210,297);
            //set Page Margin
            setMargin(sectPr,12.5,15.5,12.5,297-19*14-15.5-5);
            //create Table
            XWPFTable table = doc.createTable(rows,cols);
            //XWPFTable table1 = doc.createTable(rows,cols);
            table.getCTTbl().getTblPr().unsetTblBorders();
            //loop through Cell
            for(int i = 0;i < rows;i++){
                XWPFTableRow row = table.getRow(i);
                //set Row Height
                row.setHeight(mm_to_twip(14));
                for(int j = 0;j < cols;j++){
                    XWPFTableCell cell = row.getCell(j);
                    //set Cell Width
                    cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2098));
                    CTTcPr tcpr = cell.getCTTc().addNewTcPr();
                    // set vertical & horizontal alignment to "center"
                    CTVerticalJc va = tcpr.addNewVAlign();
                    va.setVal(STVerticalJc.CENTER);
                    XWPFParagraph p = cell.getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    //LOGIC
                }
            }
            /*
            for(int i = 0;i < rows;i++){
                XWPFTableRow row1 = table1.getRow(i);
                //set Row Height
                row1.setHeight(mm_to_twip(14));
                for(int j = 0;j < cols;j++){
                    XWPFTableCell cell1 = row1.getCell(j);
                    //set Cell Width
                    cell1.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2098));
                    CTTcPr tcpr = cell1.getCTTc().addNewTcPr();
                    // set vertical & horizontal alignment to "center"
                    CTVerticalJc va = tcpr.addNewVAlign();
                    va.setVal(STVerticalJc.CENTER);
                    XWPFParagraph p = cell1.getParagraphs().get(0);
                    p.setAlignment(ParagraphAlignment.CENTER);
                    //LOGIC
                }
            }
            */
            doc.write(outStream);
        }
        catch (Exception e){
            System.out.print("An Error Has Occurred!");
        }
    }

    @Contract(pure = true)
    private static int mm_to_twip(double mm){
        return (int)(mm*56.692913);
    }
    private static boolean setMargin(CTSectPr sectPr,double left,double top,double right,double bottom){
        int marLeft = mm_to_twip(left), marTop = mm_to_twip(top), marRight = mm_to_twip(right), marBottom = mm_to_twip(bottom);
        try {
            if (!sectPr.isSetPgMar())
                sectPr.addNewPgMar();
            CTPageMar pageMar = sectPr.getPgMar();
            pageMar.setLeft(BigInteger.valueOf(marLeft));
            pageMar.setTop(BigInteger.valueOf(marTop));
            pageMar.setRight(BigInteger.valueOf(marRight));
            pageMar.setBottom(BigInteger.valueOf(marBottom));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private static boolean setPageSize(CTSectPr sectPr,STPageOrientation.Enum orientation,double width,double height){
        try {
            if(!sectPr.isSetPgSz())
                sectPr.addNewPgSz();
            CTPageSz pageSize = sectPr.getPgSz();
            pageSize.setOrient(orientation);
            pageSize.setW(BigInteger.valueOf(mm_to_twip(width)));
            pageSize.setH(BigInteger.valueOf(mm_to_twip(height)));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
