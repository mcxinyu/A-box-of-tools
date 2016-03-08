package View.CheckPlanView;
/**
 * Created by 跃峰 on 2015/11/25.
 * View.CheckPlanView 的第二步，简单检查后导出方案检查表
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CheckPlanStep2 extends JPanel implements ActionListener{
    //检查方案步骤二：导出数据面板
    JPanel result;
    JLabel detailsJlb,exportJlb,saveJlb;
    JButton detailsBtn,exportBtn;
    JCheckBox saveData;
    public CheckPlanStep2(){
        //result应该以表格的形式提示错误信息。
        result = new Result();
        result.setBorder(BorderFactory.createTitledBorder("检查告警"));

        exportJlb = new JLabel("点击 导出检查表 用于在网优之家检查。");
        exportBtn = new JButton("导出检查表");

        detailsJlb =new JLabel("点击 查看 方案告警小区详情列表。");
        detailsBtn = new JButton(" 详情报告 ");

        //后续还需要判断 remind 是否勾选保存
        saveData = new JCheckBox("保存新导入的 cdd、cdd_Channel 以备下次使用,并删除上次保存的数据。");
        saveData.setBackground(new Color(230,230,230));
        saveData.setFocusPainted(false);
        saveJlb = new JLabel("点击 完成 保存数据。");

        detailsBtn.setFocusPainted(false);
        exportBtn.setFocusPainted(false);

        detailsBtn.setActionCommand("details");
        detailsBtn.addActionListener(this);
        exportBtn.setActionCommand("export");
        exportBtn.addActionListener(this);

        setLayout(new GridBagLayout());
        add(result,new GBC(0,0,2,1).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(1));

        //add(detailsJlb,new GBC(0,1).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(10,0));
        //add(detailsBtn,new GBC(1,1).setFill(GBC.NONE).setWeight(5,5).setAnchor(GBC.WEST).setInsets(10,0));

        add(exportJlb,new GBC(0,2).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(10,0));
        add(exportBtn,new GBC(1,2).setFill(GBC.NONE).setWeight(5,5).setAnchor(GBC.WEST).setInsets(10,0));

        add(saveData,new GBC(0,3).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.WEST).setInsets(10,0));
        add(saveJlb,new GBC(0,4).setFill(GBC.HORIZONTAL).setWeight(5,5).setAnchor(GBC.EAST).setInsets(10,0));
        setBackground(new Color(230,230,230));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //显示告警详细 和 导出数据
        e.getActionCommand();
        if (e.getActionCommand() == "details") {

            Frame f=JOptionPane.getFrameForComponent(this);
            Details details = new Details(f,"详细告警",true);

        }else if (e.getActionCommand() == "export"){
            //导出数据
            this.saveFile("导出检查表");
        }
    }

    public void saveFile (String title){

        //用于新建一个保存文件的窗口
        // 数据名称
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setDialogTitle(title);
        jfc.setFileFilter(new FileNameExtensionFilter("Excel文件", "xls", "xlsx"));
        jfc.showSaveDialog(null);
        jfc.getSelectedFile();
    }
}

