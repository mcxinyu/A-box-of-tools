package View.Cdd2ForteView;
import View.CheckPlanView.GBC;
import View.CheckPlanView.WelcomeArea;
import View.CheckPlanView.ControlBtnArea;
import View.HomeView.MyTools;
import javax.swing.*;
import java.awt.*;

/**
 * cdd2forte 的界面部分
 * Created by 跃峰 on 2016/1/27.
 */
public class Cdd2Forte extends JFrame{
    JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,okBtn,cancelBtn,readCoordinateBtn ,readCddBtn,export2ForteBtn;
    JPanel contentsArea;
    JLabel temp1,temp2,text,progressBar,expPoint,joke;

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
        progressBar = new JLabel("导入 cdd-log 进度条");
        expPoint = new JLabel("导出环境进度条");
//        expPoint.setOpaque(true);
//        expPoint.setBackground(Color.RED);
//        joke = new JLabel("点击确认和取消都是没效果的！");

        readCoordinateBtn = new JButton(" 读取坐标文件 ");
        readCoordinateBtn.setFont(MyTools.fontBold18);

        readCddBtn = new JButton("读取 cdd-log");
        readCddBtn.setFont(MyTools.fontBold18);
        readCddBtn.setEnabled(false);

        export2ForteBtn = new JButton("导出 forte 环境");
        export2ForteBtn.setFont(MyTools.fontBold18);
        export2ForteBtn.setEnabled(false);

//        contentsArea = new JPanel(new GridLayout(6,1,0,0));
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.add(text,new GBC(0,0,2,1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(30,30).setInsets(0,1));
        contentsArea.add(readCoordinateBtn,new GBC(0,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0));
        contentsArea.add(readCddBtn,new GBC(1,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0));
        contentsArea.add(progressBar,new GBC(0,2,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(30,30).setInsets(0,1));
        contentsArea.add(export2ForteBtn,new GBC(0,3,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0));
        contentsArea.add(expPoint,new GBC(0,4,2,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(30,30).setInsets(0,1));

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
}
