package Control.ToCoordinateControl;

import Control.CommonControl.ReadFile;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * cellinfo2Coordinates
 * Created by 跃峰 on 2016/3/9.
 */
public class cellinfo2Coordinates {
    //public static void main(String[] args) {
    //    long startTime=System.currentTimeMillis();
    //
    //    File f = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\CellInfo-U.txt");
    //    cellinfo2Coordinates c2c = new cellinfo2Coordinates();
    //    String[][] cellinfo = c2c.readCellinfo(f);
    //    String[][] coordinate = c2c.toCoordinates(cellinfo);
    //    c2c.createCoordinatesFile(coordinate,"D:\\");
    //
    //    long endTime=System.currentTimeMillis();
    //    System.out.println("程序运行时间： "+(endTime-startTime)/1000+"s");
    //}

    /**
     * 读取 cellinfo 文件中的重要参数
     * @param cellinfoFile
     * @return
     */
    public String[][] readCellinfo(File cellinfoFile) {
        String[][] cellinfo = null;
        // 传进来的 cellinfoFile 可能是文件夹，也可能是 null
        if (cellinfoFile != null && cellinfoFile.isFile()) {
            //String[] coordinates = ReadFile.readSingleText(coordinateFile).split("\\r|\\n");  // osx 系统
            String[] line = ReadFile.readSingleText(cellinfoFile).split("\\n1|\\n2|\\n3|\\n4|\\n5|\\n6|\\n7|\\n8|\\n9|\\n0"); //windows 系统

            // 记录参数所在的列数
            int siteCHNameInColumn = 0; //中文名、小区名
            int sectorInColumn = 0; //小区号
            int longitudeInColumn = 0;  //经度
            int latitudeInColumn = 0;   //纬度
            int azimuthInColumn = 0;    //方向角
            int cgiInColumn = 0;    //CGI

            String[] line0 = line[0].replace("\"","").split("\t");

            // 需要 UTF-8，使用直接导出的 ANSI 可能会乱码，需要使用别的方式读取，例如固定读取的位置等
            for (int i = 0; i < line0.length; i++) {
                if (line0[i].equals("小区名")){
                    siteCHNameInColumn = i;
                }else if (line0[i].equals("小区号")){
                    sectorInColumn = i;
                }else if (line0[i].equals("经度")){
                    longitudeInColumn = i;
                }else if (line0[i].equals("纬度")){
                    latitudeInColumn = i;
                }else if (line0[i].equals("方向角")){
                    azimuthInColumn = i;
                }else if (line0[i].equals("CGI")){
                    cgiInColumn = i;
                }
            }

            HashMap<String, String[]> cellinfoHM = new HashMap<String, String[]>();
            for (int i = 1; i < line.length; i++) {
                String[] lineTemp = line[i].replace("\"","").split("\t");
                String[] info = new String[6];
                info[0] = lineTemp[siteCHNameInColumn];  //小区名
                info[1] = lineTemp[sectorInColumn];  //小区号
                info[2] = lineTemp[longitudeInColumn];   //经度
                info[3] = lineTemp[latitudeInColumn];    //纬度
                //System.out.println(lineTemp[siteCHNameInColumn]);
                if (i>0 && (lineTemp[azimuthInColumn].equals("") || Integer.parseInt(lineTemp[azimuthInColumn])<=0 || Integer.parseInt(lineTemp[azimuthInColumn])>=360)) {
                    info[4] = "0";
                }else {
                    info[4] = lineTemp[azimuthInColumn]; //方向角
                }
                info[5] = lineTemp[cgiInColumn]; //CGI
                cellinfoHM.put(info[0],info);
                //System.out.println(cellinfo[i][0]+"\t"+cellinfo[i][1]+"\t"+cellinfo[i][2]+"\t"+cellinfo[i][3]+"\t"+cellinfo[i][4]+"\t"+cellinfo[i][5]);
            }

            cellinfo = new String[cellinfoHM.size()][6];
            int count = 0;
            for (Map.Entry<String, String[]> entry : cellinfoHM.entrySet()) {
                entry.getKey();
                cellinfo[count] = entry.getValue();
                count++;
            }

            //for (int i = 0; i < cellinfo.length; i++) {
            //    for (int j = 0; j < cellinfo[i].length; j++) {
            //        System.out.print(cellinfo[i][j]+" ");
            //    }
            //    System.out.println();
            //}
        }
        return cellinfo;
    }

