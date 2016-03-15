package Control.RenameFilesControl;

import java.io.File;

/**
 * Created by 跃峰 on 2016/3/9.
 */
public class Rename {
    public static void main(String[] args) {
        //new Rename();
    }

    public Rename(String path,String oldname,String newname){
        if(!oldname.equals(newname)){   //新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(path+"/"+oldname);
            File newfile=new File(path+"/"+newname);
            if(!oldfile.exists()){
                return; //重命名文件不存在
            }
            if(newfile.exists())    //若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname+"已经存在！");
            else{
                oldfile.renameTo(newfile);
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    public void replaceText(File[] fileList,String findString,String newString){

    }

    public void addText(File[] fileList,String addString,String place){

    }

    public void format(File[] fileList){    //参数太多，建个通用的类来存放指定参数的值

    }
}
