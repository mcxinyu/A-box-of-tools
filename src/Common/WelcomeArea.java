package Common;
/**
 * Created by 跃峰 on 2015/12/8.
 * View.CheckPlanView 的欢迎栏区域
 */

import View.Cdd2ForteView.Cdd2Forte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//欢迎栏区域
public class WelcomeArea extends JPanel implements ActionListener {
    JLabel logo,welcomeText;
    JButton versionBtn;
    int x,y;
    //参数：图片路径，标题。
    public WelcomeArea(ImageIcon image, String titleText,boolean useAbout){
        logo = new JLabel(image);
        welcomeText = new JLabel(titleText);
        welcomeText.setFont(MyTools.fontBold26);
        versionBtn = new JButton(new ImageIcon(Cdd2Forte.class.getResource("/icons/about.png")));
        versionBtn.setBackground(new Color(230,230,230));
        versionBtn.setFocusPainted(false);
        versionBtn.setBorder(null);
        //versionBtn.setBorderPainted(false);

        //注册监听
        versionBtn.setActionCommand("versionBtn");
        versionBtn.addActionListener(this);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(new Color(230,230,230));
        this.setPreferredSize(new Dimension(0, 70));
        this.add(logo);
        this.add(welcomeText);
        if (useAbout == true)
        this.add(versionBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("versionBtn")){
            new versionNotice();
        }
    }
}
