package Common;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 跃峰 on 2016/2/15.
 */
public class versionNotice extends JDialog {
    JDialog frame;
    JPanel welcome,center,middle;
    JLabel temp1,temp2;
    JTextArea versionText,note1,note2,email;

    public versionNotice(){
        MyTools.windowsFeel();

        welcome =new WelcomeArea(new ImageIcon(this.getClass().getResource("/icons/boxtool_64.png")),"  欢迎使用 一箱工具",false);

        versionText = new JTextArea("版 本： 2016.5.20_1333");
        versionText.setBackground(new Color(240,240,240));
        versionText.setEditable(false);
        versionText.setFont(MyTools.fontBold13);

        note1 = new JTextArea("网 址：http://mcxinyu.github.io/A-box-of-tools/");
        note1.setBackground(new Color(240,240,240));
        note1.setEditable(false);
        note1.setFont(MyTools.fontBold13);

        email = new JTextArea("联 系 我： mcxinyu@foxmail.com");
        email.setBackground(new Color(240,240,240));
        email.setEditable(false);
        email.setFont(MyTools.fontBold13);

        note2 = new JTextArea("更 新：...");
        note2.setBackground(new Color(240,240,240));
        note2.setEditable(false);
        note2.setFont(MyTools.fontBold13);

        middle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        middle.add(versionText);
        middle.add(note1);
        middle.add(email);

        temp1 = new JLabel("                ");
        temp2 = new JLabel("                ");
        center = new JPanel(new BorderLayout());
        center.add(temp1,BorderLayout.EAST);
        center.add(temp2,BorderLayout.WEST);
        center.add(middle,BorderLayout.CENTER);

        frame = new JDialog();
        frame.add(welcome,BorderLayout.NORTH);
        frame.add(center,BorderLayout.CENTER);

        frame.setModal(true);
        frame.setBackground(new Color(230,230,230));
        frame.setSize(550,300);
        frame.setTitle("一箱工具 - 关于信息");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/about.png")));
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
