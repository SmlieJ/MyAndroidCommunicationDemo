package Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TOSHIBA on 2017/8/7.
 */

public class ServerGetPostUtil {
    public static String sendGet(String webApi,String query,String queryStr,int staffId,boolean sign) {
        String  result="";
        BufferedReader in=null;
        try {
             String timeStamp=Token.GetTimeStamp();
            String nonce=Token.GetRandom();
            //TODO 这里的ip 地址一定不能使localhost 一定要是电脑的或者是正式ip地址.
            result = "http://192.168.3.26:6100/api/values"+"?"+queryStr;
            URL realUrl = new URL(result);
            HttpURLConnection conn=(HttpURLConnection)realUrl.openConnection();

            conn.setRequestProperty("staffid",String.valueOf(staffId));
            conn.setRequestProperty("timestamp",timeStamp);
            conn.setRequestProperty("nonce",nonce);
        /*    conn.setRequestProperty("signaturn",);*/
            
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.connect();

            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet())
            {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += "\n" + line;
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
    public static String sendPost() {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("ID", "1");
        params.put("Name", "admin");
        try
        {
            URL realUrl = new URL("http://192.168.3.26:6100/api/values");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params);  //②
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += "\n" + line;
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