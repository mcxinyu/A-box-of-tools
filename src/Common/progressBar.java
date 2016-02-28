package Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 跃峰 on 2016/2/28.
 */
public class progressBar extends JDialog implements Runnable{
    JDialog frame = new JDialog();
    JProgressBar bar = new JProgressBar();

    int min = 0;
    int max = 0;
    int val = 0;

    public void progressBar() {
        frame.setLayout(new FlowLayout());
        //把进度条添加到JFrame窗口中
        frame.add(bar);

        //设置在进度条中绘制完成百分比
        bar.setStringPainted(true);

//        final SimulatedTarget target = new SimulatedTarget(1000);
        //以启动一条线程的方式来执行一个耗时的任务
//        new Thread(target).start();
        //设置进度条的最大值和最小值,
        bar.setMinimum(min);
        //以总任务量作为进度条的最大值
        bar.setMaximum(max);

        Timer timer = new Timer(100 , new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //以任务的当前完成量设置进度条的value
                bar.setValue(val);
            }
        });

        timer.start();
//        indeterminate.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                //设置该进度条的进度是否确定
//                bar.setIndeterminate(indeterminate.isSelected());
//                bar.setStringPainted(!indeterminate.isSelected());
//            }
//        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public void run() {
//        bar.setValue(val);
//        while (current < amount) {
//            try {
//                Thread.sleep(50);
//            }
//            catch(InterruptedException e) {
//            }
//            current++;
//        }
    }
}
