package View.Cdd2ForteView;

import Common.WelcomeArea;
import Common.MyTools;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 跃峰 on 2016/2/15.
 */
public class C2FNotice extends JDialog {
    JDialog frame;
    public C2FNotice(){
        MyTools.windowsFeel();

        JPanel welcome =new WelcomeArea("images/boxtool_64.png","  一箱工具");

        JLabel title = new JLabel("请 注 意：                                      ");
        title.setFont(MyTools.fontBold26);
        JLabel note1 = new JLabel("坐标文件可以使用 forte 支持的格式");
        note1.setFont(MyTools.fontBold13);
        JLabel forteType = new JLabel(new ImageIcon("images/forteType.png"));
        JLabel note2 = new JLabel("也可以只包含主要内容，表头需要按顺序为：小区号、经度、纬度、方位角");
        note2.setFont(MyTools.fontBold13);
        JLabel coustomType = new JLabel(new ImageIcon("images/coustomType.png"));

        JPanel center = new JPanel(new FlowLayout());
        center.setBackground(new Color(230,230,230));
        center.add(title);
        center.add(note1);
        center.add(forteType);
        center.add(note2);
        center.add(coustomType);


        frame = new JDialog();
//        frame.add(welcome,BorderLayout.NORTH);
        frame.add(center,BorderLayout.CENTER);

        frame.setModal(true);
        frame.setBackground(new Color(230,230,230));
        frame.setSize(550,450);
        frame.setTitle("一箱工具 - cdd2forte - 使用帮助");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage("images/boxtool_64.png"));
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
