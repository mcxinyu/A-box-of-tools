package Control.FileSplitControl;

import Control.CommonControl.ReadFile;

import java.io.File;

/**
 * Created by 跃峰 on 2016/2/11.
 */
public class TxtSplit {
    public static void main(String[] args) {
        File f = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\coordinate.txt");
        TxtSplit ts = new TxtSplit();
        ts.TxtSplit(f);
    }

    public String[] TxtSplit(File logFile){
        String[] TXT = ReadFile.readSingleText(logFile).split("\\r\\n");
        for (int i=0;i<TXT.length;i++){
            System.out.println(TXT[i]);
        }
//        System.out.println("总共： "+(coordinates.length-1));
        return TXT;
    }
}
