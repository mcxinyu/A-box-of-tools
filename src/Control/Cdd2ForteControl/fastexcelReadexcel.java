package Control.Cdd2ForteControl;

import edu.npu.fastexcel.*;

import java.io.File;

/**
 * Created by 跃峰 on 2016/6/7.
 */
public class FastexcelReadexcel {
    public static void main(String[] args) throws ExcelException {
        FastexcelReadexcel fastexcelReadexcel = new FastexcelReadexcel();
        fastexcelReadexcel.basicRead();
    }

    public void basicRead() throws ExcelException {
        Workbook workBook;
        workBook = FastExcel.createReadableWorkbook(new File("D:\\thxhyf\\Documents\\现网cdd_Nrel.xlsx"));
        workBook.setSSTType(BIFFSetting.SST_TYPE_DEFAULT);//memory storage
        workBook.open();
        Sheet s;
        s = workBook.getSheet(0);
        System.out.println("SHEET:"+s);
        for (int i = s.getFirstRow(); i < s.getLastRow(); i++) {
            System.out.print(i+"#");
            for (int j = s.getFirstColumn(); j < s.getLastColumn(); j++) {
                System.out.print(","+s.getCell(i, j));
            }
            System.out.println();
        }
        workBook.close();
    }
}
