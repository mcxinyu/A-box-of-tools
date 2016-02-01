package Control.C2FControl;

import java.awt.*;
import java.io.*;
import java.util.HashMap;


/**
 * 用于读取、处理 cdd 文本文件，得到 cdd 参数
 * Created by 跃峰 on 2016/1/27.
 */
public class ReadCdd {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
//        File f = new File("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122\\SZ01A.Log");
        File f = new File("/Users/huangyuefeng/Downloads/CDD/SZ01A.log");
        ReadCdd rc = new ReadCdd();
//        String[] ss = rc.readSingleLog(f,1);
        rc.processorSingleLog(f,1);
    }

    /**
     * Instantiates a new Read cdd.
     */
    public ReadCdd(){
//        cdd = new File("/Users/huangyuefeng/Downloads/CDD/SZ01A.Log");
//        cddContent = readSingleLog(cdd);
    }

    /**
     * 读取单个 cdd-log 文件，返回一个 HashMap<Integer,String>
     *
     * @param logFile cdd-log 文件路径
     * @return 返回一个 HashMap<Integer,String>
     */
    public HashMap readSingleLog(File logFile){
        int len = 0;
        String line = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        HashMap<Integer,String> logContent = new HashMap<Integer, String>();

        try {
            fis = new FileInputStream(logFile);
            isr= new InputStreamReader(fis);
            br = new BufferedReader(isr);

            while((line=br.readLine())!=null){
                len++;
                logContent.put(len,line);
            }
//            System.out.println(len);
//            System.out.println(logContent.size());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null) br.close();
                if (isr != null) isr.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return logContent;
    }

    /**
     * 失败的案例
     *
     * @param logFile the log file
     */
    public void processorSingleLog(File logFile){
        HashMap<Integer,String> logContent = readSingleLog(logFile);
//        Iterator iter = logContent.entrySet().iterator();
        int mapSize = logContent.size();

        long startTime=System.currentTimeMillis();  //获取开始时间

        String bscName = "";

        int i = 1;
        while (i<mapSize){
            String s = logContent.get(i);
            if (s.contains("Connected")){
                bscName = s.substring(17,23);
            }
            i++;
            if (s.contains("RLDEP")&&!s.contains("EXT")){
            }
        }

//        for (int i=1;i<=mapSize;i++){
////            System.out.println(logContent.get(i));
//            String s = logContent.get(i);
//            if (s.contains("Connected")){
//                bscName = s.substring(17,23);
//            }else if (s.contains("RLDEP")&&!s.contains("EXT")){
//            }
//        }

//        while (iter.hasNext()){
//            HashMap.Entry entry = (HashMap.Entry) iter.next();
////            Object key = entry.getKey();
////            Object val = entry.getValue();
////            System.out.println(entry.getValue());
//        }

//        System.out.println(logContent.get(mapSize));

        long endTime=System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

    /**
     * 读取单个 cdd-log 文件，通过“<”分列文件，并返回一个数组
     *
     * @param logFile cdd-log 文件路径
     * @param a       随便
     * @return 返回一个数组 string [ ]
     */
    public String[] readSingleLog(File logFile,int a) {
        FileInputStream fis = null;
        Long filelength = logFile.length();    //获取文件长度
        String fileName = logFile.getName();   //文件名
        byte[] fileContent = new byte[filelength.intValue()];
        try {
            fis = new FileInputStream(logFile);
            fis.read(fileContent);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String s = new String(fileContent);
        String[] ss = s.split("<");
        return ss;
    }

    /**
     * Processor single log.
     *
     * @param logFile the log file
     * @param a       the a
     */
    public void processorSingleLog(File logFile,int a){
        //logContent 数组存放读取到的文件，文件是按照"<"分割的；
        String[] logContent = readSingleLog(logFile,1);

        //用来记录有所行数，，因为各个 P 指令块是分开在 logContent 数组里面的；
        int lineNumber = 0;

        String bscName = "";
        String RLDEPsector = "";
        String lac = "";
        String ci = "";
        String bsic = "";
        String bcch = "";
        String band = "";
        String RLCFPsector = "";
        String[][] ch_group = new String[4][14];    //保存4个信道组，每个信道组可能有14个元素（ch_group、hsn、dchno1-12）


//        System.out.println("logContent"+logContent.length);
        for (int i=0;i<logContent.length;i++){
            //将各个 P 指令的内容按行分割开来处理；
            String[] ss = logContent[i].split("\\r|\\n");

            //打印各个 P 指令的内容的行数，也既是各个 P 指令的结尾行数；
//            System.out.println("ss"+ss.length);

            for (int j=0;j<ss.length;j++){  //遍历所有 P 指令块分割后的行，j 为行号；
                //记行数；
                lineNumber++;

                String lineContent = ss[j];

                //lineNumber定位处理到总文件的哪一行了，而 j 是 ss 元素的行数；
//                System.out.println("正在处理第："+lineNumber+"行");
//                System.out.println(lineContent);

                if (lineContent.contains("Connected")){
                    //开始位置，并读取 bscName;
                    bscName = lineContent.substring(16,22);
//                    System.out.println("开始处理 "+bscName+"\r\n"+ss[j]);
                }

                if (lineContent.contains("RLDEP")&&!lineContent.contains("EXT")){
                    // 读取小区基础信息：sector、lac、ci、bsic、bcch、band
//                    System.out.println(lineContent);

                    while (j<ss.length){
                        if (ss[j]!=null && ss[j].contains("CGI")){
                            RLDEPsector = ss[j+1].substring(0,7);
                            lac = ss[j+1].substring(16,20);
                            ci = ss[j+1].substring(21,26).trim();
                            bsic = ss[j+1].substring(30,32);
                            bcch = ss[j+1].substring(36,43).trim();
                            band = ss[j+4].substring(52,ss[j+4].length());
//                            System.out.println(RLDEPsector+"-"+lac+"-"+ci+"-"+bsic+"-"+bcch+"-"+band);
                        }
                        j++;
                    }
                }

                if (lineContent.contains("RLCFP")){
                    for (int x=0;x<4;x++){
                        //给 ch_group 二维数组赋初值，因为取不到参数的时候，需要标记为 N/A；
                        for (int y=0;y<14;y++){
                            ch_group[x][y]="N/A";
                        }
                    }

                    // 读取信道信息
                    // RLCFP 按“CELL”分列后存入数组 RLCFP[] 中
                    String[] RLCFP = logContent[i].split("CELL");
//                    System.out.println(RLCFP[RLCFP.length-1]);

                    for (int k=3;k<RLCFP.length;k++) {
                        // 再将各个小区的 RLCFP 按行分割后存入数组 RLCFP_LINE[]；
                        String[] RLCFP_LINE = RLCFP[k].split("\\r|\\n");


                        // 循环各个 RLCFP_LINE[]；
                        for (int l=0;l<RLCFP_LINE.length;l++){
                            // 各个小区的信道组号统计
                            int CHGR_COUNT = 0;
                            // 各个小区的信道组号所在行数
                            int[] CHGR_LINE = new int[4];

                            // 取得  RLCFP_LINE 表头所在行，其实如果没有错误的话，一般都是第三行；
                            if (RLCFP_LINE[l]!=null && RLCFP_LINE[l].contains("SDCCHAC")){
                                RLCFPsector = RLCFP_LINE[l-2].substring(0,7);
//                                System.out.println(RLCFPsector);

                                //开始从 RLCFP_LINE 表头所在行 以后 的行，循环取得其他参数；
                                for (int m=l+1;m<RLCFP_LINE.length;m++){
//                                    if (RLCFP_LINE[m].length()==1){
//                                        System.out.println("空行： "+RLCFP_LINE[m]);
//                                    }
//                                    System.out.println("第一行："+RLCFP_LINE[0]);
//                                    System.out.println("第三行："+RLCFP_LINE[3]);
//                                    System.out.println("最后行："+RLCFP_LINE[RLCFP_LINE.length-1]);
                                    // 取得“信道组号”所在行，只能取三位出来对比，因为整个 RLCFP 指令最后有个三位的“END”标记；
                                    if (RLCFP_LINE[m]!=null && RLCFP_LINE[m].substring(0,3).trim().length()==1) {
//                                        System.out.println("CHGR_COUNT: "+CHGR_COUNT);
//                                        System.out.println("CHGR: "+RLCFP_LINE[m]);
                                        CHGR_COUNT++;

                                        // 把获取到的 CHGR_LINE 赋值给数组 CHGR_LINE[0]；
                                        if (CHGR_COUNT==1){
                                            //找到第一个信道组所在行
                                            CHGR_LINE[0] = m;
                                        }else if (CHGR_COUNT==0){
                                            System.out.println("没有信道组数据");
                                        }

                                        if (CHGR_COUNT==2){
                                            //找到第二个信道组所在行
                                            CHGR_LINE[1] = m;

                                            //取出第一个信道组数据
                                            ch_group[0][0] = RLCFP_LINE[CHGR_LINE[0]].substring(0,3).trim();    //CHGR
                                            ch_group[0][1] = RLCFP_LINE[CHGR_LINE[0]].substring(49,53).trim();    //hsn

                                            for (int n = 0; n< CHGR_LINE[1] - CHGR_LINE[0]; n++){
                                                ch_group[0][2+n] = RLCFP_LINE[CHGR_LINE[0] + n].substring(60).trim();    //dchno1-dchno12
                                            }

                                            for (int y=0;y<14;y++){
                                                System.out.println(ch_group[0][y]);
                                            }
                                            System.out.println("111111111111");
                                        }else {
                                            if (CHGR_COUNT==1) {
                                                //取出第一个信道组数据
                                                ch_group[0][0] = RLCFP_LINE[CHGR_LINE[0]].substring(0, 3).trim();    //CHGR
                                                ch_group[0][1] = RLCFP_LINE[CHGR_LINE[0]].substring(49, 53).trim();    //hsn

                                                for (int n = 0; n < RLCFP_LINE.length - CHGR_LINE[0]; n++) {
                                                    ch_group[0][2 + n] = RLCFP_LINE[CHGR_LINE[0] + n].substring(60).trim();    //dchno1-dchno12
                                                }

                                                for (int y = 0; y < 14; y++) {
                                                    System.out.println(ch_group[0][y]);
                                                }
                                                System.out.println("2222222222222");
                                            }
                                        }

                                        if (CHGR_COUNT==3){
                                            //找到第三个信道组所在行
                                            CHGR_LINE[2] = m;

                                            //取出第二个信道组数据
                                            ch_group[1][0] = RLCFP_LINE[CHGR_LINE[1]].substring(0,3).trim();    //CHGR
                                            ch_group[1][1] = RLCFP_LINE[CHGR_LINE[1]].substring(49,53).trim();    //hsn

                                            for (int n = 0 ;n<= CHGR_LINE[2] - CHGR_LINE[1];n++){
                                                ch_group[1][2+n] = RLCFP_LINE[CHGR_LINE[1] + n].substring(60).trim();    //dchno1-dchno12
                                            }
                                            for (int y=0;y<14;y++){
                                                System.out.println(ch_group[1][y]);
                                            }
                                        }else {
                                            if (CHGR_COUNT==2) {
                                                //取出第二个信道组数据
                                                ch_group[1][0] = RLCFP_LINE[CHGR_LINE[1]].substring(0, 3).trim();    //CHGR
                                                ch_group[1][1] = RLCFP_LINE[CHGR_LINE[1]].substring(49, 53).trim();    //hsn

                                                for (int n = 0; n <= RLCFP_LINE.length - CHGR_LINE[1]; n++) {
                                                    ch_group[1][2 + n] = RLCFP_LINE[CHGR_LINE[1] + n].substring(60).trim();    //dchno1-dchno12
                                                }

                                                for (int y = 0; y < 14; y++) {
                                                    System.out.println(ch_group[1][y]);
                                                }
                                            }
                                        }

                                        if (CHGR_COUNT==4) {
                                            //找到第四个信道组所在行
                                            CHGR_LINE[3] = m;

                                            //取出第三个信道组数据
                                            ch_group[2][0] = RLCFP_LINE[CHGR_LINE[2]].substring(0, 3).trim();    //CHGR
                                            ch_group[2][1] = RLCFP_LINE[CHGR_LINE[2]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < CHGR_LINE[3] - CHGR_LINE[2]; n++) {
                                                ch_group[2][2 + n] = RLCFP_LINE[CHGR_LINE[2] + n].substring(60).trim();    //dchno1-dchno12
                                            }

                                            for (int y=0;y<14;y++){
                                                System.out.println(ch_group[2][y]);
                                            }

                                            //取出第四个信道组数据,也就是最后一个
                                            ch_group[3][0] = RLCFP_LINE[CHGR_LINE[3]].substring(0, 3).trim();    //CHGR
                                            ch_group[3][1] = RLCFP_LINE[CHGR_LINE[3]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n <= RLCFP_LINE.length - CHGR_LINE[3]; n++) {
                                                ch_group[3][2 + n] = RLCFP_LINE[CHGR_LINE[3] + n].substring(60).trim();    //dchno1-dchno12
                                            }

                                            for (int y=0;y<14;y++){
                                                System.out.println(ch_group[3][y]);
                                            }
                                        }else {
                                            if (CHGR_COUNT==3) {
                                                //取出第三个信道组数据,也就是最后一个
                                                ch_group[2][0] = RLCFP_LINE[CHGR_LINE[2]].substring(0, 3).trim();    //CHGR
                                                ch_group[2][1] = RLCFP_LINE[CHGR_LINE[2]].substring(49, 53).trim();    //hsn

                                                for (int n = 0; n < RLCFP_LINE.length - CHGR_LINE[2]; n++) {
                                                    ch_group[2][2 + n] = RLCFP_LINE[CHGR_LINE[2] + n].substring(60).trim();    //dchno1-dchno12
                                                }

                                                for (int y = 0; y < 14; y++) {
                                                    System.out.println(ch_group[2][y]);
                                                }
                                            }
                                        }


                                    }
                                }
//                                System.out.println("CHGR_COUNT: "+CHGR_COUNT);
//                                System.out.println("CHGR_LINE: "+CHGR_LINE[CHGR_COUNT-1]);
//                                for (int x=0;x<4;x++){
//                                    for (int y=0;y<14;y++){
//                                        System.out.println(ch_group[x][y]);
//                                    }
//                                }
                                System.out.println("处理完： "+RLCFPsector);
                            }
                        }
                    }
                }

                if (lineContent.contains("Disconnected")){
//                    System.out.println("处理最后一行： "+lineNumber+"\r\n"+ss[j]);
                }
            }
        }

    }


    /**
     * 读取文件夹
     *
     * @param folderPath the folder path
     * @return string string
     */
    public String textFolderToString(String folderPath) {
        File[] files = new File(folderPath).listFiles();
        String connect ="";
        if (files.length>0){
            for(File file:files){
                System.out.println(file.getName());
                connect += readSingleLog(file);
            }
//            for (int i=0;i<files.length;i++){
//                System.out.println(files[i].getName());
//                connect += textToString(files[i]);
//            }
        }else {
            System.out.println("未找到文件");
        }
        return connect;
    }

}
