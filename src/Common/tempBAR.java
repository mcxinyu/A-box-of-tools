package Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 跃峰 on 2016/2/28.
 */
public class tempBAR extends JDialog implements Runnable{
    JDialog frame;
    JLabel proBar;
    public tempBAR() {
        proBar = new JLabel(new ImageIcon("images/proBar.gif"));

        frame = new JDialog();
        frame.add(proBar);

        frame.setTitle("正在读取 cdd-log 文件...");
        frame.setResizable(false);
        frame.setSize(300,60);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new tempBAR();
    }

    @Override
    public void run() {

    }
}
