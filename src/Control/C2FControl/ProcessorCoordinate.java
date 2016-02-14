package Control.C2FControl;

import java.io.File;

/**
 * 处理 coordinate 文件
 * Created by 跃峰 on 2016/2/2.
 */
public class ProcessorCoordinate {
    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();

        File f = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\coordinate.txt");
        ProcessorCoordinate pc = new ProcessorCoordinate();
        pc.processorSingleLog(f);

        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

    public String[][] processorSingleLog(File logFile){
        String[] coordinates = ReadFile.readSingleText(logFile).split("\\r\\n");
        String[] temp = new String[11];
        // cellCoordinate : Sector + Longitude + Latitude + LAC + CI + Azimuth;
        String[][] cellCoordinate = new String[coordinates.length][6];

        for (int i=0;i<coordinates.length;i++) {
            temp = coordinates[i].split("\\t");
            cellCoordinate[i][0] = temp[0];
            cellCoordinate[i][1] = temp[1];
            cellCoordinate[i][2] = temp[10];
            cellCoordinate[i][3] = temp[2];
            cellCoordinate[i][4] = temp[3];
            cellCoordinate[i][5] = temp[4];
//            for (int j = 0; j < cellCoordinate[i].length; j++) {
//                System.out.print(cellCoordinate[i][j] + " ");
//            }
//            System.out.println();
        }
//        System.out.println("总共： "+(coordinates.length-1));
        return cellCoordinate;
    }
}
