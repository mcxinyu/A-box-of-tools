package Control.UpdateC2IControl;

import Control.CommonControl.ReadFile;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 跃峰 on 2016/4/13.
 */
public class replaceText {
    public static void main(String[] args) {
        replaceText uc = new replaceText();
        File objFile = new  File("D:\\SZ\\变频工作\\数据采集\\model\\Traffic.txt");
        String newData[][] = uc.readNewDate(new File("D:\\SZ\\变频工作\\数据采集\\model\\newdata.txt"));
        uc.replace(objFile,newData);
    }

    public String readText(File txt){
        String content = null;
        if (txt != null && txt.isFile()) {
            content = ReadFile.readSingleText(txt);
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

    public void replace(File objFile,String[][] newData){

        String content = readText(objFile);

        String newName = objFile.getName().substring(0, objFile.getName().lastIndexOf(".")) + " - new" + objFile.getName().substring(objFile.getName().lastIndexOf("."));
        File exportFile = new File(objFile.getParent() + File.separator + newName);

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
