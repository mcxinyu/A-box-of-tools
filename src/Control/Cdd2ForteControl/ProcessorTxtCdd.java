package Control.Cdd2ForteControl;

import Control.CommonControl.ReadFile;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * 用于处理 cdd 文本文件，得到 cdd 参数
 * Created by 跃峰 on 2016/1/27.
 */
public class ProcessorTxtCdd {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
//    public static void main(String[] args) {
//        //long startTime=System.currentTimeMillis();
//
////        //读取坐标
//////        File f1 = new File("/Users/huangyuefeng/Downloads/coordination.txt");
////        File f1 = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\coor0301.txt");
////        ProcessorCoordinate pc = new ProcessorCoordinate();
////        String[][] coorS = pc.readCoordinates(f1);
//
////        //读取CDD
//////        File f2 = new File("/Users/huangyuefeng/Downloads/cdd20160122/SZ01A.log");
////        File f2 = new File("D:\\SZ\\变频工作\\数据采集\\CDD\\20160302\\SZ59A.Log");
////        ProcessorTxtCdd pl = new ProcessorTxtCdd();
////        HashMap hm = pl.readCoordinates(f2);
////        pl.createForteList(s,hm);
//
//        ////读取CDD文件夹
//        //String filePath = "D:\\SZ\\变频工作\\数据采集\\GZ\\0301CDD\\";
//        //String filePath = "/Users/huangyuefeng/Downloads/cdd20160122/";
//        //File f = new File(filePath);
//        //File[] ff = f2.listFiles();
//        //File[] ff = new File[1];
//        //ff[0]=f2;
//        //ArrayList[][] forteArray = pl.processorMultiLog(ff,coorS);
//
//        //String exportPath = "D:\\Common.logRecorder\\";
//        //pl.createForteFile(forteArray,exportPath);
//
//        //long endTime=System.currentTimeMillis();
//        //System.out.println("程序运行时间： "+(endTime-startTime)/1000+"s");
//
//        //读取CDD
//        File f2 = new File("D:\\SZ\\日常工作\\20160411\\20160411\\SZ35A.Log");
//        ProcessorTxtCdd pl = new ProcessorTxtCdd();
//        pl.processorSingleLog(f2);
//    }

