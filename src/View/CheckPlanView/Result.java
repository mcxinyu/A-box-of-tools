package View.CheckPlanView;
/**
 * Created by 跃峰 on 2015/12/21.
 * 简单检查后的检查结果呈现界面
 * 步骤二使用的
 */
import javax.swing.*;
import java.awt.*;

public class Result extends JPanel{
    JPanel list1,list2,list3,list4,list5;

    public Result(){
        //检查对比结果界面
        list1 = new ResultTables("","<html><b>小区数<b></html>","<html><b>信道组数<b></html>","<html><b>载波数<b></html>");
        list2 = new ResultTables("<html><b>现网数据<br><b></html>","111","111","111");
        list3 = new ResultTables("<html><b>方案数据<br><b></html>","111","111","111");
        list4 = new ResultTables("<html><b>cdd <br>无方案小区数<b></html>","111","111","111");
        list5 = new ResultTables("<html><b>cdd_Channel <br>无方案小区数<b></html>","111","111","111");

        setLayout(new GridBagLayout());

        add(list1,new GBC(0,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST).setInsets(1));
        add(list2,new GBC(1,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST).setInsets(1));
        add(list3,new GBC(2,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST).setInsets(1));
        add(list4,new GBC(3,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST).setInsets(1));
        add(list5,new GBC(4,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST).setInsets(1));
        setBackground(new Color(230,230,230));
    }

}


class ResultTables extends JPanel{
    //检查对比结果显示
    JLabel jlb0,jlb1,jlb2,jlb3;
    public ResultTables(String title, String sectorNum, String channelGroupNum, String carrierNum) {
        jlb0 = new JLabel(title);
        jlb0.setPreferredSize(new Dimension(80,35));
        jlb1 = new JLabel(sectorNum);
        jlb1.setPreferredSize(new Dimension(80,20));
        jlb2 = new JLabel(channelGroupNum);
        jlb2.setPreferredSize(new Dimension(80,20));
        jlb3 = new JLabel(carrierNum);
        jlb3.setPreferredSize(new Dimension(80,20));

        setLayout(new GridBagLayout());

        add(jlb0,new GBC(0,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.WEST).setInsets(5));
        add(jlb1,new GBC(0,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.WEST).setInsets(5));
        add(jlb2,new GBC(0,2).setFill(GBC.HORIZONTAL).setAnchor(GBC.WEST).setInsets(5));
        add(jlb3,new GBC(0,3).setFill(GBC.HORIZONTAL).setAnchor(GBC.WEST).setInsets(5));
        setBackground(new Color(230,230,230));
    }
}
//
//class details extends JDialog{
//    //显示方案告警小区详情列表-JTable
//    Vector rowData,columnName;
//    JTable jt = null;
//    JScrollPane jsp = null;
//    public details(){
//        columnName = new Vector();
//        //设置列名
//        columnName.add("行");
//        columnName.add("错误类型");
//        columnName.add("小区名");
//        columnName.add("参数类型");
//        columnName.add("原值");
//        columnName.add("新值");
//
//
//        rowData = new Vector();
//        //rowData 可以存放多行数据
//        Vector row1 = new Vector();
//        row1.add("1");
//        row1.add("原频点不存在（cdd）");
//        row1.add("大中华M1");
//        row1.add("BCCHNO");
//        row1.add("60");
//        row1.add("50");
//
//
//        Vector row2 = new Vector();
//        row2.add("2");
//        row2.add("原频点不存在（channel）");
//        row2.add("大中华M1");
//        row2.add("BCCHNO");
//        row2.add("60");
//        row2.add("50");
//
//
//        rowData.add(row1);
//        rowData.add(row2);
//
//        //初始化 JTable
//        jt = new JTable(rowData,columnName);
//
//        //初始化 JScrollPane
//        jsp = new JScrollPane(jt);
//
//        this.add(jsp);
//        this.setModal(true);
//        this.setTitle("详情报告");
//        this.setSize(626,400);
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.setVisible(true);
//    }
//}