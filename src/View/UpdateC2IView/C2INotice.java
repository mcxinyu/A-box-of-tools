package View.UpdateC2IView;

import Common.MyTools;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 跃峰 on 2016/2/15.
 */
public class C2INotice extends JDialog {
    JDialog frame;
    public C2INotice(){
        MyTools.windowsFeel();

//        JPanel welcome =new WelcomeArea("images/boxtool_64.png","  一箱工具");

        JLabel title = new JLabel("  请 注 意：");
        title.setFont(MyTools.fontBold18);

        JTextArea note1 = new JTextArea("“读取割接信息”只需要在文本中保留两列数据“原小区名”、“新小区名”即可。");
        note1.setBackground(new Color(230,230,230));
        note1.setEditable(false);
        note1.setFont(MyTools.fontBold13);

        JTextArea note4 = new JTextArea("联 系 我： mcxinyu@foxmail.com");
        note4.setBackground(new Color(230,230,230));
        note4.setEditable(false);

        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        north.setBackground(new Color(230,230,230));
        north.add(title);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.setBackground(new Color(230,230,230));
        center.add(note1);

        JPanel south = new JPanel();
        south.setBackground(new Color(230,230,230));
        south.add(note4);


        frame = new JDialog();
        frame.add(north,BorderLayout.NORTH);
        frame.add(center,BorderLayout.CENTER);
        frame.add(south,BorderLayout.SOUTH);

        frame.setModal(true);
        frame.setBackground(new Color(230,230,230));
        frame.setSize(550,200);
        frame.setTitle("一箱工具 - cdd2forte - 使用帮助");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/about.png")));
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
