package Control.Cdd2ForteControl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static Common.enumClass.excelType.*;

/**
 * Created by 跃峰 on 2016/6/1.
 */
public class ProcessorExcelCdd {
    public static void main(String[] args) {
        File cdd = new File("D:\\thxhyf\\Documents\\现网cdd.xls");
        File channel = new File("D:\\thxhyf\\Documents\\现网cdd_Channel.xls");
        File handover = new File("D:\\thxhyf\\Documents\\现网cdd_Nrel.xls");
        File[] excel = {cdd,channel,handover};

        File coordinatesFile = new File("D:\\SZ\\变频工作\\数据采集\\cellinfo\\coordinates0512.txt");
        String[][] coordinates = new ProcessorCoordinate().readCoordinates(coordinatesFile);

        ProcessorExcelCdd processorExcelCdd = new ProcessorExcelCdd();
        processorExcelCdd.createHandovers(excel,coordinates);
        //ArrayList[][] forteArray = processorExcelCdd.createForteArray(excel, coordinates);

        //System.out.println("1111111");
        //new ProcessorCddLog().createForteFile(forteArray,"D:\\thxhyf\\Documents");
    }

    public ArrayList[][] createForteArray(File[] excel, String[][] coordiantes){
        ArrayList[][] forteArray = null;

        if (coordiantes != null) {
            // excel 分别顺序包含:sector/channelGroup/handover
            forteArray = new ArrayList[3][excel.length];

            if (excel[0].isFile() && excel[1].isFile() && excel[2].isFile()) {
                System.out.println("11");
                forteArray[0][0] = createSectors(excel, coordiantes);
                System.out.println("22");
                forteArray[1][0] = createChannels(excel, coordiantes);
                System.out.println("33");
                forteArray[2][0] = createHandovers(excel, coordiantes);
                System.out.println("44");
            }
            System.out.println("cdd 文件处理完毕！");
        }else {
            System.out.println("读取文件出错");
        }
        return forteArray;
    }

    /**
     * 网优之家 现网cdd 生成 Sectors 文件
     * @param excel
     * @param coordiantes
     * @return
     */
    public ArrayList createSectors(File[] excel, String[][] coordiantes){
        ArrayList sectorslist = new ArrayList();

        //String sectorHead = "MSC\tBSC\tVendor\tSite\tLatitude\tLongitude\tSector\tID\tMaster\tLAC\tCI\tKeywords\tAzimuth\tBCCH frequency\tBSIC\tIntracell HO\tSynchronization group\tAMR HR Allocation\tAMR HR Threshold\tHR Allocation\tHR Threshold\tTCH allocation priority\tGPRS allocation priority\tRemote\tMCC\tMNC";

        POIReadExcel readExcel = new POIReadExcel();
        HashMap<String,String[]> excelContentCdd = readExcel.poiReadExcelContent(excel[0], cdd);
        HashMap<String,String[]> excelContentChannel = readExcel.poiReadExcelContent(excel[1], channel);

        //String[] targetTitle = new String[]{"CELL", "BSC", "MSC", "SITE", "lac", "ci", "bcchno", "bsic", "TCH", "tchnum", "mcc", "mnc", "bspwrb"};
        //System.out.println(sectorHead);

        for (String[] coordiante : coordiantes) {
            String SynchronizationGroup = "RXOTG";
            if (excelContentChannel.containsKey(coordiante[0]+"0")){
                SynchronizationGroup = excelContentChannel.get(coordiante[0]+"0")[3];
                //System.out.println(SynchronizationGroup);
            }
            if (excelContentCdd.containsKey(coordiante[0])){
                String[] strings = excelContentCdd.get(coordiante[0]);
                String sectorLine = strings[2] + "\t" +
                        strings[1] + "\tEricsson\t" +
                        strings[3] + "\t" +
                        coordiante[2] + "\t" +
                        coordiante[1] + "\t" +
                        strings[0] + "\t\t\t" +
                        strings[4] + "\t" +
                        strings[5] + "\tNew\t" +
                        coordiante[5] + "\t" +
                        strings[6] + "\t" +
                        strings[7] + "\tFALSE\t" +
                        strings[1] + "_" +SynchronizationGroup + "\tTRUE\t20\tTRUE\t10\tRandom\tNo Preference\tFALSE\t" +
                        strings[10] + "\t" +
                        strings[11];
                sectorslist.add(sectorLine);

                //System.out.println(sectorLine);
            }
        }
        return sectorslist;
    }

