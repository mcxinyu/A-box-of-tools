package Control.Cdd2ForteControl;

import Common.enumClass;
import Control.Cdd2ForteControl.POI.EventModelUnion;

import java.io.File;
import java.util.*;

import static Common.enumClass.excelType.*;

/**
 * Created by 跃峰 on 2016/6/1.
 */
public class ProcessorExcelCdd {
    public static void main(String[] args) throws Exception {
        File coordinateFile = new File("D:\\SZ\\日常工作\\20160608\\coordinates.txt");
        ProcessorCoordinate processorCoordinate = new ProcessorCoordinate();
        String[][] coordinate = processorCoordinate.readCoordinates(coordinateFile);

        File[] excel = new File[3];
        excel[0] = new File("D:\\thxhyf\\Documents\\现网cdd.xls");
        excel[1] = new File("D:\\thxhyf\\Documents\\现网cdd_Channel.xlsx");
        excel[2] = new File("D:\\thxhyf\\Documents\\现网cdd_Nrel.xlsx");

        String exportPath = "D:\\thxhyf\\Documents\\";

        ProcessorExcelCdd processorExcelCdd = new ProcessorExcelCdd();
        HashMap<enumClass.excelType, HashMap> allContent = processorExcelCdd.get3Content(excel);

        ArrayList[][] forteArray = processorExcelCdd.createForteArray(allContent, coordinate);
        ProcessorTxtCdd processorTxtCdd = new ProcessorTxtCdd();
        processorTxtCdd.createForteFile(forteArray,exportPath);
    }

