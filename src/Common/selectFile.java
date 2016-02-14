package Common;

import View.HomeView.MyTools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Created by 跃峰 on 2016/2/14.
 */
public class selectFile {
    String filePath = "";
    public int selectFile (String windowTitle,String fileType){
        MyTools.windowsFeel();
        int a=1;

        //用于新建一个打开文件的窗口
        // 数据名称、按钮名称
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setDialogTitle(windowTitle);
        if (fileType =="excel") {
            jfc.setFileFilter(new FileNameExtensionFilter("Excel文件", "xls", "xlsx"));
        }else if (fileType =="text"){
            jfc.setFileFilter(new FileNameExtensionFilter("文本文件", "txt", "log"));
        }
        int re = jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        if (re == 0 ){
            a = 0;
            //如果点击确定
            this.filePath = file.getAbsolutePath();
        } else if (re == 1){
            a = 1;
            //如果点击取消
        }
        return a;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
