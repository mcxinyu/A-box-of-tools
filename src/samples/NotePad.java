package samples;

/**
 * Created by 跃峰 on 2015/12/17.
 */
import jdk.internal.dynalink.support.TypeConverterFactory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class NotePad extends JFrame implements ActionListener{

    JTextArea jta = null;

    JMenuBar jmb = null;
    JMenu jmFile;
    JMenuItem jmiOpen,jmiSave;

    public static void main(String[] args) {
        NotePad np = new NotePad();
    }
    public NotePad(){
        jta = new JTextArea();

        jmb = new JMenuBar();

        jmFile = new JMenu("文件（F）");
        jmFile.setMnemonic('F');
        jmiOpen = new JMenuItem("打开（O）");
        jmiSave = new JMenuItem("保存（S）");

        //注册监听
        jmiOpen.addActionListener(this);
        jmiOpen.setActionCommand("open");
        jmiSave.addActionListener(this);
        jmiSave.setActionCommand("save");

        //加入
        this.setJMenuBar(jmb);
        //把jmFile加入jmb
        jmb.add(jmFile);
        //把jmiOpen、jmiSave加入jmb
        jmFile.add(jmiOpen);
        jmFile.add(jmiSave);

        this.add(jta);
        this.setTitle("NotePad");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent a) {
        if (a.getActionCommand().equals("open")){
            //创建文件选择组件
            JFileChooser jfcOpenFile = new JFileChooser();
            jfcOpenFile.setDialogTitle("请选择文件");
            jfcOpenFile.showOpenDialog(null);
            jfcOpenFile.setVisible(true);

            //得到文件路径
            String file = jfcOpenFile.getSelectedFile().getPath();
            System.out.println("读取文件：" + file);

            //读取文件-字符流BufferedReader
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                //将文件信息放入JTextArea中
                String s = "";
                String allContent = "";
                while ((s = br.readLine())!=null){
                    allContent += s + "\r\n";
                }
                jta.setText(allContent);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    fr.close();
                    br.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else if (a.getActionCommand().equals("save")){
            //创建文件保存组件
            JFileChooser jfcSaveFile = new JFileChooser();
            jfcSaveFile.setDialogTitle("保存文件");
            jfcSaveFile.showSaveDialog(null);
            jfcSaveFile.setVisible(true);

            //获取保存路径
            String file = jfcSaveFile.getSelectedFile().getPath();
            System.out.println("保存到：" + file);

            //写入文件到指定位置
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);

                bw.write(this.jta.getText());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    bw.close();
                    fw.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
