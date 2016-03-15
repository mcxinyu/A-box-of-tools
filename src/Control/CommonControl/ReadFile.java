package Control.CommonControl;

import java.io.*;

/**
 * 用于读取文件（读取文本、读取文件夹等）
 * Created by 跃峰 on 2016/2/3.
 */
public class ReadFile {

    /**
     * 读取单个 cdd-log 文件，并返回一个数组
     *
     * @param logFile cdd-log 文件路径
     * @return 返回一个数组 string [ ]
     */
    public static String readSingleText(File logFile) {
        FileInputStream fis = null;
        Long filelength = logFile.length();    //获取文件长度
        String fileName = logFile.getName();   //文件名
        byte[] fileContent = new byte[filelength.intValue()];
        try {
            fis = new FileInputStream(logFile);
            fis.read(fileContent);
        }catch (FileNotFoundException e) {
            System.out.println("找不到指定的文件。");
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            }catch (NullPointerException e){
                System.out.println("无法读取文件");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(fileContent);
    }

    /**
     * 读取多个文件
     * @param files 文件夹、者多个文件、单个文件 的数组
     * @return File[] 文件列表
     */
    public File[] readMultiText(File[] files) {
        File[] filesList = null;
        if (files.length != 0) {
            if (files[0].isDirectory()) {
                //System.out.println("文件夹数量： " + files.length);
                if (files[0].listFiles().length != 0) {
                    filesList = files[0].listFiles();
                    System.out.println("处理了： "+files[0].getPath());
                }
            } else {
                //System.out.println("文件数量： " + files.length);
                filesList = files;
                System.out.println("处理了该文件夹中的文件： "+filesList[0].getPath());
            }
        }else {
            System.out.println("readMultiText 收到文件为空");
        }
        return filesList;
    }

//    public File[] readMultiText(String folderPath) {
//        File[] files = new File(folderPath).listFiles();
//
////        String content ="";
////        if (files.length>0){
////            for(File file:files){
////                System.out.println(file.getName());
////                content += ReadFile.readSingleText(file);
////            }
//////            for (int i=0;i<files.length;i++){
//////                System.out.println(files[i].getName());
//////                connect += textToString(files[i]);
//////            }
////        }else {
////            System.out.println("未找到文件");
////        }
//        return files;
//    }

}