    /**
     * ProcessorExcelChannel single CDD log.
     *
     * @param logFile the log file
     */
    String notice = "";
    int noRepeatPrompt = 0; // 统计信道组数量大于6时的提示次数
    public HashMap processorSingleLog(File logFile) {
        // HashMap<bsccc+sector,string[][]>
//        HashMap<String,String[][]> contentHM = new HashMap<String, String[][]>();
        HashMap<String,String[][]> contentHM = null;

        String[] checkCDD = ReadFile.readSingleText(logFile).split("\\r|\\n");

        // 检查CDD有效性
        //if (checkCDD.length>1 && checkCDD[1].contains("Connected") && (checkCDD[checkCDD.length-1].contains("Disconnected") || checkCDD[checkCDD.length-1].contains("Run stopped"))){
        contentHM = new HashMap<String, String[][]>();
        //logContent 数组存放读取到的文件，文件按照"<"分割的；一个 logContent 就是一个 OSS 指令块;
        String[] logContent = ReadFile.readSingleText(logFile).split("<");

        //用来记录有所行数，，因为各个 P 指令块是分开在 logContent 数组里面的；
        int lineNumber = 0;

        // 遍历所有 OSS 指令块，一个一个处理；
        for (int i=0;i<logContent.length;i++){


            //将各个 OSS 指令块的内容按行分割开来处理；
            String[] ss = logContent[i].split("\\r|\\n");

            //打印各个 P 指令的内容的行数，也既是各个 P 指令的结尾行数；
//            System.out.println("ss"+ss.length);

            for (int j=0;j<ss.length;j++){  //遍历 this OSS 指令块分割后的行，j 为行号；
                //记行数；
                lineNumber++;
//                System.out.println(lineNumber);

                String lineContent = ss[j];

                //lineNumber定位处理到总文件的哪一行了，而 j 是 ss 元素的行数；
//                System.out.println("正在处理第："+lineNumber+"行");
//                System.out.println(lineContent);



                // 判断指令块是否为 Connected 块；
                if (lineContent.contains("Connected")){
                    String bscName = "";
                    String[][] bsc = new String[1][1];

                    //开始位置，并读取 bscName;
                    bscName = lineContent.substring(17,22);
                    bsc[0][0] = bscName;
                    contentHM.put("bscccConnected",bsc);
//                    System.out.println("开始处理 "+ bscName +"\r\n"+ss[j]);
//                    System.out.println("开始处理 "+ bsc[0][0] +"\r\n"+ss[j]);
                }

                // 判断指令块是否为 IOEXP 块；
                if (lineContent.contains("IOEXP")){
                    String bscName = "";
                    String[][] bsc = new String[1][1];

                    String[] IOEXP_LINE = logContent[i].split("\\r|\\n");
                    for (int k = 0; k < IOEXP_LINE.length; k++) {
                        if (IOEXP_LINE[k].equals("IDENTITY")){
                            String[] con = IOEXP_LINE[k+1].split("/");
                            bscName = con[0].substring(0,con[0].length()-3);
                            bsc[0][0] = bscName;
                            contentHM.put("bscccIOEXP",bsc);
//                                System.out.println(bscName);
                        }
                    }
                }

                // 判断指令块是否为 RLSTP 块；
                if (lineContent.contains("RLSTP")){
                    String[][] sectorState = new String[1][1];
                    String[] RLSTP_LINE = logContent[i].split("\\r|\\n");
                    for (int k = 0; k < RLSTP_LINE.length; k++) {
                        if (RLSTP_LINE[k].trim().length()>=28) {
                            String sector = RLSTP_LINE[k].substring(0,9).trim();
                            sectorState[0][0] = RLSTP_LINE[k].substring(10,18).trim();
//                                System.out.println(sector);
                            contentHM.put("rlstp"+sector,sectorState);
                        }else{

                        }
                    }
                }

                // 判断指令块是否为 RLDEP 块；
                if (lineContent.contains("RLDEP") && !lineContent.contains("EXT")){
                    // 读取小区基础信息：sector、cgi、bsic、bcch、band
//                    System.out.println(lineContent);

                    while (j<ss.length){
                        if (ss[j]!=null && ss[j].contains("CGI")){
                            String[][] sectors = new String[1][5];
                            sectors[0][0] = ss[j+1].substring(0,7); //sector
                            sectors[0][1] = ss[j+1].substring(9,29).trim();   //cgi
                            sectors[0][2] = ss[j+1].substring(30,32);   //bsic
                            sectors[0][3] = ss[j+1].substring(36,43).trim();    //bcch
                            sectors[0][4] = ss[j+4].substring(52).trim(); //band
//                                System.out.println(sectors[0]+"-"+sectors[1]+"-"+sectors[2]+"-"+sectors[3]+"-"+sectors[4]+"-"+sectors[5]);
                            contentHM.put("rldep"+sectors[0][0],sectors);
                            //String line = "MSC\t"+"bsc"+"\tEricsson\t"+sectors[0][0].substring(0,6)+"\t22.68933\t113.77698\t"+sectors[0][0]+"\t"+sectors[0][1]+"\t"+sectors[0][2]+"\tNew\t50\t"+sectors[0][4]+"\t"+sectors[0][3]+"\tTRUE\tRXOTG\tTRUE\t20\tTRUE\t10\tRandom\tNo Preference";
                            //System.out.println(sectors[0][0]);
                        }
                        j++;
                    }
//                    System.out.println("小区数： "+(ss.length-3)/9);
                }

                // 判断指令块是否为 RLCPP；读取功率
                if (lineContent.contains("RLCPP") && !lineContent.contains("EXT")){
//                        System.out.println(logContent[i]);
                    String[] RLCPP_CELL = logContent[i].split("\\r|\\n");

                    for (int k=0;k<RLCPP_CELL.length;k++){

                        if (RLCPP_CELL[k].contains("TYPE")){
                            for (int l=k;l<RLCPP_CELL.length;l++){
                                //if (RLCPP_CELL[l].contains("END"))break;
                                if ((!RLCPP_CELL[l].contains("UL") || !RLCPP_CELL[l].contains("OL")) && RLCPP_CELL[l].length()>=31){
                                    String[][] rlcpp = new String[1][2];
                                    rlcpp[0][0] = RLCPP_CELL[l].substring(0,9).trim();    // 小区名
                                    rlcpp[0][1] = RLCPP_CELL[l].substring(15,22).trim();   // BSPWRB
                                    contentHM.put("rlcpp"+rlcpp[0][0],rlcpp);
                                }else if ((!RLCPP_CELL[l].contains("UL") || !RLCPP_CELL[l].contains("OL")) && RLCPP_CELL[l].length()>=17){
                                    String[][] rlcpp = new String[1][2];
                                    rlcpp[0][0] = RLCPP_CELL[l].substring(0,9).trim();    // 小区名
                                    rlcpp[0][1] = RLCPP_CELL[l].substring(15,17).trim();   // BSPWRB
                                    contentHM.put("rlcpp"+rlcpp[0][0],rlcpp);
                                }
                            }
                            break;
                        }
                    }

                }

                // 判断指令块是否为 RXTCP；
                if (lineContent.contains("RXTCP")){
//                        System.out.println(logContent[i]);
                    // 通过 RXOTG 分裂后得到每个小区的 RXTCP 块
                    String[] RXTCP_TG = logContent[i].split("RXOTG");

//                        System.out.println(RXTCP_CELL[2]);
                    // 遍历所有按 TG 号分裂的 RXTCP 块
                    for (int k=2;k<RXTCP_TG.length;k++){
                        // 定义一个存储最终数据的数组
                        String[][] rxtcp = new String[1][3];

                        // 补充分裂后消失的“RXOTG”字符
                        String temp = "RXOTG"+RXTCP_TG[k];
                        // 再按行分裂每个 TG 的 RXTCP 块
                        String[] cell_line = temp.split("\\r|\\n");

                        //String[][] rxtcp = new String[1][cell_line.length+1];

                        // 遍历每个 RXTCP 块里面的所有行
                        for (int l=0;l<cell_line.length;l++){
                            if (cell_line[l].length()==0){break;}
                            rxtcp[0][0] = cell_line[l].substring(16,26).trim();   //小区名
                            rxtcp[0][1] = cell_line[0].substring(0,15).trim();  //获取TG号，格式“RXOTG-XXX”,一个小区可能有两个TG号
                            rxtcp[0][2] = cell_line[l].substring(27).trim();    //获取对应的 CHGR
                            contentHM.put("rxtcp"+rxtcp[0][0]+rxtcp[0][2],rxtcp);

                            //System.out.println(rxtcp[0][0]+rxtcp[0][2]+":"+rxtcp[0][0]+" "+rxtcp[0][1]+" "+rxtcp[0][2]);
                        }
                    }
                }

                // 判断指令块是否为 RLCFP 块；
                if (lineContent.contains("RLCFP")){
                    // 读取信道信息
                    // RLCFP_CELL 按“CELL”分列后存入数组 RLCFP_CELL[] 中，每个 RLCFP_CELL 就是一个小区的数据；
                    String[] RLCFP_CELL = logContent[i].split("CELL");
//                    System.out.println(RLCFP_CELL[RLCFP_CELL.length-1]);

//                    String head = "Sector\tChannel Group\tSubcell\tBand\tExtended\tHopping method\tContains BCCH\tHSN\tDTX\tPower control\tSubcell Signal Threshold\tSubcell Tx Power\t# TRXs\t# SDCCH TSs\t# Fixed GPRS TSs\tPriority\tTCH 1\tTCH 2\tTCH 3\tTCH 4\tTCH 5\tTCH 6\tTCH 7\tTCH 8\tTCH 9\tTCH 10\tTCH 11\tTCH 12\tTCH 13\tTCH 14\tTCH 15\tTCH 16\tTCH 17\tTCH 18\tTCH 19\tTCH 20\tTCH 21\tTCH 22\tTCH 23\tTCH 24\tTCH 25\tTCH 26\tTCH 27\tTCH 28\tTCH 29\tTCH 30\tTCH 31\tTCH 32\tTCH 33\tTCH 34\tTCH 35\tTCH 36\tTCH 37\tTCH 38\tTCH 39\tTCH 40\tTCH 41\tTCH 42\tTCH 43\tTCH 44\tTCH 45\tTCH 46\tTCH 47\tTCH 48\tTCH 49\tTCH 50\tTCH 51\tTCH 52\tTCH 53\tTCH 54\tTCH 55\tTCH 56\tTCH 57\tTCH 58\tTCH 59\tTCH 60\tTCH 61\tTCH 62\tTCH 63\tTCH 64\tMAIO 1\tMAIO 2\tMAIO 3\tMAIO 4\tMAIO 5\tMAIO 6\tMAIO 7\tMAIO 8\tMAIO 9\tMAIO 10\tMAIO 11\tMAIO 12\tMAIO 13\tMAIO 14\tMAIO 15\tMAIO 16";
//                    String line = "01DA011\t0\tUL\t1800\tN/A\tNon hopping\tTRUE\t3\tDownlink and Uplink\tDownlink and Uplink\tN/A\t47\t4\t1\t1\tNormal\t567\t575\t627\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A";
//                    System.out.println(head);

                    // 遍历各个小区的数据，一个 k 就是一个小区块；
                    for (int k=3;k<RLCFP_CELL.length;k++) {

                        // 再将各个小区的 RLCFP_CELL 按行分割后存入数组 RLCFP_CELL_LINE[]；
                        String[] RLCFP_CELL_LINE = RLCFP_CELL[k].split("\\r|\\n");
                        // 各个小区的信道组号统计
                        int CHGR_COUNT = 0;
                        // 各个小区的信道组号所在行数，暂时只读取6个信道组的数据
                        int[] CHGR_IN_LINE = new int[6];

                        // 循环各个 RLCFP_CELL_LINE[]；
                        for (int l=0;l<RLCFP_CELL_LINE.length;l++){

                            // 取得  RLCFP_CELL_LINE 表头所在行，其实如果没有错误的话，一般都是第三行；
                            if (RLCFP_CELL_LINE[l]!=null && RLCFP_CELL_LINE[l].contains("SDCCHAC")){
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
                                        //找到各个信道组所在行
                                        if (CHGR_COUNT==1) {CHGR_IN_LINE[0] = m;}
                                        if (CHGR_COUNT==2) {CHGR_IN_LINE[1] = m;}
                                        if (CHGR_COUNT==3) {CHGR_IN_LINE[2] = m;}
                                        if (CHGR_COUNT==4) {CHGR_IN_LINE[3] = m;}
                                        if (CHGR_COUNT==5) {CHGR_IN_LINE[4] = m;}
                                        if (CHGR_COUNT==6) {CHGR_IN_LINE[5] = m;}
                                        //if (CHGR_COUNT >6) {
                                        //    JOptionPane.showMessageDialog(null,"信道组数量大于6，文件不会处理，请联系你的开发人员。","警告",JOptionPane. WARNING_MESSAGE);
                                        //}
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
                                String RLCFPsector = RLCFP_CELL_LINE[l-2].trim();
                                //System.out.println(RLCFPsector);
                                String[][] ch_group = new String[6][68];    //保存4个信道组，每个信道组可能有16 or 68个元素（ch_group、hsn、hopping、sdcch、dchno1-12?64）
                                // 初始化 ch_group[][]，因为取不到参数的时候，需要标记为 N/A；
                                for (int x=0;x<ch_group.length;x++){
                                    for (int y=0;y<ch_group[x].length;y++){
                                        ch_group[x][y]="N/A";
                                    }
                                }

                                //System.out.println(RLCFPsector);

                                //开始从 RLCFP_CELL_LINE 表头所在行 以后 的行，循环取得其他参数；
                                for (int m = l + 1; m < RLCFP_CELL_LINE.length; m++) {
                                    //String RLCFPsector = RLCFP_CELL_LINE[l-2].trim();
                                    if (RLCFP_CELL_LINE[m] != null && RLCFP_CELL_LINE[m].substring(0, 3).trim() != "END" && RLCFP_CELL_LINE[m].length()>=64) {
                                        //ch_group = readChannelParamaters(ch_group,RLCFP_CELL_LINE,CHGR_IN_LINE,CHGR_COUNT);

                                        for (int n = 0; n < CHGR_COUNT; n++) {
                                            if (RLCFP_CELL_LINE[CHGR_IN_LINE[n]].length() >= 64) {
                                                ch_group[n][0] = RLCFP_CELL_LINE[CHGR_IN_LINE[n]].substring(0, 3).trim();    //CHGR
                                                ch_group[n][1] = RLCFP_CELL_LINE[CHGR_IN_LINE[n]].substring(49, 53).trim();    //hsn
                                                ch_group[n][2] = RLCFP_CELL_LINE[CHGR_IN_LINE[n]].substring(55, 58).trim();    //hopping
                                                ch_group[n][3] = RLCFP_CELL_LINE[CHGR_IN_LINE[n]].substring(17, 23).trim(); //sdcch

                                                int tchLength;
                                                if (CHGR_COUNT - 1 == n) {
                                                    tchLength = RLCFP_CELL_LINE.length - CHGR_IN_LINE[n];
                                                } else {
                                                    tchLength = CHGR_IN_LINE[n + 1] - CHGR_IN_LINE[n];
                                                }

                                                for (int o = 0; o < tchLength; o++) {
                                                    if (RLCFP_CELL_LINE[CHGR_IN_LINE[n] + o].length() >= 64) {
                                                        ch_group[n][4 + o] = RLCFP_CELL_LINE[CHGR_IN_LINE[n] + o].substring(60).trim();    //dchno1-dchno64
                                                    }
                                                }
                                            }
                                        }
                                        contentHM.put("rlcfp" + RLCFPsector, ch_group);
                                    }
                                }
                                //for (int x=0;x<4;x++){
                                //    for (int y=0;y<ch_group[x].length;y++){
                                //        System.out.print(ch_group[x][y]+" ");;
                                //    }
                                //    System.out.println();
                                //}
                                //System.out.println("处理完： " + RLCFPsector);
                            }
                        }

                        // 统计信道组数量大于6时的提示，提示过一次后将不会再出现
                        if (CHGR_COUNT >6 && noRepeatPrompt<1) {
                            JOptionPane.showMessageDialog(null,"信道组数量大于 6 的部分将不被处理，如果需要，请联系你的开发人员。","警告",JOptionPane. WARNING_MESSAGE);
                            noRepeatPrompt++;
                            //break;
                        }
                    }
                }

                // 判断指令块是否为 RLNRP 块；
                if (lineContent.contains("RLNRP") && !lineContent.contains("UTRAN")){

                    // RLNRP_CELL 按“CELL”分列后存入数组 RLNRP_CELL[] 中，每个 RLNRP_CELL 就是一个小区的数据；
                    String[] RLNRP_CELL = logContent[i].split("CELL\n");

//                    System.out.println("------------1----------");
//                    System.out.println(RLNRP_CELL[2]);
//                    System.out.println("------------2----------");

                    for (int k=0;k<RLNRP_CELL.length;k++){

                        String[] RLNRP_line = RLNRP_CELL[k].split("\\r|\\n");
//                            System.out.println(RLNRP_line.length);

                        // 暂存32个邻区,发现现网有的小区邻区有60+个;
                        String[][] rlnrp = new String[1][33];
                        // 先给数组设置默认值
                        for (int l=0;l<rlnrp.length;l++){
                            for (int m=0;m<rlnrp[l].length;m++){
                                rlnrp[l][m] = "N/A";
                            }
                        }

                        if (!RLNRP_line[0].contains("RLNRP")){

                            // 取得主小区的小区名
                            rlnrp[0][0] = RLNRP_line[0].substring(0).trim();

                            // 取得各个邻区的小区名
                            int temp = 1;
                            for (int l=1;l<RLNRP_line.length;l++){
                                if (temp >= 33) {break;}
                                if (RLNRP_line[l].contains("CELLR")){
//                                        System.out.println(RLNRP_line[l+1]);
                                    if (RLNRP_line[l+1].length()>7) {
                                        rlnrp[0][temp] = RLNRP_line[l + 1].substring(0, 7).trim();
                                        temp++;
                                    }
                                }
                            }
                            // put 本小区邻区数据
                            contentHM.put("rlnrp"+rlnrp[0][0],rlnrp);
                        }

                        // 测试，遍历数组 rlnrp[][]
//                            for (int l=0;l<rlnrp.length;l++){
//                                for (int m=0;m<rlnrp[l].length;m++) {
//                                    System.out.print(rlnrp[l][m] + " ");
//                                }
//                            }
//                            System.out.println();

                    }
                }

                // 判断指令块是否为 结束块；
                if (lineContent.contains("Disconnected")){
//                    System.out.println("处理最后一行： "+lineNumber+"\r\n"+ss[j]);
                }
            }
        }
        notice = "cdd 文件处理完毕";
//        System.out.println("cdd 文件处理完毕");
//        }else {
//            notice = "cdd 文件错误";
//            System.out.println("cdd 文件错误");
//        }
    return contentHM;
    }


