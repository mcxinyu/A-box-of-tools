package Control.C2FControl;

import java.io.*;

/**
 * 处理 coordinate 文件
 * Created by 跃峰 on 2016/2/2.
 */
public class ProcessorCoordinate {

//    public static void main(String[] args) {
//        long startTime=System.currentTimeMillis();
//
//        File f = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\coor.txt");
//        ProcessorCoordinate pc = new ProcessorCoordinate();
//        pc.processorSingleLog(f);
//
//        long endTime=System.currentTimeMillis();
//        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
//    }

    String notice = "";
    public String[][] processorSingleLog(File coordinateFile){
        String[] coordinates = ReadFile.readSingleText(coordinateFile).split("\\r\\n");

//        System.out.println(coordinates[0].length());
//        for (int c=0;c<coordinates.length;c++){
//            System.out.println(coordinates[0]);
//        }

        // cellCoordinate : Sector + Longitude + Latitude + LAC + CI + Azimuth;
        String[][] cellCoordinate = new String[coordinates.length][6];

        if (coordinates[0].length()==85) {
            for (int i = 0; i < coordinates.length; i++){
                String[] temp = coordinates[i].split("\\t");
                cellCoordinate[i][0] = temp[0]; //Sector
                cellCoordinate[i][1] = temp[1]; //Longitude
                cellCoordinate[i][2] = temp[10];    //Latitude
                cellCoordinate[i][3] = temp[2]; //LAC
                cellCoordinate[i][4] = temp[3]; //CI
                cellCoordinate[i][5] = temp[4]; //Azimuth

//                for (int j = 0; j < cellCoordinate[i].length; j++) {
//                    System.out.print(cellCoordinate[i][j] + " ");
//                }
//                System.out.println();
            }
            notice = "坐标处理完毕："+coordinateFile.getName();
//            System.out.println("处理完毕： "+(coordinates.length-1));
        }else if(coordinates[0].length()==14 || coordinates[0].length()==20){
            for (int i = 0; i < coordinates.length; i++){
                String[] temp = coordinates[i].split("\\t");
                cellCoordinate[i][0] = temp[0]; //Sector
                cellCoordinate[i][1] = temp[1]; //Longitude
                cellCoordinate[i][2] = temp[2]; //Latitude
                cellCoordinate[i][3] = "N/A";   //LAC
                cellCoordinate[i][4] = "N/A";   //CI
                cellCoordinate[i][5] = temp[3]; //Azimuth

//                for (int j = 0; j < cellCoordinate[i].length; j++) {
//                    System.out.print(cellCoordinate[i][j] + " ");
//                }
//                System.out.println();
            }
            notice = "坐标处理完毕："+coordinateFile.getName();
//            System.out.println("处理完毕： "+(coordinates.length-1));
        }else {
            System.out.println("坐标文件错误");
            notice = "坐标文件错误";
        }
        return cellCoordinate;
    }

    public String getNotice() {
        return notice;
    }
}
