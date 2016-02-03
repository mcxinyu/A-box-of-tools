package View.HomeView;
/**
 * Created by 跃峰 on 2015/11/19.
 * View.CheckPlanView 的主界面，承载了主要功能按钮
 * 版本号：0.1.0
 * a.b.c a++UI改动，b++功能增加，c++小修改
 */
import View.Cdd2ForteView.Cdd2Forte;
import View.CheckPlanView.CheckPlan;
import View.CheckPlanView.WelcomeArea;
import View.FileSplitView.FileSplit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Home extends JFrame implements ActionListener{
    WelcomeArea welcome;
    HomeFunctionBtn checkPlanBtn,cdd2forteBtn,fileSplitBtn,placeholderBtn3;
    JPanel functionalArea;
    JFrame j1,j2;

    public static void main(String[] args) {
        JFrame home = new Home();
    }
    public Home() {

        welcome =new WelcomeArea("images/boxtool_64.png","  欢迎使用 一箱工具 应用");

        cdd2forteBtn = new HomeFunctionBtn("images/cdd2forte_128.png","cdd2forte","   使用 cdd-log 生成 forte 环境          （目前只支持爱立信设备）",200,0);
        checkPlanBtn = new HomeFunctionBtn("images/CheckPlan_128.png","变频方案检查表","  现网 cdd/cdd_Channel 与变频      方案生成网优之家检查表",400,0);
        fileSplitBtn = new HomeFunctionBtn("images/ad_128.png","广告位出租","                    上广告",200,200);
        placeholderBtn3 = new HomeFunctionBtn("images/ad_128.png","广告位出租","                    上广告",400,200);
        placeholderBtn3.button.setEnabled(false);

        functionalArea = new JPanel(null);
        functionalArea.setBackground(new Color(230,230,230));
        functionalArea.add(cdd2forteBtn);
        functionalArea.add(checkPlanBtn);
        functionalArea.add(fileSplitBtn);
        functionalArea.add(placeholderBtn3);

        //注册监听
        cdd2forteBtn.button.setActionCommand("cdd2forteBtn");
        cdd2forteBtn.button.addActionListener(this);
        checkPlanBtn.button.setActionCommand("checkPlanBtn");
        checkPlanBtn.button.addActionListener(this);
//        checkPlan.setActionCommand("checkPlan");
//        checkPlan.addActionListener(this);
        fileSplitBtn.button.setActionCommand("fileSplitBtn");
        fileSplitBtn.button.addActionListener(this);
        placeholderBtn3.button.setActionCommand("placeholderBtn3");
        placeholderBtn3.button.addActionListener(this);
        //添加入Frame
        j1 = new JFrame();
        j1.add(welcome,BorderLayout.NORTH);
        j1.add(functionalArea,BorderLayout.CENTER);
        //设置窗体
        j1.setTitle("一箱工具");
        j1.setIconImage (Toolkit.getDefaultToolkit ().getImage ("images/boxtool_64.png"));
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
//            j1.setVisible(false);
            j2 = new Cdd2Forte();
            j2.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    j1.setVisible(true);
                }
            });
        }else if (e.getActionCommand().equals("checkPlanBtn")){
//            j1.setVisible(false);
            j2 = new CheckPlan();
            j2.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    j1.setVisible(true);
                }
            });
        }else if (e.getActionCommand().equals("fileSplitBtn")){
//            j1.setVisible(false);
            j2 = new FileSplit();
            j2.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    j1.setVisible(true);
                }
            });
        }else if (e.getActionCommand().equals("placeholderBtn3")){
//            j1.setVisible(false);
            j2 = new CheckPlan();
            j2.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
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