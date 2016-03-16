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
        fileList[0] = new File("D:\\1.23.txt");
        rn.addText(fileList,"kkkkkkk",1);
    }

    public void replaceText(File fileList[],String findString,String newString){
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].getName().contains(findString) || fileList[i].exists()){
                File newFile = new File(fileList[i].getParent()+fileList[i].getName().replace(findString,newString));
                if (!newFile.exists()) {
                    fileList[i].renameTo(newFile);
                }else {
                    System.out.println("无法重命名:"+fileList[i]+" ,因为新文件："+newFile+" 已经存在！");
                }
            }else {
                System.out.println(fileList[i]+" 已经不存在！");
            }
        }
    }

    public void addText(File[] fileList,String addString,int place){
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].exists()){
                File newFile = null;
                if (place == 0){    //前
                    newFile = new File(fileList[i].getParent()+addString+fileList[i].getName());
                }else if (place == 1){  //后
                    String newName = fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf("."))+addString+fileList[i].getName().substring(fileList[i].getName().lastIndexOf("."));
                    newFile = new File(fileList[i].getParent()+newName);
                }
                if (!newFile.exists()) {
                    fileList[i].renameTo(newFile);
                }else {
                    System.out.println("无法重命名:"+fileList[i]+" ,因为新文件："+newFile+" 已经存在！");
                }
            }else {
                System.out.println(fileList[i]+" 已经不存在！");
            }
        }
    }

    public void format(File[] fileList,int formatFun,int place,String content,int index){    //参数太多，建个通用的类来存放指定参数的值
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].exists()){
                File newFile = null;
                if (formatFun == 0){    //索引0
                    if (place == 0){    //前
                        newFile = new File(fileList[i].getParent() + index + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    }else if (place == 1){  //后
                        newFile = new File(fileList[i].getParent() + content + index + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    }
                }else if (formatFun == 1){  //计数1,计算到万位00001
                    String s = "";
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
                    }else {
                        System.out.println("重命名数量大于10000");
                    }
                    if (place == 0){    //前
                        newFile = new File(fileList[i].getParent() + s + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    }else if (place == 1){  //后
                        newFile = new File(fileList[i].getParent() + content + s + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    }
                }else if (formatFun == 2){  //日期2
                    DateFormat nowdDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss EE");
                    if (place == 0){    //前
                        newFile = new File(fileList[i].getParent() + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    }else if (place == 1){  //后
                        newFile = new File(fileList[i].getParent() + content + fileList[i].getName().substring(fileList[i].getName().lastIndexOf(".")));
                    }
                }
                if (!newFile.exists()) {
                    fileList[i].renameTo(newFile);
                }else {
                    System.out.println("无法重命名:"+fileList[i]+" ,因为新文件："+newFile+" 已经存在！");
                }
            }else {
                System.out.println(fileList[i]+" 已经不存在！");
            }
            index++;
        }
    }
}
