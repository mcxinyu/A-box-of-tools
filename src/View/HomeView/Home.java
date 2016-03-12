package View.HomeView;
/**
 * Created by 跃峰 on 2015/11/19.
 * View.CheckPlanView 的主界面，承载了主要功能按钮
 * 版本号：0.1.0
 * a.b.c a++UI改动，b++功能增加，c++小修改
 */
import Common.DistributOutputStream;
import Common.WelcomeArea;
import View.Cdd2ForteView.Cdd2Forte;
import View.FileSplitView.FileSplit;
import View.RenameFilesView.RenameFiles;
import View.ToCoordinateView.ToCoordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener{
    WelcomeArea welcome;
    HomeFunctionBtn renameFilesBtn,cdd2forteBtn,fileSplitBtn,toCOordinateBtn,placeholderBtn4,placeholderBtn5;
    JPanel functionalArea;
    JFrame j1,j2;

    public static void main(String[] args) {
        JFrame home = new Home();
    }
    public Home() {

        welcome =new WelcomeArea(new ImageIcon(this.getClass().getResource("/icons/boxtool_64.png")),"  欢迎使用 一箱工具 应用");

        cdd2forteBtn = new HomeFunctionBtn(new ImageIcon(this.getClass().getResource("/icons/cdd2forte_128.png")),"cdd2forte","   使用 cdd-log 生成 forte 环境          （目前只支持爱立信设备）",100,0);
        renameFilesBtn = new HomeFunctionBtn(new ImageIcon(this.getClass().getResource("/icons/rename_128.png")),"批量文件重命名","      批量重命名文件或文件夹",300,0);
        fileSplitBtn = new HomeFunctionBtn(new ImageIcon(this.getClass().getResource("/icons/split_128.png")),"文本分割合并","可将文本文件分割为小文件，解决    excel 无法打开大文件的困难",500,0);
        toCOordinateBtn = new HomeFunctionBtn(new ImageIcon(this.getClass().getResource("/icons/coordinate_128.png")),"地图转坐标","       cellinfo to coordinates",100,200);
        placeholderBtn4 = new HomeFunctionBtn(new ImageIcon(this.getClass().getResource("/icons/ad_128.png")),"广告位出租","                    上广告",300,200);
        placeholderBtn5 = new HomeFunctionBtn(new ImageIcon(this.getClass().getResource("/icons/ad_128.png")),"广告位出租","                    上广告",500,200);
        placeholderBtn4.button.setEnabled(false);
        placeholderBtn5.button.setEnabled(false);

        functionalArea = new JPanel(null);
        functionalArea.setBackground(new Color(230,230,230));
        functionalArea.add(cdd2forteBtn);
        functionalArea.add(renameFilesBtn);
        functionalArea.add(fileSplitBtn);
        functionalArea.add(toCOordinateBtn);
        functionalArea.add(placeholderBtn4);
        functionalArea.add(placeholderBtn5);

        //注册监听
        cdd2forteBtn.button.setActionCommand("cdd2forteBtn");
        cdd2forteBtn.button.addActionListener(this);
        renameFilesBtn.button.setActionCommand("renameFilesBtn");
        renameFilesBtn.button.addActionListener(this);
        fileSplitBtn.button.setActionCommand("fileSplitBtn");
        fileSplitBtn.button.addActionListener(this);
        toCOordinateBtn.button.setActionCommand("toCOordinateBtn");
        toCOordinateBtn.button.addActionListener(this);

        //添加入Frame
        j1 = new JFrame();
        j1.add(welcome,BorderLayout.NORTH);
        j1.add(functionalArea,BorderLayout.CENTER);
        //设置窗体
        j1.setTitle("一箱工具");
        j1.setIconImage (Toolkit.getDefaultToolkit ().getImage (this.getClass().getResource("/icons/boxtool_64.png")));
        j1.setSize(800,500);
        j1.setResizable(false);//固定窗体大小
        j1.setLocationRelativeTo(null);//打开时相对window居中
        j1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j1.setVisible(true);
//        System.out.println(this.getHeight()-this.getContentPane().getHeight());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("cdd2forteBtn")){
            j1.setVisible(false);
            j2 = new Cdd2Forte();
            new DistributOutputStream("cdd2forte.log");
            j2.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    j1.setVisible(true);
                }
            });
        }else if (e.getActionCommand().equals("renameFilesBtn")){
            j1.setVisible(false);
            j2 = new RenameFiles();
            j2.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    j1.setVisible(true);
                }
            });
        }else if (e.getActionCommand().equals("fileSplitBtn")){
            //j1.setVisible(false);
            j2 = new FileSplit();
            j2.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    j1.setVisible(true);
                }
            });
        }else if (e.getActionCommand().equals("toCOordinateBtn")){
            j1.setVisible(false);
            j2 = new ToCoordinate();
            new DistributOutputStream("toCoordiantes.log");
            j2.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e) {
                    j1.setVisible(true);
                }
            });
        }
    }
}

// 0,152,227
// 0,196,238
// 170,229,246
// 248,248,248
// 230,230,230