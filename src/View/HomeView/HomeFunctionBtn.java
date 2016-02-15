package View.HomeView;
/**
 * Created by 跃峰 on 2015/12/8.
 * 主页功能按钮界面布局
 */
import Common.MyTools;

import javax.swing.*;
import java.awt.*;

public class HomeFunctionBtn extends JButton {
    JTextArea textJTA;
    JPanel btnArea;
    JButton button;
    //参数：图片路径，按钮名称，说明文本，坐标x，坐标y。
    public HomeFunctionBtn(String image,String btnName,String text,int x,int y){
        //设置按钮里面图片与标签的位置垂直居中.
        button = new JButton(btnName,new ImageIcon(image));
        button.setBackground(new Color(248,248,248));
        button.setSize(198,153);
        button.setFocusPainted(false);
        button.setFont(MyTools.fontBold18);
        button.setVerticalAlignment(SwingConstants.BOTTOM);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        //设置说明文本的内容及显示.
        textJTA= new JTextArea(text);
        textJTA.setFont(MyTools.fontPlain13);
        textJTA.setBounds(0,153,200,47);
        textJTA.setLineWrap(true);
        textJTA.setEditable(false);
        textJTA.setBackground(new Color(230,230,230));
        //添加组件
        btnArea = new JPanel(null);//new FlowLayout()??
        btnArea.add(button);
        btnArea.add(textJTA);
        this.setBorder(null);
        this.setLocation(x,y);
        this.add(btnArea);
        this.setSize(200,200);
    }
}
