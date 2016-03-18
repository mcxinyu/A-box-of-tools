package View.RenameFilesView;

/**
 * 批量文件重命名
 * Created by 跃峰 on 2016/3/8.
 */

import Common.ControlBtnArea;
import Common.MyTools;
import Common.WelcomeArea;
import Control.CommonControl.selectFile;
import Control.RenameFilesControl.Rename;
import View.Cdd2ForteView.Cdd2Forte;
import View.CheckPlanView.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RenameFiles extends JFrame implements ActionListener,Runnable {
    //JFrame frame;
    JPanel welcomeArea,fileArea,controlBtnArea,doArea,noticeArea,contentsArea;
    JPanel selectBar,replaceText,addText,format,fileListArea;
    JButton selectFileBtn,addBtn,backBtn,nextBtn,homeBtn,renameBtn,aboutBtn;
    JLabel selectJL,text,findJL,replaceJL,egJL,egConJL,nameFormatJL,locationJL,customFromatJL,startFromNum;
    JTextField findJTF,addJTF,replaceJTF,customFromatJTF,startFromNumJTF;
    JComboBox renameStyle,nameFormat,location_add,location_fromat;
    JScrollPane fileListAreaScroll;
    int height = 66;
    String customFromatText = "";
    Rename rn = new Rename();

    //public static void main(String[] args) {
    //    RenameFiles rn = new RenameFiles();
    //    Thread thread = new Thread(rn);
    //    thread.start();
    //}

    public RenameFiles(){
        Thread thread = new Thread(this);
        thread.start();
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(Cdd2Forte.class.getResource("/icons/rename_64.png")),"  一箱工具 - 批量文件重命名");

        // 选择的项目、文件
        selectJL = new JLabel("选取要重命名的项目");
        selectJL.setFont(MyTools.fontBold13);
        selectFileBtn = new JButton(" 选 取 ");
        addBtn = new JButton(" 添 加 ");
        addBtn.setVisible(false);
        selectBar = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        selectBar.add(selectJL);
        selectBar.add(selectFileBtn);
        selectBar.add(addBtn);

        fileListArea = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        fileListArea.setPreferredSize(new Dimension(580, height));
        fileListAreaScroll = new JScrollPane(fileListArea);
        fileListAreaScroll.setPreferredSize(new Dimension(580, 90));
        fileListAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fileListAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        fileArea = new JPanel(new BorderLayout());
        fileArea.add(selectBar,BorderLayout.NORTH);
        fileArea.add(fileListAreaScroll,BorderLayout.CENTER);

        //contentsArea
        text = new JLabel("    给选取的项目重命名");
        text.setFont(MyTools.fontBold13);

        // 重命名方式
        renameStyle = new JComboBox();
        renameStyle.addItem(" 替换文本 ");  //0
        renameStyle.addItem(" 添加文本 ");  //1
        renameStyle.addItem(" 格式 ");    //2

        // 替换文本0
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

        // 添加文本1
        addJTF = new JTextField(15);
        location_add = new JComboBox();
        location_add.addItem(" 于名称之后 ");    //0
        location_add.addItem(" 于名称之前 ");    //1
        addText = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addText.setVisible(false);
        addText.add(addJTF);
        addText.add(location_add);

        // 格式2
        nameFormatJL = new JLabel("名称格式：");
        nameFormat = new JComboBox();
        nameFormat.addItem(" 名称和索引 ");  //0
        nameFormat.addItem(" 名称和计数 ");  //1
        nameFormat.addItem(" 名称和日期 ");  //2
        locationJL = new JLabel("位 置：");
        location_fromat = new JComboBox();
        location_fromat.addItem(" 于名称之后 "); //0
        location_fromat.addItem(" 于名称之前 "); //1
        customFromatJL =  new JLabel("自定名称：");
        customFromatJTF = new JTextField(15);
        startFromNum = new JLabel("开始数字为：");
        startFromNumJTF = new JTextField(15);
        startFromNumJTF.setText("0");
        startFromNumJTF.addKeyListener(new KeyAdapter(){    //监听，限制只输入数字,最大 9223372036854775807
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 ){
                    if (!startFromNumJTF.getText().equals("") && (startFromNumJTF.getText().length() >= 19 || (new BigInteger(startFromNumJTF.getText()).compareTo(new BigInteger("9223372036854775800")) == 1 ))){
                        //JOptionPane.showMessageDialog(null,"超过最大值9223372036854775807");
                        startFromNumJTF.setText("922337203685477480");  // 9223372036854775807 千位减一，去掉个位
                    }
                }else{
                    e.consume(); //关键，屏蔽掉非法输入
                }
            }
        });
        startFromNumJTF.addFocusListener(new FocusAdapter() {   //监听，设置占位符
            @Override
            public void focusGained(FocusEvent e) {
                //System.out.println("focusGained");
                if (startFromNumJTF.isEditable() && startFromNumJTF.getText().equals("0")){ //如果不是可编辑的状态，那么点击的时候就置空
                    startFromNumJTF.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                //System.out.println("focusLost");
                if (startFromNumJTF.getText().length()==0){ //如果为空就设置默认值
                    startFromNumJTF.setText("0");
                }
            }
        });

        format = new JPanel(new GridLayout(2,4,10,5));
        format.setVisible(false);
        format.add(nameFormatJL);
        format.add(nameFormat);
        format.add(locationJL);
        format.add(location_fromat);
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
        egConJL = new JLabel("example.txt");
        //renameBtn = new JButton("重新命名");

        noticeArea = new JPanel(new BorderLayout());
        noticeArea.setBackground(new Color(204,204,204));
        noticeArea.add(egJL,BorderLayout.WEST);
        noticeArea.add(egConJL,BorderLayout.CENTER);
        //noticeArea.add(renameBtn,BorderLayout.EAST);

        //添加组件到 contentsArea
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(fileArea,new GBC(0,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,90).setInsets(10,5));
        contentsArea.add(text,new GBC(0,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,1));
        contentsArea.add(doArea,new GBC(0,2).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,0).setWeight(100,100).setInsets(0,1));
        contentsArea.add(noticeArea,new GBC(0,3).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,1));

        //控制栏
        //aboutBtn = new JButton(new ImageIcon(Cdd2Forte.class.getResource("/icons/about.png")));
        backBtn = new JButton("< 上一步");
        nextBtn = new JButton("下一步 >");
        renameBtn = new JButton(" 重命名 ");
        homeBtn = new JButton(" 主功能 ");
        //aboutBtn.setEnabled(false);
        backBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        controlBtnArea = new ControlBtnArea(backBtn,nextBtn,renameBtn,homeBtn);

        //设置监听addBtn
        renameStyle.setActionCommand("renameStyle");
        renameStyle.addActionListener(this);
        selectFileBtn.setActionCommand("selectFileBtn");
        selectFileBtn.addActionListener(this);
        addBtn.setActionCommand("addBtn");
        addBtn.addActionListener(this);
        nameFormat.setActionCommand("nameFormat");
        nameFormat.addActionListener(this);
        startFromNumJTF.setActionCommand("startFromNumJTF");
        startFromNumJTF.addActionListener(this);

        backBtn.setActionCommand("backBtn");
        backBtn.addActionListener(this);
        nextBtn.setActionCommand("nextBtn");
        nextBtn.addActionListener(this);
        renameBtn.setActionCommand("renameBtn");
        renameBtn.addActionListener(this);
        homeBtn.setActionCommand("homeBtn");
        homeBtn.addActionListener(this);

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

    int count = 0;
    int oneLineWidth = 10;
    int totalLineHeight = 10;
    List<File> fileListArray = null;
    File[] filesList = null;
    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(oneLineWidth);
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
        }else if(e.getActionCommand().equals("nameFormat")){
            if (nameFormat.getSelectedIndex() == 0){
                startFromNumJTF.setEditable(true);
            }else if (nameFormat.getSelectedIndex() == 1){
                startFromNumJTF.setEditable(true);
            }else if (nameFormat.getSelectedIndex() == 2){
                startFromNumJTF.setEditable(false);
            }
        }else if (e.getActionCommand().equals("selectFileBtn")){
            if (filesList != null){
                Object[] options = {"确定","我手贱"};
                int response=JOptionPane.showOptionDialog(this, "清空已选取的项目？", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(response==0) {
                    filesList = null;
                    fileListArray = null;
                    fileListArea.removeAll();
                    fileListArea.setPreferredSize(new Dimension(580, 66));
                    fileListArea.revalidate();
                    fileListArea.repaint();
                    addBtn.setVisible(false);
                }
            }
            if (filesList == null) {
                int state;
                selectFile select = new selectFile();
                state = select.selectFile("选取项目", null);
                filesList = select.getFile();
                fileListArray = new ArrayList();
                if (state == 0) {
                    //如果点击确定
                    for (int i = 0; i < filesList.length; i++) {
                        String fileName = " " + filesList[i].getName() + " ";
                        JLabel jl = new JLabel(fileName);
                        jl.setOpaque(true);
                        jl.setBackground(new Color(84, 189, 241));
                        fileListArea.add(jl);
                        fileListArray.add(filesList[i]);
                        //System.out.println(jl.getFontMetrics(jl.getFont()).getHeight());
                        oneLineWidth += jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 5;  //合计每行的 JLabel 的宽度和间隔
                        if (oneLineWidth >= 580) {
                            totalLineHeight += jl.getFontMetrics(jl.getFont()).getHeight() + 5;  //当 JLabel 的宽度大于JPanel的宽度时，总高度加一个 JLabel 的高度和间隔
                            if (totalLineHeight >= height) {
                                height = totalLineHeight;    //当行高的高度大于原来设置的高度时，设置原来的高度等于总高度
                                fileListArea.setPreferredSize(new Dimension(580, totalLineHeight + 20));  //设置JPanel的高度为总高度，为什么加76？
                            }
                            oneLineWidth = jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 10;  //值得再考虑！！每次没有放满一行后的元素宽度
                        }
                    }
                    addBtn.setVisible(true);
                    fileListArea.revalidate();
                }else if (state == 1){
                    filesList = null;
                    fileListArray = null;
                }
            }
        }else if (e.getActionCommand().equals("addBtn")){
            if (filesList != null) {
                int state;
                selectFile select = new selectFile();
                state = select.selectFile("添加项目", null);
                filesList = select.getFile();
                //fileListArray = new ArrayList();
                if (state == 0) {
                    //如果点击确定
                    for (int i = 0; i < filesList.length; i++) {
                        String fileName = " " + filesList[i].getName() + " ";
                        JLabel jl = new JLabel(fileName);
                        jl.setOpaque(true);
                        jl.setBackground(new Color(84, 189, 241));
                        fileListArea.add(jl);
                        fileListArray.add(filesList[i]);
                        oneLineWidth += jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 5;  //合计每行的 JLabel 的宽度和间隔
                        if (oneLineWidth >= 580) {
                            totalLineHeight += jl.getFontMetrics(jl.getFont()).getHeight() + 5;  //当 JLabel 的宽度大于JPanel的宽度时，总高度加一个 JLabel 的高度和间隔
                            if (totalLineHeight >= height) {
                                height = totalLineHeight;    //当行高的高度大于原来设置的高度时，设置原来的高度等于总高度
                                fileListArea.setPreferredSize(new Dimension(580, totalLineHeight + 21));  //设置JPanel的高度为总高度，为什么加76？
                            }
                            oneLineWidth = jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 10;  //值得再考虑！！每次没有放满一行后的元素宽度
                        }
                    }
                    fileListArea.revalidate();
                }
            }
        }else if (e.getActionCommand().equals("renameBtn")){
            File[] filesListReal = null;
            if (filesList != null){
                filesListReal = fileListArray.toArray(new File[0]);
            }else {
                JOptionPane.showMessageDialog(null,"请先选取要重命名的项目！");
            }
            if (renameStyle.getSelectedIndex() == 0){   //替换0---------------
                if (!findJTF.getText().equals("")){
                    rn.replaceText(filesListReal,findJTF.getText(), replaceJTF.getText());
                }else {
                    JOptionPane.showMessageDialog(null,"请先填写条件！");
                }
            }else if (renameStyle.getSelectedIndex() == 1){
                if (!addJTF.getText().equals("")){
                    rn.addText(filesListReal,addJTF.getText(), location_add.getSelectedIndex());
                }else {
                    JOptionPane.showMessageDialog(null,"请先填写条件！");
                }
            }else if (renameStyle.getSelectedIndex() == 2){
                if (!customFromatJTF.getText().equals("")){
                    rn.format(filesListReal,nameFormat.getSelectedIndex(), location_fromat.getSelectedIndex(),customFromatJTF.getText(),Long.parseLong(startFromNumJTF.getText()));
                }else {
                    JOptionPane.showMessageDialog(null,"请先填写条件！");
                }
            }
        }
    }

    @Override
    public void run() {
        //int time = 0;
        while (true){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            //time++;
            if (fileListArray != null){
                if (renameStyle.getSelectedIndex() == 0){   //替换0---------------
                    if (findJTF.getText().equals("")){
                        egConJL.setText(fileListArray.get(0).getName());
                    }else {
                        egConJL.setText(fileListArray.get(0).getName().replace(findJTF.getText(), replaceJTF.getText()));
                    }
                }else if (renameStyle.getSelectedIndex() == 1){ //添加1---------------
                    if (location_add.getSelectedIndex() == 0){  //后面
                        if (addJTF.getText().equals("")){
                            egConJL.setText(fileListArray.get(0).getName());
                        }else {
                            String newName = fileListArray.get(0).getName().substring(0, fileListArray.get(0).getName().lastIndexOf("."))+addJTF.getText()+fileListArray.get(0).getName().substring(fileListArray.get(0).getName().lastIndexOf("."));
                            egConJL.setText(newName);
                        }
                    }else if (location_add.getSelectedIndex() == 1){    //前面
                        if (addJTF.getText().equals("")){
                            egConJL.setText(fileListArray.get(0).getName());
                        }else {
                            egConJL.setText(addJTF.getText()+fileListArray.get(0).getName());
                        }
                    }
                }else if (renameStyle.getSelectedIndex() == 2){ //格式2---------------
                    if (nameFormat.getSelectedIndex() == 0){    //索引0-------
                        customFromatText = "";
                        if (location_fromat.getSelectedIndex() == 0 && !startFromNumJTF.getText().equals("")){   //后面
                            if (customFromatJTF.getText().equals("")){
                                egConJL.setText(fileListArray.get(0).getName());
                            }else {
                                String s = customFromatJTF.getText() + startFromNumJTF.getText() + fileListArray.get(0).getName().substring(fileListArray.get(0).getName().lastIndexOf("."));
                                egConJL.setText(s);
                            }
                        }else if (location_fromat.getSelectedIndex() == 1 && !startFromNumJTF.getText().equals("")){ //前面
                            if (customFromatJTF.getText().equals("")){
                                egConJL.setText(fileListArray.get(0).getName());
                            }else {
                                String s = startFromNumJTF.getText() + customFromatJTF.getText() + fileListArray.get(0).getName().substring(fileListArray.get(0).getName().lastIndexOf("."));
                                egConJL.setText(s);
                            }
                        }
                    }else if (nameFormat.getSelectedIndex() == 1){  //计数1-------
                        customFromatText = "";
                        if (location_fromat.getSelectedIndex() == 0 && !startFromNumJTF.getText().equals("")){   //后面
                            if (customFromatJTF.getText().equals("")){
                                egConJL.setText(fileListArray.get(0).getName());
                            }else {
                                String s = customFromatJTF.getText() + rn.s2index("",Long.parseLong(startFromNumJTF.getText())) + fileListArray.get(0).getName().substring(fileListArray.get(0).getName().lastIndexOf("."));
                                egConJL.setText(s);
                            }
                        }else if (location_fromat.getSelectedIndex() == 1 && !startFromNumJTF.getText().equals("")){ //前面
                            if (customFromatJTF.getText().equals("")){
                                egConJL.setText(fileListArray.get(0).getName());
                            }else {
                                String s = rn.s2index("",Long.parseLong(startFromNumJTF.getText())) + customFromatJTF.getText() + fileListArray.get(0).getName().substring(fileListArray.get(0).getName().lastIndexOf("."));
                                egConJL.setText(s);
                            }
                        }
                    }else if (nameFormat.getSelectedIndex() == 2){  //日期2-------
                        Date nowdDate = new Date();
                        DateFormat nowdDateDF = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
                        if (location_fromat.getSelectedIndex() == 0){   //后面
                            if (customFromatJTF.getText().equals("")){
                                egConJL.setText(fileListArray.get(0).getName());
                            }else if (!customFromatJTF.getText().equals(customFromatText)){
                                customFromatText = customFromatJTF.getText();
                                String s = customFromatJTF.getText() + nowdDateDF.format(nowdDate) + fileListArray.get(0).getName().substring(fileListArray.get(0).getName().lastIndexOf("."));
                                egConJL.setText(s);
                            }
                        }else if (location_fromat.getSelectedIndex() == 1){ //前面
                            if (customFromatJTF.getText().equals("")){
                                egConJL.setText(fileListArray.get(0).getName());
                            }else if (!customFromatJTF.getText().equals(customFromatText)){
                                customFromatText = customFromatJTF.getText();
                                String s = nowdDateDF.format(nowdDate) + customFromatJTF.getText() + fileListArray.get(0).getName().substring(fileListArray.get(0).getName().lastIndexOf("."));
                                egConJL.setText(s);
                            }
                        }
                    }
                }
            }else if (fileListArray == null){
                egConJL.setText("example.txt");
            }
        }
    }
}
