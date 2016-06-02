package Control.Cdd2ForteControl;

import Common.enumClass;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;

import static Common.enumClass.excelType.cdd;
import static Common.enumClass.excelType.channel;

/**
 * Created by 跃峰 on 2016/6/1.
 */
public class ReadExcel {
    public static void main(String[] args) {
        File excel = new File("D:\\SZ\\日常工作\\20160601\\现网cdd-20160601.xls");
        ReadExcel readExcel = new ReadExcel();
        readExcel.checkExcelTitle(excel, cdd);
    }

    public void readCddContent(File excel){
        HSSFWorkbook hssfWorkbook;
        //读取工作簿
        try (POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(excel))) {
            hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
            //循环读取工作表
            for (int i = 0; i < hssfWorkbook.getNumberOfSheets(); i++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
                int firstRowNumber = 0;
                int lastRowNumber = hssfSheet.getLastRowNum();
                //循环读取表中的每一行
                for (int j = 0; j <= lastRowNumber; j++) {
                    HSSFRow hssfRow = hssfSheet.getRow(i);
                    int lastCellNumber = hssfRow.getLastCellNum();
                    //读取该行的所有cell
                    for (int k = 0; k < lastCellNumber; k++) {
                        HSSFCell hssfCell = hssfRow.getCell(k);
                        String value = hssfCell.getStringCellValue();
                        System.out.print(value+" ");
                    }
                    System.out.println();
                }
                System.out.println("----------");
            }
        }catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null,"导出失败，另一个程序正在使用此文件！");
        }
    }

    /**
     * 获取表头数据，用于判断需要读取的数据在哪一列
     * @param excel 目标 excel 文件.xls
     * @return
     */
    public String[] poiReadExcelTitle(File excel){
        String[] excelTitle = null;
        HSSFWorkbook hssfWorkbook;
        //读取工作簿
        try (POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(excel))) {

            //读取工作簿
            hssfWorkbook = new HSSFWorkbook(poifsFileSystem);

            //读取第一个工作表
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

            //读取第一行作为表头
            HSSFRow hssfRow = hssfSheet.getRow(0);

            //初始化存储表头的 excelTitle 数组
            excelTitle = new String[hssfRow.getLastCellNum()];

            for (int k = 0; k < hssfRow.getLastCellNum(); k++) {
                HSSFCell hssfCell = hssfRow.getCell(k);
                excelTitle[k] = hssfCell.getStringCellValue();
                //System.out.println(hssfCell.getStringCellValue());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return excelTitle;
    }

    /**
     * 检查确认需要的参数在文件中的位置
     * @param excel
     * @param excelType
     * @return
     */
    public int[] checkExcelTitle(File excel, enumClass.excelType excelType){
        String[] targetTitle = null;
        if (excelType.equals(cdd)){
            targetTitle = new String[]{"MSC", "BSC", "SITE", "CELL", "lac", "ci", "bcchno", "bsic", "TCH", "tchnum"};
        }else if (excelType.equals(channel)){
            targetTitle = new String[]{"bsc", "cell", "ch_group", "chgr_tg", "band", "bccd", "channel_tch", "hsn", "sdcch", "tchnum"};
        }

        //xls文件最多就256列,xls是16384列
        int[] targetTitlePosition = new int[10];
        for (int i = 0; i < targetTitlePosition.length; i++) {
            targetTitlePosition[i] = 22222;
        }

        String[] excelTitle = poiReadExcelTitle(excel);

        //判断参数在文件中所在的位置
        for (int i = 0; i < excelTitle.length; i++) {
            for (int j = 0; j < targetTitle.length; j++) {
                if (excelTitle[i].equals(targetTitle[j])){
                    targetTitlePosition[j] = i;
                }
            }
        }
        //
        //for (int i = 0; i < targetTitlePosition.length; i++) {
        //    System.out.print(targetTitlePosition[i]+" ");
        //}
        return targetTitlePosition;
    }

}
