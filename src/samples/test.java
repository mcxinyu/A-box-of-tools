package samples;

import javax.swing.JDialog;
import javax.swing.JFrame;
public class test {
    public static void main(String[] args) {
        JFrame jframe = new JFrame("parent");
        jframe.setLocation(200, 200);
        jframe.setSize(200, 200);
        jframe.setVisible(true);
        JDialog jdlg = new JDialog(jframe, "son", true);// 最后一个参数 true 为模态，false为非模态
        jdlg.setLocation(400, 400);
        jdlg.setSize(200, 200);
        jdlg.setVisible(true);
    }
}