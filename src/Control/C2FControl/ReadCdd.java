package Control.C2FControl;

import java.io.*;

/**
 * 用于读取cdd文本文件
 * Created by 跃峰 on 2016/1/27.
 */
public class ReadCdd {
    File cdd;
    String cddContent;

    public static void main(String[] args) {
        ReadCdd rc = new ReadCdd();
//        rc.textFolderToString("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122");
//        System.out.println(rc.textFolderToString("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122"));
    }

    public ReadCdd(){
        cdd = new File("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122\\SZ01A.Log");
        cddContent = textToString(cdd);
        System.out.println(cddContent);
    }

    public void readParameter(String logContent){
        while (logContent != ""){

        }
    }


    /**
     * 用于读取单个文本文件，返回文本内容；
     * @param file 单个 cdd-log 文件
     * @return cdd-log 的内容
     */
    public String textToString(File file) {
        FileInputStream in = null;
        Long filelength = file.length();    //获取文件长度
        String fileName = file.getName();   //获取文件名
        String fileType = fileName.substring(fileName.lastIndexOf("."));    //获取文件后缀

        byte[] fileContent = new byte[filelength.intValue()];
        try {
            in = new FileInputStream(file);
            in.read(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(fileContent);//返回 cdd-log 文件内容,默认编码
    }

    /**
     * 把文件夹的文本都读取出来，容易溢出，还是不用了
     * @param folderPath
     * @return
     */
    public String textFolderToString(String folderPath) {
        File[] files = new File(folderPath).listFiles();
        String content = "";
        if (files.length>0){
            for(File file:files){
                System.out.println(file.getName());
                content += textToString(file);
            }
//            for (int i=0;i<files.length;i++){
//                System.out.println(files[i].getName());
//                textToString(files[i]);
//                connect = "完成";
//            }
        }else {
            System.out.println("文件夹是空的");
        }
        return content;
    }

    /**
     * 按行读取，可能会慢一点
     * @param fileName
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一行");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            //一次读一行，读入null时文件结束
            while ((tempString = reader.readLine()) != null) {
                //把当前行号显示出来
                System.out.println("line " + line + ": " + tempString);
                line++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {
                }
            }
        }
    }

    public void ReadString4Line(String s) {
        //用回车键来分隔几个元素
        String[] ss = s.split("\n");
        for (int i=0; i<ss.length; i++) {
            //do something
        }
    }
}
