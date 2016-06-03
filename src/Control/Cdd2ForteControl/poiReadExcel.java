package Control.Cdd2ForteControl;

import Common.enumClass;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static Common.enumClass.excelType.cdd;
import static Common.enumClass.excelType.channel;

/**
 * 读取 excel 工作簿，兼容 Excel 2003/2007/2010/2013/2016 格式为 .xls and .xlsx，其他未测试
 * Created by 跃峰 on 2016/6/1.
 */
public class poiReadExcel {
    public static void main(String[] args) {
        File excel = new File("D:\\thxhyf\\Documents\\现网cdd_Channel.xlsx");
        poiReadExcel readExcel = new poiReadExcel();
        readExcel.poiReadExcelContent(excel,channel);
    }

    /**
     * 使用 POI 读取 excel
     * @param excel
     * @param excelType cdd or channel
     * @return
     */
    public ArrayList poiReadExcelContent(File excel, enumClass.excelType excelType){
        //声明一个数组用于存放读取的行内容
        ArrayList<String[]> excelContent = null;

        //读取工作簿，这种方式 Excel 2003/2007/2010 都是可以处理
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(excel))) {
            //实例化workbook
            //hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
            //检查该文件的表头数据在文件中的位置
            int[] checkExcelTitle = checkExcelTitle(excel,excelType);
            //循环读取工作表
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                //创建一个sheet
                Sheet sheet = workbook.getSheetAt(i);
                //实例化excelContent，用于存放读取到的数据
                excelContent = new ArrayList<String[]>();
                //循环读取表中的每一行（不包含第一行）
                for (int j = 1; j <= sheet.getLastRowNum(); j++) {  //不读取表头
                    //创建每一行
                    Row row = sheet.getRow(j);
                    //创建用于存放行内的数组
                    String[] rowContent = new String[10];
                    //按 cell 读取数据，后存入 rowContent
                    for (int k = 0; k < checkExcelTitle.length; k++) {
                        if (checkExcelTitle[k]!=22222){ //如果等于22222说明是不存在该参数
                            //按照checkExcelTitle提供的位置读取cell
                            Cell cell = row.getCell(checkExcelTitle[k]);
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            rowContent[k] = cell.getStringCellValue();
                        }
                    }
                    //将rowContent存入excelContent
                    excelContent.add(rowContent);
                }
            }
            for (int i = 0; i < excelContent.size(); i++) {
                for (int j = 0; j < excelContent.get(i).length; j++) {
                    System.out.print(excelContent.get(i)[j]+" ");
                }
                System.out.println();
            }
        }catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null,"导出失败，另一个程序正在使用此文件！");
        }
        return excelContent;
    }

    /**
     * 获取表头数据，用于判断需要读取的数据在哪一列
     * @param excel 目标 excel 文件
     * @return
     */
    public String[] poiReadExcelTitle(File excel){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String[] excelTitle = null;
        //读取工作簿
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(excel))) {
            //读取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);

            //读取第一行作为表头
            Row row = sheet.getRow(0);

            //初始化存储表头的 excelTitle 数组
            excelTitle = new String[row.getLastCellNum()];

            for (int k = 0; k < row.getLastCellNum(); k++) {
                if (row.getCell(k)!=null) {
                    Cell cell = row.getCell(k);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    excelTitle[k] = cell.getStringCellValue();
                    //System.out.println(hssfCell.getStringCellValue());
                }
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"读取失败，有空值表头！");
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
                if (excelTitle[i] != null && excelTitle[i].equals(targetTitle[j])){
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
