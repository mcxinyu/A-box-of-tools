package Common;

/**
 * Created by 跃峰 on 2016/3/21.
 */

import javax.swing.*;
import java.awt.*;


public class MyProgressBar extends JFrame implements Runnable{
    JFrame frame = new JFrame("测试进度条");
    volatile boolean running = true;
    //创建一条垂直进度条
    JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
    //JCheckBox indeterminate = new JCheckBox("不确定进度");
    public MyProgressBar() {
        Box box = new Box(BoxLayout.X_AXIS);
        //box.add(indeterminate);
        frame.setLayout(new FlowLayout());
        frame.add(box);
        //把进度条添加到JFrame窗口中
        frame.add(bar);

        //设置在进度条中绘制完成百分比
        //bar.setStringPainted(true);

        //设置进度条的最大值和最小值,
        //bar.setMinimum(0);
        //以总任务量作为进度条的最大值
        //bar.setMaximum(ProcessorTxtCdd.getFileListAmount());
        //Timer timer = new Timer(300 , new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
        //        //以任务的当前完成量设置进度条的value
        //        bar.setValue(ProcessorTxtCdd.getFileListNum());
        //    }
        //});
        //timer.start();
        //indeterminate.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent event) {
        //        //设置该进度条的进度是否确定
        //        bar.setIndeterminate(indeterminate.isSelected());
        //        bar.setStringPainted(!indeterminate.isSelected());
        //    }
        //});
        bar.setIndeterminate(true);
        frame.setSize(500,200);
        frame.setResizable(false);//固定窗体大小
        frame.setLocationRelativeTo(null);//打开时相对window居中
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.pack();
        //frame.setVisible(true);
    }
    public static void main(String[] args) {
        new MyProgressBar();
    }

    @Override
    public void run() {
        while (running){
            //try {
            //    Thread.sleep(100);
            //    //bar.setValue(ProcessorTxtCdd.getFileListNum());
            //    //bar.setIndeterminate(true);
            //    frame.repaint();
            //    frame.setVisible(true);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
            //if (ProcessorTxtCdd.getFileListNum() == ProcessorTxtCdd.getFileListAmount()-1){
            //    running = false;
            //    frame.dispose();
            //    System.out.println("close");
            //}
        }
    }
}