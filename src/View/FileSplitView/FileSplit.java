package View.FileSplitView;

import Common.ControlBtnArea;
import Common.MyTools;
import Common.WelcomeArea;
import Control.CommonControl.ReadFile;
import Control.CommonControl.saveFile;
import Control.CommonControl.selectFile;
import Control.FileSplitControl.TxtSplit;
import View.CheckPlanView.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * 分割大文本文件为小文件
 * Created by 跃峰 on 2016/2/3.
 */
public class FileSplit extends JFrame implements ActionListener,Runnable {
    //JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton aboutBtn,backBtn,nextBtn,okBtn,homeBtn,selectBtn1,selectBtn2,addBtn;
    JPanel contentsArea,setArea,noticeArea;
    JLabel lineNum,text,importJlb,setJlb,noticeOld,noticeNew;
    JTextField filePath,setNum;
    JPanel fileListArea;
    JScrollPane fileListAreaScroll;
    JComboBox setJcb;
    JCheckBox setTitleJcb,onlySaveFirstTitle,noticeJcb;
    String title = "分割";
    int height = 66;
    volatile boolean windowRuning = true;

    public static void main(String[] args) {
        FileSplit fp = new FileSplit();
    }
    public FileSplit(){
        MyTools.windowsFeel();

        Thread thread = new Thread(this);
        thread.start();
        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(this.getClass().getResource("/icons/split_64.png")),"  一箱工具 - 文本"+title);

        // 选择文件饿设置部分
        text = new JLabel("可将文本文件分割为小文件，解决 excel 无法打开大文件的困难。");
        text.setFont(MyTools.fontPlain13);
        text.setOpaque(true);

        importJlb = new JLabel("选择文件");
        importJlb.setFont(MyTools.fontBold13);
        filePath = new JTextField();
        filePath.setEditable(false);
        //filePath.setPreferredSize(new Dimension(350,20));
        selectBtn1 = new JButton(" 选  择 ");
        selectBtn2 = new JButton(" 选  择 ");
        selectBtn2.setVisible(false);
        addBtn = new JButton(" 添  加 ");
        addBtn.setVisible(false);

        fileListArea = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        fileListArea.setPreferredSize(new Dimension(300, height));
        fileListAreaScroll = new JScrollPane(fileListArea);
        fileListAreaScroll.setPreferredSize(new Dimension(300, 90));
        fileListAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fileListAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        fileListAreaScroll.setVisible(false);

        setJlb = new JLabel("设置分割颗粒");
        setJlb.setFont(MyTools.fontBold13);
        setJcb = new JComboBox();
        setJcb.addItem("适合 Excel 97-2003");   // 65536+256
        setJcb.addItem("适合 Excel 2007 及之后");  // 1048576+16384
        setJcb.addItem("平均分割为若干份");
        setJcb.addItem("自定义行数");

