package Control.C2FControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 跃峰 on 2016/1/27.
 * 用于读取cdd文本文件
 */
public class ReadCdd {
    File cdd;
    String cddContent;

    public static void main(String[] args) {
        ReadCdd rc = new ReadCdd();
        System.out.println(rc.cddContent);
    }

    public ReadCdd(){
        cdd = new File("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122\\SZ01A.Log");
        cddContent = readToString(cdd);
    }

    /**
     * 用于读取文本文件，返回文本内容；
     * @param file
     * @return
     */
    public static String readToString(File file) {
        FileInputStream in = null;
        Long filelength = file.length();     //获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            in = new FileInputStream(file);
            in.read(filecontent);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(filecontent);//返回文件内容,默认编码
    }
}
