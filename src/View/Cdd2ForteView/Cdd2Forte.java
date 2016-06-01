package View.Cdd2ForteView;

import Common.*;
import Control.Cdd2ForteControl.ProcessorCddLog;
import Control.Cdd2ForteControl.ProcessorCoordinate;
import Control.CommonControl.ReadFile;
import Control.CommonControl.saveFile;
import Control.CommonControl.selectFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;


/**
 * cdd2forte 的界面、调用部分
 * Created by 跃峰 on 2016/1/27.
 */
public class Cdd2Forte extends JFrame implements ActionListener{
    //JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,homeBtn,okBtn,aboutBtn,readCoordinateBtn ,readCddBtn,export2ForteBtn,otherBtn,readExcelCDD,readExcelChannel;
    JPanel contentsArea,left,right;
    JLabel text,otherJlb,dataSource;
    JTextArea progressBar1,progressBar2;
    JCheckBox jcb1,jcb2;
    JComboBox selectDataSource;
    manageProperties mp = new manageProperties();
    ProcessorCddLog pl = null;
    String path = null;
    String[][] cellCoordinate = null;
    ArrayList[][] cddLogArray = null;
    ArrayList[][] WYZJArray = null;
    String coorPath = null;

    public static void main(String[] args) {
        Cdd2Forte c2f = new Cdd2Forte();
        new DistributOutputStream("cdd2forte.log");
    }

    public Cdd2Forte(){
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(Cdd2Forte.class.getResource("/icons/cdd2forte_64.png")),"  一箱工具 - cdd2forte",false);

        //contentsArea
        text = new JLabel("使用 cdd-log 生成 forte 环境，目前只支持爱立信设备。");
        text.setFont(MyTools.fontPlain13);

        progressBar1 = new JTextArea("请先读取坐标文件",1,1);
        progressBar1.setEditable(false);
        progressBar1.setLineWrap(true);
        progressBar1.setBackground(new Color(238,238,238));
        progressBar2 = new JTextArea("",1,1);
        progressBar2.setEditable(false);
        progressBar2.setVisible(false);
        progressBar2.setBackground(new Color(238,238,238));

        readCoordinateBtn = new JButton(" 读取坐标文件 ");
        readCoordinateBtn.setFont(MyTools.fontBold18);
        //注册监听
        readCoordinateBtn.setActionCommand("readCoordinateBtn");
        readCoordinateBtn.addActionListener(this);

        jcb1 = new JCheckBox("使用之前保存的坐标文件");
        jcb2 = new JCheckBox("使用网优之家的数据");
        jcb2.setEnabled(false);

        dataSource = new JLabel("数据源：");
        dataSource.setVisible(false);
        selectDataSource = new JComboBox();
        selectDataSource.addItem("使用导出后的Excel");
        selectDataSource.addItem("直接使用数据库");
        selectDataSource.setVisible(false);

        otherJlb = new JLabel("保存或替换之前的坐标文件");

        path = mp.readProperties("c2f.properties","coordinatePath");
        if (path.equals("")){
            jcb1.setEnabled(false);
        }else {
            jcb1.setEnabled(true);
        }

        //注册监听
        jcb1.setActionCommand("jcb1");
        jcb1.addActionListener(this);
        jcb2.setActionCommand("jcb2");
        jcb2.addActionListener(this);
        selectDataSource.setActionCommand("selectDataSource");
        selectDataSource.addActionListener(this);

        readCddBtn = new JButton("读取 cdd-log");
        readCddBtn.setFont(MyTools.fontBold18);
        readCddBtn.setEnabled(false);

        readExcelCDD = new JButton("cdd");
        readExcelCDD.setFont(MyTools.fontPlain18);
        readExcelCDD.setVisible(false);
        readExcelChannel = new JButton("channel");
        readExcelChannel.setFont(MyTools.fontPlain18);
        readExcelChannel.setVisible(false);

        //注册监听
        readCddBtn.setActionCommand("readCddBtn");
        readCddBtn.addActionListener(this);
        readExcelCDD.setActionCommand("readExcelCDD");
        readExcelCDD.addActionListener(this);
        readExcelChannel.setActionCommand("readExcelChannel");
        readExcelChannel.addActionListener(this);

