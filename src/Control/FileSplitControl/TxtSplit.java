package Control.FileSplitControl;

import Control.CommonControl.ReadFile;

import javax.swing.*;
import java.io.*;

/**
 * Created by 跃峰 on 2016/2/11.
 */
public class TxtSplit {
    public static void main(String[] args) {
        //File oldFile = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\TT\\coor0301.txt");
        //File newPath = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\TT\\");

        //File[] oldFiles = new File[3];
        //oldFiles[0] = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\TT\\coor0301.part-0.txt");
        //oldFiles[1] = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\TT\\coor0301.part-1.txt");
        //oldFiles[2] = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\TT\\coor0301.part-2.txt");
        //File newPath = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\TT\\coor-merge.txt");

        //TxtSplit ts = new TxtSplit();
        //ts.merge(oldFiles,newPath,true,false);
    }

    /**
     * 分割文本文件
     * @param oldFile 要分隔的文本文件
     * @param newPath 新文件路径
     * @param setNum 分割颗粒（行数）
     * @param saveTitle 新文件路径
     * @param deleteOldFile 是否删除源文件
     * @return 返回新文件列表
     */
    public File[] split(File oldFile,File newPath,int setNum,boolean saveTitle,boolean deleteOldFile){
        String[] txt = ReadFile.readSingleText(oldFile).split("\\r\\n");
        File[] files = new File[(int) Math.ceil(txt.length/setNum)+1];
        //System.out.println(txt.length);
        //System.out.println(txt.length/setNum);
        //System.out.println((int) Math.ceil(txt.length/setNum));

        for (int i = 0; i < files.length; i++) {
            files[i] = new File(newPath + "/" + oldFile.getName().substring(0,oldFile.getName().indexOf(".")) + ".part-" + i + oldFile.getName().substring(oldFile.getName().lastIndexOf(".")));
            try (FileWriter outFileFW = new FileWriter(files[i])) {
                if (saveTitle && i != 0){
                    outFileFW.write(txt[0] + "\r\n");
                }
                for (int j = i*setNum; j < txt.length; j++) {
                    if (j==setNum*(i+1)){
                        break;
                    }
                    outFileFW.write(txt[j] + "\r\n");
                }
            }catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"导出失败，另一个程序正在使用此文件！");
            }
        }
        if (deleteOldFile) {
            if(oldFile.exists()){
                oldFile.delete();
            }
        }
        return files;
    }

    /**
     * 合并文本文件
     * @param oldFiles 要合并的文件列表，务必保证文件名的顺序，01、02...
     * @param newFile 新文件
     * @param onlySaveFirstTitle 只保留第1个文件的表头，否则全部保留
     * @param deleteOldFile 是否删除源文件
     * @return 返回新文件
     */
    public File merge(File[] oldFiles,File newFile,boolean onlySaveFirstTitle,boolean deleteOldFile){
        try (FileWriter outFileFW = new FileWriter(newFile)) {
            for (int i = 0; i < oldFiles.length; i++) {
                String[] txt = ReadFile.readSingleText(oldFiles[i]).split("\\r\\n");
                if (onlySaveFirstTitle && i!=0){
                    for (int j = 1; j < txt.length; j++) {
                        outFileFW.write(txt[j] + "\r\n");
                    }
                }else {
                    for (int j = 0; j < txt.length; j++) {
                        outFileFW.write(txt[j] + "\r\n");
                    }
                }
                if (deleteOldFile) {
                    if(oldFiles[i].exists()){
                        oldFiles[i].delete();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"导出失败，另一个程序正在使用此文件！");
        }
        return newFile;
    }
}
