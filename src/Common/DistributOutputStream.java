package Common;

/**
 * Created by 跃峰 on 2016/3/6.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class DistributOutputStream extends OutputStream {
    private OutputStream[] outputStreams = null;

    public DistributOutputStream(){
        try {
            // 创建一个文件流
            FileOutputStream fos = new FileOutputStream("console.log",true);
            // 先保存原来的标准输出
            OutputStream cos = System.out;
            // 创建一个分发流分发到文件流和标准输出
            DistributOutputStream osc = new DistributOutputStream(new OutputStream[] { fos, cos });
            // 分发流的打印方式
            PrintStream ps = new PrintStream(osc);
            // 设置到Err和Out
            System.setErr(ps);
            System.setOut(ps);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public DistributOutputStream(OutputStream[] outputStreams) {
        super();
        this.outputStreams = outputStreams;
    }

    @Override
    public void write(int v) throws IOException {
        for (OutputStream os : outputStreams) {
            try {
                os.write(v);
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void close() throws IOException {
        for (OutputStream os : outputStreams) {
            try {
                os.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void flush() throws IOException {
        for (OutputStream os : outputStreams) {
            try {
                os.flush();
            } catch (IOException e) {
            }
        }
    }
}
