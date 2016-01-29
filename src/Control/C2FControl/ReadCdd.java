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
//        String[] ss = rc.readSingleLog(f,1);
        rc.processorSingleLog(f,1);
    }

    public ReadCdd(){
//        cdd = new File("/Users/huangyuefeng/Downloads/SZ01A.Log");
//        cddContent = readSingleLog(cdd);
    }

    /**
     * 读取单个 cdd-log 文件，返回一个 HashMap<Integer,String>
     * @param logFile cdd-log 文件路径
     * @return 返回一个 HashMap<Integer,String>
     */
    public HashMap readSingleLog(File logFile){
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

    /**
     * 失败的案例
     * @param logFile
     */
    public void processorSingleLog(File logFile){
        HashMap<Integer,String> logContent = readSingleLog(logFile);
//        Iterator iter = logContent.entrySet().iterator();
        int mapSize = logContent.size();

        long startTime=System.currentTimeMillis();  //获取开始时间

        String bscName = "";

        int i = 1;
        while (i<mapSize){
            String s = logContent.get(i);
            if (s.contains("Connected")){
                bscName = s.substring(17,23);
            }
            i++;
            if (s.contains("RLDEP")&&!s.contains("EXT")){
            }
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
     * 读取单个 cdd-log 文件，通过“<”分列文件，并返回一个数组
     * @param logFile cdd-log 文件路径
     * @param a 随便
     * @return 返回一个数组
     */
    public String[] readSingleLog(File logFile,int a) {
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
        String s = new String(fileContent);
        String[] ss = s.split("<");
        return ss;
    }

    public void processorSingleLog(File logFile,int a){
        String[] logContent = readSingleLog(logFile,1);
//        HashMap<Integer,String> hsm = new HashMap<Integer, String>();
        int lineNumber = 0;
        int endLineNumber = 0;

        String bscName = "";
        String RLDEPsector = "";
        String lac = "";
        String ci = "";
        String bsic = "";
        String bcch = "";
        String band = "";
        String RLCFPsector = "";


//        System.out.println("logContent"+logContent.length);
        for (int i=0;i<logContent.length;i++){
            String[] ss = logContent[i].split("\\r|\\n");


//            System.out.println("ss"+ss.length);

            for (int j=0;j<ss.length;j++){

                String lineContent = ss[j];

                lineNumber++;
//                System.out.println("正在处理第："+lineNumber+"行");
//                System.out.println(lineContent);

                if (lineContent.contains("Connected")){
                    //开始位置，并读取 bscName;
                    bscName = lineContent.substring(16,22);
//                    System.out.println(j+""+bscName);
                }

                if (lineContent.contains("RLDEP")&&!lineContent.contains("EXT")){
                    // 读取小区基础信息：sector、lac、ci、bsic、bcch、band
//                    System.out.println(lineContent);
                    int k=j;
//                    System.out.println("k"+k+" < end "+endLineNumber);

                    while (k<endLineNumber){
                        if (ss[k]!=null && ss[k].contains("CGI")){
                            RLDEPsector = ss[k+1].substring(0,7);
                            lac = ss[k+1].substring(16,20);
                            ci = ss[k+1].substring(21,26).trim();
                            bsic = ss[k+1].substring(30,32);
                            bcch = ss[k+1].substring(36,43).trim();
                            band = ss[k+4].substring(52,ss[k+4].length());
//                            System.out.println(sector+"-"+lac+"-"+ci+"-"+bsic+"-"+bcch+"-"+band);
                        }
                        k++;
                    }
                }

                if (lineContent.contains("RLCFP")){
                    int k=j;
                    while (k<endLineNumber){
                        if (ss[k]!=null && ss[k].contains("CHGR") && !ss[k].contains("FREQUENCY")){
                            RLCFPsector = ss[k-2].substring(0,7);
                            ??

                            System.out.println(RLCFPsector);
                        }
                        k++;
                    }
                }

                if (lineContent.contains("Disconnected")){
                    System.out.println("处理最后一行： "+lineNumber);
                }
            }

            //结束位置；
            endLineNumber = ss.length;
//            System.out.println(endLineNumber);
        }

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
