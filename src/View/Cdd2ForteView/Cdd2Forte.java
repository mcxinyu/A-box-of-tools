package View.Cdd2ForteView;

import Common.saveFile;
import Common.selectFile;
import Control.C2FControl.ProcessorCoordinate;
import Control.C2FControl.ProcessorLog;
import Control.C2FControl.ReadFile;
import View.CheckPlanView.GBC;
import Common.WelcomeArea;
import Common.ControlBtnArea;
import Common.MyTools;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * cdd2forte 的界面部分
 * Created by 跃峰 on 2016/1/27.
 */
public class Cdd2Forte extends JFrame implements ActionListener{
    JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,homeBtn,okBtn,aboutBtn,readCoordinateBtn ,readCddBtn,export2ForteBtn,otherBtn;
    JPanel contentsArea;
    JLabel temp1,temp2,text,progressBar1,progressBar2,otherJlb;
    JCheckBox jcb1,jcb2;

    public static void main(String[] args) {
        Cdd2Forte c2f = new Cdd2Forte();
    }
    public Cdd2Forte(){
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea("images/cdd2forte_64.png","  一箱工具 - cdd2forte");

        //contentsArea
        text = new JLabel("使用 cdd-log 生成 forte 环境，目前只支持爱立信设备。");
        text.setFont(MyTools.fontPlain13);
        temp1 = new JLabel("                    ");
        temp2 = new JLabel("                    ");
        progressBar1 = new JLabel("请先读取坐标文件");
        progressBar2 = new JLabel("");

        readCoordinateBtn = new JButton(" 读取坐标文件 ");
        readCoordinateBtn.setFont(MyTools.fontBold18);
        //注册监听
        readCoordinateBtn.setActionCommand("readCoordinateBtn");
        readCoordinateBtn.addActionListener(this);

        jcb1 = new JCheckBox("使用之前保存的坐标文件");
        jcb1.setEnabled(false);
        otherJlb = new JLabel("保存或替换之前的坐标文件");

        readCddBtn = new JButton("读取 cdd-log");
        readCddBtn.setFont(MyTools.fontBold18);
//        readCddBtn.setEnabled(false);
        //注册监听
        readCddBtn.setActionCommand("readCddBtn");
        readCddBtn.addActionListener(this);

        export2ForteBtn = new JButton("导出 forte 环境");
        export2ForteBtn.setFont(MyTools.fontBold18);
//        export2ForteBtn.setEnabled(false);
        //注册监听
        export2ForteBtn.setActionCommand("export2ForteBtn");
        export2ForteBtn.addActionListener(this);

        otherBtn = new JButton("保存坐标文件");
        otherBtn.setEnabled(false);
        //注册监听
        otherBtn.setActionCommand("otherBtn");
        otherBtn.addActionListener(this);

        //添加组件到 contentsArea
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(text,new GBC(0,0,4,1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(0,30).setInsets(0,1));
        contentsArea.add(readCoordinateBtn,new GBC(0,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,1,0,0));
        contentsArea.add(readCddBtn,new GBC(2,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));
        contentsArea.add(jcb1,new GBC(0,2,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        contentsArea.add(progressBar1,new GBC(0,3,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        contentsArea.add(progressBar2,new GBC(0,4,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        contentsArea.add(export2ForteBtn,new GBC(0,5,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0));
        contentsArea.add(otherJlb,new GBC(0,6,3,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(5,5).setInsets(0,1));
        contentsArea.add(otherBtn,new GBC(3,6,1,1).setAnchor(GBC.WEST).setIpad(0,5).setInsets(5,1));

        //控制栏
        aboutBtn = new JButton(new ImageIcon("images/about.png"));
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
        homeBtn.setActionCommand("homeBtn");
        homeBtn.addActionListener(this);
        okBtn.setActionCommand("cancelBtn");
        okBtn.addActionListener(this);

        controlBtnArea = new ControlBtnArea(backBtn,nextBtn,okBtn,homeBtn,aboutBtn);

        //添加入Frame
        frame = new JFrame();
        frame.add(welcomeArea, BorderLayout.NORTH);
        frame.add(contentsArea,BorderLayout.CENTER);
        frame.add(temp1,BorderLayout.EAST);
        frame.add(temp2,BorderLayout.WEST);
        frame.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        frame.setTitle("一箱工具 - cdd2forte");
        frame.setIconImage (Toolkit.getDefaultToolkit().getImage("images/boxtool_64.png"));

        frame.setSize(626,500);
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    String[][] cellCoordinate = null;
    ArrayList[][] forteArray = null;
    @Override
    public void actionPerformed(ActionEvent e) {
        ProcessorLog pl = new ProcessorLog();
        if (e.getActionCommand() == "readCoordinateBtn"){
            if (cellCoordinate != null){
                Object[] options = {"确定","我手贱"};
                int response=JOptionPane.showOptionDialog(this, "再次读取会清空上一次的数据！", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(response==0) {
                    cellCoordinate = null;
                    forteArray = null;
                    readCddBtn.setEnabled(false);
                    export2ForteBtn.setEnabled(false);
                }else if(response==1) {
                }
            }
            if (cellCoordinate == null){
                int state;
                progressBar2.setText("");
                selectFile select = new selectFile();
                state = select.selectFile("读取坐标文件", "text");
                File[] fileList = new ReadFile().readMultiText(select.getFile());
                if (state == 0) {
                    //如果点击确定
                    ProcessorCoordinate pc = new ProcessorCoordinate();
                    if (fileList != null && fileList.length == 1) {
                        System.out.println("ccccccccccccc" + fileList.length);
                        cellCoordinate = pc.processorSingleLog(fileList[0]);
                    }
                    if (pc.getNotice().contains("处理完毕")) {
                        progressBar1.setText(pc.getNotice());
                        readCddBtn.setEnabled(true);
                        otherBtn.setEnabled(true);
                    } else {
                        cellCoordinate = null;
                        progressBar1.setText("坐标文件错误!");
                    }
                } else if (state == 1) {
                    if (cellCoordinate == null) {
                        progressBar1.setText("未选择坐标文件");
                    }
                }
            }
        }else if (e.getActionCommand() == "readCddBtn"){
            if (forteArray != null){
                Object[] options = {"确定","我手贱"};
                int response=JOptionPane.showOptionDialog(this, "再次读取会清空上一次的 cdd-log 数据！", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(response==0) {
                    forteArray = null;
                    export2ForteBtn.setEnabled(false);
                }else if(response==1) {
                }
            }
            if (forteArray == null){
                int state;
                progressBar2.setText("");
                selectFile select = new selectFile();
                state = select.selectFile("读取 cdd-log", "text");
                File[] fileList = new ReadFile().readMultiText(select.getFile());
                if (state == 0) {
                    //如果点击确定
                    if (fileList != null) {
                        forteArray = pl.processorMultiLog(fileList, cellCoordinate);
                        progressBar2.setText(pl.getNotice());
                        if (forteArray != null) {
                            export2ForteBtn.setEnabled(true);
                        }
                    }
                } else if (state == 1) {
                    progressBar2.setText("未选择 cdd-log 文件");
                }
            }
        }else if (e.getActionCommand() == "export2ForteBtn"){
            saveFile save = new saveFile();
            int state = save.saveFile("导出 forte 环境",1);
            if (state == 0){
                pl.createForteFile(forteArray,save.getFile().getPath());
                System.out.println("可以保存");
            }else if (state == 1){
                System.out.println("取消保存");
            }
        }else if (e.getActionCommand() == "aboutBtn"){
            new C2FNotice();
        }else if (e.getActionCommand().equals("backBtn")){
        }else if (e.getActionCommand().equals("nextBtn")){
        }else if (e.getActionCommand().equals("okBtn")){
        }else if (e.getActionCommand().equals("homeBtn")){
            JOptionPane.showMessageDialog(null,"其他功能还在测试，暂时不可用。","主功能",JOptionPane. WARNING_MESSAGE);
        }
        else if (e.getActionCommand().equals("otherBtn")){
            JOptionPane.showMessageDialog(null,"成功");
        }
    }
}
