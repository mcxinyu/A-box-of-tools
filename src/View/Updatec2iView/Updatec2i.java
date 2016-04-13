package View.UpdateC2IView;

import Common.ControlBtnArea;
import Common.MyTools;
import Common.WelcomeArea;
import Control.ToCoordinateControl.cellinfo2Coordinates;
import View.Cdd2ForteView.Cdd2Forte;
import View.CheckPlanView.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 跃峰 on 2016/4/13.
 */
public class UpdateC2I extends JFrame implements ActionListener {
    //JFrame frame;
    JPanel welcomeArea;
    JPanel controlBtnArea;
    JButton backBtn,nextBtn,homeBtn,okBtn,aboutBtn,readC2i,readNewData,toC2i;
    JPanel contentsArea;
    JLabel temp1,temp2,text,taps;

    public static void main(String[] args) {
        new UpdateC2I();
    }

    public UpdateC2I(){
        MyTools.windowsFeel();

        //欢迎栏
        welcomeArea =new WelcomeArea(new ImageIcon(Cdd2Forte.class.getResource("/icons/UpdateC2I_64.png")),"  一箱工具 - 更新c2i");

        //contentsArea
        text = new JLabel("小区割接后更新c2i。");
        text.setFont(MyTools.fontPlain13);
        temp1 = new JLabel("                    ");
        temp2 = new JLabel("                    ");

        readC2i = new JButton("读取 c2i ");
        readC2i.setFont(MyTools.fontBold18);

        readNewData = new JButton("读取割接信息");
        readNewData.setFont(MyTools.fontBold18);

        toC2i = new JButton("导出 c2i ");
        toC2i.setFont(MyTools.fontBold18);
        toC2i.setEnabled(false);

        taps = new JLabel("开始读取 c2i 吧");

        //注册监听
        readC2i.setActionCommand("readC2i");
        readC2i.addActionListener(this);
        readNewData.setActionCommand("readNewData");
        readNewData.addActionListener(this);
        toC2i.setActionCommand("toC2i");
        toC2i.addActionListener(this);

        //添加组件到 contentsArea238,238,238
        contentsArea = new JPanel(new GridBagLayout());
        contentsArea.setBackground(new Color(238,238,238));
        contentsArea.add(text,new GBC(0,0,4,1).setFill(GBC.VERTICAL).setAnchor(GBC.CENTER).setIpad(0,20).setInsets(0,1));
        contentsArea.add(readC2i,new GBC(0,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,1,0,0));
        contentsArea.add(readNewData,new GBC(1,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));
        contentsArea.add(toC2i,new GBC(2,1,1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setIpad(20,20).setInsets(0,0,0,1));
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
        this.setTitle("一箱工具 - 更新c2i");
        this.setIconImage (Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icons/UpdateC2I_64.png")));

        this.setSize(626,300);
        this.setResizable(false);//固定窗体大小
        this.setLocationRelativeTo(null);//打开时相对window居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    String[][] cellinfo = null;
    cellinfo2Coordinates c2c = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        c2c = new cellinfo2Coordinates();
        if (e.getActionCommand().equals("readCoordinateBtn")){
            //toC2i.setEnabled(false);
            //int state;
            //taps.setText("正在读取 Cellinfo...");
            //selectFile select = new selectFile();
            //state = select.selectFile("读取 Cellinfo 文件", "text",JFileChooser.FILES_ONLY,false,this);
            //File[] fileList = new ReadFile().readMultiText(select.getFile());
            //if (state == 0) {
            //    //如果点击确定
            //    if (fileList != null && fileList.length == 1) {
            //        try {
            //            cellinfo = c2c.readCellinfo(fileList[0]);
            //            taps.setText("Cellinfo 中读取到 "+ cellinfo.length +" 个小区的数据");
            //            JOptionPane.showMessageDialog(null,"Cellinfo 读取成功");
            //            toCoordinateBtn.setEnabled(true);
            //        }catch (Exception e1){
            //            e1.printStackTrace();
            //            JOptionPane.showMessageDialog(null,"Cellinfo 文件错误");
            //            cellinfo = null;
            //            taps.setText("重新选取 Cellinfo 文件");
            //            toCoordinateBtn.setEnabled(false);
            //        }
            //    }
            //}else if (state == 1) {
            //    taps.setText("开始读取 Cellinfo 吧");
            //}
        }else if (e.getActionCommand().equals("toCoordinateBtn")){
            //saveFile save = new saveFile();
            //int state = save.saveFile("导出 Coordinates 文件",1,this);
            //if (state == 0){
            //    if (cellinfo!=null) {
            //        String[][] coordinate = c2c.toCoordinates(cellinfo);
            //        c2c.createCoordinatesFile(coordinate, save.getFile().getPath(),jcb1.isSelected(),jcb2.isSelected());
            //    }
            //}else if (state == 1){
            //}
        }else if (e.getActionCommand().equals("aboutBtn")){
            new C2INotice();
        }else if (e.getActionCommand().equals("cancelBtn")){
            //if(JOptionPane.showConfirmDialog(this,"退出","提示",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            //    //System.exit(0);
            //    this.dispose();
            //}
        }else if (e.getActionCommand().equals("homeBtn")){
            this.dispose();
        }
    }
}