    /**
     * 将 cellinfo 格式转换为 coordinates 格式
     * @param cellinfo
     * @return
     */
    public String[][] toCoordinates(String[][] cellinfo){
        String[][] coordinate = null;

        if (cellinfo !=null){
            coordinate = new String[cellinfo.length][11];
            //coordinate[0][0] = "Sector";
            //coordinate[0][1] = "Longitude";
            //coordinate[0][2] = "LAC";
            //coordinate[0][3] = "CI";
            //coordinate[0][4] = "Azimuth";
            //coordinate[0][5] = "Downtilt";
            //coordinate[0][6] = "SiteCHName";
            //coordinate[0][7] = "Height";
            //coordinate[0][8] = "CoverType";
            //coordinate[0][9] = "CDUTYPE";
            //coordinate[0][10] = "Latitude";

            for (int i = 0; i < cellinfo.length; i++) {
                for (int j = 0; j < cellinfo[i].length; j++) {
                    String[] cgiSplit = new String[4];
                    if (!cellinfo[i][5].equals("")){
                        cgiSplit = cellinfo[i][5].split("-");
                    }else {
                        cgiSplit[0] ="";
                        cgiSplit[1] ="";
                        cgiSplit[2] ="";
                        cgiSplit[3] ="";
                    }
                    coordinate[i][0] = cellinfo[i][1];
                    coordinate[i][1] = cellinfo[i][2];
                    coordinate[i][2] = cgiSplit[2];
                    coordinate[i][3] = cgiSplit[3];
                    coordinate[i][4] = cellinfo[i][4];
                    coordinate[i][5] = "";
                    coordinate[i][6] = cellinfo[i][0];
                    coordinate[i][7] = "";
                    coordinate[i][8] = "";
                    coordinate[i][9] = "";
                    coordinate[i][10] = cellinfo[i][3];
                }
            }
            //for (int i = 0; i < coordinate.length; i++) {
            //    for (int j = 0; j < coordinate[i].length; j++) {
            //        System.out.print(coordinate[i][j]+"\t");
            //    }
            //    System.out.println();
            //}
        }
        return coordinate;
    }

    /**
     * 生成坐标文件
     * @param coordinate
     * @param exportPath
     * @param weed
     * @param holdSiteCHName
     * @return
     */
    public File createCoordinatesFile(String[][] coordinate, String exportPath,boolean weed,boolean holdSiteCHName){
        File coordinatesFile = null;
        int count = 0;
        if (coordinate != null) {
            coordinatesFile = new File(exportPath + "\\coordinates.txt");

            String coordinatesHead = "Sector\tLongitude\tLAC\tCI\tAzimuth\tDowntilt\tSiteCHName\tHeight\tCoverType\tCDUTYPE\tLatitude";

            try (FileWriter coordinateFW = new FileWriter(coordinatesFile);) {
                coordinateFW.write(coordinatesHead + "\r\n");

                if (weed == false && holdSiteCHName == false) {
                    for (int i = 0; i < coordinate.length; i++) {
                        String line = coordinate[i][0] + "\t" +
                                coordinate[i][1] + "\t" +
                                coordinate[i][2] + "\t" +
                                coordinate[i][3] + "\t" +
                                coordinate[i][4] + "\t" +
                                coordinate[i][5] + "\t" +
                                "" + "\t" +
                                coordinate[i][7] + "\t" +
                                coordinate[i][8] + "\t" +
                                coordinate[i][9] + "\t" +
                                coordinate[i][10];
                        coordinateFW.write(line + "\r\n");
                        count++;
                    }
                }else if (weed == false && holdSiteCHName == true){
                    for (int i = 0; i < coordinate.length; i++) {
                        String line = coordinate[i][0] + "\t" +
                                coordinate[i][1] + "\t" +
                                coordinate[i][2] + "\t" +
                                coordinate[i][3] + "\t" +
                                coordinate[i][4] + "\t" +
                                coordinate[i][5] + "\t" +
                                coordinate[i][6] + "\t" +
                                coordinate[i][7] + "\t" +
                                coordinate[i][8] + "\t" +
                                coordinate[i][9] + "\t" +
                                coordinate[i][10];
                        coordinateFW.write(line + "\r\n");
                        count++;
                    }
                }else if (weed == true && holdSiteCHName == true){
                    for (int i = 0; i < coordinate.length; i++) {
                        if (!coordinate[i][0].equals("") && !coordinate[i][1].equals("") && !coordinate[i][2].equals("") && !coordinate[i][3].equals("") && !coordinate[i][4].equals("") && !coordinate[i][10].equals("")) {
                            String line = coordinate[i][0] + "\t" +
                                    coordinate[i][1] + "\t" +
                                    coordinate[i][2] + "\t" +
                                    coordinate[i][3] + "\t" +
                                    coordinate[i][4] + "\t" +
                                    coordinate[i][5] + "\t" +
                                    coordinate[i][6] + "\t" +
                                    coordinate[i][7] + "\t" +
                                    coordinate[i][8] + "\t" +
                                    coordinate[i][9] + "\t" +
                                    coordinate[i][10];
                            coordinateFW.write(line + "\r\n");
                            count++;
                        }
                    }
                }else if (weed == true && holdSiteCHName == false){
                    for (int i = 0; i < coordinate.length; i++) {
                        if (!coordinate[i][0].equals("") && !coordinate[i][1].equals("") && !coordinate[i][2].equals("") && !coordinate[i][3].equals("") && !coordinate[i][4].equals("") && !coordinate[i][10].equals("")) {
                            String line = coordinate[i][0] + "\t" +
                                    coordinate[i][1] + "\t" +
                                    coordinate[i][2] + "\t" +
                                    coordinate[i][3] + "\t" +
                                    coordinate[i][4] + "\t" +
                                    coordinate[i][5] + "\t" +
                                    "" + "\t" +
                                    coordinate[i][7] + "\t" +
                                    coordinate[i][8] + "\t" +
                                    coordinate[i][9] + "\t" +
                                    coordinate[i][10];
                            coordinateFW.write(line + "\r\n");
                            count++;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Coordinates 导出成功，小区数量： "+ count);
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "导出失败，另一个程序正在使用此文件！");
            }
        }
        return coordinatesFile;
    }
}
