package Model;
/**
 * Created by 跃峰 on 2015/12/30.
 * 完成信息的查询逻辑
 */
import Common.SQLHelper.SqlHelper;

import javax.swing.table.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class CPModel extends AbstractTableModel {
    Vector<String>colums;
    Vector<Vector>rows;
    public void query(String sql, String paras[]){
        //初始化列、行
        this.colums=new Vector<String >();
        this.rows=new Vector <Vector>();

        SqlHelper sh = new SqlHelper();
        ResultSet rs= sh.query(sql,paras);

        try {
            //从rs 对象中可以得到ResultSetMetadata
            //rsmt可以得到结果又多少列，而且可以知道每一列的名字
            ResultSetMetaData rsmt=rs.getMetaData();

            for(int i=0; i<rsmt.getColumnCount(); i++)    //知道要查询要显示多少列
            {
                this.colums.add(rsmt.getColumnName(i+1));  //返回每一列的名字
            }

            //把rs的结果集放入到rows中
            while(rs.next())
            {
                Vector<String> temp=new Vector<String>();
                for(int i=0; i<rsmt.getColumnCount(); i++)
                {
                    temp.add(rs.getString(i+1));   //返回查询到的值
                }
                rows.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            sh.close();
        }
    }

    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {
        return this.colums.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector)rows.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int arg0) {
        return this.colums.get(arg0).toString();
    }
}
