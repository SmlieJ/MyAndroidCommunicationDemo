package Socket;

/**
 * Created by Twj on 2017/6/3.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;



public class SocketClinet implements Runnable  {
    private  Socket s;
    private Handler handler;
    public  Handler revHandler;
    BufferedReader br=null;
    OutputStream os=null;
    public SocketClinet(Handler handler)
    {
        this.handler=handler;
    }
    public  void run()
    {

        try {
            s=new Socket("192.168.1.117",65258);
            br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            os=s.getOutputStream();
            new Thread()
            {
                @Override
                public void run()
                {
                    String content =null;
                    try{
                        while((content=br.readLine())!=null)
                        {
                            // 每当读到来自服务器的数据之后，发送消息通知程序界面显示该数据
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = content;
                            handler.sendMessage(msg);
                        }
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }.start();
            Looper.prepare();
            // 创建revHandler对象
            revHandler = new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    // 接收到UI线程中用户输入的数据
                    if (msg.what == 0x345)
                    {
                        // 将用户在文本框内输入的内容写入网络
                        try
                        {
                            os.write((msg.obj.toString() + "\r\n")
                                    .getBytes("utf-8"));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            };
            // 启动Looper
            Looper.loop();
        }
        catch (SocketTimeoutException e1)
        {
            System.out.println("网络连接超时！！");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
