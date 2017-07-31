package GetPost;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 import org.apache.http.HttpResponse;
 import org.apache.http.client.ClientProtocolException;
 import org.apache.http.client.HttpClient;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.impl.client.DefaultHttpClient;
 import org.apache.http.message.BasicNameValuePair;
 import org.apache.http.util.EntityUtils;
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
/**
 * Created by Twj on 2017/6/21.
 */

public class GetPostUtil {



    public static String sendGet() {
        try {
            //配置 HttpURLConnection

            //TODO 这里的ip 地址一定不能使localhost 一定要是电脑的或者是正式ip地址.
            //如果写成了localhost，那么就会报错java.net.ConnectException: localhost/127.0.0.1:8080 - Connection refused
            //URL url = new URL("http://localhost:8080/tomcat.png");
            URL realUrl = new URL("http://localhost:35444/api/values/5");
            HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            //Sets the flag indicating whether this URLConnection allows input. It cannot be set after the connection is established.
            httpURLConnection.setDoInput(true);


            //判断服务器端的响应码是不是200
            InputStream in = null;
            if(httpURLConnection.getResponseCode()==200){
                in = httpURLConnection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] arr = new byte[1024];
                int len=0;
                while((len=in.read(arr))!=-1){
                    bos.write(arr,0,len);
                }

                byte[] b = bos.toByteArray();
                String ss = new String(b,"utf-8");
                return ss;
            }
            //关闭流
            in.close();
            return "";

            //获取返回的文件
           /* InputStream in = null;
            FileOutputStream fos = null;
            if (httpURLConnection.getResponseCode() == 200) {
                in = httpURLConnection.getInputStream();
                //一定不能直接在FileOutputStream里面写文件名，需要添加路径
                //错误的写法：fos = new FileOutputStream("a.bmp");
                //下面存储到内部存储的私有的cache目录里面，注意了生成的文件名是cachea.bmp
             *//*   fos = new FileOutputStream(getCacheDir().getPath() + "a.bmp");
                byte[] arr = new byte[1024];
                int len = 0;
                //每次读取 1024个字节，如果读取返回为-1说明到了文件的末尾，结束读取
                while ((len = in.read(arr)) != -1) {
                    fos.write(arr, 0, len);
                }
                //一定要记住要关闭读取流。
                in.close();
                fos.close();*//*
                return "";
            }
            else
                return  "";*/

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
            return e.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }

    }


    /**
     * 向指定URL发送POST方法的请求
     * @param url 发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String params)
    {
        URL realUrl = null;
        try {
            //定义存储要提交的数据的Map
            //如果 仅仅是为了测试那么直接使用
            //测试的请求+++++++++++++开始+++++++++++++++
            //StringBuilder sb = new StringBuilder("username=哈哈&password=psw");
            //测试的请求++++++++++++++结束++++++++++++++

            //如果为了模拟真实的请求，那么就使用下面的
            //模拟真实的请求+++++++++++++开始+++++++++++++++
            HashMap<String,String> params1 = new HashMap<String,String>();
            params1.put("username", "哈哈");
            params1.put("password", "psw");
            //把要提交的 数据类型定义为 username=哈哈&password=psw的格式
            StringBuilder sb = new StringBuilder();
            //通过Map的遍历的到
            for(Map.Entry<String,String> en:params1.entrySet()){
                sb.append(en.getKey())
                        .append("=")
                        .append(URLEncoder.encode(en.getValue(), "utf-8"))
                        .append("&");
            }
            //删除最后一个&,注意 这里是 length-1，因为是从0开始计数的。
            sb.deleteCharAt(sb.length()-1);
            //模拟真实的请求++++++++++++++结束++++++++++++++

            realUrl = new URL("http://192.168.1.106:8080/ServerDemo/servlet/LoginServlert");
            HttpURLConnection urlConnection = (HttpURLConnection)realUrl.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);//设置可以向服务器传递数据

            //设置提交的内容的类型
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //设置提交的内容的长度
            byte[] data = sb.toString().getBytes("utf-8");//注意这里的编码utf-8
            urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

            //提交数据
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(data);
            outputStream.close();


            //判断服务器端的响应码是不是200
            InputStream in = null;
            if(urlConnection.getResponseCode()==200){
                in = urlConnection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] arr = new byte[1024];
                int len=0;
                while((len=in.read(arr))!=-1){
                    bos.write(arr,0,len);
                }

                byte[] b = bos.toByteArray();
                String ss = new String(b,"utf-8");
                return ss;
            }
            //关闭流
            in.close();
            return "";

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return  e.toString();
        }
    }
}
