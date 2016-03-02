package Common;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 跃峰 on 2016/3/1.
 */
public class MyThread extends JDialog implements ActionListener,Runnable {
    JDialog proBar;
    JProgressBar bar;
    Timer timer;
    int barValue = 0;
    public MyThread(int min,int max,int value){
        this.barValue = value;
        proBar = new JDialog();
        bar = new JProgressBar();
        bar.setStringPainted(true);
        bar.setMinimum(min);
        bar.setMaximum(max);
//        bar.setValue(value);
        proBar.add(bar);
        proBar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        proBar.pack();
        proBar.setVisible(true);

        timer = new Timer(50, this);
    }

    public void run() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            int val = bar.getValue();
//            if (value > 0) {
//                value--;
//                bar.setValue(value);
//            }
            bar.setValue(barValue);
        }
    }
}
