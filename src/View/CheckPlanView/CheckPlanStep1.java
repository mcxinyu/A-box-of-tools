package View.CheckPlanView;
/**
 * Created by 跃峰 on 2015/11/25.
 * View.CheckPlanView 的第一步，导入数据：现网cdd与cdd_Channel、方案
 */
import View.HomeView.MyTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CheckPlanStep1 extends JPanel implements ItemListener,ActionListener {
    //检查方案步骤一：导入数据面板
    JPanel importData,planData,planText;
    FileItem ago_cdd,ago_channel,fresh_cdd,fresh_channel,plan;
    ButtonGroup bg;
    JRadioButton ago,fresh;
    public CheckPlanStep1(){
        //初始化导入数据面板
        importData = new JPanel(new GridBagLayout());
        importData.setBorder(BorderFactory.createTitledBorder("导入现网数据"));
        importData.setBackground(new Color(230,230,230));
        planData = new JPanel(new GridBagLayout());
        planData.setBorder(BorderFactory.createTitledBorder("导入变频方案"));
        planData.setBackground(new Color(230,230,230));

        bg = new ButtonGroup();
        ago = new JRadioButton("使用上次保存的数据",false);
        fresh = new JRadioButton("重新导入数据",true);
        bg.add(ago);
        bg.add(fresh);
        ago.setBackground(new Color(230,230,230));
        ago.setFocusPainted(false);
        fresh.setBackground(new Color(230,230,230));
        fresh.setFocusPainted(false);

        //给复选框设置监听
        ago.addItemListener(this);
        fresh.addItemListener(this);

        ago_cdd = new FileItem("现网 cdd",2);
        ago_channel = new FileItem("cdd_channel",2);
        fresh_cdd = new FileItem("现网 cdd",3);
        fresh_channel = new FileItem("cdd_channel",3);
        planText = new FileItem("变频方案要包含 小区名、参数名、原值、新值",1);
        plan = new FileItem("变频方案",3);


        //给按钮设置监听
        fresh_cdd.selectBtn.setActionCommand("cdd");
        fresh_cdd.selectBtn.addActionListener(this);
        fresh_channel.selectBtn.setActionCommand("channel");
        fresh_channel.selectBtn.addActionListener(this);
        plan.selectBtn.setActionCommand("plan");
        plan.selectBtn.addActionListener(this);

        ago_cdd.use(false);
        ago_channel.use(false);

        importData.add(ago,new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));
        importData.add(ago_cdd,new GBC(0,1).setFill(GBC.NONE).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));
        importData.add(ago_channel,new GBC(0,2).setFill(GBC.NONE).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));
        importData.add(fresh,new GBC(0,3).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));
        importData.add(fresh_cdd,new GBC(0,4).setFill(GBC.NONE).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));
        importData.add(fresh_channel,new GBC(0,5).setFill(GBC.NONE).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));
        planData.add(planText,new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));
        planData.add(plan,new GBC(0,1).setFill(GBC.NONE).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));

        setLayout(new GridBagLayout());
        add(importData,new GBC(0,0).setInsets(0,0,10,0));
        add(planData,new GBC(0,1).setInsets(10,0));
        setBackground(new Color(230,230,230));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //选择数据时文件信息区的变化，设置不选中的不可用
        Object source = e.getItemSelectable();
        if (source == ago) {
            ago_cdd.use(true);
            ago_channel.use(true);
            fresh_cdd.use(false);
            fresh_channel.use(false);
//            saveData.setEnabled(false);
//            saveData.setSelected(false);
        }else {
            ago_cdd.use(false);
            ago_channel.use(false);
            fresh_cdd.use(true);
            fresh_channel.use(true);
//            saveData.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String filePath = "";
        //监听,调用 selectFile() 打开文件
        e.getActionCommand();
        if (e.getActionCommand() == "cdd") {
            filePath = this.selectFile("导入 现网 cdd");
            fresh_cdd.filePath.setText(filePath);
        }else if (e.getActionCommand() == "channel"){
            filePath = this.selectFile("导入 cdd_Channel");
            fresh_channel.filePath.setText(filePath);
        }else if (e.getActionCommand() == "plan"){
            filePath = this.selectFile("导入 变频方案");
            plan.filePath.setText(filePath);
        }
    }

    public String selectFile (String title){
        MyTools.windowsFeel();
        String filePath = "";

        //用于新建一个打开文件的窗口
        // 数据名称、按钮名称
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setDialogTitle(title);
        jfc.setFileFilter(new FileNameExtensionFilter("Excel文件", "xls", "xlsx"));
        int re = jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        if (re == 0 ){
            //如果点击确定，将选择的文件路径传给文本框
            filePath = file.getAbsolutePath();
        }
//            else if (re == 1){
//                //如果点击取消，清空文本框
//                btnName.FilePath.setText("...");
//            }
        return filePath;
    }
}