        export2ForteBtn = new JButton("导出 forte 环境");
        export2ForteBtn.setFont(MyTools.fontBold18);
        export2ForteBtn.setEnabled(false);
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
        contentsArea.setBackground(new Color(238,238,238));
        contentsArea.add(text,new GBC(0,0,4,1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(0,30).setInsets(0,1));
        contentsArea.add(readCoordinateBtn,new GBC(0,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,1,0,0));
        contentsArea.add(readCddBtn,new GBC(2,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));
        contentsArea.add(readExcelCDD,new GBC(2,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,0,0,0));
        contentsArea.add(readExcelChannel,new GBC(3,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,0,0,0));
        contentsArea.add(jcb1,new GBC(0,2,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        contentsArea.add(jcb2,new GBC(1,3,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        //contentsArea.add(dataSource,new GBC(1,3,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.EAST).setIpad(0,5).setInsets(1,1));
        contentsArea.add(selectDataSource,new GBC(2,3,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        contentsArea.add(progressBar1,new GBC(0,4,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        contentsArea.add(progressBar2,new GBC(0,5,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,5).setInsets(0,1));
        contentsArea.add(export2ForteBtn,new GBC(0,6,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0));
        contentsArea.add(otherJlb,new GBC(0,7,3,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(5,5).setInsets(0,1));
        contentsArea.add(otherBtn,new GBC(2,7,2,1).setAnchor(GBC.WEST).setIpad(0,5).setInsets(5,1));

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
        okBtn.setActionCommand("okBtn");
        okBtn.addActionListener(this);
        homeBtn.setActionCommand("homeBtn");
        homeBtn.addActionListener(this);

        controlBtnArea = new ControlBtnArea(aboutBtn,backBtn,nextBtn,okBtn,homeBtn);

        //添加入Frame
        //frame = new JFrame();
        this.add(welcomeArea, BorderLayout.NORTH);
        this.add(contentsArea,BorderLayout.CENTER);
        //this.add(temp1,BorderLayout.EAST);
        //this.add(temp2,BorderLayout.WEST);
        this.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        this.setTitle("一箱工具 - cdd2forte");
        this.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/cdd2forte_64.png")));

        this.setSize(626,500);
        this.setResizable(false);//固定窗体大小
        this.setLocationRelativeTo(null);//打开时相对window居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        pl = new ProcessorCddLog();
        if (e.getActionCommand() == "readCoordinateBtn"){
            if (cellCoordinate != null){
                Object[] options = {"确定","我手贱"};
                int response=JOptionPane.showOptionDialog(this, "再次读取该数据会清空上一次的数据！", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(response==0) {
                    coorPath = null;
                    cellCoordinate = null;
                    cddLogArray = null;
                    readCddBtn.setEnabled(false);
                    export2ForteBtn.setEnabled(false);
                    otherBtn.setEnabled(false);
                }else if(response==1) {
                }
            }
            if (cellCoordinate == null){
                int state;
                //按下 cellCoordinate 后重置的控件
                progressBar2.setVisible(false);
                progressBar2.setText("");
                jcb1.setEnabled(false);
                jcb1.setSelected(false);
                selectFile select = new selectFile();
                state = select.selectFile("读取坐标文件", "text",JFileChooser.FILES_ONLY,false,this);
                File[] fileList = new ReadFile().readMultiText(select.getFile());
                if (state == 0) {
                    //如果点击确定
                    ProcessorCoordinate pc = new ProcessorCoordinate();
                    if (fileList != null && fileList.length == 1) {
//                        System.out.println("ccccccccccccc" + fileList.length);
                        cellCoordinate = pc.readCoordinates(fileList[0]);
                    }
                    if (pc.getNotice().contains("处理完毕")) {
                        //JOptionPane.showMessageDialog(null,"读取成功");
                        progressBar1.setText(pc.getNotice());
                        readCddBtn.setEnabled(true);
                        otherBtn.setEnabled(true);
                        coorPath = fileList[0].getPath();
                    }else {
                        cellCoordinate = null;
                        progressBar1.setText("坐标文件错误!");
                    }
                }else if (state == 1) {
                    if (cellCoordinate == null) {
                        progressBar1.setText("未选择坐标文件");
                    }
                }
            }
        }else if (e.getActionCommand() == "readCddBtn"){
            if (cddLogArray != null){
                Object[] options = {"确定","我手贱"};
                int response=JOptionPane.showOptionDialog(this, "再次读取该数据会清空上一次的数据！", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(response==0) {
                    cddLogArray = null;
                    export2ForteBtn.setEnabled(false);
                }else if(response==1) {
                }
            }
            if (cddLogArray == null){
                int state;
                progressBar2.setVisible(true);
                progressBar2.setText("正在处理 cdd-log 文件...");

                selectFile select = new selectFile();
                state = select.selectFile("读取 cdd-log", "text",JFileChooser.FILES_AND_DIRECTORIES,true,this);
                File[] fileList = new ReadFile().readMultiText(select.getFile());
                if (state == 0) {
                    //如果点击确定
                    if (fileList != null) {
                        try {
                            long startTime=System.currentTimeMillis();
                            cddLogArray = pl.processorMultiLog(fileList, cellCoordinate);
                            long endTime=System.currentTimeMillis();
                            System.out.println("cdd读取时间： "+(endTime-startTime)/1000+"s");
                            JOptionPane.showMessageDialog(null,"cdd-log 读取成功");
                            progressBar2.setVisible(true);
                            progressBar2.setText(pl.getNotice());
                        }catch (Exception e1){
                            e1.printStackTrace();
                            progressBar2.setVisible(false);
                            progressBar2.setText("");
                            JOptionPane.showMessageDialog(null,"cdd-log 读取失败，文件错误！");
                        }
                        if (pl.getNotice().contains("错误")){
                            cddLogArray = null;
                            progressBar2.setVisible(true);
                            progressBar2.setText("cdd-log 文件错误！");
                        }
                        if (cddLogArray != null) {
                            export2ForteBtn.setEnabled(true);
                        }
                    }else {
                        System.out.println("未选择cdd文件");
                    }
                } else if (state == 1) {
                    progressBar2.setVisible(true);
                    progressBar2.setText("未选择 cdd-log 文件");
                }
            }
        }else if (e.getActionCommand() == "export2ForteBtn"){
            saveFile save = new saveFile();
            int state = save.saveFile("导出 forte 环境",1,this);
            if (state == 0){
                pl.createForteFile(cddLogArray,save.getFile().getPath());
            }else if (state == 1){
            }
        }else if (e.getActionCommand() == "aboutBtn"){
            new C2FNotice();
        }else if (e.getActionCommand().equals("backBtn")){
        }else if (e.getActionCommand().equals("nextBtn")){
        }else if (e.getActionCommand().equals("okBtn")){
            if(JOptionPane.showConfirmDialog(this,"退出","提示",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                //System.exit(0);
                this.dispose();
            }
        }else if (e.getActionCommand().equals("homeBtn")){
            //JOptionPane.showMessageDialog(null,"其他功能还在测试，暂时不可用。","主功能",JOptionPane. WARNING_MESSAGE);
            this.dispose();
        }else if (e.getActionCommand().equals("otherBtn")){
            mp.writeProperties("c2f.properties","coordinatePath",coorPath);
            path = mp.readProperties("c2f.properties","coordinatePath");
            jcb1.setEnabled(true);
            JOptionPane.showMessageDialog(null,"成功");
        }else if (e.getActionCommand().equals("jcb1")){
            if (jcb1.isSelected()){
                if (cellCoordinate != null){
                    Object[] options = {"确定","我手贱"};
                    int response=JOptionPane.showOptionDialog(this, "再次读取该数据会清空上一次的数据！", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(response==0) {
                        coorPath = null;
                        cellCoordinate = null;
                        cddLogArray = null;
                        progressBar2.setVisible(false);
                        progressBar2.setText("");
                        otherBtn.setEnabled(false);
                        readCddBtn.setEnabled(false);
                        //readCddBtn.setVisible(true);
                        jcb2.setEnabled(false);
                        selectDataSource.setEnabled(false);
                        readExcelCDD.setEnabled(false);
                        readExcelChannel.setEnabled(false);
                        export2ForteBtn.setEnabled(false);
                    }else if(response==1) {
                        jcb1.setSelected(false);
                    }
                }
                if (cellCoordinate == null) {
                    readCoordinateBtn.setEnabled(false);
                    readCddBtn.setEnabled(true);
                    //readCddBtn.setVisible(true);
                    jcb2.setEnabled(true);
                    ProcessorCoordinate pc = new ProcessorCoordinate();
                    cellCoordinate = pc.readCoordinates(new File(path));
                    progressBar1.setText("选定："+path);
                    if (pc.getNotice().contains("不存在")){
                        progressBar1.setText(pc.getNotice());
                        readCddBtn.setEnabled(false);
                        //readCddBtn.setVisible(true);
                        jcb2.setEnabled(false);
                        selectDataSource.setEnabled(false);
                        readExcelCDD.setEnabled(false);
                        readExcelChannel.setEnabled(false);
                        readCoordinateBtn.setEnabled(true);
                    }
                }
            }else {
                if (cddLogArray != null){
                    Object[] options = {"确定","我手贱"};
                    int response=JOptionPane.showOptionDialog(this, "再次读取该数据会清空上一次的数据！", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(response==0) {
                        cddLogArray = null;
                        export2ForteBtn.setEnabled(false);
                        progressBar2.setVisible(false);
                        progressBar2.setText("");
                    }else if(response==1) {
                        jcb1.setSelected(true);
                    }
                }
                if (cddLogArray == null) {
                    readCoordinateBtn.setEnabled(true);
                    readCddBtn.setEnabled(false);
                    //readCddBtn.setVisible(true);
                    jcb2.setEnabled(false);
                    selectDataSource.setEnabled(false);
                    readExcelCDD.setEnabled(false);
                    readExcelChannel.setEnabled(false);
                    cellCoordinate = null;
                    cddLogArray = null;
                    progressBar1.setText("未选择坐标文件");
                }
            }
        }else if (e.getActionCommand().equals("jcb2")){
            if (jcb2.isSelected()){
                dataSource.setVisible(true);
                selectDataSource.setEnabled(true);
                selectDataSource.setVisible(true);
                readCddBtn.setVisible(false);
                readExcelCDD.setVisible(true);
                readExcelChannel.setVisible(true);
                if (selectDataSource.getSelectedIndex()==0){
                    readExcelCDD.setEnabled(true);
                    readExcelChannel.setEnabled(true);
                }else {
                    readExcelCDD.setEnabled(false);
                    readExcelChannel.setEnabled(false);

                }
            }else {
                dataSource.setVisible(false);
                selectDataSource.setVisible(false);
                readCddBtn.setVisible(true);
                readExcelCDD.setVisible(false);
                readExcelChannel.setVisible(false);
            }
        }else if (e.getActionCommand().equals("selectDataSource")){
            if (selectDataSource.isVisible() && selectDataSource.getSelectedIndex()==0){
                readExcelCDD.setEnabled(true);
                readExcelChannel.setEnabled(true);
            }else if (selectDataSource.isVisible() && selectDataSource.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"数据库连接成功");
                readExcelCDD.setEnabled(false);
                readExcelChannel.setEnabled(false);
            }
        }else if (e.getActionCommand().equals("readExcelCDD")){
            if (WYZJArray == null){
                int state;
                progressBar2.setVisible(true);
                progressBar2.setText("正在处理 现网cdd 文件...");

                selectFile select = new selectFile();
                state = select.selectFile("读取 现网cdd","xls",JFileChooser.FILES_ONLY,false,this);
                File[] fileList = new ReadFile().readMultiText(select.getFile());
                if (state == 0){
                }else if (state == 1) {
                    progressBar2.setVisible(true);
                    progressBar2.setText("未选择 现网cdd 文件");
                }
            }
        }else if (e.getActionCommand().equals("readExcelChannel")){

        }
    }
}
