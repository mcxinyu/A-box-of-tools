package View.CheckPlanView;
/**
 * Created by 跃峰 on 2015/12/8.
 * 导入文件区域的显示界面，文件列表
 * 步骤一里面使用的
 */
import View.HomeView.MyTools;
import javax.swing.*;
import java.awt.*;

public class FileItem extends JPanel {
    //创建一个显示处理文件的信息区
    JLabel text;
    JButton selectBtn;
    JTextField filePath;
    //数据标签、组件数量3、2、1
    public FileItem(String t,int num) {
        text = new JLabel(t);
        text.setPreferredSize(new Dimension(70,20));
        filePath = new JTextField("");
        filePath.setPreferredSize(new Dimension(350,20));
        selectBtn = new JButton("选择文件");
        selectBtn.setBackground(new Color(230,230,230));
        selectBtn.setFocusPainted(false);
        setLayout(new GridBagLayout());
        setBackground(new Color(230, 230, 230));
        if (num == 3) {
            //标签、文本框、选按钮
            add(text,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(5));
            add(filePath,new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.CENTER).setInsets(5));
            add(selectBtn,new GBC(2, 0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.EAST).setInsets(5));
        } else if (num == 2) {
            //不包含按钮
            add(text,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(3));
            add(filePath,new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.CENTER).setInsets(3));
        } else if (num == 1) {
            //只有标签
            text.setPreferredSize(new Dimension(300,20));
            add(text,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(3));
        }
        setFont(MyTools.fontPlain13);
    }

    public void use(boolean a){
        //组件是否可用，默认可用
        if (a == false){
            text.setEnabled(false);
            filePath.setEnabled(false);
            selectBtn.setEnabled(false);
        }else {
            text.setEnabled(true);
            filePath.setEnabled(true);
            selectBtn.setEnabled(true);
        }
    }
}
