package Common;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 跃峰 on 2016/2/15.
 */
public class About extends JPanel {

    public About(){
        MyTools.windowsFeel();
        JPanel welcome =new WelcomeArea("images/boxtool_64.png","  欢迎使用 一箱工具 应用");

        this.add(welcome,BorderLayout.NORTH);
    }
}
