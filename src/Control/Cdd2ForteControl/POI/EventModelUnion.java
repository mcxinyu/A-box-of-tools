package Control.Cdd2ForteControl.POI;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 跃峰 on 2016/6/8.
 */
public class EventModelUnion {

    //excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    //excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";

    /**
     * 读取Excel文件，可能是03也可能是07版本
     * @param fileName
     * @throws Exception
     */

    /**
     * 读取Excel文件，可能是03也可能是07版本
     * @param excelFile
     * @throws Exception
     */
    public static HashMap<String, List<String>> readerUtilWithEventModel(File excelFile) throws Exception{
        String fileName = excelFile.getAbsolutePath();
        //System.out.println(fileName);

        HashMap<String, List<String>> excelContent = null;
        // 处理excel2003文件
        if (fileName.endsWith(EXCEL03_EXTENSION)){
            //RowReader rowReader = new RowReader();
            POIReadExcel03WithEventModel excel03 = new POIReadExcel03WithEventModel();
            //excel03.setRowReader(rowReader);
            excel03.process(fileName);
            excelContent = excel03.getExcelContent();
            // 处理excel2007文件
        } else if (fileName.endsWith(EXCEL07_EXTENSION)){
            POIReadExcel07WithEventModel excel07 = new POIReadExcel07WithEventModel();
            //excel07.setRowReader(reader);
            excel07.process(fileName);
            excelContent = excel07.getExcelContent();
        } else {
            throw new  Exception("文件格式错误，只能处理.xls或.xlsx文件。");
        }
        return excelContent;
    }

    //public static void main(String[] args) throws Exception {
    //    IRowReader reader = new RowReader();
    //    //ExcelReaderUtil.readExcel(reader, "F://te03.xls");
    //    EventModelUnion.readerUtilWithEventModel(reader, "F://test07.xlsx");
    //}

}
