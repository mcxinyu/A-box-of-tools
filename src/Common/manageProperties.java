package Common;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by 跃峰 on 2016/2/28.
 */
public class manageProperties {
    //配置文件都存在 config/ 文件夹下
    Properties pro = new Properties();

    /**
     * 读取配置文件中对应 key 的值
     * @param fileName 配置文件的文件名
     * @param getKey 要获取的key
     * @return 返回 value ，无则返回 ""
     */
    public String readProperties(String fileName,String getKey){
        String value = "";
        try(FileInputStream in = new FileInputStream("config/"+fileName)){
            pro.load(in);
            Iterator<String> it = pro.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                if (key.equals(getKey)){
                    value = pro.getProperty(key);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 写出配置文件
     * @param fileName 配置文件的文件名
     * @param key 要写入的 key
     * @param value 要写入的 key 对应的 value
     */
    public void writeProperties(String fileName,String key,String value){
        File file = new File("config/" + fileName);
        try(FileOutputStream out = new FileOutputStream(file, false)){   //true表示追加打开,false表示覆盖原文件
            pro.setProperty(key, value);
            pro.store(out, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        manageProperties mp = new manageProperties();
//        mp.readProperties("c2f.properties");
//        mp.writeProperties("c2f.properties","D:\\SZ\\变频工作\\数据采集\\cellinfo\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\coor.txt");
//        mp.writeProperties("c2f.properties",null);
//    }
}
