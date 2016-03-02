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
    //创建一条垂直进度条
    JProgressBar bar = new JProgressBar();
    public progressBar(int fileCount) {
        //把进度条添加到JFrame窗口中
        frame.add(bar);

        //设置在进度条中绘制完成百分比
        bar.setStringPainted(true);
        //根据该选择框决定是否绘制进度条的边框
        bar.setBorderPainted(false);
        final SimulatedTarget target = new SimulatedTarget(fileCount);
        //以启动一条线程的方式来执行一个耗时的任务
        new Thread(target).start();
        //设置进度条的最大值和最小值,
        bar.setMinimum(0);
        //以总任务量作为进度条的最大值
        bar.setMaximum(target.getAmount()+1);
        Timer timer = new Timer(50 , new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //以任务的当前完成量设置进度条的value
                bar.setValue(target.getCurrent());
            }
        });
        timer.start();
//            indeterminate.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent event) {
                //设置该进度条的进度是否确定
//                    bar.setIndeterminate(indeterminate.isSelected());
//                    bar.setStringPainted(!indeterminate.isSelected());
//                }
//            });

        bar.setIndeterminate(true);
        bar.setStringPainted(true);

        frame.setTitle("正在读取 cdd-log 文件...");
//        frame.setDefaultCloseOperation(0);
        frame.setResizable(false);
        frame.setSize(300,60);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
//    public static void main(String[] args) {
//        new progressBar(5);
//    }

    @Override
    public void run() {

    }
}

//模拟一个耗时的任务
class SimulatedTarget implements Runnable {
    //任务的当前完成量
    private volatile int current;
    //总任务量
    private int amount;
    public SimulatedTarget(int amount) {
        current = 0;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getCurrent() {
        return current;
    }
    //run方法代表不断完成任务的过程
    public void run() {

        while (current < amount) {
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
            }
            current++;
        }
    }
}
