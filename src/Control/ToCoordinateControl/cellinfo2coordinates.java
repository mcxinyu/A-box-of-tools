package Control.ToCoordinateControl;

import Control.CommonControl.ReadFile;

import java.io.File;

/**
 * cellinfo2coordinates
 * Created by 跃峰 on 2016/3/9.
 */
public class cellinfo2coordinates {
    public static void main(String[] args) {
        File f = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\CellInfo.txt");
        cellinfo2coordinates c2c = new cellinfo2coordinates();
        c2c.readCellinfo(f);
    }

    public String[][] readCellinfo(File cellinfoFile) {
        String[][] cellinfo = null;
        // 传进来的 cellinfoFile 可能是文件夹，也可能是 null
        if (cellinfoFile != null && cellinfoFile.isFile()) {
            //String[] coordinates = ReadFile.readSingleText(coordinateFile).split("\\r|\\n");  // osx 系统
            String[] line = ReadFile.readSingleText(cellinfoFile).replace("\"","").split("\\r\\n"); //windows 系统

            // 记录参数所在的列数
            int siteCHNameInColumn = 0; //中文名、小区名
            int sectorInColumn = 0; //小区号
            int longitudeInColumn = 0;  //经度
            int latitudeInColumn = 0;   //纬度
            int azimuthInColumn = 0;    //方向角
            int cgiInColumn = 0;    //CGI

            String[] temp = line[0].split("\t");
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].equals("小区名")){
                    siteCHNameInColumn = i;
                }else if (temp[i].equals("小区号")){
                    sectorInColumn = i;
                }else if (temp[i].equals("经度")){
                    longitudeInColumn = i;
                }else if (temp[i].equals("纬度")){
                    latitudeInColumn = i;
                }else if (temp[i].equals("方向角")){
                    azimuthInColumn = i;
                }else if (temp[i].equals("CGI")){
                    cgiInColumn = i;
                }
            }

            cellinfo = new String[line.length][6];

            for (int i = 0; i < line.length; i++) {
                String[] lineTemp = line[i].split("\t");
                cellinfo[i][0] = lineTemp[siteCHNameInColumn];  //小区名
                cellinfo[i][1] = lineTemp[sectorInColumn];  //小区号
                cellinfo[i][2] = lineTemp[longitudeInColumn];   //经度
                cellinfo[i][3] = lineTemp[latitudeInColumn];    //纬度
                if (i>0 && (Integer.parseInt(lineTemp[azimuthInColumn])<=0 || Integer.parseInt(lineTemp[azimuthInColumn])>=360 || lineTemp[azimuthInColumn].equals(""))) {
                    cellinfo[i][4] = "0";
                }else {
                    cellinfo[i][4] = lineTemp[azimuthInColumn]; //方向角
                }
                cellinfo[i][5] = lineTemp[cgiInColumn]; //CGI
                System.out.println(cellinfo[i][0]+"\t"+cellinfo[i][1]+"\t"+cellinfo[i][2]+"\t"+cellinfo[i][3]+"\t"+cellinfo[i][4]+"\t"+cellinfo[i][5]);
            }

        }
        return cellinfo;
    }
}
