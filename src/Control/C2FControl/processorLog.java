package Control.C2FControl;

import java.io.*;


/**
 * 用于处理 cdd 文本文件，得到 cdd 参数
 * Created by 跃峰 on 2016/1/27.
 */
public class ProcessorLog {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        File f = new File("D:\\SZ\\变频工作\\数据采集\\CDD\\20160122\\SZ01A.Log");
//        File f = new File("/Users/huangyuefeng/Downloads/CDD/SZ01A.log");
        ProcessorLog rc = new ProcessorLog();
//        String[] ss = rc.readSingleLog(f,1);
        rc.processorSingleLog(f);
    }

    /**
     * Instantiates a new Read cdd.
     */
    public ProcessorLog(){
//        cdd = new File("/Users/huangyuefeng/Downloads/CDD/SZ01A.Log");
//        cddContent = readSingleLog(cdd);
    }


    /**
     * Processor single log.
     *
     * @param logFile the log file
     */
    public void processorSingleLog(File logFile){
        //logContent 数组存放读取到的文件，文件按照"<"分割的；一个 logContent 就是一个 OSS 指令块;
        String[] logContent = ReadFile.readSingleText(logFile).split("<");

        //用来记录有所行数，，因为各个 P 指令块是分开在 logContent 数组里面的；
        int lineNumber = 0;

        String[] sectors = new String[7];
//        String bscName = "";
//        String RLDEPsector = "";
//        String lac = "";
//        String ci = "";
//        String bsic = "";
//        String bcch = "";
//        String band = "";
        String RLCFPsector = "";

        String[][] ch_group = new String[4][14];    //保存4个信道组，每个信道组可能有14个元素（ch_group、hsn、dchno1-12）

//        System.out.println("logContent"+logContent.length);

        // 遍历所有 OSS 指令块，一个一个处理；
        for (int i=0;i<logContent.length;i++){
            //将各个 OSS 指令块的内容按行分割开来处理；
            String[] ss = logContent[i].split("\\r|\\n");

            //打印各个 P 指令的内容的行数，也既是各个 P 指令的结尾行数；
//            System.out.println("ss"+ss.length);

            for (int j=0;j<ss.length;j++){  //遍历 this OSS 指令块分割后的行，j 为行号；
                //记行数；
                lineNumber++;

                String lineContent = ss[j];

                //lineNumber定位处理到总文件的哪一行了，而 j 是 ss 元素的行数；
//                System.out.println("正在处理第："+lineNumber+"行");
//                System.out.println(lineContent);

                // 判断指令块是否为 Connected 块；
                if (lineContent.contains("Connected")){
                    //开始位置，并读取 bscName;
                    sectors[0] = lineContent.substring(16,22);
//                    System.out.println("开始处理 "+bscName+"\r\n"+ss[j]);
                }

                // 判断指令块是否为 RLDEP 块；
                if (lineContent.contains("RLDEP")&&!lineContent.contains("EXT")){
                    // 读取小区基础信息：sector、lac、ci、bsic、bcch、band
//                    System.out.println(lineContent);

                    while (j<ss.length){
                        if (ss[j]!=null && ss[j].contains("CGI")){
                            sectors[1] = ss[j+1].substring(0,7);
                            sectors[2] = ss[j+1].substring(16,20);
                            sectors[3] = ss[j+1].substring(21,26).trim();
                            sectors[4] = ss[j+1].substring(30,32);
                            sectors[5] = ss[j+1].substring(36,43).trim();
                            sectors[6] = ss[j+4].substring(52,ss[j+4].length());
//                            System.out.println(RLDEPsector+"-"+lac+"-"+ci+"-"+bsic+"-"+bcch+"-"+band);
                        }
                        j++;
                    }
                }

                // 判断指令块是否为 RLCFP 块；
                if (lineContent.contains("RLCFP")){
                    // 读取信道信息
                    // RLCFP_CELL 按“CELL”分列后存入数组 RLCFP_CELL[] 中，每个 RLCFP_CELL 就是一个小区的数据；
                    String[] RLCFP_CELL = logContent[i].split("CELL");
//                    System.out.println(RLCFP_CELL[RLCFP_CELL.length-1]);

                    // 遍历各个小区的数据，一个 k 就是一个小区块；
                    for (int k=3;k<RLCFP_CELL.length;k++) {
                        for (int x=0;x<4;x++){
                            //给 ch_group 二维数组赋初值，因为取不到参数的时候，需要标记为 N/A；
                            for (int y=0;y<14;y++){
                                ch_group[x][y]="N/A";
                            }
                        }

                        // 再将各个小区的 RLCFP_CELL 按行分割后存入数组 RLCFP_CELL_LINE[]；
                        String[] RLCFP_CELL_LINE = RLCFP_CELL[k].split("\\r|\\n");
                        // 各个小区的信道组号统计
                        int CHGR_COUNT = 0;
                        // 各个小区的信道组号所在行数
                        int[] CHGR_LINE = new int[4];

                        // 循环各个 RLCFP_CELL_LINE[]；
                        for (int l=0;l<RLCFP_CELL_LINE.length;l++){

                            // 取得  RLCFP_CELL_LINE 表头所在行，其实如果没有错误的话，一般都是第三行；
                            if (RLCFP_CELL_LINE[l]!=null && RLCFP_CELL_LINE[l].contains("SDCCHAC")){
                                RLCFPsector = RLCFP_CELL_LINE[l-2].substring(0,7);
//                                System.out.println(RLCFPsector);

                                //开始从 RLCFP_CELL_LINE 表头所在行 以后 的行，循环取得其他参数；
                                for (int m=l+1;m<RLCFP_CELL_LINE.length;m++){
//                                    if (RLCFP_CELL_LINE[m].length()==1){
//                                        System.out.println("空行： "+RLCFP_CELL_LINE[m]);
//                                    }
//                                    System.out.println("第一行："+RLCFP_CELL_LINE[0]);
//                                    System.out.println("第三行："+RLCFP_CELL_LINE[3]);
//                                    System.out.println("最后行："+RLCFP_CELL_LINE[RLCFP_CELL_LINE.length-1]);

                                    // 取得“信道组号”所在行，只能取三位出来对比，因为整个 RLCFP_CELL 指令最后有个三位的“END”标记；
                                    if (RLCFP_CELL_LINE[m]!=null && RLCFP_CELL_LINE[m].substring(0,3).trim().length()==1) {
//                                        System.out.println("CHGR_COUNT: "+CHGR_COUNT);
//                                        System.out.println("CHGR: "+RLCFP_CELL_LINE[m]);
                                        CHGR_COUNT++;
                                        if (CHGR_COUNT==1){
                                            //找到第一个信道组所在行
                                            CHGR_LINE[0] = m;
                                        }
                                        if (CHGR_COUNT==2) {
                                            //找到第二个信道组所在行
                                            CHGR_LINE[1] = m;
                                        }
                                        if (CHGR_COUNT==3) {
                                            //找到第三个信道组所在行
                                            CHGR_LINE[2] = m;
                                        }
                                        if (CHGR_COUNT==4) {
                                            //找到第四个信道组所在行
                                            CHGR_LINE[3] = m;
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
                            }
                        }

                        // 循环各个 RLCFP_CELL_LINE[]；
                        for (int l=0;l<RLCFP_CELL_LINE.length;l++) {
                            // 取得  RLCFP_CELL_LINE 表头所在行，其实如果没有错误的话，一般都是第三行；
                            if (RLCFP_CELL_LINE[l]!=null && RLCFP_CELL_LINE[l].contains("SDCCHAC")) {
                                //开始从 RLCFP_CELL_LINE 表头所在行 以后 的行，循环取得其他参数；
                                for (int m = l + 1; m < RLCFP_CELL_LINE.length; m++) {
                                    if (RLCFP_CELL_LINE[m] != null && RLCFP_CELL_LINE[m].substring(0, 3).trim() != "END") {
                                        if (CHGR_COUNT == 1) {
                                            //取出第一个信道组数据
                                            ch_group[0][0] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(0, 3).trim();    //CHGR
                                            ch_group[0][1] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(49, 53).trim();    //hsn
//
                                            for (int n = 0; n < RLCFP_CELL_LINE.length - CHGR_LINE[0]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[0] + n].length()>59) {
                                                    ch_group[0][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[0] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

//                                            System.out.println("只有一个信道组");
                                        } else if (CHGR_COUNT == 2) {
                                            //取出第一个信道组数据
                                            ch_group[0][0] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(0, 3).trim();    //CHGR
                                            ch_group[0][1] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < CHGR_LINE[1] - CHGR_LINE[0]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[0] + n].length()>59) {
                                                    ch_group[0][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[0] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

                                            //取出第二个信道组数据
                                            ch_group[1][0] = RLCFP_CELL_LINE[CHGR_LINE[1]].substring(0, 3).trim();    //CHGR
                                            ch_group[1][1] = RLCFP_CELL_LINE[CHGR_LINE[1]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < RLCFP_CELL_LINE.length - CHGR_LINE[1]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[1] + n].length()>59) {
                                                    ch_group[1][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[1] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

//                                            System.out.println("有二个信道组");
                                        } else if (CHGR_COUNT == 3) {
                                            //取出第一个信道组数据
                                            ch_group[0][0] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(0, 3).trim();    //CHGR
                                            ch_group[0][1] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < CHGR_LINE[1] - CHGR_LINE[0]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[0] + n].length()>59) {
                                                    ch_group[0][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[0] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

                                            //取出第二个信道组数据
                                            ch_group[1][0] = RLCFP_CELL_LINE[CHGR_LINE[1]].substring(0, 3).trim();    //CHGR
                                            ch_group[1][1] = RLCFP_CELL_LINE[CHGR_LINE[1]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < CHGR_LINE[2] - CHGR_LINE[1]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[1] + n].length()>59) {
                                                    ch_group[1][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[1] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

                                            //取出第三个信道组数据
                                            ch_group[2][0] = RLCFP_CELL_LINE[CHGR_LINE[2]].substring(0, 3).trim();    //CHGR
                                            ch_group[2][1] = RLCFP_CELL_LINE[CHGR_LINE[2]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < RLCFP_CELL_LINE.length - CHGR_LINE[2]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[2] + n].length()>59) {
                                                    ch_group[2][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[2] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

//                                            System.out.println("有三个信道组");
                                        } else if (CHGR_COUNT == 4) {
                                            //取出第一个信道组数据
                                            ch_group[0][0] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(0, 3).trim();    //CHGR
                                            ch_group[0][1] = RLCFP_CELL_LINE[CHGR_LINE[0]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < CHGR_LINE[1] - CHGR_LINE[0]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[0] + n].length()>59) {
                                                    ch_group[0][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[0] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

                                            //取出第二个信道组数据
                                            ch_group[1][0] = RLCFP_CELL_LINE[CHGR_LINE[1]].substring(0, 3).trim();    //CHGR
                                            ch_group[1][1] = RLCFP_CELL_LINE[CHGR_LINE[1]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < CHGR_LINE[2] - CHGR_LINE[1]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[1] + n].length()>59) {
                                                    ch_group[1][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[1] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

                                            //取出第三个信道组数据
                                            ch_group[2][0] = RLCFP_CELL_LINE[CHGR_LINE[2]].substring(0, 3).trim();    //CHGR
                                            ch_group[2][1] = RLCFP_CELL_LINE[CHGR_LINE[2]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < CHGR_LINE[3] - CHGR_LINE[2]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[2] + n].length()>59) {
                                                    ch_group[2][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[2] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }

                                            //取出第四个信道组数据
                                            ch_group[3][0] = RLCFP_CELL_LINE[CHGR_LINE[3]].substring(0, 3).trim();    //CHGR
                                            ch_group[3][1] = RLCFP_CELL_LINE[CHGR_LINE[3]].substring(49, 53).trim();    //hsn

                                            for (int n = 0; n < RLCFP_CELL_LINE.length - CHGR_LINE[3]; n++) {
                                                if (RLCFP_CELL_LINE[CHGR_LINE[3] + n].length()>59) {
                                                    ch_group[3][2 + n] = RLCFP_CELL_LINE[CHGR_LINE[3] + n].substring(60).trim();    //dchno1-dchno12
                                                }
                                            }
//                                            System.out.println("有四个信道组");
                                        }
                                    }

                                }

                            }

                        }
                        for (int x=0;x<4;x++){
                            for (int y=0;y<14;y++){
                                System.out.print(ch_group[x][y]+" ");;
                            }
                            System.out.println();
                        }

                        System.out.println("处理完： " + RLCFPsector);
                    }
                }

                if (lineContent.contains("Disconnected")){
//                    System.out.println("处理最后一行： "+lineNumber+"\r\n"+ss[j]);
                }
            }
        }

    }

}
