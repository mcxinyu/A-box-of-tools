package Control.Cdd2ForteControl;

import Common.SQLHelper.SqlHelper;
import Common.enumClass;

import javax.swing.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static Common.enumClass.excelType.*;

/**
 * Created by 跃峰 on 2016/6/15.
 */
public class ProcessorSQLCdd {
    //定义查询语句
    public static final String queryCdd = "SELECT CELL, BSC, MSC, SITE, lac, ci, bcchno, bsic, TCH, tchnum, mcc, mnc, bspwrb, CDate FROM WYZJ.dbo.S_cdd_Internal WHERE cell_name IS NOT NULL and 1 = ?";
    public static final String queryChannel = "SELECT cell, ch_group, bsc, chgr_tg, band, channel_tch, hsn, sdcch, tchnum, hop, CDate FROM WYZJ.dbo.S_cdd_Channel WHERE cell_name IS NOT NULL and 1 = ?";
    public static final String queryHandover = "SELECT CELL, BSC, n_cell, n_bsc, hihyst, khyst, koffset, CDate FROM WYZJ.dbo.S_cdd_Nrel WHERE cell_name IS NOT NULL and 1 = ?";

    boolean isConnected = false;
    String CDate = "";

    /**
     * 获取 ExcelContent，避免每次都读取。
     * @return
     * @throws SQLException
     */
    public HashMap<enumClass.excelType, HashMap> get3Query(){
        HashMap<enumClass.excelType, HashMap> content = new HashMap<enumClass.excelType, HashMap>();

        ResultSet[] resultSets = new ResultSet[3];
        SqlHelper sqlHelper = new SqlHelper();
        //开始查询sql
        resultSets[0] = sqlHelper.query(queryCdd, new String[]{"1"});
        resultSets[1] = sqlHelper.query(queryChannel, new String[]{"1"});
        resultSets[2] = sqlHelper.query(queryHandover, new String[]{"1"});
        System.out.println("数据库查询完毕");


        try {
            HashMap<String, String[]> paramContentCdd = new HashMap<>();
            while (resultSets[0].next()){
                String[] row = new String[14];
                for (int j = 0; j < row.length; j++) {
                    row[j] = resultSets[0].getString(j+1);
                }
                CDate = resultSets[0].getString("CDate").split(" ")[0];
                paramContentCdd.put(resultSets[0].getString("CELL"),row);
            }
            content.put(cdd,paramContentCdd);

            HashMap<String, String[]> paramContentChannel = new HashMap<>();
            while (resultSets[1].next()){
                String[] row = new String[11];
                for (int j = 0; j < row.length; j++) {
                    row[j] = resultSets[1].getString(j+1);
                }
                paramContentChannel.put(resultSets[1].getString("cell") + resultSets[1].getString("ch_group"),row);
            }
            content.put(channel,paramContentChannel);

            HashMap<String, String[]> paramContentHandover = new HashMap<>();
            while (resultSets[2].next()){
                String[] row = new String[8];
                for (int j = 0; j < row.length; j++) {
                    row[j] = resultSets[2].getString(j+1);
                }
                paramContentHandover.put(resultSets[2].getString("CELL") + resultSets[2].getString("n_cell"),row);
            }
            content.put(handover,paramContentHandover);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sqlHelper.close();
        }

        isConnected = sqlHelper.isConnected();
        if (isConnected){
            JOptionPane.showMessageDialog(null,"数据库连接成功，读取到 " + CDate +" 的数据。");
        }else {
            JOptionPane.showMessageDialog(null,"数据库连接失败，请确认数据库连接正常后再试一次。");
        }

        return content;
    }


    public static void main(String[] args) throws SQLException {
        File coordinateFile = new File("D:\\SZ\\日常工作\\20160608\\coordinates.txt");
        ProcessorCoordinate processorCoordinate = new ProcessorCoordinate();
        String[][] coordinate = processorCoordinate.readCoordinates(coordinateFile);

        String exportPath = "D:\\thxhyf\\Documents\\";

        ProcessorSQLCdd readSQLData = new ProcessorSQLCdd();
        HashMap<enumClass.excelType, HashMap> query = readSQLData.get3Query();

        //ProcessorExcelCdd processorExcelCdd = new ProcessorExcelCdd();
        //ArrayList[][] forteArray = processorExcelCdd.createForteArray(query, coordinate);
        //
        //ProcessorTxtCdd processorTxtCdd = new ProcessorTxtCdd();
        //processorTxtCdd.createForteFile(forteArray,exportPath);
    }
}
