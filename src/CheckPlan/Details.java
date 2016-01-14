package CheckPlan;
/**
 * Created by 跃峰 on 2015/12/26.
 * 显示 details 数据
 */
import Model.*;
import java.awt.*;
import java.util.Enumeration;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.*;

public class Details extends JDialog{
    JTable jt = null;
    JScrollPane jsp = null;

    CPModel dm=null;

    /**
     * 初始化 Details
     * @param owner 它的父窗口
     * @param title 窗口名
     * @param modal 指定的模式窗口，还有非模式窗口
     */
    public Details(Frame owner, String title, boolean modal) {
        super(owner, title, modal); //调用父类的构造方法

        //查询语句
        String sql = "select cell_name 小区名,CELL 小区号,bcchno,bsic,TCH,tchnum from cdd_internal where 1=?";
//        String sql = "select top 30 * from cdd_internal where 1=?";
        String [] paras = {"1"};
        //查询数据
        dm = new CPModel();
        dm.query(sql,paras);

        //初始化 JTable
        jt = new JTable(dm);
        this.FitTableColumns(jt);
//        jt.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

        //初始化 JScrollPane
        jsp = new JScrollPane(jt);
        jsp.setRowHeaderView(new RowHeaderTable(jt,40));

        this.add(jsp);
        this.setSize(760,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * 基于JTable的列宽与内容自适应的实现方法
     * @param myTable 要调整的表
     */
    public void FitTableColumns(JTable myTable) {
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();
        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable, column.getIdentifier()
                            , false, false, -1, col).getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
                        myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column); // 此行很重要
            column.setWidth(width + myTable.getIntercellSpacing().width);
        }
    }
}


/**
 * 用于显示RowHeader的JTable，只需要将其加入JScrollPane的RowHeaderView即可为JTable生成行标题
 */
class RowHeaderTable extends JTable {
    private JTable refTable;//需要添加rowHeader的JTable
    /**
     * 为JTable添加RowHeader 行号
     * @param refTable 需要添加rowHeader的JTable
     * @param columnWidth rowHeader的宽度
     */
    public RowHeaderTable(JTable refTable,int columnWidth){
        super(new RowHeaderTableModel(refTable.getRowCount()));
        this.refTable=refTable;
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//不可以调整列宽
        this.getColumnModel().getColumn(0).setPreferredWidth(columnWidth);
        this.setDefaultRenderer(Object.class,new RowHeaderRenderer(refTable,this));//设置渲染器
        this.setPreferredScrollableViewportSize (new Dimension(columnWidth,0));
    }
}

/**
 * 用于显示RowHeader的JTable的渲染器，可以实现动态增加，删除行，在Table中增加、删除行时RowHeader
 * 一起变化。当选择某行时，该行颜色会发生变化
 */
class RowHeaderRenderer extends JLabel implements TableCellRenderer,ListSelectionListener {
    JTable reftable;//需要添加rowHeader的JTable
    JTable tableShow;//用于显示rowHeader的JTable
    public RowHeaderRenderer(JTable reftable,JTable tableShow) {
        this.reftable = reftable;
        this.tableShow=tableShow;
        //增加监听器，实现当在reftable中选择行时，RowHeader会发生颜色变化
        ListSelectionModel listModel=reftable.getSelectionModel();
        listModel.addListSelectionListener(this);
    }
    public Component getTableCellRendererComponent(JTable table,Object obj,boolean isSelected,boolean hasFocus,int row, int col) {
        ((RowHeaderTableModel)table.getModel()).setRowCount(reftable.getRowCount());
        JTableHeader header = reftable.getTableHeader();
//        this.setOpaque(true);
//        setBorder(UIManager.getBorder("TableHeader.cellBorder"));//设置为TableHeader的边框类型
        setHorizontalAlignment(CENTER);//让text居中显示
        setBackground(header.getBackground());//设置背景色为TableHeader的背景色
        if ( isSelect(row) ){    //当选取单元格时,在row header上设置成选取颜色
//            setForeground(Color.white);
//            setBackground(Color.blue);
        }
        else {
            setForeground(header.getForeground());
        }
        setFont(header.getFont());
        setText(String.valueOf(row+1));
        return this;
    }
    public void valueChanged(ListSelectionEvent e){
        this.tableShow.repaint();
    }
    private boolean isSelect(int row) {
        int[] sel = reftable.getSelectedRows();
        for ( int i=0; i<sel.length; i++ )
            if (sel[i] == row )
                return true;
        return false;
    }
}

/**
 * 用于显示表头RowHeader的JTable的TableModel，实际不存储数据
 */
class RowHeaderTableModel extends AbstractTableModel {
    private int rowCount;//当前JTable的行数，与需要加RowHeader的TableModel同步
    public RowHeaderTableModel(int rowCount){
        this.rowCount=rowCount;
    }
    public void setRowCount(int rowCount){
        this.rowCount=rowCount;
    }
    public int getRowCount(){
        return rowCount;
    }
    public int getColumnCount(){
        return 1;
    }
    public Object getValueAt(int row, int column){
        return row;
    }
}