    public String getNotice() {
        return notice;
    }

    /**
     * 生成 sectors 一个网元文件应该运行一次该方法;
     * @param validCoordinate 接收坐标文件传递过来的 coordinate[][] 数组；
     * @param contentHM 接收CDD文件传递过来的 contentHM HashMap；
     */
    public ArrayList createSectorsList(String[][] validCoordinate,HashMap<String,String[][]> contentHM){
//        String[][] validCoordinate = this.checkValidCoordinate(coordinate,contentHM);
        ArrayList sectorslist = null;

        if (contentHM != null && validCoordinate != null) {
            sectorslist = new ArrayList();
            //String sectorHead = "MSC\tBSC\tVendor\tSite\tLatitude\tLongitude\tSector\tID\tMaster\tLAC\tCI\tKeywords\tAzimuth\tBCCH frequency\tBSIC\tIntracell HO\tSynchronization group\tAMR HR Allocation\tAMR HR Threshold\tHR Allocation\tHR Threshold\tTCH allocation priority\tGPRS allocation priority\tRemote\tMCC\tMNC";

            String bscName = "";
            if (contentHM.containsKey("bscccConnected")){
                String[][] bsc = contentHM.get("bscccConnected");
                bscName = bsc[0][0];
            }else {
                String[][] bsc = contentHM.get("bscccIOEXP");
                bscName = bsc[0][0];
            }


            //System.out.println(sectorHead);

            // 对坐标文件中的小区进行遍历，只处理坐标文件夹出现的小区
            for (int i = 0; i < validCoordinate.length; i++) {
            //System.out.println(validCoordinate[i][0]);

                if (contentHM.containsKey("rldep" + validCoordinate[i][0])) {
                    // 遍历数组，打印 Sectors 文件

                    // 小区基础信息：sector、cgi、bsic、bcch、band
                    String[][] sectors = contentHM.get("rldep" + validCoordinate[i][0]);    // 获取小区号
                    String[][] rxtcp = contentHM.get("rxtcp" + validCoordinate[i][0]+"0");  // 值获取信道组为 0 的 TG 号
                    String rxotg = bscName + "_" + "RXOTG"; // 默认值
                    if (rxtcp != null) {
                        rxotg = bscName + "_" + rxtcp[0][1];    // 取得 TG 号后的值
                    }
//                    System.out.print(rxtcp[0][0]+"====");
//                    System.out.println(validCoordinate[i][0]);
//                    System.out.println(rxotg);

                    // 获取 CGI 内的信息,如果没有cgi则不处理该小区的数据
                    if (!sectors[0][1].equals("")){
                        String[] cgi = sectors[0][1].split("-");
                        String MCC = cgi[0];
                        String MNC = cgi[1];
                        String LAC = cgi[2];
                        String CI = cgi[3];

                        String sectorLine = "MSC\t" +
                                bscName + "\tEricsson\t" +
                                sectors[0][0].substring(0, 6) + "\t" +
                                validCoordinate[i][2] + "\t" +
                                validCoordinate[i][1] + "\t" +
                                sectors[0][0] + "\t\t\t" +
                                LAC + "\t" +
                                CI + "\tNew\t" +
                                validCoordinate[i][5] + "\t" +
                                sectors[0][3] + "\t" +
                                sectors[0][2] + "\tFALSE\t" +
                                rxotg + "\tTRUE\t20\tTRUE\t10\tRandom\tNo Preference\tFALSE\t" +
                                MCC + "\t" +
                                MNC;

                        sectorslist.add(sectorLine);
                    }
//                    System.out.println(sectorLine);

                } else {
//                    System.out.println("无"+coordinate[i][0]);
                }
            }

//        String[][] rlnrp = contentHM.get("rlnrp01DA121");
//        for (int x=0;x<rlnrp.length;x++){
//            for (int y=0;y<rlnrp[x].length;y++){
//                System.out.print(rlnrp[x][y]+" ");
//            }
//            System.out.println();
//        }

        }
        return sectorslist;
    }

