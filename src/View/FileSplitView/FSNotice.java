package View.FileSplitView;

import Common.MyTools;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 跃峰 on 2016/2/15.
 */
public class FSNotice extends JDialog {
    JDialog frame;
    public FSNotice(){
        MyTools.windowsFeel();

//        JPanel welcome =new WelcomeArea("images/boxtool_64.png","  一箱工具");

        JLabel title = new JLabel("  请 注 意：");
        title.setFont(MyTools.fontBold18);

        JTextArea note1 = new JTextArea("（1）坐标文件可以使用 forte 支持的格式；");
        note1.setBackground(new Color(230,230,230));
        note1.setEditable(false);
        note1.setFont(MyTools.fontBold13);

        JLabel forteType = new JLabel(new ImageIcon(this.getClass().getResource("/icons/forteType.png")));
        JTextArea note2 = new JTextArea("  也可以只包含主要内容，表头需要按顺序为：小区号、经度、纬度、方位角。");
        note2.setBackground(new Color(230,230,230));
        note2.setEditable(false);
        note2.setFont(MyTools.fontBold13);

        JLabel coustomType = new JLabel(new ImageIcon(this.getClass().getResource("/icons/coustomType.png")));
        JTextArea note3 = new JTextArea("（2）根据坐标文件的小区列表生成 forte 文件，遂务必保持坐标文件中的小区勿重复出现。");
        note3.setBackground(new Color(230,230,230));
        note3.setEditable(false);
        note3.setFont(MyTools.fontBold13);

        JTextArea note5 = new JTextArea("（3）选择 CDD 文件的时候，可以单个文件处理，也可以选择整个文件夹。");
        note5.setBackground(new Color(230,230,230));
        note5.setEditable(false);
        note5.setFont(MyTools.fontBold13);

        JTextArea note4 = new JTextArea("联 系 我： mcxinyu@foxmail.com");
        note4.setBackground(new Color(230,230,230));
        note4.setEditable(false);

        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        north.setBackground(new Color(230,230,230));
        north.add(title);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.setBackground(new Color(230,230,230));
        center.add(note1);
        center.add(forteType);
        center.add(note2);
        center.add(coustomType);
        center.add(note3);
        center.add(note5);

        JPanel south = new JPanel();
        south.setBackground(new Color(230,230,230));
        south.add(note4);


        frame = new JDialog();
        frame.add(north,BorderLayout.NORTH);
        frame.add(center,BorderLayout.CENTER);
        frame.add(south,BorderLayout.SOUTH);

        frame.setModal(true);
        frame.setBackground(new Color(230,230,230));
        frame.setSize(550,500);
        frame.setTitle("一箱工具 - cdd2forte - 使用帮助");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/about.png")));
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