    /**
     * 网优之家 现网cdd_Channel 生成 ChannelGroups 文件
     * @param excel
     * @param coordiantes
     * @return
     */
    public ArrayList createChannels(File[] excel, String[][] coordiantes){
        ArrayList channelGroupslist = new ArrayList();

        //String channelGroupHead = "Sector\tChannel Group\tSubcell\tBand\tExtended\tHopping method\tContains BCCH\tHSN\tDTX\tPower control\tSubcell Signal Threshold\tSubcell Tx Power\t# TRXs\t# SDCCH TSs\t# Fixed GPRS TSs\tPriority\tTCH 1\tTCH 2\tTCH 3\tTCH 4\tTCH 5\tTCH 6\tTCH 7\tTCH 8\tTCH 9\tTCH 10\tTCH 11\tTCH 12\tTCH 13\tTCH 14\tTCH 15\tTCH 16\tTCH 17\tTCH 18\tTCH 19\tTCH 20\tTCH 21\tTCH 22\tTCH 23\tTCH 24\tTCH 25\tTCH 26\tTCH 27\tTCH 28\tTCH 29\tTCH 30\tTCH 31\tTCH 32\tTCH 33\tTCH 34\tTCH 35\tTCH 36\tTCH 37\tTCH 38\tTCH 39\tTCH 40\tTCH 41\tTCH 42\tTCH 43\tTCH 44\tTCH 45\tTCH 46\tTCH 47\tTCH 48\tTCH 49\tTCH 50\tTCH 51\tTCH 52\tTCH 53\tTCH 54\tTCH 55\tTCH 56\tTCH 57\tTCH 58\tTCH 59\tTCH 60\tTCH 61\tTCH 62\tTCH 63\tTCH 64\tMAIO 1\tMAIO 2\tMAIO 3\tMAIO 4\tMAIO 5\tMAIO 6\tMAIO 7\tMAIO 8\tMAIO 9\tMAIO 10\tMAIO 11\tMAIO 12\tMAIO 13\tMAIO 14\tMAIO 15\tMAIO 16";

        POIReadExcel readExcel = new POIReadExcel();
        HashMap<String,String[]> excelContentCdd = readExcel.poiReadExcelContent(excel[0], cdd);
        HashMap<String,String[]> excelContentChannel = readExcel.poiReadExcelContent(excel[1], channel);

        //String[] targetTitle = new String[]{"cell", "ch_group", "bsc", "chgr_tg", "band", "bccd", "channel_tch", "hsn", "sdcch", "tchnum", "hop"};

        String tab = "\t";
        String MAIO = "\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A";

        Iterator<Map.Entry<String, String[]>> iterator = excelContentChannel.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            String[] strings = entry.getValue();

            // 判断信道存储的频率为什么类型：1~124 PGSM、975~1024 EGSM、N/A
            String extended = "N/A";
            String[] channel_tch = strings[6].split(",");
            for (String s : channel_tch) {
                if (Integer.parseInt(s) != 0 && Integer.parseInt(s) <= 124){
                    extended = "PGSM";
                    break;
                }else if ((Integer.parseInt(s) >=975 && Integer.parseInt(s) <= 1024) || Integer.parseInt(s) == 0 ){
                    extended = "EGSM";
                    break;
                }
            }

            // 判断跳频方式
            String hoppingMethod = "Non hopping";
            if (strings[10].equals("ON")) {
                hoppingMethod = "Base band";
            }

            // 判断是否为BCCH频点所在信道,并计算信道的频点数
            String ContainsBCCH = "FALSE";
            if (strings[5].equals("YES")){
                ContainsBCCH = "TRUE";
            }

            // 如果功率值为null，设置默认功率
            String subcellTxPower = "45";
            if (excelContentCdd.containsKey(strings[0])){
                subcellTxPower = excelContentCdd.get(strings[0])[12];
            }

            String tchFrequency = "";
            String[] TCH = new String[64];
            //初始化 TCH 数组
            for (int i = 0; i < TCH.length; i++) {
                TCH[i] = "N/A";
            }

            //将 channel_tch 的数据剔除 BCCH 后赋值给 TCH[]
            int j = 0;
            for (int i = 0; i < channel_tch.length; i++) {
                if (excelContentCdd.containsKey(strings[0]) && channel_tch[i].equals(excelContentCdd.get(strings[0])[6]) && ContainsBCCH == "TRUE"){
                    //TCH += tab + "N/A";
                }else {
                    TCH[j] = channel_tch[i];
                    j++;
                }
            }

            //确定 tchFrequency
            for (String s : TCH) {
                tchFrequency += tab + s;
            }

            String channelGroupLine = strings[0] + tab +
                    strings[1] + tab +
                    "UL" + tab +
                    strings[4].substring(3) + tab +
                    extended + tab +
                    hoppingMethod + tab +
                    ContainsBCCH + tab +
                    strings[7] + tab +
                    "Downlink and Uplink" + tab +
                    "Downlink and Uplink" + tab +
                    "N/A" + tab +
                    subcellTxPower + tab +
                    strings[9] + tab +
                    strings[8] + tab +
                    "1" + tab +
                    "Normal" +
                    tchFrequency + MAIO;

            for (String[] coordiante : coordiantes) {
                if (coordiante[0].equals(strings[0])){
                    channelGroupslist.add(channelGroupLine);
                    //System.out.println(channelGroupLine);
                }
            }
        }
        return channelGroupslist;
    }

    /**
     * 网优之家 现网cdd_Nrel 生成 Handovers 文件
     * @param excel
     * @param coordiantes
     * @return
     */
    public ArrayList createHandovers(File[] excel, String[][] coordiantes){
        ArrayList handoverslist = new ArrayList();

        //String handoverHead = "Serving Sector\tTarget Sector\tHysteresis\tSector Threshold";

        POIReadExcel readExcel = new POIReadExcel();
        HashMap<String,String[]> excelContentHandover = readExcel.poiReadExcelContent(excel[2], handover);

        //String[] targetTitle = new String[]{"CELL", "BSC", "n_cell", "n_bsc", "hihyst", "khyst", "koffset"};
        //System.out.println(handoverHead);

        Iterator<Map.Entry<String, String[]>> iterator = excelContentHandover.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            String[] strings = entry.getValue();

            String handoverLine = strings[0] + "\t" + strings[2] + "\t" + strings[4] + "\tN/A";

            for (String[] coordiante : coordiantes) {
                if (coordiante[0].equals(strings[0])){
                    handoverslist.add(handoverLine);
                    //System.out.println(handoverLine);
                }
            }
        }
        return handoverslist;
    }
}