    /**
     * 生成 channelGroups 一个网元文件应该运行一次该方法;
     * @param validCoordinate 接收坐标文件传递过来的 coordinate[][] 数组；
     * @param contentHM 接收CDD文件传递过来的 contentHM HashMap；
     */
    public ArrayList createChannelGroupsList(String[][] validCoordinate,HashMap<String,String[][]> contentHM){
        //String[][] validCoordinate = this.checkValidCoordinate(coordinate,contentHM);
        ArrayList channelGroupslist = null;
        if (contentHM != null && validCoordinate != null) {
            channelGroupslist = new ArrayList();

            //String channelGroupHead = "Sector\tChannel Group\tSubcell\tBand\tExtended\tHopping method\tContains BCCH\tHSN\tDTX\tPower control\tSubcell Signal Threshold\tSubcell Tx Power\t# TRXs\t# SDCCH TSs\t# Fixed GPRS TSs\tPriority\tTCH 1\tTCH 2\tTCH 3\tTCH 4\tTCH 5\tTCH 6\tTCH 7\tTCH 8\tTCH 9\tTCH 10\tTCH 11\tTCH 12\tTCH 13\tTCH 14\tTCH 15\tTCH 16\tTCH 17\tTCH 18\tTCH 19\tTCH 20\tTCH 21\tTCH 22\tTCH 23\tTCH 24\tTCH 25\tTCH 26\tTCH 27\tTCH 28\tTCH 29\tTCH 30\tTCH 31\tTCH 32\tTCH 33\tTCH 34\tTCH 35\tTCH 36\tTCH 37\tTCH 38\tTCH 39\tTCH 40\tTCH 41\tTCH 42\tTCH 43\tTCH 44\tTCH 45\tTCH 46\tTCH 47\tTCH 48\tTCH 49\tTCH 50\tTCH 51\tTCH 52\tTCH 53\tTCH 54\tTCH 55\tTCH 56\tTCH 57\tTCH 58\tTCH 59\tTCH 60\tTCH 61\tTCH 62\tTCH 63\tTCH 64\tMAIO 1\tMAIO 2\tMAIO 3\tMAIO 4\tMAIO 5\tMAIO 6\tMAIO 7\tMAIO 8\tMAIO 9\tMAIO 10\tMAIO 11\tMAIO 12\tMAIO 13\tMAIO 14\tMAIO 15\tMAIO 16";

            //System.out.println(channelGroupHead);

            // 对坐标文件中的小区进行遍历，只处理坐标文件夹出现的小区
            for (int i = 1; i < validCoordinate.length; i++) {
            //System.out.println(validCoordinate[i][0]);

                if (contentHM.containsKey("rlcfp" + validCoordinate[i][0])) {

                    // 遍历数组，打印 ChannelGroups 文件
                    String[][] ch_group = contentHM.get("rlcfp" + validCoordinate[i][0]);
                    String[][] sectors = contentHM.get("rldep" + validCoordinate[i][0]);
                    String[][] rlcpp = contentHM.get("rlcpp" + validCoordinate[i][0]);

                    //for (int x=0;x<ch_group.length;x++){
                    //    for (int y=0;y<ch_group[x].length;y++){
                    //        System.out.print(ch_group[x][y]+" ");
                    //    }
                    //    System.out.println();
                    //}

                    for (int x = 0; x < ch_group.length; x++) {
//                        System.out.println("cgcgcgcgccgcgcgcgcg");

                        if (ch_group[x][0] != "N/A") {   //首先，判断信道是存在的

                            // 判断信道存储的频率为什么类型：1~124 PGSM、975~1024 EGSM、N/A
                            String extended = "N/A";
                            if (ch_group[x][4] != "N/A" && Integer.parseInt(ch_group[x][4]) != 0 && Integer.parseInt(ch_group[x][4]) <= 124) {
                                extended = "PGSM";
                                for (int j = 5; j < ch_group[x].length; j++) {
                                    if (ch_group[x][j].equals("N/A")) break;
                                    if ((Integer.parseInt(ch_group[x][j]) >= 975 && Integer.parseInt(ch_group[x][j]) <= 1024) || Integer.parseInt(ch_group[x][j]) == 0) {
                                        extended = "N/A";
                                        break;
                                    }
                                }
                            } else if (ch_group[x][4] != "N/A" && ((Integer.parseInt(ch_group[x][4]) >= 975 && Integer.parseInt(ch_group[x][4]) <= 1024) || Integer.parseInt(ch_group[x][4]) == 0)) {
                                extended = "EGSM";
                                for (int j = 5; j < ch_group[x].length; j++) {
                                    if (ch_group[x][j].equals("N/A")) break;
                                    if (Integer.parseInt(ch_group[x][j]) < 124 && Integer.parseInt(ch_group[x][j]) == 0) {
                                        extended = "N/A";
                                        break;
                                    }
                                }
                            }

                            // 判断跳频方式
                            String hoppingMethod = "Non hopping";
                            if (ch_group[x][2].contains("ON")) {
                                hoppingMethod = "Base band";
                            }

                            // 判断是否为BCCH频点所在信道,并计算信道的频点数
                            String ContainsBCCH = "FALSE";
                            int frequencyCount = 0;
                            int locationOfBCCH = 4;
                            for (int j = 4; j < ch_group[x].length; j++) {
                                // 如果遇到 N/A 说明后面没有参数了,可以退出
                                if (ch_group[x][j].equals("N/A")) break;
                                // 有参数就比较参数是不是主频,是的话标记这一信道组为 bcch 信道
                                if (ch_group[x][j].equals(sectors[0][3])) {
                                    ContainsBCCH = "TRUE";
                                    locationOfBCCH = j;
                                    //break;
                                }
                                frequencyCount++;
                            }

                            // 计算信道的频点数，bcch也包含在内了
//                        int frequencyCount = 0;
//                        for (int j=4;j<ch_group[x].length;j++){
//                            if (ch_group[x][j].equals("N/A")){
//                                break;
//                            }
//                            frequencyCount++;
//                        }

                            // 如果功率值为null，设置默认功率
                            if (rlcpp == null){
                                rlcpp = new String[1][2];
                                rlcpp[0][0] = sectors[0][0];
                                rlcpp[0][1] = "45";
                            }

                            //System.out.println(sectors[0][0]);

                            String TCH = "";
                            String tab = "\t";
                            String MAIO = "\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A";
                            for (int j = 4; j < ch_group[x].length; j++) {
                                if (j == locationOfBCCH && ContainsBCCH == "TRUE"){
                                    //TCH += tab + "N/A";
                                }else {
                                    TCH += tab + ch_group[x][j];
                                }
                            }

                            if (ContainsBCCH == "TRUE"){
                                TCH += "\tN/A";
                            }

                            if (!sectors[0][1].equals("")) {
                                String channelGroupLine = sectors[0][0] + tab +
                                        ch_group[x][0] + tab +
                                        "UL" + tab +
                                        sectors[0][4].substring(3) + tab +
                                        extended + tab +
                                        hoppingMethod + tab +
                                        ContainsBCCH + tab +
                                        ch_group[x][1] + tab +
                                        "Downlink and Uplink" + tab +
                                        "Downlink and Uplink" + tab +
                                        "N/A" + tab +
                                        rlcpp[0][1] + tab +
                                        frequencyCount + tab +
                                        ch_group[x][3] + tab +
                                        "1" + tab +
                                        "Normal" +
                                        TCH + MAIO;
                                channelGroupslist.add(channelGroupLine);
                            }
                        }

//                    for (int y=0;y<ch_group[x].length;y++){
//                        System.out.print(ch_group[x][y]+" ");
//                    }
                    }
                }
            }
        }
        return channelGroupslist;
    }

