package Common;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Created by 跃峰 on 2016/2/14.
 */
public class selectFile {
//    String filePath = "";
//    String fileName = "";
    File file = null;
    public int selectFile (String windowTitle,String fileType){
        MyTools.windowsFeel();
        int a=1;

        //用于新建一个打开文件的窗口
        // 数据名称、按钮名称
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogTitle(windowTitle);

        if (fileType =="excel") {
            jfc.setFileFilter(new FileNameExtensionFilter("Excel文件", "xls", "xlsx"));
        }else if (fileType =="text"){
            jfc.setFileFilter(new FileNameExtensionFilter("文本文件", "txt", "log"));
        }

        int state = jfc.showOpenDialog(null);
        file = jfc.getSelectedFile();

//        if(file.isDirectory()){
//            System.out.println("文件夹:"+file.getAbsolutePath());
//        }else if(file.isFile()){
//            System.out.println("文件:"+file.getAbsolutePath());
//        }

        return state;
    }

//    public String getFilePath() {
//        return filePath;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }

    public File getFile() {
        return file;
    }
}