        lineNum = new JLabel("行数：");
        setNum = new JTextField("65536");
        setNum.setEditable(false);
        setNum.addKeyListener(new KeyAdapter(){    //监听，限制只输入数字,最大 1048576
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 ){
                    if (!setNum.getText().equals("") && (setNum.getText().length() >= 7 || Integer.parseInt(setNum.getText())>1048576)){
                        //JOptionPane.showMessageDialog(null,"超过最大值1048576");
                        setNum.setText("104857");  // 1048576 去掉个位
                    }
                }else{
                    e.consume(); //关键，屏蔽掉非法输入
                }
            }
        });
        //setNum.setPreferredSize(new Dimension(100,20));
        setTitleJcb = new JCheckBox("每个文件都包含表头");
        setTitleJcb.setSelected(true);
        setTitleJcb.setVisible(true);
        onlySaveFirstTitle = new JCheckBox("仅保留第一个文件的表头");
        onlySaveFirstTitle.setSelected(true);
        onlySaveFirstTitle.setVisible(false);

        //加入
        setArea = new JPanel(new GridBagLayout());
        setArea.add(text,new GBC(0,0,6,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(5,0).setWeight(100,0));
        setArea.add(importJlb,new GBC(0,1,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(10,0,0,0).setWeight(100,0));
        setArea.add(filePath,new GBC(0,2,6,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(5,0,5,0).setWeight(100,0));
        setArea.add(fileListAreaScroll,new GBC(0,2,6,3).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(300,82).setInsets(5,0,0,0).setWeight(0,0));
        setArea.add(selectBtn1,new GBC(6,2,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(5,5,5,0).setWeight(0,0));
        setArea.add(selectBtn2,new GBC(6,2,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(5,5,5,0).setWeight(0,0));
        setArea.add(addBtn,new GBC(6,3,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,5,5,0).setWeight(0,0));
        setArea.add(setJlb,new GBC(0,3,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(10,0,0,0).setWeight(100,0));
        setArea.add(setJcb,new GBC(0,4,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(5,0,5,5).setWeight(0,0));
        setArea.add(lineNum,new GBC(2,4,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(5,5).setWeight(0,0));
        setArea.add(setNum,new GBC(3,4,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(5,0,5,100).setWeight(100,0));
        setArea.add(setTitleJcb,new GBC(0,5,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,0).setWeight(100,0));
        setArea.add(onlySaveFirstTitle,new GBC(0,5,1,1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setIpad(0,0).setInsets(0,0).setWeight(100,0));

        //提醒部分
        noticeOld = new JLabel("现在，开始选择文本吧...");
        //noticeNew = new JLabel("文件将被分割为 x 个文件，每个文件最多包含 xxx 行");
        noticeNew = new JLabel(" ");
        noticeJcb = new JCheckBox("完成后删除源文件");
        noticeJcb.setBackground(new Color(230,230,230));

        noticeArea = new JPanel(new GridBagLayout());
        noticeArea.setBackground(new Color(230,230,230));
        noticeArea.add(noticeOld,new GBC(0,0,3,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(5,5).setInsets(0,65,0,0).setWeight(100,0));
        noticeArea.add(noticeNew,new GBC(0,1,3,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(5,5).setInsets(0,65,0,0).setWeight(100,0));
        noticeArea.add(noticeJcb,new GBC(0,2,1,1).setFill(GBC.BOTH).setAnchor(GBC.WEST).setIpad(5,5).setInsets(0,65,0,0).setWeight(0,0));

        //添加到中间的JPanel
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(setArea,new GBC(0,0,1,8).setFill(GBC.VERTICAL).setAnchor(GBC.WEST).setIpad(30,30).setInsets(0,65,0,0).setWeight(100,100));
        contentsArea.add(noticeArea,new GBC(0,9,1,4).setFill(GBC.HORIZONTAL).setAnchor(GBC.WEST).setIpad(30,30).setWeight(100,100));

        //控制栏
        aboutBtn = new JButton(new ImageIcon(FileSplit.class.getResource("/icons/about.png")));
        backBtn = new JButton("文本分割功能");
        nextBtn = new JButton("文本合并功能");
        okBtn = new JButton(" 运  行 ");
        homeBtn = new JButton(" 主功能 ");
        backBtn.setEnabled(false);
        controlBtnArea = new ControlBtnArea(aboutBtn,backBtn,nextBtn,okBtn,homeBtn);

        //设置监听addBtn
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
        selectBtn1.setActionCommand("selectBtn1");
        selectBtn1.addActionListener(this);
        selectBtn2.setActionCommand("selectBtn2");
        selectBtn2.addActionListener(this);
        addBtn.setActionCommand("addBtn");
        addBtn.addActionListener(this);
        setJcb.setActionCommand("setJcb");
        setJcb.addActionListener(this);
        noticeJcb.setActionCommand("noticeJcb");
        noticeJcb.addActionListener(this);

        //添加入Frame
        //frame = new JFrame();
        this.add(welcomeArea, BorderLayout.NORTH);
        this.add(contentsArea,BorderLayout.CENTER);
        this.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        this.setTitle("一箱工具 - 分本文件分割");
        this.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/split_64.png")));
        this.setSize(626,420);
        this.setResizable(false);//固定窗体大小
        this.setLocationRelativeTo(null);//打开时相对window居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowRuning = false;
            }
            @Override
            public void windowClosed(WindowEvent e) {
                windowRuning = false;
            }
        });
    }

    int oneLineWidth = 10;
    int totalLineHeight = 10;
    java.util.List<File> fileListArray = null;
    File[] filesList_split = null;
    File[] filesList_merge = null;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("aboutBtn")){
            new FSNotice();
        }else if (e.getActionCommand().equals("okBtn")){
            if (!backBtn.isEnabled()){
                if (filesList_split != null) {
                    saveFile save = new saveFile();
                    int state = save.saveFile("保存分割后的文件...", 1, this);
                    if (state == 0) {
                        if (setJcb.getSelectedIndex() == 0 || setJcb.getSelectedIndex() == 1 || setJcb.getSelectedIndex() == 3) {
                            TxtSplit.split(filesList_split[0], save.getFile().getPath(), Integer.parseInt(setNum.getText()), setTitleJcb.isSelected(), noticeJcb.isSelected());
                        } else if (setJcb.getSelectedIndex() == 2) {
                            String[] txt = ReadFile.readSingleText(filesList_split[0]).split("\\r\\n");
                            TxtSplit.split(filesList_split[0], save.getFile().getPath(), (int) Math.ceil((float) txt.length / Integer.parseInt(setNum.getText())), setTitleJcb.isSelected(), noticeJcb.isSelected());
                        }
                    } else if (state == 1) {
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"应该先选择文件！");
                }
            }else if (!nextBtn.isEnabled()){
                if (filesList_merge != null) {
                    saveFile save = new saveFile();
                    int state = save.saveFile("保存合并后的文件...", 1, this);
                    if (state == 0) {
                        TxtSplit.merge(filesList_merge, save.getFile().getPath(), onlySaveFirstTitle.isSelected(), noticeJcb.isSelected());
                    } else if (state == 1) {
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"应该先选择文件！");
                }
            }
        }else if (e.getActionCommand().equals("homeBtn")){
            this.dispose();
        }else if (e.getActionCommand().equals("backBtn")){  //选择分割文件
            title = "分割";
            welcomeArea =new WelcomeArea(new ImageIcon(this.getClass().getResource("/icons/split_64.png")),"  一箱工具 - 文本"+title);
            this.add(welcomeArea, BorderLayout.NORTH);
            welcomeArea.revalidate();

            backBtn.setEnabled(false);
            onlySaveFirstTitle.setVisible(false);
            fileListAreaScroll.setVisible(false);
            addBtn.setVisible(false);
            selectBtn2.setVisible(false);

            selectBtn1.setVisible(true);
            nextBtn.setEnabled(true);
            setTitleJcb.setVisible(true);
            filePath.setVisible(true);
            setJlb.setVisible(true);
            setJcb.setVisible(true);
            lineNum.setVisible(true);
            setNum.setVisible(true);
        }else if (e.getActionCommand().equals("nextBtn")){  //选择合并文件
            title = "合并";
            welcomeArea =new WelcomeArea(new ImageIcon(this.getClass().getResource("/icons/split_64.png")),"  一箱工具 - 文本"+title);
            this.add(welcomeArea, BorderLayout.NORTH);
            welcomeArea.revalidate();

            if (filesList_merge != null){
                addBtn.setVisible(true);
            }else {
                addBtn.setVisible(false);
            }
            backBtn.setEnabled(true);
            onlySaveFirstTitle.setVisible(true);
            fileListAreaScroll.setVisible(true);
            selectBtn2.setVisible(true);

            selectBtn1.setVisible(false);
            nextBtn.setEnabled(false);
            setTitleJcb.setVisible(false);
            filePath.setVisible(false);
            setJlb.setVisible(false);
            setJcb.setVisible(false);
            lineNum.setVisible(false);
            setNum.setVisible(false);
        }else if (e.getActionCommand().equals("selectBtn1")){   //分割文件的读取文件按钮
            int state;
            selectFile select = new selectFile();
            state = select.selectFile("选取项目", "text", JFileChooser.FILES_ONLY, false, this);
            if (state == 0) {
                filesList_split = select.getFile();
                filePath.setText(filesList_split[0].getPath());
            } else if (state == 1) {
                //filesList_split = null;
                //noticeOld = new JLabel("现在，开始选择文本吧...");
            }
        }else if (e.getActionCommand().equals("selectBtn2")){   //合并文件的读取文件按钮
            if (filesList_merge != null){
                Object[] options = {"确定","我手贱"};
                int response=JOptionPane.showOptionDialog(this, "清空已选取的项目？", "thinkPad",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(response==0) {
                    filesList_merge = null;
                    fileListArray = null;
                    fileListArea.removeAll();
                    fileListArea.setPreferredSize(new Dimension(300, 66));
                    fileListArea.revalidate();
                    fileListArea.repaint();
                    addBtn.setVisible(false);
                }
            }
            if (filesList_merge == null) {
                int state;
                selectFile select = new selectFile();
                state = select.selectFile("选取项目", "text", JFileChooser.FILES_ONLY, true, this);
                filesList_merge = select.getFile();
                fileListArray = new ArrayList();
                if (state == 0) {
                    for (int i = 0; i < filesList_merge.length; i++) {
                        String fileName = " " + filesList_merge[i].getName() + " ";
                        JLabel jl = new JLabel(fileName);
                        jl.setOpaque(true);
                        jl.setBackground(new Color(84, 189, 241));
                        fileListArea.add(jl);
                        fileListArray.add(filesList_merge[i]);
                        oneLineWidth += jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 5;  //合计每行的 JLabel 的宽度和间隔
                        if (oneLineWidth >= 300) {
                            totalLineHeight += jl.getFontMetrics(jl.getFont()).getHeight() + 5;  //当 JLabel 的宽度大于JPanel的宽度时，总高度加一个 JLabel 的高度和间隔
                            if (totalLineHeight >= height) {
                                height = totalLineHeight;    //当行高的高度大于原来设置的高度时，设置原来的高度等于总高度
                                fileListArea.setPreferredSize(new Dimension(300, totalLineHeight + 20));  //设置JPanel的高度为总高度
                            }
                            oneLineWidth = jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 10;  //值得再考虑！！每次没有放满一行后的元素宽度
                        }
                    }
                    addBtn.setVisible(true);
                    fileListArea.revalidate();
                } else if (state == 1) {
                    filesList_merge = null;
                    fileListArray = null;
                    //noticeOld = new JLabel("现在，开始选择文本吧...");
                }
            }
        }else if (e.getActionCommand().equals("addBtn")){
            if (filesList_merge != null) {
                int state;
                selectFile select = new selectFile();
                state = select.selectFile("添加项目","text",JFileChooser.FILES_ONLY,true,this);
                filesList_merge = select.getFile();
                if (state == 0) {
                    //如果点击确定
                    for (int i = 0; i < filesList_merge.length; i++) {
                        String fileName = " " + filesList_merge[i].getName() + " ";
                        JLabel jl = new JLabel(fileName);
                        jl.setOpaque(true);
                        jl.setBackground(new Color(84, 189, 241));
                        fileListArea.add(jl);
                        fileListArray.add(filesList_merge[i]);
                        oneLineWidth += jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 5;  //合计每行的 JLabel 的宽度和间隔
                        if (oneLineWidth >= 300) {
                            totalLineHeight += jl.getFontMetrics(jl.getFont()).getHeight() + 5;  //当 JLabel 的宽度大于JPanel的宽度时，总高度加一个 JLabel 的高度和间隔
                            if (totalLineHeight >= height) {
                                height = totalLineHeight;    //当行高的高度大于原来设置的高度时，设置原来的高度等于总高度
                                fileListArea.setPreferredSize(new Dimension(300, totalLineHeight + 21));  //设置JPanel的高度为总高度，为什么加76？
                            }
                            oneLineWidth = jl.getFontMetrics(jl.getFont()).stringWidth(jl.getText()) + 10;  //值得再考虑！！每次没有放满一行后的元素宽度
                        }
                    }
                    noticeNew.setText(" ");
                    fileListArea.revalidate();
                }
            }
        }else if (e.getActionCommand().equals("setJcb")){
            if (setJcb.getSelectedIndex() == 0){
                lineNum.setText("行数：");
                setNum.setText("65536");
                setNum.setEditable(false);
            }else if (setJcb.getSelectedIndex() == 1){
                lineNum.setText("行数：");
                setNum.setText("1048576");
                setNum.setEditable(false);
            }else if (setJcb.getSelectedIndex() == 2){
                lineNum.setText("份数：");
                setNum.setText("");
                setNum.setEditable(true);
            }else if (setJcb.getSelectedIndex() == 3){
                lineNum.setText("行数：");
                setNum.setText("");
                setNum.setEditable(true);
            }
        }else if (e.getActionCommand().equals("noticeJcb")){
            if (noticeJcb.isSelected()){
                Object[] options = {"确定","我手贱"};
                int response=JOptionPane.showOptionDialog(this, "危险动作！请再次确认！", "删除源文件吗？",JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if(response==0) {
                }else if(response==1) {
                    noticeJcb.setSelected(false);
                }
            }
        }
    }

    //int allExists = 0;

    @Override
    public void run() {
        //动态修改提示信息。
        while (windowRuning) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!backBtn.isEnabled()){
                if (filesList_split == null){
                    noticeOld.setText("现在，开始选择文本吧...");
                    noticeNew.setText(" ");
                }else {
                    if (filesList_split[0].exists()) {
                        String[] txt = ReadFile.readSingleText(filesList_split[0]).split("\\r\\n");
                        if (setJcb.getSelectedIndex() == 0 || setJcb.getSelectedIndex() == 1) {  //按行分割
                            noticeOld.setText("选择的文件有 " + txt.length + " 行数据");
                            if (setTitleJcb.isSelected()){
                                //增加表头导致增多的行,每个文件最多包含的行数=（总行数+（总行数/每个文件的行数）-1）/每个文件的行数
                                //真正的总行数=（总行数+（总行数/每个文件的行数）-1）
                                noticeNew.setText("文件将被分割为 " + (txt.length + (txt.length / Integer.parseInt(setNum.getText()))-1) / Integer.parseInt(setNum.getText()) + " 个文件，每个文件最多包含 " + setNum.getText() + " 行");
                            }else {
                                noticeNew.setText("文件将被分割为 " + txt.length / Integer.parseInt(setNum.getText()) + " 个文件，每个文件最多包含 " + setNum.getText() + " 行");
                            }
                        } else if (setJcb.getSelectedIndex() == 2) {    //按数量分割
                            if (!setNum.getText().equals("") && !setNum.getText().equals("0")) {
                                noticeOld.setText("选择的文件有 " + txt.length + " 行数据");
                                if (setTitleJcb.isSelected()){
                                    //增加表头导致增多的行,每个文件最多包含的行数=（总行数+（分割文件数）-1）/每个文件的行数
                                    noticeNew.setText("文件将被分割为 " + setNum.getText() + " 个文件，每个文件最多包含 " + (int) Math.ceil((float) (txt.length +Integer.parseInt(setNum.getText()) -1) / Integer.parseInt(setNum.getText())) + " 行");
                                }else {
                                    noticeNew.setText("文件将被分割为 " + setNum.getText() + " 个文件，每个文件最多包含 " + (int) Math.ceil((float) txt.length / Integer.parseInt(setNum.getText())) + " 行");
                                }
                            } else {
                                noticeOld.setText("选择的文件有 " + txt.length + " 行数据");
                                noticeNew.setText("文件将被分割为 - - 个文件，每个文件最多包含 - - 行");
                            }
                        } else if (setJcb.getSelectedIndex() == 3) {  //按行分割
                            if (!setNum.getText().equals("") && !setNum.getText().equals("0")) {
                                noticeOld.setText("选择的文件有 " + txt.length + " 行数据");
                                if (setTitleJcb.isSelected()){
                                    //增加表头导致增多的行,每个文件最多包含的行数=（总行数+（总行数/每个文件的行数）-1）/每个文件的行数
                                    noticeNew.setText("文件将被分割为 " + (int) Math.ceil((float) (txt.length + (txt.length / Integer.parseInt(setNum.getText()))-1) / Integer.parseInt(setNum.getText())) + " 个文件，每个文件最多包含 " + setNum.getText() + " 行");
                                }else {
                                    noticeNew.setText("文件将被分割为 " + (int) Math.ceil((float) txt.length / Integer.parseInt(setNum.getText())) + " 个文件，每个文件最多包含 " + setNum.getText() + " 行");
                                }
                            } else {
                                noticeOld.setText("选择的文件有 " + txt.length + " 行数据");
                                noticeNew.setText("文件将被分割为 - - 个文件，每个文件最多包含 - - 行");
                            }
                        }
                    }else {
                        noticeOld.setText("文件或已被删除，请重新选择文件");
                        noticeNew.setText(" ");
                    }
                }
            }else if (!nextBtn.isEnabled()){
                if (filesList_merge == null){
                    noticeOld.setText("现在，开始选择文本吧...");
                    noticeNew.setText(" ");
                }else {
                    int line = 0;
                    int allExists = 0;
                    for (int i = 0; i < fileListArray.size(); i++) {
                        if (fileListArray.get(i).exists()) {
                            allExists++;
                            String[] txt = ReadFile.readSingleText(fileListArray.get(i)).split("\\r\\n");
                            line += txt.length;
                        }
                    }
                    if (allExists == fileListArray.size()){
                        noticeOld.setText("选择了 " + fileListArray.size() + " 个文件");
                        if (onlySaveFirstTitle.isSelected()){
                            noticeNew.setText("将合并为一个有 "+ (line - allExists +1) +" 行数据的文件 ");
                        }else {
                            noticeNew.setText("将合并为一个有 "+ line +" 行数据的文件 ");
                        }
                    }else if (allExists < fileListArray.size()){
                        noticeOld.setText("选择了 " + fileListArray.size() + " 个文件,但 "+ (fileListArray.size() - allExists)+" 个文件或已被删除");
                        if (onlySaveFirstTitle.isSelected()){
                            noticeNew.setText("将合并为一个有 "+ (line - allExists +1) +" 行数据的文件 ");
                        }else {
                            noticeNew.setText("将合并为一个有 "+ line +" 行数据的文件 ");
                        }
                    }
                }
            }
        }
    }
}


