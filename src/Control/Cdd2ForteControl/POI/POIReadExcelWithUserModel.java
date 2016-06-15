package Control.Cdd2ForteControl.POI;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 读取 excel 工作簿，兼容 Excel 2003/2007/2010/2013/2016 格式为 .xls and .xlsx，其他未测试
 * Created by 跃峰 on 2016/6/1.
 */
public class POIReadExcelWithUserModel {

    /**
     * 使用 POI 用户模型读取 excel
     * @param excel
     * @return
     */
    public HashMap<String,List<String>> readerUtilWithUserModel(File excel){
        //创建一个HashMap用于存放读取的行内容
        HashMap<String,List<String>> excelContent = new HashMap<String, List<String>>();

        //读取工作簿，这种方式 Excel 2003/2007/2010 都是可以处理
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(excel))) {
            //循环读取工作表
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                //创建一个sheet
                Sheet sheet = workbook.getSheetAt(i);
                //循环读取表中的每一行（不包含第一行）
                for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                    List<String> rowlist = new ArrayList<String>();
                    //创建每一行
                    Row row = sheet.getRow(j);
                    //按 cell 读取数据，后存入 excelContent
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        //按照checkExcelTitle提供的位置读取cell
                        Cell cell = row.getCell(k);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        rowlist.add(cell.getStringCellValue());
                    }
                    excelContent.put(i+"."+j, rowlist);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return excelContent;
    }

}
