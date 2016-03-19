package View.ToCoordinateView;

/**
 * 批量文件重命名
 * Created by 跃峰 on 2016/3/8.
 */

import Common.ControlBtnArea;
import Common.DistributOutputStream;
import Common.MyTools;
import Common.WelcomeArea;
import Control.CommonControl.ReadFile;
import Control.CommonControl.saveFile;
import Control.CommonControl.selectFile;
import Control.ToCoordinateControl.cellinfo2Coordinates;
import View.Cdd2ForteView.Cdd2Forte;
import View.CheckPlanView.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ToCoordinate extends JFrame implements ActionListener {
    //JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,homeBtn,okBtn,aboutBtn,readCellinfoBtn,toCoordinateBtn;
    JPanel contentsArea;
    JLabel temp1,temp2,text,taps;
    JCheckBox jcb1,jcb2;

    public static void main(String[] args) {
        new ToCoordinate();
        new DistributOutputStream("toCoordiantes.log");
    }

    public ToCoordinate(){
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(Cdd2Forte.class.getResource("/icons/coordinate_64.png")),"  一箱工具 - 地图转坐标");

        //contentsArea
        text = new JLabel("将 cellinfo 转换为 forte 格式的坐标文件。");
        text.setFont(MyTools.fontPlain13);
        temp1 = new JLabel("                    ");
        temp2 = new JLabel("                    ");

        readCellinfoBtn = new JButton("读取 Cellinfo");
        readCellinfoBtn.setFont(MyTools.fontBold18);

        toCoordinateBtn = new JButton("导出 Coordinates");
        toCoordinateBtn.setFont(MyTools.fontBold18);
        toCoordinateBtn.setEnabled(false);

        taps = new JLabel("开始读取 Cellinfo 吧");
        jcb1 = new JCheckBox("剔除无效数据");
        jcb1.setSelected(true);
        jcb2 = new JCheckBox("保留中文名");
        jcb2.setSelected(false);

        //注册监听
        readCellinfoBtn.setActionCommand("readCoordinateBtn");
        readCellinfoBtn.addActionListener(this);
        toCoordinateBtn.setActionCommand("toCoordinateBtn");
        toCoordinateBtn.addActionListener(this);

        //添加组件到 contentsArea238,238,238
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.setBackground(new Color(238,238,238));
        contentsArea.add(text,new GBC(0,0,4,1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,1));
        contentsArea.add(readCellinfoBtn,new GBC(0,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,1,0,0));
        contentsArea.add(toCoordinateBtn,new GBC(2,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));
        contentsArea.add(taps,new GBC(0,2,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,5).setInsets(0,1));
        contentsArea.add(jcb1,new GBC(0,3,3,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,5).setInsets(0,1));
        contentsArea.add(jcb2,new GBC(2,3,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,5).setInsets(0,1));


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
        //frame = new JFrame();
        this.add(welcomeArea, BorderLayout.NORTH);
        this.add(contentsArea,BorderLayout.CENTER);
        this.add(temp1,BorderLayout.EAST);
        this.add(temp2,BorderLayout.WEST);
        this.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        this.setTitle("一箱工具 - 地图转坐标");
        this.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/coordinate_64.png")));

        this.setSize(626,300);
        this.setResizable(false);//固定窗体大小
        this.setLocationRelativeTo(null);//打开时相对window居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    String[][] cellinfo = null;
    cellinfo2Coordinates c2c = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        c2c = new cellinfo2Coordinates();
        if (e.getActionCommand().equals("readCoordinateBtn")){
            toCoordinateBtn.setEnabled(false);
            int state;
            taps.setText("正在读取 Cellinfo...");
            selectFile select = new selectFile();
            state = select.selectFile("读取 Cellinfo 文件", "text",JFileChooser.FILES_ONLY,this);
            File[] fileList = new ReadFile().readMultiText(select.getFile());
            if (state == 0) {
                //如果点击确定
                if (fileList != null && fileList.length == 1) {
                    try {
                        cellinfo = c2c.readCellinfo(fileList[0]);
                        taps.setText("Cellinfo 中读取到 "+ cellinfo.length +" 个小区的数据");
                        JOptionPane.showMessageDialog(null,"Cellinfo 读取成功");
                        toCoordinateBtn.setEnabled(true);
                    }catch (Exception e1){
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Cellinfo 文件错误");
                        cellinfo = null;
                        taps.setText("重新选取 Cellinfo 文件");
                        toCoordinateBtn.setEnabled(false);
                    }
                }
            }else if (state == 1) {
                taps.setText("开始读取 Cellinfo 吧");
            }
        }else if (e.getActionCommand().equals("toCoordinateBtn")){
            saveFile save = new saveFile();
            int state = save.saveFile("导出 Coordinates 文件",1,this);
            if (state == 0){
                if (cellinfo!=null) {
                    String[][] coordinate = c2c.toCoordinates(cellinfo);
                    c2c.createCoordinatesFile(coordinate, save.getFile().getPath(),jcb1.isSelected(),jcb2.isSelected());
                }
            }else if (state == 1){
            }
        }
    }
}