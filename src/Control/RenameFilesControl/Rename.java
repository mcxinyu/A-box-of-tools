package Control.RenameFilesControl;

import javax.swing.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 跃峰 on 2016/3/9.
 */
public class Rename {
    //public static void main(String[] args) {
    //    Rename rn = new Rename();
    //    File[] fileList = new File[1];
    //    fileList[0] = new File("D:\\123.txt");
    //    rn.format(fileList,2,0,"QQ",0);
    //}

    public void replaceText(File fileList[], String findString, String newString){
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].getName().contains(findString)){
                File newFile = new File(fileList[i].getParent()+File.separator+fileList[i].getName().replace(findString,newString));
                rename(fileList[i],newFile);
            }else {
                System.out.println(fileList[i]+" 的文件名不包含："+findString+"。");
                //JOptionPane.showMessageDialog(null,fileList[i]+" 的文件名不包含："+findString+"。");
            }
        }
        JOptionPane.showMessageDialog(null,"完成。");
    }

    public void addText(File[] fileList,String addString,int place){
        if (place == 1){    //前
            for (int i = 0; i < fileList.length; i++) {
                File newFile = new File(fileList[i].getParent()+File.separator+addString+fileList[i].getName());
                rename(fileList[i],newFile);
            }
            JOptionPane.showMessageDialog(null,"完成。");
        }else if (place == 0){  //后
            for (int i = 0; i < fileList.length; i++) {
                String newName = fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf("."))+addString+fileList[i].getName().substring(fileList[i].getName().lastIndexOf("."));
                File newFile = new File(fileList[i].getParent()+File.separator+newName);
                rename(fileList[i],newFile);
            }
            JOptionPane.showMessageDialog(null,"完成。");
        }
    }

    public void format(File[] fileList,int formatFun,int place,String content,long index){
        if (formatFun == 0){    //索引0
            if (place == 1){    //前
                for (int i = 0; i < fileList.length; i++) {
                    File newFile = new File(fileList[i].getParent()+File.separator + index + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    rename(fileList[i],newFile);
                    index++;
                }
                JOptionPane.showMessageDialog(null,"完成。");
            }else if (place == 0){  //后
                for (int i = 0; i < fileList.length; i++) {
                    File newFile = new File(fileList[i].getParent()+File.separator + content + index + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    index++;
                    rename(fileList[i],newFile);
                }
                JOptionPane.showMessageDialog(null,"完成。");
            }
        }else if (formatFun == 1){  //计数1,计算到万位00001
            if (place == 1){    //前
                for (int i = 0; i < fileList.length; i++) {
                    String s = s2index("",index);
                    File newFile  = new File(fileList[i].getParent()+File.separator + s + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    index++;
                    rename(fileList[i],newFile);
                }
                JOptionPane.showMessageDialog(null,"完成。");
            }else if (place == 0){  //后
                for (int i = 0; i < fileList.length; i++) {
                    String s = s2index("",index);
                    File newFile  = new File(fileList[i].getParent()+File.separator + content + s + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    rename(fileList[i],newFile);
                    index++;
                }
                JOptionPane.showMessageDialog(null,"完成。");
            }
        }else if (formatFun == 2){  //日期2
            Date nowdDate = new Date();
            DateFormat nowdDateDF = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            if (place == 1){    //前
                //int count = 0;
                for (int i = 0; i < fileList.length; i++) {
                    if (i==0){
                        File newFile  = new File(fileList[i].getParent()+File.separator + nowdDateDF.format(nowdDate) + " " + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                        rename(fileList[i],newFile);
                    }else {
                        File newFile  = new File(fileList[i].getParent()+File.separator + nowdDateDF.format(nowdDate) + " " + i + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                        rename(fileList[i],newFile);
                    }
                    //count++;
                }
                JOptionPane.showMessageDialog(null,"完成。");
            }else if (place == 0){  //后
                //int count = 0;
                for (int i = 0; i < fileList.length; i++) {
                    if (i==0){
                        File newFile  = new File(fileList[i].getParent()+File.separator + content + nowdDateDF.format(nowdDate) + " " + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                        System.out.print("");
                        rename(fileList[i],newFile);
                    }else {
                        File newFile  = new File(fileList[i].getParent()+File.separator + content + nowdDateDF.format(nowdDate) + " " + i + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                        rename(fileList[i],newFile);
                    }
                    //count++;
                }
                JOptionPane.showMessageDialog(null,"完成。");
            }
        }
    }

    public void rename(File oldFile,File newFile){
        if (oldFile.exists()){
            if (!newFile.exists()) {
                oldFile.renameTo(newFile);
            }else {
                System.out.println("无法重命名:"+oldFile+" ,因为新文件："+newFile+" 已经存在！");
                //JOptionPane.showMessageDialog(null,"无法重命名:"+oldFile+" ,因为新文件："+newFile+" 已经存在！");
            }
        }else {
            System.out.println(oldFile+" 已经不存在！");
            //JOptionPane.showMessageDialog(null,oldFile+" 已经不存在！");
        }
    }

    public String s2index(String s, long index){
        if (index >= 0 && index < 10) {
            s = "0000"+index;
        }else if (index >= 10 && index < 100){
            s = "000"+index;
        }else if (index >= 100 && index < 1000){
            s = "00"+index;
        }else if (index >= 1000 && index < 10000){
            s = "0"+index;
        }else if (index >= 10000){
            s = s+index;
        }
        return s;
    }
}
