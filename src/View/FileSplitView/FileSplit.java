package View.FileSplitView;

import Common.ControlBtnArea;
import View.CheckPlanView.GBC;
import Common.WelcomeArea;
import Common.MyTools;

import javax.swing.*;
import java.awt.*;

/**
 * 分割大文本文件为小文件
 * Created by 跃峰 on 2016/2/3.
 */
public class FileSplit extends JFrame {
    //JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,okBtn,cancelBtn,selectBtn,exportBtn;
    JPanel contentsArea,setArea,noticeArea;
    JLabel temp1,temp2,text,importJlb,setJbl,noticeOld,noticeNew;
    JTextField filePath,setNum;
    JComboBox setJcb;
    JCheckBox setTitleJcb,noticeJcb;

    public static void main(String[] args) {
        FileSplit fp = new FileSplit();
    }
    public FileSplit(){
        MyTools.windowsFeel();
        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(this.getClass().getResource("/icons/split_64.png")),"  一箱工具 - 文本文件分割");

        temp1 = new JLabel("      ");
        temp2 = new JLabel("  包含： ");
        // 选择文件饿设置部分
        text = new JLabel("可将文本文件分割为小文件，解决 excel 无法打开大文件的困难。");
        text.setFont(MyTools.fontPlain13);
        text.setOpaque(true);

        importJlb = new JLabel("① 选择文件");
        filePath = new JTextField("D:\\Program Files\\abcacabcabcabc.txt");
        filePath.setPreferredSize(new Dimension(350,20));
        selectBtn = new JButton(" 选  择 ");

        setJbl = new JLabel("② 分割颗粒");
        setJcb = new JComboBox();
        setJcb.addItem("适合 Excel 97-2003");   // 65536+256
        setJcb.addItem("适合 Excel 2007 及之后");  // 1048576+16384
        setJcb.addItem("平均分割为若干份");
        setJcb.addItem("自定义行数");

        setNum = new JTextField("65536");
        setNum.setEnabled(false);
        setNum.setPreferredSize(new Dimension(100,20));
        setTitleJcb = new JCheckBox("每个文件都包含表头");
        setTitleJcb.setSelected(true);

        //加入
        setArea = new JPanel(new GridBagLayout());
        setArea.add(text,new GBC(0,0,6,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));
        setArea.add(temp1,new GBC(0,1,6,1));
        setArea.add(importJlb,new GBC(1,2,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));
        setArea.add(filePath,new GBC(1,3,3,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));
        setArea.add(selectBtn,new GBC(5,3,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));
        setArea.add(temp1,new GBC(0,4,6,1));
        setArea.add(setJbl,new GBC(1,5,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));
        setArea.add(setJcb,new GBC(1,6,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));
        setArea.add(temp2,new GBC(2,6,1,1));
        setArea.add(setNum,new GBC(3,6,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));
        setArea.add(setTitleJcb,new GBC(1,7,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(5,5).setWeight(100,0));

        // 提醒部分
        noticeOld = new JLabel("选中的文件中有 xxxx 行数据");
        noticeNew = new JLabel("文件将被分割为 x 个文件，每个文件最多包含 xxx 行");
        noticeJcb = new JCheckBox("完成后删除源文件");
        noticeJcb.setBackground(new Color(204,204,204));
        exportBtn = new JButton("③ 开 始 分 割");

        noticeArea = new JPanel(new GridBagLayout());
        noticeArea.setBackground(new Color(204,204,204));
        noticeArea.add(temp1,new GBC(0,0,1,1));
        noticeArea.add(noticeOld,new GBC(1,0,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(5,5).setWeight(100,0));
        noticeArea.add(temp1,new GBC(0,1,1,1));
        noticeArea.add(temp1,new GBC(0,2,1,1));
        noticeArea.add(noticeNew,new GBC(1,2,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(5,5).setWeight(100,0));
        noticeArea.add(temp1,new GBC(0,3,1,1));
        noticeArea.add(noticeJcb,new GBC(1,3,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(5,5).setWeight(100,0));
        noticeArea.add(exportBtn,new GBC(5,3,2,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(5,5).setWeight(100,0));

        //添加到中间的JPanel
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(setArea,new GBC(0,0,1,8).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(30,30).setWeight(100,100));
        contentsArea.add(noticeArea,new GBC(0,9,1,4).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(30,30).setWeight(100,100));

        //控制栏
        backBtn = new JButton("< 上一步");
        nextBtn = new JButton("下一步 >");
        okBtn = new JButton(" 完  成 ");
        cancelBtn = new JButton(" 取  消 ");
        backBtn.setEnabled(false);
        nextBtn.setEnabled(false);
        okBtn.setEnabled(false);

        controlBtnArea = new ControlBtnArea(backBtn,nextBtn,okBtn,cancelBtn);

        //添加入Frame
        //frame = new JFrame();
        this.add(welcomeArea, BorderLayout.NORTH);
        this.add(contentsArea,BorderLayout.CENTER);
        this.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        this.setTitle("一箱工具 - 分本文件分割");
        this.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/boxtool_64.png")));
        this.setSize(626,500);
        this.setResizable(false);//固定窗体大小
        this.setLocationRelativeTo(null);//打开时相对window居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}


