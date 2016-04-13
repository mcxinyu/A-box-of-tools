package Control.UpdateC2IControl;

import Control.CommonControl.ReadFile;

import java.io.File;

/**
 * Created by 跃峰 on 2016/4/13.
 */
public class Update {
    public static void main(String[] args) {
        Update uc = new Update();
        //uc.readC2I(new File("D:\\SZ\\变频工作\\数据采集\\model\\c2i.txt"));
        uc.readNewDate(new File("D:\\SZ\\变频工作\\数据采集\\model\\new.txt"));
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


            //for (int i = 0; i < newData.length; i++) {
            //    System.out.println(newData[i][0]+"-"+newData[i][1]);
            //}

            //for (int i = 0; i < line.length; i++) {
            //    System.out.println(line[i]);
            //}
        }
        return newData;
    }
}
