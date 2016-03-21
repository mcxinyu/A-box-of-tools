package Control.CommonControl;

import Common.MyTools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Created by 跃峰 on 2016/2/14.
 */
public class selectFile {
    File files[] = null;
    public int selectFile (String windowTitle,String fileType,int selectionMode,boolean multiSelectionEnabled,Component parent){
        MyTools.windowsFeel();

        //用于新建一个打开文件的窗口
        // 数据名称、按钮名称
        JFileChooser jfc = new JFileChooser();
        //jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setFileSelectionMode(selectionMode);
        jfc.setMultiSelectionEnabled(multiSelectionEnabled);
        jfc.setDialogTitle(windowTitle);

        if (fileType.equals("excel")){
            jfc.setFileFilter(new FileNameExtensionFilter("Excel文件", "xls", "xlsx"));
        }else if (fileType.equals("text")){
            jfc.setFileFilter(new FileNameExtensionFilter("文本文件", "txt", "log"));
        }else if (fileType == "all"){
        }

        //返回值为 state 即 APPROVE_OPTION 0，CANCEL_OPTION 1
        int state = jfc.showOpenDialog(parent);

        if(multiSelectionEnabled == true){
            files = jfc.getSelectedFiles();
        }else if(multiSelectionEnabled == false){
            files = new File[1];
            files[0] = jfc.getSelectedFile();
        }

        return state;
    }

    public File[] getFile() {
        return files;
    }
}
