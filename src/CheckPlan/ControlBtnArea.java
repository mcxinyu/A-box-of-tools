package CheckPlan;
/**
 * Created by 跃峰 on 2015/12/26.
 *控制 上一步、下一步、完成、取消 的按钮
 */
import javax.swing.*;
import java.awt.*;


class ControlBtnArea extends JPanel {
    //控制 上一步、下一步、完成、取消 的按钮
    JButton backBtn,nextBtn,okBtn,cancelBtn;
    public ControlBtnArea(JButton backBtn, JButton nextBtn, JButton okBtn, JButton cancelBtn){
        this.backBtn = backBtn;
        this.nextBtn = backBtn;
        this.okBtn = okBtn;
        this.cancelBtn = cancelBtn;

        backBtn.setBackground(new Color(230,230,230));
        nextBtn.setBackground(new Color(230,230,230));
        okBtn.setBackground(new Color(230,230,230));
        cancelBtn.setBackground(new Color(230,230,230));

        backBtn.setFocusPainted(false);
        nextBtn.setFocusPainted(false);
        okBtn.setFocusPainted(false);
        cancelBtn.setFocusPainted(false);

        add(backBtn);
        add(nextBtn);
        add(okBtn);
        add(cancelBtn);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBackground(new Color(230,230,230));
    }
}
