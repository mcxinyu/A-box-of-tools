package Control.Cdd2ForteControl.POI;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 跃峰 on 2016/6/13.
 */
public class RowReader implements IRowReader{
    public static HashMap<String,List<String>> excelContent = new HashMap<String, List<String>>();

    /* 业务逻辑实现方法
     * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
     */
    @Override
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
        //System.out.print(curRow+" ");
        //for (int i = 0; i < rowlist.size(); i++) {
        //    System.out.print(rowlist.get(i) + " ");
        //}
        //System.out.println();

        excelContent.put(sheetIndex+"."+curRow, rowlist);
    }

    public static HashMap<String, List<String>> getExcelContent() {
        return excelContent;
    }

}
