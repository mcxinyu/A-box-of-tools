package Control.C2FControl;

import java.io.File;

/**
 * 处理 coordinate 文件
 * Created by 跃峰 on 2016/2/2.
 */
public class ProcessorCoordinate {
    public static void main(String[] args) {
        File f = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\coordinate.txt");
        ProcessorCoordinate pc = new ProcessorCoordinate();
        pc.processorSingleLog(f);
    }

    public String[] processorSingleLog(File logFile){
        String[] coordinates = ReadFile.readSingleText(logFile).split("\\r\\n");
        for (int i=0;i<coordinates.length;i++){
            System.out.println(coordinates[i]);
        }
//        System.out.println("总共： "+(coordinates.length-1));
        return coordinates;
    }
}
