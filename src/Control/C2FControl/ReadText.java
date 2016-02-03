package Control.C2FControl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 用于读取文本文件
 * Created by 跃峰 on 2016/2/3.
 */
public class ReadText {

    /**
     * 读取单个 cdd-log 文件，通过“<”分列文件，并返回一个数组
     *
     * @param logFile cdd-log 文件路径
     * @param a       随便
     * @return 返回一个数组 string [ ]
     */
    public static String readSingleText(File logFile,int a) {
        FileInputStream fis = null;
        Long filelength = logFile.length();    //获取文件长度
        String fileName = logFile.getName();   //文件名
        byte[] fileContent = new byte[filelength.intValue()];
        try {
            fis = new FileInputStream(logFile);
            fis.read(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(fileContent);
    }


    /**
     * 读取单个 cdd-log 文件，返回一个 HashMap<Integer,String>
     *
     * @param logFile cdd-log 文件路径
     * @return 返回一个 HashMap<Integer,String>
     */
    public static HashMap readSingleText(File logFile){
        int len = 0;
        String line = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        HashMap<Integer,String> logContent = new HashMap<Integer, String>();

        try {
            fis = new FileInputStream(logFile);
            isr= new InputStreamReader(fis);
            br = new BufferedReader(isr);

            while((line=br.readLine())!=null){
                len++;
                logContent.put(len,line);
            }
//            System.out.println(len);
//            System.out.println(logContent.size());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null) br.close();
                if (isr != null) isr.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return logContent;
    }

}
