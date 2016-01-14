package CheckPlan;
/**
 * Created by 跃峰 on 2015/11/25.
 * CheckPlan 的界面承载
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CheckPlan extends JFrame implements ActionListener{
    JFrame checkPlan;
    JPanel welcomeArea,contentsArea,checkPlanStep1,checkPlanStep2,controlBtnArea;
    JButton backBtn,nextBtn,okBtn,cancelBtn;

    public static void main(String[] args) {
        new CheckPlan();
    }

    public CheckPlan(){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //欢迎栏
        welcomeArea =new WelcomeArea("images/boxtool_64.png","  一箱工具 - 变频方案检查表");
        //内容栏
        checkPlanStep1 = new CheckPlanStep1();
        checkPlanStep2 = new CheckPlanStep2();
        checkPlanStep2.setVisible(false);

        contentsArea = new JPanel();
        contentsArea.setBackground(new Color(230,230,230));
        contentsArea.add(checkPlanStep1);
        contentsArea.add(checkPlanStep2);

        //控制栏
        backBtn = new JButton("< 上一步");
        nextBtn = new JButton("下一步 >");
        okBtn = new JButton(" 完  成 ");
        cancelBtn = new JButton(" 取  消 ");

        backBtn.setEnabled(false);
        okBtn.setEnabled(false);

        //设置监听
        backBtn.setActionCommand("backBtn");
        backBtn.addActionListener(this);
        nextBtn.setActionCommand("nextBtn");
        nextBtn.addActionListener(this);
        okBtn.setActionCommand("okBtn");
        okBtn.addActionListener(this);
        cancelBtn.setActionCommand("cancelBtn");
        cancelBtn.addActionListener(this);

        controlBtnArea = new ControlBtnArea(backBtn,nextBtn,okBtn,cancelBtn);

        //添加入Frame
        checkPlan = new JFrame();
        checkPlan.add(welcomeArea,BorderLayout.NORTH);
        checkPlan.add(contentsArea,BorderLayout.CENTER);
        checkPlan.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        checkPlan.setTitle("一箱工具 - 变频方案检查表");
        checkPlan.setIconImage (Toolkit.getDefaultToolkit().getImage("images/boxtool_64.png"));

        checkPlan.setSize(626,500);
        checkPlan.setResizable(false);//固定窗体大小
        checkPlan.setLocationRelativeTo(null);//打开时相对window居中
        checkPlan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkPlan.setVisible(true);
//        checkPlan.pack();
//        System.out.println(checkPlan.getHeight()-checkPlanStep1.getHeight()-this.getContentPane().getHeight());
//        System.out.println(contentsArea.getHeight()-checkPlanStep1.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("backBtn")){
            checkPlanStep1.setVisible(true);
            checkPlanStep2.setVisible(false);

            backBtn.setEnabled(false);
            nextBtn.setEnabled(true);
            okBtn.setEnabled(true);
        }else if (e.getActionCommand().equals("nextBtn")){
            checkPlanStep1.setVisible(false);
            checkPlanStep2.setVisible(true);

            backBtn.setEnabled(true);
            nextBtn.setEnabled(false);
            okBtn.setEnabled(true);
        }else if (e.getActionCommand().equals("okBtn")){
            checkPlan.dispose();
        }else if (e.getActionCommand().equals("cancelBtn")){
            checkPlan.dispose();
        }
    }
}