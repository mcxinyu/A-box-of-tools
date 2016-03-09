package View.ToCoordinateView;

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

public class ToCoordinate extends JFrame implements ActionListener {
    JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,homeBtn,okBtn,aboutBtn,readCellinfoBtn,toCoordinateBtn;
    JPanel contentsArea;
    JLabel temp1,temp2,text;

    public static void main(String[] args) {
        new ToCoordinate();
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
        //toCoordinateBtn.setEnabled(false);

        //注册监听
        readCellinfoBtn.setActionCommand("readCoordinateBtn");
        readCellinfoBtn.addActionListener(this);
        toCoordinateBtn.setActionCommand("readCddBtn");
        toCoordinateBtn.addActionListener(this);

        //添加组件到 contentsArea
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(text,new GBC(0,0,4,1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,1));
        contentsArea.add(readCellinfoBtn,new GBC(0,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,1,0,0));
        contentsArea.add(toCoordinateBtn,new GBC(2,1,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));

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
        frame.add(temp1,BorderLayout.EAST);
        frame.add(temp2,BorderLayout.WEST);
        frame.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        frame.setTitle("一箱工具 - 地图转坐标");
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