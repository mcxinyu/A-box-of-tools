package Control.C2FControl;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.*;

/**
 * Created by 跃峰 on 2016/1/27.
 * 用于读取cdd文本文件
 */
public class ReadCdd {
    File cdd;
    String cddContent;

    public static void main(String[] args) {
        File f = new File("/Users/huangyuefeng/Downloads/CDD/SZ01A.Log");
        ReadCdd rc = new ReadCdd();
        rc.processorSingleLog(f);
    }

    public ReadCdd(){
//        cdd = new File("/Users/huangyuefeng/Downloads/SZ01A.Log");
//        cddContent = readSingleLog(cdd);
    }

    /**
     * 读取并解析单个 cdd-log 文件
     * @param logFile cdd-log 文件路径
     * @return
     */
    private void processorSingleLog(File logFile){
        int len = 0;
        String line = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer str = new StringBuffer("");

        try {
            fis = new FileInputStream(logFile);
            isr= new InputStreamReader(fis);
            br = new BufferedReader(isr);

            while((line=br.readLine())!=null){
//                if(len != 0){   // 处理换行符的问题
//                    str.append("\r\n"+line);
//                }
//                else{
//                    str.append(line);
//                }


                if (line.substring(3)=="***"){
                    len++;
                    System.out.println(len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                isr.close();
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 用于读取单个文本文件，返回文本内容；
     * @param logFile cdd-log 路径地址
     * @return
     */
    public String readSingleLog(File logFile) {
        FileInputStream fis = null;
        Long filelength = logFile.length();    //获取文件长度
        String fileName = logFile.getName();   //文件名
//    String fileType = fileName.substring(fileName.lastIndexOf("."));    //文件后缀
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
        return new String(fileContent); //返回文件内容,默认编码
    }

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
