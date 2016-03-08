package Common;
/**
 * Created by 跃峰 on 2015/12/26.
 *控制 上一步、下一步、完成、取消 的按钮
 */
import javax.swing.*;
import java.awt.*;


public class ControlBtnArea extends JPanel {
    //控制 上一步、下一步、完成、取消 的按钮
    JButton btn1,btn2,btn3,btn4,btn5;
    public ControlBtnArea(JButton btn1, JButton btn2, JButton btn3, JButton btn4){
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.btn3 = btn3;
        this.btn4 = btn4;

        btn1.setBackground(new Color(230,230,230));
        btn2.setBackground(new Color(230,230,230));
        btn3.setBackground(new Color(230,230,230));
        btn4.setBackground(new Color(230,230,230));

        btn1.setFocusPainted(false);
        btn2.setFocusPainted(false);
        btn3.setFocusPainted(false);
        btn4.setFocusPainted(false);

        add(btn1);
        add(btn2);
        add(btn3);
        add(btn4);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBackground(new Color(230,230,230));
    }
    public ControlBtnArea(JButton btn1, JButton btn2, JButton btn3, JButton btn4,JButton btn5){
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.btn3 = btn3;
        this.btn4 = btn4;
        this.btn5 = btn5;

        btn1.setBackground(new Color(230,230,230));
        btn2.setBackground(new Color(230,230,230));
        btn3.setBackground(new Color(230,230,230));
        btn4.setBackground(new Color(230,230,230));
        btn5.setBackground(new Color(230,230,230));

        btn1.setFocusPainted(false);
        btn2.setFocusPainted(false);
        btn3.setFocusPainted(false);
        btn4.setFocusPainted(false);
        btn5.setFocusPainted(false);

        add(btn1);
        add(btn2);
        add(btn3);
        add(btn4);
        add(btn5);
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        setBackground(new Color(230,230,230));
    }
}
