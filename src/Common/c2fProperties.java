package Common;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by 跃峰 on 2016/2/28.
 */
public class c2fProperties {
    Properties pro = new Properties();
    //配置文件都存在 config/ 文件夹下
    public String readProperties(String fileName){
        String coordinatePath = "";
        try(FileInputStream in = new FileInputStream("config/"+fileName)){
            pro.load(in);
            Iterator<String> it=pro.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                if (key.equals("coordinatePath")){
                    coordinatePath = pro.getProperty(key);
//                    System.out.println(coordinatePath);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return coordinatePath;
    }

    public void writeProperties(String fileName,String coorPath){
        File file = new File("config/" + fileName);
        try(FileOutputStream out = new FileOutputStream(file, false)){   //true表示追加打开,false表示覆盖原文件
            pro.setProperty("coordinatePath", coorPath);
            pro.store(out, "This is a properties for Cdd2Forte.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        c2fProperties mp = new c2fProperties();
//        mp.readProperties("c2f.properties");
//        mp.writeProperties("c2f.properties","D:\\SZ\\变频工作\\数据采集\\cellinfo\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\coor.txt");
//        mp.writeProperties("c2f.properties",null);
//    }
}
