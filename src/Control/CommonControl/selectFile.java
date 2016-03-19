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
    public int selectFile (String windowTitle,String fileType,int mode,Component parent){
        MyTools.windowsFeel();

        //用于新建一个打开文件的窗口
        // 数据名称、按钮名称
        JFileChooser jfc = new JFileChooser();
        //jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setFileSelectionMode(mode);
        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogTitle(windowTitle);

        if (fileType.equals("excel")){
            jfc.setFileFilter(new FileNameExtensionFilter("Excel文件", "xls", "xlsx"));
        }else if (fileType.equals("text")){
            jfc.setFileFilter(new FileNameExtensionFilter("文本文件", "txt", "log"));
        }else if (fileType == null){
        }

        //返回值为 state 即 APPROVE_OPTION 0，CANCEL_OPTION 1
        int state = jfc.showOpenDialog(parent);

        files = jfc.getSelectedFiles();

//        if(files[0].isDirectory()){
//            files[] = files[0].listFiles();
//        }else if(file.isFile()){
//        }

        return state;
    }

    public File[] getFile() {
        return files;
    }
}
