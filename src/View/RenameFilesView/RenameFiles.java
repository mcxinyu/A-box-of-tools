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
    //JFrame frame;
    JPanel welcomeArea,fileArea,controlBtnArea,doArea,noticeArea,contentsArea;
    JPanel selectBar,replaceText,addText,format;
    JButton selectFileBtn,renameBtn,backBtn,nextBtn,homeBtn,okBtn,aboutBtn;
    JLabel selectJL,text,findJL,replaceJL,egJL,egConJL,nameFormatJL,locationJL,customFromatJL,startFromNum;
    JTextArea selectFileList;
    JTextField findJTF,addJTF,replaceJTF,customFromatJTF,startFromNumJTF;
    JComboBox renameStyle,frontORbehid,nameFormat,location;
    JScrollPane selectFileListScroll;

    public static void main(String[] args) {
        new RenameFiles();
    }

    public RenameFiles(){
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(Cdd2Forte.class.getResource("/icons/rename_64.png")),"  一箱工具 - 批量文件重命名");

        // 选择的项目、文件
        selectJL = new JLabel("选取要重命名的项目");
        selectJL.setFont(MyTools.fontBold13);
        selectFileBtn = new JButton(" 选 取 ");
        selectBar = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        selectBar.add(selectJL);
        selectBar.add(selectFileBtn);

        selectFileList = new JTextArea("selectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileListselectFileList");
        selectFileList.setLineWrap(true);
        //selectFileList.setColumns(9);
        selectFileList.setEnabled(false);
        selectFileListScroll = new JScrollPane(selectFileList);
        selectFileListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fileArea = new JPanel(new BorderLayout());
        fileArea.add(selectBar,BorderLayout.NORTH);
        fileArea.add(selectFileListScroll,BorderLayout.CENTER);

        //contentsArea
        text = new JLabel("    给选取的项目重命名");
        text.setFont(MyTools.fontBold13);

        // 重命名方式
        renameStyle = new JComboBox();
        renameStyle.addItem(" 替换文本 ");
        renameStyle.addItem(" 添加文本 ");
        renameStyle.addItem(" 格式 ");

        // 替换文本
        findJL = new JLabel("查找名称：");
        findJTF = new JTextField(15);
        replaceJL = new JLabel("替换名称为：");
        replaceJTF =new JTextField(15);

        replaceText = new JPanel(new FlowLayout(FlowLayout.LEFT));
        replaceText.setVisible(true);
        replaceText.add(findJL);
        replaceText.add(findJTF);
        replaceText.add(replaceJL);
        replaceText.add(replaceJTF);

        // 添加文本
        addJTF = new JTextField(15);
        frontORbehid = new JComboBox();
        frontORbehid.addItem(" 于名称之后 ");
        frontORbehid.addItem(" 于名称之前 ");
        addText = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addText.setVisible(false);
        addText.add(addJTF);
        addText.add(frontORbehid);

        // 格式
        nameFormatJL = new JLabel("名称格式：");
        nameFormat = new JComboBox();
        nameFormat.addItem(" 名称和索引 ");
        nameFormat.addItem(" 名称和计数 ");
        nameFormat.addItem(" 名称和日期 ");
        locationJL = new JLabel("位 置：");
        location = new JComboBox();
        location.addItem(" 于名称之后 ");
        location.addItem(" 于名称之前 ");
        customFromatJL =  new JLabel("名称主体：");
        customFromatJTF = new JTextField(15);
        startFromNum = new JLabel("开始数字为：");
        startFromNumJTF = new JTextField(15);

        format = new JPanel(new GridLayout(2,4,10,5));
        format.setVisible(false);
        format.add(nameFormatJL);
        format.add(nameFormat);
        format.add(locationJL);
        format.add(location);
        format.add(customFromatJL);
        format.add(customFromatJTF);
        format.add(startFromNum);
        format.add(startFromNumJTF);

        doArea = new JPanel(new GridBagLayout());
        doArea.add(renameStyle,new GBC(0,0).setFill(GBC.NONE).setAnchor(GBC.WEST).setIpad(0,0).setInsets(5,10).setWeight(100,0));
        doArea.add(replaceText,new GBC(0,1).setFill(GBC.NONE).setAnchor(GBC.WEST).setIpad(0,0).setInsets(0,10).setWeight(100,0));
        doArea.add(addText,new GBC(0,2).setFill(GBC.NONE).setAnchor(GBC.WEST).setIpad(0,0).setInsets(0,10).setWeight(100,0));
        doArea.add(format,new GBC(0,3).setFill(GBC.NONE).setAnchor(GBC.WEST).setIpad(0,0).setInsets(0,10).setWeight(100,0));

        // 提醒部分
        egJL = new JLabel("   示 例： ");
        egConJL = new JLabel("1111.jpg");
        //renameBtn = new JButton("重新命名");

        noticeArea = new JPanel(new BorderLayout());
        noticeArea.setBackground(new Color(204,204,204));
        noticeArea.add(egJL,BorderLayout.WEST);
        noticeArea.add(egConJL,BorderLayout.CENTER);
        //noticeArea.add(renameBtn,BorderLayout.EAST);

        //添加组件到 contentsArea
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(fileArea,new GBC(0,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,60).setInsets(10,5));
        contentsArea.add(text,new GBC(0,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,1));
        contentsArea.add(doArea,new GBC(0,2).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,0).setWeight(100,100).setInsets(0,1));
        contentsArea.add(noticeArea,new GBC(0,3).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,1));

        //控制栏
        //aboutBtn = new JButton(new ImageIcon(Cdd2Forte.class.getResource("/icons/about.png")));
        backBtn = new JButton("< 上一步");
        nextBtn = new JButton("下一步 >");
        okBtn = new JButton(" 重命名 ");
        homeBtn = new JButton(" 主功能 ");
        //aboutBtn.setEnabled(false);
        backBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        //设置监听
        renameStyle.setActionCommand("renameStyle");
        renameStyle.addActionListener(this);

        //aboutBtn.setActionCommand("aboutBtn");
        //aboutBtn.addActionListener(this);
        backBtn.setActionCommand("backBtn");
        backBtn.addActionListener(this);
        nextBtn.setActionCommand("nextBtn");
        nextBtn.addActionListener(this);
        okBtn.setActionCommand("cancelBtn");
        okBtn.addActionListener(this);
        homeBtn.setActionCommand("homeBtn");
        homeBtn.addActionListener(this);

        controlBtnArea = new ControlBtnArea(backBtn,nextBtn,okBtn,homeBtn);

        //添加入Frame
        //frame = new JFrame();
        this.add(welcomeArea, BorderLayout.NORTH);
        this.add(contentsArea,BorderLayout.CENTER);
        this.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        this.setTitle("一箱工具 - 批量文件重命名");
        this.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/boxtool_64.png")));
        this.setSize(600,430);
        this.setResizable(false);//固定窗体大小
        this.setLocationRelativeTo(null);//打开时相对window居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("renameStyle")){
            if (renameStyle.getSelectedIndex() == 0){
                replaceText.setVisible(true);
                addText.setVisible(false);
                format.setVisible(false);
            }else if (renameStyle.getSelectedIndex() == 1){
                replaceText.setVisible(false);
                addText.setVisible(true);
                format.setVisible(false);
            }else if (renameStyle.getSelectedIndex() == 2){
                replaceText.setVisible(false);
                addText.setVisible(false);
                format.setVisible(true);
            }
        }
    }
}
