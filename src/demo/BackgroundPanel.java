package demo;

import java.awt.*;
import javax.swing.*;

/**
 * 有背景图片的Panel类
 * @author tntxia
 *
 */
public class BackgroundPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -6352788025440244338L;

    private Image image = null;

    public BackgroundPanel(Image image) {
        this.image = image;


    }

    // 固定背景图片，允许这个JPanel可以在图片上添加其他组件
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public static void main(String[] args) {
        JPanel panel = new BackgroundPanel(new ImageIcon("images/backImage.png").getImage());
        JFrame frame = new JFrame();
        frame.add(panel);

//        setTitle("一箱工具 - 变频方案检查表");
//        setIconImage (Toolkit.getDefaultToolkit().getImage("images/boxtool_64.png"));
        frame.setSize(1000,615);
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}