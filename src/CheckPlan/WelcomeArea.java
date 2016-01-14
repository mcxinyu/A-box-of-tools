package CheckPlan;
/**
 * Created by 跃峰 on 2015/12/8.
 * CheckPlan 的欢迎栏区域
 */
import Home.MyTools;
import javax.swing.*;
import java.awt.*;
//欢迎栏区域
public class WelcomeArea extends JPanel {
    JLabel logo,welcomeText,stepsText;
    int x,y;
    //参数：图片路径，标题。
    public WelcomeArea(String image, String titleText){
        logo = new JLabel(new ImageIcon(image));
        welcomeText = new JLabel(titleText);
        welcomeText.setFont(MyTools.fontBold26);
        stepsText = new JLabel("  导入数据 ( x of y )");
        stepsText.setFont(MyTools.fontPlain13);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(new Color(230,230,230));
        this.setPreferredSize(new Dimension(0, 70));
        this.add(logo);
        this.add(welcomeText);
        this.add(stepsText);
    }
}
