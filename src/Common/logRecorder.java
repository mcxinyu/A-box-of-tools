package Common;
/**
 * Created by 跃峰 on 2016/3/4.
 */

import View.Cdd2ForteView.Cdd2Forte;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class logRecorder {
    //private static Log log = LogFactory.getLog(logRecorder.class);
    static Logger logger = Logger.getLogger(logRecorder.class);

    public void logRecorder(){
        PropertyConfigurator.configure("./src/Common/log4j.properties");

        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        logger.fatal("fatal");
    }

    public static void main(String[] args) {
        //PropertyConfigurator.configure("./src/Common/log4j.properties");
        //log.info("start");
        logRecorder lg = new logRecorder();
        lg.logRecorder();

        Cdd2Forte c2f = new Cdd2Forte();
    }

}
