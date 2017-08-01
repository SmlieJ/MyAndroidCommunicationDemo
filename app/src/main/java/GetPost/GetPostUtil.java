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

            String resultString = null;
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            ByteArrayOutputStream bos = null;


            String srcUrl = "http://localhost:35444/api/values";
            URL url = new URL(srcUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            // 请求头必须设置，参数必须正确。
            conn.setRequestProperty("Accept", "application/json,text/html;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Cache-Control", "max-age=0");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            conn.connect();

            int responseCode = conn.getResponseCode();


            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[10240];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            byte[] resultByte = bos.toByteArray();

            resultString = new String(resultByte);
            return resultString;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        } catch (ProtocolException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }

    }


    /**
     * 向指定URL发送POST方法的请求

     * @return URL所代表远程资源的响应
     */
    public static String sendPost()
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
