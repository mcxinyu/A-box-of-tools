package Control.RenameFilesControl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by 跃峰 on 2016/3/9.
 */
public class Rename {
    public static void main(String[] args) {
        Rename rn = new Rename();
        File[] fileList = new File[1];
        fileList[0] = new File("D:\\123.txt");
        rn.replaceText(fileList,"1","No.");
    }

    public void replaceText(File fileList[],String findString,String newString){
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].getName().contains(findString)){
                File newFile = new File(fileList[i].getParent()+fileList[i].getName().replace(findString,newString));
                rename(fileList[i],newFile);
            }else {
                System.out.println(fileList[i]+" 的文件名不包含："+findString+"。");
            }
        }
    }

    public void addText(File[] fileList,String addString,int place){
        if (place == 0){    //前
            for (int i = 0; i < fileList.length; i++) {
                File newFile = new File(fileList[i].getParent()+addString+fileList[i].getName());
                rename(fileList[i],newFile);
            }
        }else if (place == 1){  //后
            for (int i = 0; i < fileList.length; i++) {
                String newName = fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf("."))+addString+fileList[i].getName().substring(fileList[i].getName().lastIndexOf("."));
                File newFile = new File(fileList[i].getParent()+newName);
                rename(fileList[i],newFile);
            }
        }
    }

    public void format(File[] fileList,int formatFun,int place,String content,int index){    //参数太多，建个通用的类来存放指定参数的值
        if (formatFun == 0){    //索引0
            if (place == 0){    //前
                for (int i = 0; i < fileList.length; i++) {
                    File newFile = new File(fileList[i].getParent() + index + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    rename(fileList[i],newFile);
                    index++;
                }
            }else if (place == 1){  //后
                for (int i = 0; i < fileList.length; i++) {
                    File newFile = new File(fileList[i].getParent() + content + index + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    index++;
                    rename(fileList[i],newFile);
                }
            }
        }else if (formatFun == 1){  //计数1,计算到万位00001
            if (place == 0){    //前
                for (int i = 0; i < fileList.length; i++) {
                    String s = s2index("",index);
                    File newFile  = new File(fileList[i].getParent() + s + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    index++;
                    rename(fileList[i],newFile);
                }
            }else if (place == 1){  //后
                for (int i = 0; i < fileList.length; i++) {
                    String s = s2index("",index);
                    File newFile  = new File(fileList[i].getParent() + content + s + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    rename(fileList[i],newFile);
                    index++;
                }
            }
        }else if (formatFun == 2){  //日期2
            DateFormat nowdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (place == 0){    //前
                //int count = 0;
                for (int i = 0; i < fileList.length; i++) {
                    File newFile  = new File(fileList[i].getParent() + nowdDate + " " + i + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    rename(fileList[i],newFile);
                    //count++;
                }
            }else if (place == 1){  //后
                //int count = 0;
                for (int i = 0; i < fileList.length; i++) {
                    File newFile  = new File(fileList[i].getParent() + content + nowdDate + " " + i + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    rename(fileList[i],newFile);
                    //count++;
                }
            }
        }
    }

    public void rename(File oldFile,File newFile){
        if (oldFile.exists()){
            if (!newFile.exists()) {
                oldFile.renameTo(newFile);
            }else {
                System.out.println("无法重命名:"+oldFile+" ,因为新文件："+newFile+" 已经存在！");
            }
        }else {
            System.out.println(oldFile+" 已经不存在！");
        }
    }

    public String s2index(String s,int index){
        if (index >= 0 && index < 10) {
            s = "0000"+index;
        }else if (index >= 10 && index < 100){
            s = "000"+index;
        }else if (index >= 100 && index < 1000){
            s = "00"+index;
        }else if (index >= 1000 && index < 10000){
            s = "0"+index;
        }else if (index == 10000){
            s = s+index;
        }
        return s;
    }
}
