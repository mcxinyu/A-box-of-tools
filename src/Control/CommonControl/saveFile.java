package Control.CommonControl;

import javax.swing.*;
import java.io.File;

/**
 * Created by 跃峰 on 2016/2/15.
 */
public class saveFile {
    File file = null;
    public int saveFile (String windowTitle,int fileMode){
        //用于新建一个保存文件的窗口
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle(windowTitle);

        if (fileMode == 0){
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }else if (fileMode == 1){
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }

        int state = jfc.showSaveDialog(null);
        file = jfc.getSelectedFile();

        return state;
    }

    public File getFile() {
        return file;
    }
}
