package View.CheckPlanView;
/**
 * Created by 跃峰 on 2015/12/8.
 * GridBagLayout 的一些组件定义、方法
 */
import java.awt.*;

public class GBC extends GridBagConstraints {
    /**
     * 初始化左上角位置
     * @param gridx 组件的横向坐标
     * @param gridy 组件的纵向坐标
     */
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * 初始化左上角位置和所占行数和列数，从0开始
     * @param gridx
     * @param gridy
     * @param gridwidth 组件的横向宽度，也就是指组件占用的列数，这与HTML的colspan类似
     * @param gridheight 组件的纵向长度，也就是指组件占用的行数，这与HTML的rowspan类似
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }


    /**
     * 对齐方式
     * @param anchor 告诉布局管理器组件在表格空间中的位置
     * @return
     */
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }


    /**
     * 是否拉伸及拉伸方向
     * @param fill 如果显示区域比组件的区域大的时候，可以用来控制组件的行为。控制组件是垂直填充VERTICAL，还是水平填充HORIZONTAL，或者两个方向一起填充BOTH
     * @return
     */
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }


    /**
     * x和y方向上的增量,如设置weightx=100，组件会随着单元格而变化，设置weightx=0时，组件大小不会发生变化。
     * @param weightx 指行的权重，告诉布局管理器如何分配额外的水平空间
     * @param weighty 指列的权重，告诉布局管理器如何分配额外的垂直空间
     * @return
     */
    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }


    /**
     * 外部填充;fill=NONE时，指定insects值是无意义
     * @param distance 指组件与表格空间四周边缘的空白区域的大小
     * @return
     */
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    /**
     * 外部填充
     * @param topBottom 上下
     * @param leftRight 左右
     * @return
     */
    public GBC setInsets(int topBottom,int leftRight) {
        this.insets = new Insets(topBottom,leftRight,topBottom,leftRight);
        return this;
    }

    /**
     * 外部填充
     * @param top
     * @param left
     * @param bottom
     * @param right
     * @return
     */
    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }


    /**
     * 内部填充
     * @param ipadx 组件间的横向间距,组件的宽度就是这个组件的最小宽度加上ipadx值
     * @param ipady 组件间的纵向间距,组件的高度就是这个组件的最小高度加上ipady值
     * @return
     */
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}