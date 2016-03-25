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

        JTextArea note1 = new JTextArea("合并文件的时候，注意文件在文件框中的顺序，程序会根据呈现出来的顺序合并文件。");
        note1.setBackground(new Color(230,230,230));
        note1.setEditable(false);
        note1.setFont(MyTools.fontBold13);

        JTextArea note2 = new JTextArea("由于系统原因，01、02、...、10 和 1、2、...、10的排序是不同的。");
        note2.setBackground(new Color(230,230,230));
        note2.setEditable(false);
        note2.setFont(MyTools.fontBold13);

        JTextArea note3 = new JTextArea("建议使用“批量文件重命名”的“名称和计数”重命名，请勿造成错行。");
        note3.setBackground(new Color(230,230,230));
        note3.setEditable(false);
        note3.setFont(MyTools.fontBold13);

        JTextArea note4 = new JTextArea("联 系 我： mcxinyu@foxmail.com");
        note4.setBackground(new Color(230,230,230));
        note4.setEditable(false);

        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        north.setBackground(new Color(230,230,230));
        north.add(title);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.setBackground(new Color(230,230,230));
        center.add(note1);
        center.add(note2);
        center.add(note3);

        JPanel south = new JPanel();
        south.setBackground(new Color(230,230,230));
        south.add(note4);


        frame = new JDialog();
        frame.add(north,BorderLayout.NORTH);
        frame.add(center,BorderLayout.CENTER);
        frame.add(south,BorderLayout.SOUTH);

        frame.setModal(true);
        frame.setBackground(new Color(230,230,230));
        frame.setSize(550,300);
        frame.setTitle("一箱工具 - cdd2forte - 使用帮助");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/about.png")));
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
