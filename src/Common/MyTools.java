package Common;
/**
 * Created by 跃峰 on 2015/12/25.
 * 一些常用的公共数据、变量、类等
 */
import javax.swing.*;
import java.awt.*;

public class MyTools {
    public static Font fontBold13 = new Font("微软雅黑",Font.BOLD,13);
    public static Font fontPlain13 = new Font("微软雅黑",Font.PLAIN,13);

    public static Font fontBold15 = new Font("微软雅黑",Font.BOLD,15);
    public static Font fontPlain15 = new Font("微软雅黑",Font.PLAIN,15);

    public static Font fontBold18  = new Font("微软雅黑",Font.BOLD,18);
    public static Font fontPlain18  = new Font("微软雅黑",Font.PLAIN,18);

    public static Font fontBold26 = new Font("微软雅黑",Font.BOLD,26);
    public static Font fontPlain26 = new Font("微软雅黑",Font.PLAIN,26);

    public static void windowsFeel(){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

