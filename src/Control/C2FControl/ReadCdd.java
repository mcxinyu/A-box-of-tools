package Control.C2FControl;

import java.io.*;

/**
 * Created by 跃峰 on 2016/1/27.
 * 用于读取cdd文本文件
 */
public class ReadCdd {
    File cdd;
    String cddContent;

    public static void main(String[] args) {
        ReadCdd rc = new ReadCdd();
        System.out.println(rc.textFolderToString("/Users/huangyuefeng/Downloads/CDD/"));
    }

    public ReadCdd(){
//        cdd = new File("/Users/huangyuefeng/Downloads/SZ01A.Log");
//        cddContent = readToString(cdd);
    }

    /**
     * 用于读取单个文本文件，返回文本内容；
     * @param file
     * @return
     */
    public String textToString(File file) {
        FileInputStream in = null;
        Long filelength = file.length();     //获取文件长度
        String fileName = file.getName();   //文件名
        String fileType = fileName.substring(fileName.lastIndexOf("."));    //文件后缀

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
        return new String(fileContent);//返回文件内容,默认编码
    }

    public String textFolderToString(String folderPath) {
        File[] files = new File(folderPath).listFiles();
        String connect = null;
        if (files.length>0){
//            for(File file:files){
//                System.out.println(file.getName());
//                connect += textToString(file);
//            }
            for (int i=0;i<files.length;i++){
                System.out.println(files[i].getName());
                connect += textToString(files[i]);
            }
        }else {
            System.out.println("文件夹是空的");
        }
        return connect;
    }
}
