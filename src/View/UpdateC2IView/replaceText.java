package View.UpdateC2IView;

import Common.ControlBtnArea;
import Common.MyTools;
import Common.WelcomeArea;
import Control.CommonControl.ReadFile;
import Control.CommonControl.selectFile;
import Control.UpdateC2IControl.replace;
import View.Cdd2ForteView.Cdd2Forte;
import View.CheckPlanView.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by 跃峰 on 2016/4/13.
 */
public class replaceText extends JFrame implements ActionListener {
    //JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,homeBtn,okBtn,aboutBtn,readText,readNewData, toRun;
    JPanel contentsArea;
    JLabel temp1,temp2,text,taps;

    public static void main(String[] args) {
        new replaceText();
    }

    public replaceText(){
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(Cdd2Forte.class.getResource("/icons/UpdateC2I_64.png")),"  一箱工具 - 批量文本替换",false);

        //contentsArea
        text = new JLabel("批量文本替换，可以更新割接后的模型等。");
        text.setFont(MyTools.fontPlain13);
        temp1 = new JLabel("               ");
        temp2 = new JLabel("               ");

        readText = new JButton("目标文件");
        readText.setFont(MyTools.fontBold18);

        readNewData = new JButton("读取更新信息");
        readNewData.setFont(MyTools.fontBold18);

        toRun = new JButton("开始替换");
        toRun.setFont(MyTools.fontBold18);
        toRun.setEnabled(false);

        taps = new JLabel("开始读取文件吧");

        //注册监听
        readText.setActionCommand("readText");
        readText.addActionListener(this);
        readNewData.setActionCommand("readNewData");
        readNewData.addActionListener(this);
        toRun.setActionCommand("toRun");
        toRun.addActionListener(this);

        //添加组件到 contentsArea238,238,238
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.setBackground(new Color(238,238,238));
        contentsArea.add(text,new GBC(0,0,4,1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,1));
        contentsArea.add(readText,new GBC(0,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,1,0,0));
        contentsArea.add(readNewData,new GBC(1,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));
        contentsArea.add(toRun,new GBC(2,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));
        contentsArea.add(taps,new GBC(0,2,4,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,5).setInsets(0,1));

        //控制栏
        aboutBtn = new JButton(new ImageIcon(Cdd2Forte.class.getResource("/icons/about.png")));
        backBtn = new JButton("< 上一步");
        nextBtn = new JButton("下一步 >");
        okBtn = new JButton(" 完  成 ");
        homeBtn = new JButton(" 主功能 ");
        backBtn.setEnabled(false);
        nextBtn.setEnabled(false);

        //设置监听
        aboutBtn.setActionCommand("aboutBtn");
        aboutBtn.addActionListener(this);
        backBtn.setActionCommand("backBtn");
        backBtn.addActionListener(this);
        nextBtn.setActionCommand("nextBtn");
        nextBtn.addActionListener(this);
        okBtn.setActionCommand("cancelBtn");
        okBtn.addActionListener(this);
        homeBtn.setActionCommand("homeBtn");
        homeBtn.addActionListener(this);

        controlBtnArea = new ControlBtnArea(aboutBtn,backBtn,nextBtn,okBtn,homeBtn);

        //添加入Frame
        //frame = new JFrame();
        this.add(welcomeArea, BorderLayout.NORTH);
        this.add(contentsArea,BorderLayout.CENTER);
        this.add(temp1,BorderLayout.EAST);
        this.add(temp2,BorderLayout.WEST);
        this.add(controlBtnArea,BorderLayout.SOUTH);

        //设置窗体
        this.setTitle("一箱工具 - 批量文本替换");
        this.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/UpdateC2I_64.png")));

        this.setSize(626,300);
        this.setResizable(false);//固定窗体大小
        this.setLocationRelativeTo(null);//打开时相对window居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    File[] fileList = null;
    replace replacetxt = null;
    String newData[][] = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        replacetxt = new replace();
        if (e.getActionCommand().equals("readText")){
            int state;
            taps.setText("正在读取 目标文件...");
            selectFile select = new selectFile();
            state = select.selectFile("读取 目标文件", "text",JFileChooser.FILES_ONLY,true,this);
            fileList = new ReadFile().readMultiText(select.getFile());
            if (state == 0) {
                //如果点击确定
                if (fileList != null) {
                    try {
                        taps.setText("读取到 "+ fileList.length +" 个文件");
                        JOptionPane.showMessageDialog(null,"目标文件读取成功");
                    }catch (Exception e1){
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null,"读取错误");
                        fileList = null;
                        taps.setText("重新选取 目标文件");
                    }
                }
            }else if (state == 1) {
                taps.setText("开始读取文件吧");
            }
        }else if (e.getActionCommand().equals("readNewData")){
            int state;
            taps.setText("正在读取更新信息...");
            selectFile select = new selectFile();
            state = select.selectFile("读取更新信息", "text",JFileChooser.FILES_ONLY,false,this);
            File[] newDataFile = new ReadFile().readMultiText(select.getFile());
            if (state == 0) {
                //如果点击确定
                if (newDataFile != null && newDataFile.length == 1) {
                    try {
                        newData = replacetxt.readNewDate(newDataFile[0]);
                        taps.setText("读取到 "+ newData.length +" 条更新信息。");
                        JOptionPane.showMessageDialog(null,"更新信息读取成功");
                        toRun.setEnabled(true);
                    }catch (Exception e1){
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null,"读取错误");
                        toRun.setEnabled(false);
                        newData = null;
                        taps.setText("重新选取更新信息");
                    }
                }
            }else if (state == 1) {
                toRun.setEnabled(false);
                taps.setText("开始读取文件吧");
            }
        }else if (e.getActionCommand().equals("toRun")){
            replacetxt.replaceMult(fileList,newData);
            JOptionPane.showMessageDialog(null,"替换完成。");
        }else if (e.getActionCommand().equals("aboutBtn")){
            new C2INotice();
        }else if (e.getActionCommand().equals("cancelBtn")){
            //if(JOptionPane.showConfirmDialog(this,"退出","提示",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            //    //System.exit(0);
            //    this.dispose();
            //}
            this.dispose();
        }else if (e.getActionCommand().equals("homeBtn")){
            this.dispose();
        }
    }
}