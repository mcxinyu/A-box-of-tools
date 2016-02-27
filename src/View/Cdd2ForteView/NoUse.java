package View.Cdd2ForteView;

import Common.MyTools;
import Common.WelcomeArea;
import javax.swing.*;
import java.awt.*;

/**
 * Created by 跃峰 on 2016/2/27.
 */
public class NoUse extends JDialog {
    JDialog frame;
    public NoUse(){
        MyTools.windowsFeel();

        JPanel welcome =new WelcomeArea("images/boxtool_64.png","  一箱工具");

        JLabel title = new JLabel("测试版不能使用完整功能");
        title.setFont(MyTools.fontBold26);
        JLabel note1 = new JLabel("其他功能还在测试，暂时不能使用。");
        note1.setFont(MyTools.fontBold13);

        JPanel center = new JPanel(new FlowLayout());
        center.setBackground(new Color(230,230,230));
        center.add(title);
        center.add(note1);


        frame = new JDialog();
//        frame.add(welcome,BorderLayout.NORTH);
        frame.add(center,BorderLayout.CENTER);

        frame.setModal(true);
        frame.setBackground(new Color(230,230,230));
        frame.setSize(550,200);
        frame.setTitle("一箱工具 - cdd2forte - 提示");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage("images/boxtool_64.png"));
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
