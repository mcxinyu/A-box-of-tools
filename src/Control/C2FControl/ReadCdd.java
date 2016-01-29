package Control.C2FControl;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用于读取、处理 cdd 文本文件，得到 cdd 参数
 * Created by 跃峰 on 2016/1/27.
 */
public class ReadCdd {

    public static void main(String[] args) {
        File f = new File("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122\\SZ01A.Log");
        ReadCdd rc = new ReadCdd();
        rc.processorSingleLog(f);
    }

    public ReadCdd(){
//        cdd = new File("/Users/huangyuefeng/Downloads/SZ01A.Log");
//        cddContent = readSingleLog(cdd);
    }

    /**
     * 读取单个 cdd-log 文件
     * @param logFile cdd-log 文件路径
     * @return 返回一个 HashMap<Integer,String>
     */
    public HashMap readSingleLog(File logFile){
        int len = 0;
        String line = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer str = new StringBuffer("");
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

    public void processorSingleLog(File logFile){
        HashMap<Integer,String> logContent = readSingleLog(logFile);
//        Iterator iter = logContent.entrySet().iterator();
        int mapSize = logContent.size();

        long startTime=System.currentTimeMillis();  //获取开始时间

        String bscName = "";

        int i = 1;
        String s = logContent.get(i);
        System.out.println(s);
        while (i<mapSize){
            i++;
            if (s.contains("Connected")){
                bscName = s.substring(17,23);
                System.out.println(bscName);
            }

//            if (s.contains("RLDEP")){
//                System.out.println(i);
//            }
        }


//        for (int i=1;i<=mapSize;i++){
////            System.out.println(logContent.get(i));
//            String s = logContent.get(i);
//            if (s.contains("Connected")){
//                bscName = s.substring(17,23);
//            }else if (s.contains("RLDEP")&&!s.contains("EXT")){
//            }
//        }

//        while (iter.hasNext()){
//            HashMap.Entry entry = (HashMap.Entry) iter.next();
////            Object key = entry.getKey();
////            Object val = entry.getValue();
////            System.out.println(entry.getValue());
//        }

//        System.out.println(logContent.get(mapSize));

        long endTime=System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

    /**
     * 读取文件夹
     * @param folderPath
     * @return
     */
    public String textFolderToString(String folderPath) {
        File[] files = new File(folderPath).listFiles();
        String connect ="";
        if (files.length>0){
            for(File file:files){
                System.out.println(file.getName());
                connect += readSingleLog(file);
            }
//            for (int i=0;i<files.length;i++){
//                System.out.println(files[i].getName());
//                connect += textToString(files[i]);
//            }
        }else {
            System.out.println("未找到文件");
        }
        return connect;
    }

}
