package Control.ToCoordinateControl;

import Control.CommonControl.ReadFile;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Hashtable;
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
     * @param filterData    剔除无效数据
     * @param holdSiteCHName    保留中文名
     * @param optLocation   优化重叠小区经纬度
     * @param optAzimuth    优化重叠小区方向角
     * @return
     */
    public File createCoordinatesFile(String[][] coordinate, String exportPath, boolean filterData, boolean holdSiteCHName, boolean optLocation, boolean optAzimuth, int parameter){
        File coordinatesFile = null;
        int count = 0;
        if (coordinate != null) {
            coordinatesFile = new File(exportPath + "\\coordinates.txt");

            String coordinatesHead = "Sector\tLongitude\tLAC\tCI\tAzimuth\tDowntilt\tSiteCHName\tHeight\tCoverType\tCDUTYPE\tLatitude";

            try (FileWriter coordinatesFW = new FileWriter(coordinatesFile);) {
                coordinatesFW.write(coordinatesHead + "\r\n");

                String[][] coordinates = new String[coordinate.length][];

                // 剔除无效数据
                if (filterData == true){
                    coordinates = filterData(coordinate);
                }else {
                    coordinates = coordinate;
                }

                // 保留中文名
                if (holdSiteCHName == true){
                    //不用修改上一步的内容；
                }else {
                    coordinates = notHoldSiteCHName(coordinates);
                }

                // 优化重叠小区经纬度
                if (optLocation == true){
                    coordinates = optLocation(coordinates,parameter);
                }

                // 优化重叠数小区的方向角
                if (optAzimuth ==true){
                    coordinates = optAzimuth(coordinates,parameter);
                }

                for (int i = 0; i < coordinates.length; i++) {
                    if (coordinates[i][0] != "null" &&coordinates[i][0] != "" && coordinates[i][0] != null){
                        String line = "";
                        for (int j = 0; j < coordinates[i].length; j++) {
                            line += coordinates[i][j] + "\t";
                        }
                        coordinatesFW.write(line + "\r\n");
                        count++;
                    }
                }

                JOptionPane.showMessageDialog(null, "Coordinates 导出成功，小区数量： " + count);
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "导出失败，另一个程序正在使用此文件！");
            }
        }
        return coordinatesFile;
    }

    /**
     * 剔除无效数据的方法
     * @param coordinate
     * @return
     */
    public String[][] filterData(String[][] coordinate){
        //String[][] coordinates = new String[coordinate.length][11];

        for (int i = 0; i < coordinate.length; i++) {
            if (!coordinate[i][0].equals("") && !coordinate[i][1].equals("") && !coordinate[i][2].equals("") && !coordinate[i][3].equals("") && !coordinate[i][4].equals("") && !coordinate[i][10].equals("")) {
                //for (int j = 0; j < coordinate[i].length; j++) {
                //    coordinate[i][j] = coordinate[i][j];
                //}
            }else {
                for (int j = 0; j < coordinate[i].length; j++) {
                    coordinate[i][j] = null;   //如果一条数据出现了任意一个空值，整条数据就置为 null
                }
            }
        }
        return coordinate;
    }

    /**
     * 不保留中文名的方法
     * @param coordinate
     */
    public String[][] notHoldSiteCHName(String[][] coordinate){
        //String[][] coordinates = new String[coordinate.length][11];

        for (int i = 0; i < coordinate.length; i++) {
            for (int j = 0; j < coordinate[i].length; j++) {
                if (j == 6){
                    coordinate[i][6] = "";  //不保留中文名，则置空
                }else {
                    //coordinates[i][j] = coordinate[i][j];
                }
            }
        }
        return coordinate;
    }

    /**
     * 优化重叠小区经纬度的方法，未完成
     * @param coordinate
     * @param parameter
     * @return
     */
    public String[][] optLocation(String[][] coordinate, int parameter){
        //String[][] coordinates = new String[coordinate.length][11];

        //统计出现次数
        Hashtable<String, Integer> map=new Hashtable<String,Integer>(coordinate.length);

        for (int i = 0; i < coordinate.length; i++) {
            if (coordinate[i][0] != "" && coordinate[i][0] != null) {
                map.put(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter), map.containsKey(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter)) ? map.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter)) + 1 : 1);
            }
        }

        //for(Map.Entry<String, Integer> e:map.entrySet()){
        //    System.out.println(e.getKey()+" 出现次数是:"+e.getValue());
        //}

        // 记录小区出现次数大于6的出现次数，以便确定是否是第一个小区
        Hashtable<String, Integer> map2 = new Hashtable<String,Integer>();

        for (int i = 0; i < coordinate.length; i++) {
            if (coordinate[i][0] != null && map.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter))>parameter){
                //统计大于3的出现次数，出现第二次就处理
                map2.put(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter),map2.containsKey(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter))?map2.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter))+1:1);

                if (map2.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter))>1){
                    coordinate[i][1] = String.valueOf(Float.parseFloat(coordinate[i][1]) - 0.003);
                }
            }
        }
        return coordinate;
    }

    /**
     * 优化重叠数小区的方向角的方法
     * @param coordinate
     * @return
     */
    public String[][] optAzimuth(String[][] coordinate, int parameter){
        //String[][] coordinates = new String[coordinate.length][11];

        //统计重复的小区数
        Hashtable<String, Integer> map = new Hashtable<String,Integer>();

        for (int i = 0; i < coordinate.length; i++) {
            if (coordinate[i][0] != "null" && coordinate[i][0] != "" && coordinate[i][0] != null){

                map.put(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4],map.containsKey(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4])?map.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4])+1:1);
            }
        }

        //for(Map.Entry<String, Integer> e:map.entrySet()){
        //    System.out.println(e.getKey()+" 出现次数是:"+e.getValue());
        //}

        // 记录小区出现次数大于3的出现次数，以便确定是否是第一个小区
        Hashtable<String, Integer> map2 = new Hashtable<String,Integer>();

        for (int i = 0; i < coordinate.length; i++) {
            if (coordinate[i][0] != "null" && coordinate[i][0] != "" && coordinate[i][0] != null && map.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4])>1){
                //统计大于3的出现次数，出现第二次就处理
                map2.put(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4],map2.containsKey(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4])?map2.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4])+1:1);

                int angle = (Integer) 360/
                        map.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4]) *
                        map2.get(coordinate[i][1] + coordinate[i][10] + coordinate[i][0].substring(parameter-1,parameter) + coordinate[i][4]);

                if (angle == 360){
                    angle = 0;
                }

                coordinate[i][4] = String.valueOf(angle);

                System.out.println(coordinate[i][0] +"-"+ coordinate[i][4]);
            }
        }
        return coordinate;
    }
}