    /**
     * 生成 handovers 一个网元文件应该运行一次该方法;
     * @param validCoordinate 接收坐标文件传递过来的 coordinate[][] 数组；
     * @param contentHM 接收CDD文件传递过来的 contentHM HashMap；
     */
    public ArrayList createHandoversList(String[][] validCoordinate,HashMap<String,String[][]> contentHM){
//        String[][] validCoordinate = this.checkValidCoordinate(coordinate,contentHM);
        ArrayList handoverslist = null;
        if (contentHM != null && validCoordinate != null) {
            handoverslist = new ArrayList();

            //String handoverHead = "Serving Sector\tTarget Sector\tHysteresis\tSector Threshold";

//        handoverslist.add(handoverHead);
//        System.out.println(handoverHead);

            // 对坐标文件中的小区进行遍历，只处理坐标文件夹出现的小区
            for (int i = 1; i < validCoordinate.length; i++) {
//            System.out.print(coordinate[i][0]);

                if (contentHM.containsKey("rlnrp" + validCoordinate[i][0])) {
                    // 遍历数组，打印 handovers 文件

                    String[][] rlnrp = contentHM.get("rlnrp" + validCoordinate[i][0]);
//                System.out.println(rlnrp[0][0]+"===="+coordinate[i][0]);

                    for (int j = 0; j < rlnrp.length; j++) {

//                      String servingSector = rlnrp[j][0];
                        for (int k = 1; k < rlnrp[j].length; k++) {
                            if (rlnrp[j][k].equals("N/A")) break;
                            String handoverLine = rlnrp[j][0] + "\t" + rlnrp[j][k] + "\t5\tN/A";

                            handoverslist.add(handoverLine);
//                        System.out.println(handoverLine);
                        }
                    }

                }
            }
        }
        return handoverslist;
    }

