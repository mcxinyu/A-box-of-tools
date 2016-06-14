package Control.CommonControl;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 跃峰 on 2016/6/13.
 */
public class IRowReader {
    public static HashMap<String,List<String>> excelContent = new HashMap<String, List<String>>();

    /**业务逻辑实现方法
     * @param sheetIndex
     * @param curRow
     * @param rowlist
     */
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
        System.out.print(sheetIndex+"."+curRow+" ");
        for (int i = 0; i < rowlist.size(); i++) {
            System.out.print(rowlist.get(i) + " ");
        }
        System.out.println();

        excelContent.put(sheetIndex+"."+curRow, rowlist);
    }

    public static HashMap<String, List<String>> getExcelContent() {
        return excelContent;
    }
}