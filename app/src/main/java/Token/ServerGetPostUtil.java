package Token;

import android.content.SharedPreferences;
import android.util.Xml;

import com.example.twj.myandroidcommunicationdemo.Main2Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import static Token.Token.GetRandom;
import static Token.Token.GetSignature;
import static Token.Token.GetTimeStamp;

/**
 * Created by TOSHIBA on 2017/8/7.
 */

public class ServerGetPostUtil {
    public static String sendGet(String webApi,String query,String queryStr,String staffName,String Password,boolean sign) {
        query=query.replace("[","").replace("]","");
        queryStr=queryStr.replace("[","").replace("]","");
        if (!sign)
           queryStr="staffID=10000";
        String  result="";
        BufferedReader in=null;
        try {
            String timeStamp = GetTimeStamp();
/*            String nonce= GetRandom();*/
            String nonce = GetRandom();
            //TODO 这里的ip 地址一定不能使localhost 一定要是电脑的或者是正式ip地址.
            result = webApi + "?" + queryStr;
            URL realUrl = new URL(result);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("timestamp", timeStamp);
            conn.setRequestProperty("nonce", nonce);
            if (sign) {
                conn.setRequestProperty("signature", GetSignature(timeStamp, nonce, staffName, Password, query));
                SharedPreferences preferences= Main2Activity.getAppContext().getSharedPreferences("user", 0);
                String tokenValue=preferences.getString("SignToken","none");
                conn.setRequestProperty("token",tokenValue.toString());
            }
            else
            {
                conn.setRequestProperty("staffName", String.valueOf(staffName));
                conn.setRequestProperty("Password", Password);
            }
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoInput(true);
            conn.connect();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result = line;
            }

        }
        catch(Exception e)
        {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return  result;
    }


    /**
     * 向指定URL发送POST方法的请求

     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url,String data,String staffName,String password) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        byte[] requestStringBytes = data.getBytes();
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            String timeStamp = GetTimeStamp();
            String nonce = GetRandom();
            conn.setRequestProperty("staffName",String.valueOf(staffName));
            conn.setRequestProperty("password",String.valueOf(password));
            conn.setRequestProperty("timestamp",timeStamp);
            conn.setRequestProperty("nonce",nonce);
            conn.setRequestProperty("signature", GetSignature(timeStamp, nonce, staffName,password, data));

            SharedPreferences preferences= Main2Activity.getAppContext().getSharedPreferences("user", 0);
            String tokenValue=preferences.getString("SignToken","none");
            conn.setRequestProperty("token",tokenValue.toString());

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");


            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream());
            // 发送请求参数
            out.write(data);  //②
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null)
            {
                result =  line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
