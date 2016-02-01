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
        File f = new File("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122\\SZ01A.Log");
        ReadCdd rc = new ReadCdd();
//        String[] ss = rc.readSingleLog(f,1);
        rc.processorSingleLog(f,1);
    }

    /**
     * Instantiates a new Read cdd.
     */
    public ReadCdd(){
//        cdd = new File("/Users/huangyuefeng/Downloads/SZ01A.Log");
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

                                        // 把获取到的 CHGR_LINE 负值给数组；
                                        if (CHGR_COUNT==1){
                                            CHGR_LINE[0] = m;
                                        }if (CHGR_COUNT==2){
                                            CHGR_LINE[1] = m;
                                            ch_group[0][0] = RLCFP_LINE[CHGR_LINE[0]].substring(0,5).trim();    //CHGR
                                            ch_group[0][1] = RLCFP_LINE[CHGR_LINE[0]].substring(49,53).trim();    //hsn
                                            ch_group[0][2] = RLCFP_LINE[CHGR_LINE[0]].substring(60).trim();    //dchno1
                                            if (CHGR_LINE[1] != CHGR_LINE[0] && CHGR_LINE[1] < CHGR_LINE[0]) { //此时 CHGR_LINE[1] 一定不是 0
                                                ch_group[0][3] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno2
                                                ch_group[0][4] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno3
                                                ch_group[0][5] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno4
                                                ch_group[0][6] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno5
                                                ch_group[0][7] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno6
                                                ch_group[0][8] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno7
                                                ch_group[0][9] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno8
                                                ch_group[0][10] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno9
                                                ch_group[0][11] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno10
                                                ch_group[0][12] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno11
                                                ch_group[0][13] = RLCFP_LINE[CHGR_LINE[0] + 1].substring(60).trim();    //dchno12
                                            }
                                        }if (CHGR_COUNT==3){
                                            CHGR_LINE[2] = m;
                                        }if (CHGR_COUNT==4){
                                            CHGR_LINE[3] = m;
                                        }
                                        int i0=CHGR_LINE[0],i1=CHGR_LINE[1],i2=CHGR_LINE[2],i3=CHGR_LINE[3];
                                        switch (i0){
                                            case i1:break;
                                            default:
                                        }
                                    }

//                                    System.out.println("CHGR_COUNT: "+CHGR_COUNT);
//                                    System.out.println("CHGR_LINE: "+CHGR_LINE[CHGR_COUNT-1]);
                                }

                                for (int n=0;n<CHGR_COUNT;n++){
                                    //给数组 ch_group[CHGR_COUNT][] 赋值；
                                    for (int o=0;o<RLCFP_LINE.length-l;o++) {
                                        if (RLCFP_LINE[m].contains("END")) {
                                            System.out.println("处理完");
                                        }else if (o == 0){
                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m].substring(0,5).trim();    //CHGR
                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                        }else if (o == 1){
                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m].substring(49,53).trim();    //hsn
                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                        }else if (o == 2){
                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m].substring(60).trim();    //dchno1
                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                        }else if (o >= 3) {
                                            for (int p=n;p<RLCFP_LINE.length-n;p++) {
                                                switch (RLCFP_LINE[p].substring(0, 3).trim().length()) {
                                                    case 0:
                                                        System.out.println("break0");
                                                        break;
                                                    case 1:
                                                        System.out.println("break1");
                                                        break;
                                                    case 2:
                                                        System.out.println("break2");
                                                        break;
                                                    case 3:
                                                        System.out.println("break3");
                                                        break;
                                                    case 4:
                                                        System.out.println("break4");
                                                        break;
                                                    default:
                                                        if (n == 3) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 1].substring(60).trim();    //dchno2
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 4) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 2].substring(60).trim();    //dchno3
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 5) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 3].substring(60).trim();    //dchno4
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 6) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 4].substring(60).trim();    //dchno5
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 7) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 5].substring(60).trim();    //dchno6
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 8) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 6].substring(60).trim();    //dchno7
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 9) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 7].substring(60).trim();    //dchno8
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 10) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 8].substring(60).trim();    //dchno9
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 11) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 9].substring(60).trim();    //dchno10
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 12) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 10].substring(60).trim();    //dchno11
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        } else if (o == 13) {
                                                            ch_group[CHGR_COUNT][n] = RLCFP_LINE[m + 11].substring(60).trim();    //dchno12
                                                            System.out.println("this is "+ch_group[CHGR_COUNT][n]);
                                                        }
                                                }
                                            }
                                        }

//                                            System.out.println("this is"+ch_group[CHGR_COUNT][n]);
                                    }
                                }

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