    /**
     * 检查 coordinate 中有多少小区是现网cdd中有效的
     * @param coordinate
     * @param contentHM
     * @return
     */
    public String[][] checkValidCoordinate(String[][] coordinate,HashMap<String,String[][]> contentHM){
        String[][] validCoordinate = null;
        if (contentHM != null && coordinate != null) {
            int validValue = 0;
            //检查coordinate中有多少小区是现网cdd中有效的
            for (int i = 0; i < coordinate.length; i++) {
                if (contentHM.containsKey("rldep" + coordinate[i][0])) {
                    validValue++;
                }
            }
            validCoordinate = new String[validValue][];
            int validValueCHECK = 0;
            //将coordinate中有效的小区复制到validCoordinate中
            for (int i = 0; i < coordinate.length; i++) {
                if (validValueCHECK > validValue)break;
                if (contentHM.containsKey("rldep" + coordinate[i][0])) {
                    validCoordinate[validValueCHECK] = coordinate[i];
                    validValueCHECK++;
                }
            }
            //遍历validCoordinate中的数据
            //for (int i = 0; i < validCoordinate.length; i++) {
            //    for (int j = 0; j < validCoordinate[i][j].length(); j++) {
            //        System.out.print(validCoordinate[i][j]+" ");
            //    }
            //    System.out.println();
            //}
        }
        return validCoordinate;
    }

