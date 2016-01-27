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
    JButton backBtn,nextBtn,okBtn,cancelBtn,readCddBtn,export2ForteBtn;
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
        progressBar = new JLabel("导入数据进度条");
        expPoint = new JLabel("导出数据进度条");
//        joke = new JLabel("点击确认和取消都是没效果的！");

        readCddBtn = new JButton("读取 cdd-log");
        readCddBtn.setFont(MyTools.fontBold18);
        export2ForteBtn = new JButton("导出 forte 环境");
        export2ForteBtn.setFont(MyTools.fontBold18);

        contentsArea = new JPanel(new GridLayout(6,1,0,0));
        contentsArea.add(text);
        contentsArea.add(readCddBtn);
        contentsArea.add(progressBar);
        contentsArea.add(export2ForteBtn);
        contentsArea.add(expPoint);

        //控制栏
        backBtn = new JButton("< 上一步");
        nextBtn = new JButton("下一步 >");
        okBtn = new JButton(" 完  成 ");
        cancelBtn = new JButton(" 取  消 ");
        backBtn.setEnabled(false);
        nextBtn.setEnabled(false);

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
