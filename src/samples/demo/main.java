package samples.demo;

import java.awt.*;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

class GBC extends GridBagConstraints {
    //初始化左上角位置
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    //初始化左上角位置和所占行数和列数
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    //对齐方式
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    //是否拉伸及拉伸方向
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    //x和y方向上的增量
    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    //外部填充
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    //外填充
    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    //内填充
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}



public class main extends JFrame
{
    public static void main(String[] args)
    {
        main main = new main();
        main.addGridBagPanes();
        main.setVisible(true);
        main.pack();

    }

    private void addGridBagPanes()
    {
        //上侧的工具选择面板
        JPanel toolSelectPanel = new JPanel();
        toolSelectPanel.setBackground(Color.green);
        this.setLayout(new GridBagLayout());
        this.add(toolSelectPanel, new GBC(0, 0, 2, 1).setFill(GBC.BOTH)
                .setIpad(200, 50).setWeight(100, 0));
        //左侧的具体工具面板
        JPanel toolConcretePanel = new JPanel();
        toolConcretePanel.setBackground(Color.YELLOW);
        this.add(toolConcretePanel, new GBC(0, 1).setFill(GBC.BOTH).setIpad(70,
                90).setWeight(0, 100));
        //右侧的绘图面板
        JPanel drawPanel = new JPanel();
        drawPanel.setBackground(Color.WHITE);
        this.add(drawPanel, new GBC(1, 1).setFill(GBC.BOTH));
        //下侧的颜色选择面板
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.LIGHT_GRAY);
        this.add(colorPanel, new GBC(0, 2, 2, 1).setFill(GBC.BOTH).setIpad(200,
                50).setWeight(100, 0));
        //下侧的状态面板
        JPanel statePanel = new JPanel();
        statePanel.setBackground(Color.CYAN);
        this.add(statePanel, new GBC(0, 3, 2, 1).setFill(GBC.BOTH).setIpad(200,
                20).setWeight(100, 0));
    }

}