    /**
     * 批量处理cdd文件，将文件列表中的文件一个个处理后再调用生成相应文件的方法，最后返回一个包含内容的数组
     * @param fileList cdd 文件列表
     * @param coordinatesList 坐标文件返回的内容，程序只处理坐标文件中出现的小区名
     * @return 返回的数组中包含了三个文件的内容:sector/channelGroup/handover
     */
    public ArrayList[][] processorMultiLog(File[] fileList,String[][] coordinatesList){
        ArrayList[][] forteArray = null;
        if (fileList != null && coordinatesList != null) {
            // forteArray 分别顺序包含:sector/channelGroup/handover
            forteArray = new ArrayList[3][fileList.length];
            // 遍历 fileList 一个个 cdd 文件处理后赋值给 forteArray 数组
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isFile()) {
                    System.out.println("开始处理"+fileList[i]);
                    HashMap contentHM = processorSingleLog(fileList[i]);
                    //String[][] validCoordinate = this.checkValidCoordinate(coordinatesList,contentHM);
                    //System.out.println(coordinatesList.length);
                    forteArray[0][i] = createSectorsList(coordinatesList, contentHM);
                    forteArray[1][i] = createChannelGroupsList(coordinatesList, contentHM);
                    forteArray[2][i] = createHandoversList(coordinatesList, contentHM);
                    //System.out.println("完成"+fileList[i]);
                }
            }
            System.out.println("cdd 文件处理完毕！");
        }else {
            System.out.println("读取文件出错");
        }
        return forteArray;
    }

    /**
     *
     * @param forteArray 包含了三个文件的内容:sector/channelGroup/handover
     * @param exportPath 文件保存的位置
     */
    public void createForteFile(ArrayList[][] forteArray,String exportPath){
        if (forteArray != null) {
            File sectors = new File(exportPath + "\\Sectors.txt");
            File channelGroups = new File(exportPath + "\\ChannelGroups.txt");
            File handovers = new File(exportPath + "\\Handovers.txt");

            String sectorHead = "MSC\tBSC\tVendor\tSite\tLatitude\tLongitude\tSector\tID\tMaster\tLAC\tCI\tKeywords\tAzimuth\tBCCH frequency\tBSIC\tIntracell HO\tSynchronization group\tAMR HR Allocation\tAMR HR Threshold\tHR Allocation\tHR Threshold\tTCH allocation priority\tGPRS allocation priority\tRemote\tMCC\tMNC";

            String channelGroupHead = "Sector\tChannel Group\tSubcell\tBand\tExtended\tHopping method\tContains BCCH\tHSN\tDTX\tPower control\tSubcell Signal Threshold\tSubcell Tx Power\t# TRXs\t# SDCCH TSs\t# Fixed GPRS TSs\tPriority\tTCH 1\tTCH 2\tTCH 3\tTCH 4\tTCH 5\tTCH 6\tTCH 7\tTCH 8\tTCH 9\tTCH 10\tTCH 11\tTCH 12\tTCH 13\tTCH 14\tTCH 15\tTCH 16\tTCH 17\tTCH 18\tTCH 19\tTCH 20\tTCH 21\tTCH 22\tTCH 23\tTCH 24\tTCH 25\tTCH 26\tTCH 27\tTCH 28\tTCH 29\tTCH 30\tTCH 31\tTCH 32\tTCH 33\tTCH 34\tTCH 35\tTCH 36\tTCH 37\tTCH 38\tTCH 39\tTCH 40\tTCH 41\tTCH 42\tTCH 43\tTCH 44\tTCH 45\tTCH 46\tTCH 47\tTCH 48\tTCH 49\tTCH 50\tTCH 51\tTCH 52\tTCH 53\tTCH 54\tTCH 55\tTCH 56\tTCH 57\tTCH 58\tTCH 59\tTCH 60\tTCH 61\tTCH 62\tTCH 63\tTCH 64\tMAIO 1\tMAIO 2\tMAIO 3\tMAIO 4\tMAIO 5\tMAIO 6\tMAIO 7\tMAIO 8\tMAIO 9\tMAIO 10\tMAIO 11\tMAIO 12\tMAIO 13\tMAIO 14\tMAIO 15\tMAIO 16";

            String handoverHead = "Serving Sector\tTarget Sector\tHysteresis\tSector Threshold";

            try (FileWriter sectorsFW = new FileWriter(sectors);
                 FileWriter channelGroupsFW = new FileWriter(channelGroups);
                 FileWriter handoversFW = new FileWriter(handovers)) {
                // 如果文件不存在就创建一个新的
                //if (!sectors.exists()) {sectors.createNewFile();}
                //if (!channelGroups.exists()) {channelGroups.createNewFile();}
                //if (!handovers.exists()) {handovers.createNewFile();}

                // 打印表头
                sectorsFW.write(sectorHead + "\r\n");
                channelGroupsFW.write(channelGroupHead + "\r\n");
                handoversFW.write(handoverHead + "\r\n");

                // 处理 sectors 文件
                for (int i = 0; i < forteArray[0].length; i++) {
                    if (forteArray[0][i] != null) {
                        ArrayList sectorslist = forteArray[0][i];
                        Iterator it1 = sectorslist.iterator();
                        while (it1.hasNext()) {
                            sectorsFW.write(it1.next() + "\r\n");
                        }
                    }

//                    if (forteArray[0][i] != null) {
//                        ArrayList sectorslist = forteArray[0][i];
//                        String[] sectorsString = new String[sectorslist.size()];
//                        sectorsString = (String[]) sectorslist.toArray(sectorsString);
//
//                        for (int j = 0; j < sectorsString.length; j++) {
////                            byte[] contentInBytes = sectorsString[j].getBytes();
////                            System.out.println(sectorsString[j]);
//                            sectorsFW.write(sectorsString[j]+"\r\n");
//                        }
//                    }
                }
                // 处理 channelGroups 文件
                for (int i = 0; i < forteArray[1].length; i++) {
                    if (forteArray[1][i] != null) {
                        ArrayList channelGroupslist = forteArray[1][i];
                        Iterator it1 = channelGroupslist.iterator();
                        while (it1.hasNext()) {
                            channelGroupsFW.write(it1.next() + "\r\n");
                        }
                    }
                }
                // 处理 handovers 文件
                for (int i = 0; i < forteArray[2].length; i++) {
                    if (forteArray[2][i] != null) {
                        ArrayList handoverslist = forteArray[2][i];
                        Iterator it1 = handoverslist.iterator();
                        while (it1.hasNext()) {
                            handoversFW.write(it1.next() + "\r\n");
                        }
                    }
                }
                System.out.println("导出成功！");
                JOptionPane.showMessageDialog(null,"导出成功！");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"导出失败，另一个程序正在使用此文件！");
            }
        }
    }
}