    /**
     * 检查确认需要的参数在HashMap中的位置
     * @param excelContent
     * @param excelType
     * @return
     */
    public int[] checkExcelTitle(HashMap<String,List<String>> excelContent, enumClass.excelType excelType){
        String[] targetTitle = null;
        if (excelType.equals(cdd)){
            targetTitle = new String[]{"CELL", "BSC", "MSC", "SITE", "lac", "ci", "bcchno", "bsic", "TCH", "tchnum", "mcc", "mnc", "bspwrb"};
        }else if (excelType.equals(channel)){
            targetTitle = new String[]{"cell", "ch_group", "bsc", "chgr_tg", "band", "channel_tch", "hsn", "sdcch", "tchnum", "hop"};
        }else if (excelType.equals(handover)){
            targetTitle = new String[]{"CELL", "BSC", "n_cell", "n_bsc", "hihyst", "khyst", "koffset"};
        }

        //xls文件最多就256列,xls是16384列
        int[] targetTitlePosition = new int[targetTitle.length];
        for (int i = 0; i < targetTitlePosition.length; i++) {
            targetTitlePosition[i] = 22222;
        }

        Iterator<Map.Entry<String,List<String>>> iterator = excelContent.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,List<String>> entry = iterator.next();
            System.out.println(entry.getKey());

            List list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i) + " ");
            }
            System.out.println();
        }


        //获取表头
        List<String> excelTitle = excelContent.get("0.0");

        //判断参数在HashMap中所在的位置
        for (int i = 0; i < excelTitle.size(); i++) {
            for (int j = 0; j < targetTitlePosition.length; j++) {
                if (excelTitle.get(i) != null && excelTitle.get(i).equals(targetTitle[j])){
                    targetTitlePosition[j] = i;
                }
            }
        }
        return targetTitlePosition;
    }

    public HashMap<String,String[]> getParamContent(HashMap<String,List<String>> excelContent, enumClass.excelType excelType){
        //HashMap<enumClass.excelType, HashMap> content = new HashMap<enumClass.excelType, HashMap>();
        HashMap<String,String[]> paramContent = new HashMap<>();
        int[] targetTitle = checkExcelTitle(excelContent, excelType);

        Iterator<Map.Entry<String,List<String>>> iterator = excelContent.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String,List<String>> entry = iterator.next();
            if (entry.getKey()!= "0.0"){
                List<String> list = entry.getValue();

                String[] row = new String[targetTitle.length];
                for (int i = 0; i < targetTitle.length; i++) {
                    row[i] = list.get(targetTitle[i]);
                }

                if (excelType.equals(cdd)){
                    paramContent.put(list.get(targetTitle[0]),row);
                }else if (excelType.equals(channel)){
                    paramContent.put(list.get(targetTitle[0])+list.get(targetTitle[1]),row);
                }else if (excelType.equals(handover)){
                    paramContent.put(list.get(targetTitle[0])+list.get(targetTitle[2]),row);
                }
            }
        }
        return paramContent;
    }

    /**
     * 获取 ExcelContent，避免每次都读取。
     * @param excel
     * @return
     */
    public HashMap<enumClass.excelType,HashMap> get3Content(File[] excel) throws Exception {
        HashMap<enumClass.excelType, HashMap> content = new HashMap<enumClass.excelType, HashMap>();
        //IRowReader reader = new IRowReader();

        EventModelUnion eventModelUnion = new EventModelUnion();

        HashMap<String, List<String>> excelContentCdd = eventModelUnion.readerUtilWithEventModel(excel[0]);
        HashMap<String, String[]> paramContentCdd = getParamContent(excelContentCdd, cdd);
        content.put(cdd,paramContentCdd);

        HashMap<String, List<String>> excelContentChannel = eventModelUnion.readerUtilWithEventModel(excel[1]);
        HashMap<String, String[]> paramContentChannel = getParamContent(excelContentChannel, channel);
        content.put(channel,paramContentChannel);

        HashMap<String, List<String>> excelContentHandover = eventModelUnion.readerUtilWithEventModel(excel[2]);
        HashMap<String, String[]> paramContentHandover = getParamContent(excelContentHandover, handover);
        content.put(handover,paramContentHandover);

        return content;
    }


    /**
     * 网优之家 现网cdd 生成 Sectors 文件
     * @param coordiantes
     * @return
     */
    public ArrayList createSectors(HashMap<enumClass.excelType, HashMap> excelContent, String[][] coordiantes){
        System.out.println("createSectors-start");
        ArrayList sectorslist = new ArrayList();

        //String sectorHead = "MSC\tBSC\tVendor\tSite\tLatitude\tLongitude\tSector\tID\tMaster\tLAC\tCI\tKeywords\tAzimuth\tBCCH frequency\tBSIC\tIntracell HO\tSynchronization group\tAMR HR Allocation\tAMR HR Threshold\tHR Allocation\tHR Threshold\tTCH allocation priority\tGPRS allocation priority\tRemote\tMCC\tMNC";

        HashMap<String,String[]> excelContentCdd = excelContent.get(cdd);
        HashMap<String,String[]> excelContentChannel = excelContent.get(channel);

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
        System.out.println("createSectors-end");
        return sectorslist;
    }

    /**
     * 网优之家 现网cdd_Channel 生成 ChannelGroups 文件
     * @param coordiantes
     * @return
     */
    public ArrayList createChannels(HashMap<enumClass.excelType, HashMap> excelContent, String[][] coordiantes){
        System.out.println("createChannels-start");
        ArrayList channelGroupslist = new ArrayList();

        //String channelGroupHead = "Sector\tChannel Group\tSubcell\tBand\tExtended\tHopping method\tContains BCCH\tHSN\tDTX\tPower control\tSubcell Signal Threshold\tSubcell Tx Power\t# TRXs\t# SDCCH TSs\t# Fixed GPRS TSs\tPriority\tTCH 1\tTCH 2\tTCH 3\tTCH 4\tTCH 5\tTCH 6\tTCH 7\tTCH 8\tTCH 9\tTCH 10\tTCH 11\tTCH 12\tTCH 13\tTCH 14\tTCH 15\tTCH 16\tTCH 17\tTCH 18\tTCH 19\tTCH 20\tTCH 21\tTCH 22\tTCH 23\tTCH 24\tTCH 25\tTCH 26\tTCH 27\tTCH 28\tTCH 29\tTCH 30\tTCH 31\tTCH 32\tTCH 33\tTCH 34\tTCH 35\tTCH 36\tTCH 37\tTCH 38\tTCH 39\tTCH 40\tTCH 41\tTCH 42\tTCH 43\tTCH 44\tTCH 45\tTCH 46\tTCH 47\tTCH 48\tTCH 49\tTCH 50\tTCH 51\tTCH 52\tTCH 53\tTCH 54\tTCH 55\tTCH 56\tTCH 57\tTCH 58\tTCH 59\tTCH 60\tTCH 61\tTCH 62\tTCH 63\tTCH 64\tMAIO 1\tMAIO 2\tMAIO 3\tMAIO 4\tMAIO 5\tMAIO 6\tMAIO 7\tMAIO 8\tMAIO 9\tMAIO 10\tMAIO 11\tMAIO 12\tMAIO 13\tMAIO 14\tMAIO 15\tMAIO 16";

        HashMap<String,String[]> excelContentCdd = excelContent.get(cdd);
        HashMap<String,String[]> excelContentChannel = excelContent.get(channel);

        //String[] targetTitle = new String[]{"cell", "ch_group", "bsc", "chgr_tg", "band", "bccd", "channel_tch", "hsn", "sdcch", "tchnum", "hop"};


        String tab = "\t";
        String MAIO = "\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A\tN/A";

        Iterator<Map.Entry<String, String[]>> iterator = excelContentChannel.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            String[] strings = entry.getValue();


            // 判断信道存储的频率为什么类型：1~124 PGSM、975~1024 EGSM、N/A
            String extended = "N/A";
            String[] channel_tch = strings[5].split(",");
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
            if (strings[9].equals("ON")) {
                hoppingMethod = "Base band";
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

            // 判断是否为BCCH频点所在信道,并计算信道的频点数，网优直接的数据有问题，不能用这个字段来标记。
            String ContainsBCCH = "FALSE";
            //if (strings[5].equals("YES")){
            //    ContainsBCCH = "TRUE";
            //}

            //将 channel_tch 的数据剔除 BCCH 后赋值给 TCH[]
            int j = 0;
            for (int i = 0; i < channel_tch.length; i++) {
                if (excelContentCdd.containsKey(strings[0]) && channel_tch[i].equals(excelContentCdd.get(strings[0])[6])){
                    ContainsBCCH = "TRUE";
                    //TCH += tab + "N/A";
                }else{
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
                    strings[6] + tab +
                    "Downlink and Uplink" + tab +
                    "Downlink and Uplink" + tab +
                    "N/A" + tab +
                    subcellTxPower + tab +
                    strings[8] + tab +
                    strings[7] + tab +
                    "1" + tab +
                    "Normal" +
                    tchFrequency + MAIO;


            for (String[] coordiante : coordiantes) {
                if (coordiante[0].equals(strings[0])){
                    channelGroupslist.add(channelGroupLine);
                }
            }
        }
        System.out.println("createChannels-end");
        return channelGroupslist;
    }

    /**
     * 网优之家 现网cdd_Nrel 生成 Handovers 文件
     * @param coordiantes
     * @return
     */
    public ArrayList createHandovers(HashMap<enumClass.excelType, HashMap> excelContent, String[][] coordiantes){
        System.out.println("createHandovers");
        ArrayList handoverslist = new ArrayList();

        //String handoverHead = "Serving Sector\tTarget Sector\tHysteresis\tSector Threshold";

        HashMap<String,String[]> excelContentHandover = excelContent.get(handover);

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

    /**
     * 通过 ExcelContent 生成 forte 文件
     * @param excelContent
     * @param coordiantes
     * @return
     */
    public ArrayList[][] createForteArray(HashMap<enumClass.excelType, HashMap> excelContent, String[][] coordiantes){
        System.out.println("createForteArray");
        ArrayList[][] forteArray = null;

        if (coordiantes != null) {
            // excel 分别顺序包含:sector/channelGroup/handover
            forteArray = new ArrayList[3][1];

            forteArray[0][0] = createSectors(excelContent, coordiantes);
            forteArray[1][0] = createChannels(excelContent, coordiantes);
            //forteArray[2][0] = createHandovers(excelContent, coordiantes);
            System.out.println("cdd 文件处理完毕！");
        }else {
            System.out.println("读取文件出错");
        }
        return forteArray;
    }
}
