package Control.Cdd2ForteControl.POI;

import java.util.List;

/**
 * Created by 跃峰 on 2016/6/13.
 */
public interface IRowReader {

    /**业务逻辑实现方法
     * @param sheetIndex
     * @param curRow
     * @param rowlist
     */
    public void getRows(int sheetIndex, int curRow, List<String> rowlist);
}