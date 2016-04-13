package Control.UpdateC2IControl;

import Control.CommonControl.ReadFile;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 跃峰 on 2016/4/13.
 */
public class Update {
    public static void main(String[] args) {
        Update uc = new Update();
        File obj = new  File("D:\\SZ\\变频工作\\数据采集\\model\\RxLevels.txt");
        String newData[][] = uc.readNewDate(new File("D:\\SZ\\变频工作\\数据采集\\model\\newdata.txt"));
        String exportPath = "D:\\SZ\\变频工作\\数据采集\\model\\";
        uc.replace(obj,newData,exportPath);
    }
    public String readC2I(File c2i){
        String content = null;
        if (c2i != null && c2i.isFile()) {
            content = ReadFile.readSingleText(c2i);
        }
        return content;
    }

    public String[][] readNewDate(File newDataFile){
        String newData[][] = null;
        if (newDataFile != null && newDataFile.isFile()){
            String[] line = ReadFile.readSingleText(newDataFile).split("\\r\\n");

            newData = new String[line.length][2];
            for (int i = 0; i < line.length; i++) {
                String temp[] = line[i].split("\\t");
                newData[i][0] = temp[0];
                newData[i][1] = temp[1];
            }
        }
        return newData;
    }

    public void replace(File c2i,String[][] newData,String exportPath){

        String content = null;
        if (c2i != null && c2i.isFile()) {
            content = ReadFile.readSingleText(c2i);
        }

        File exportFile = new File(exportPath + "\\c-new.txt");

        try (FileWriter exportFileFW = new FileWriter(exportFile)) {
            for (int i = 0; i < newData.length; i++) {
                content = content.replace(newData[i][0],newData[i][1]);
            }
            exportFileFW.write(content);
        }catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"执行失败，找不到文件或文件正在被使用。");
        }
    }
}
