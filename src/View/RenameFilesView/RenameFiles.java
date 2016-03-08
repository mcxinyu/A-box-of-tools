package View.RenameFilesView;

/**
 * 批量文件重命名
 * Created by 跃峰 on 2016/3/8.
 */

import Common.ControlBtnArea;
import Common.MyTools;
import Common.WelcomeArea;
import View.Cdd2ForteView.Cdd2Forte;
import View.CheckPlanView.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameFiles extends JFrame implements ActionListener {
    JFrame frame;
    JPanel welcomeArea,controlBtnArea,doArea,noticeArea,contentsArea;
    JPanel replaceText,addText,format;
    JButton backBtn,nextBtn,homeBtn,okBtn,aboutBtn;
    JLabel text,findJL,replaceJL,egJL,egConJL;
    JTextField findJTF,replaceJTF;
    JComboBox renameStyle;

    public static void main(String[] args) {
        new RenameFiles();
    }

    public RenameFiles(){
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(Cdd2Forte.class.getResource("/icons/cdd2forte_64.png")),"  一箱工具 - 批量文件重命名");

        //contentsArea
        text = new JLabel("  给选择的项目重命名：");
        text.setFont(MyTools.fontPlain15);

        // 重命名方式
        renameStyle = new JComboBox();
        renameStyle.addItem(" 替换文本 ");
        renameStyle.addItem(" 添加文本 ");
        renameStyle.addItem(" 格式 ");

        // 替换文本
        findJL = new JLabel("查找：");
        findJTF = new JTextField();
        replaceJL = new JLabel("替换为：");
        replaceJTF =new JTextField();

        replaceText = new JPanel(new FlowLayout());
        replaceText.add(findJL);
        replaceText.add(findJTF);
        replaceText.add(replaceJL);
        replaceText.add(replaceJTF);

        // 添加文本
        // 格式

        doArea = new JPanel(new GridBagLayout());
        doArea.add(renameStyle,new GBC(0,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,1));
        doArea.add(replaceText,new GBC(0,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,1));

        // 提醒部分
        egJL = new JLabel("示 例：");
        egConJL = new JLabel("1111111.jpg");
        noticeArea = new JPanel(new FlowLayout());
        noticeArea.setBackground(new Color(204,204,204));
        noticeArea.add(egJL);
        noticeArea.add(egConJL);

        //添加组件到 contentsArea
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(text,new GBC(0,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,1));
        contentsArea.add(doArea,new GBC(0,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,1));
        contentsArea.add(noticeArea,new GBC(0,2).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(30,30).setWeight(100,100));

        //控制栏
        aboutBtn = new JButton(new ImageIcon(Cdd2Forte.class.getResource("/icons/about.png")));
        backBtn = new JButton("< 上一步");
        nextBtn = new JButton("下一步 >");
        okBtn = new JButton(" 完  成 ");
        homeBtn = new JButton(" 主功能 ");
        backBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        //设置监听
        aboutBtn.setActionCommand("aboutBtn");
        aboutBtn.addActionListener(this);
        backBtn.setActionCommand("backBtn");
        backBtn.addActionListener(this);
        nextBtn.setActionCommand("nextBtn");
        nextBtn.addActionListener(this);
        okBtn.setActionCommand("cancelBtn");
        okBtn.addActionListener(this);
        homeBtn.setActionCommand("homeBtn");
        homeBtn.addActionListener(this);

        controlBtnArea = new ControlBtnArea(aboutBtn,backBtn,nextBtn,okBtn,homeBtn);

        //添加入Frame
        frame = new JFrame();
        frame.add(welcomeArea, BorderLayout.NORTH);
        frame.add(contentsArea,BorderLayout.CENTER);
        frame.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        frame.setTitle("一箱工具 - 批量文件重命名");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/boxtool_64.png")));

        frame.setSize(626,300);
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
