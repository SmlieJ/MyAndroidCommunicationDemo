package Token;

import android.text.format.DateUtils;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Random;

import java.util.Calendar;
import java.security.MessageDigest;
import Model.StatusCodeEnum;

/**
 * Created by TOSHIBA on 2017/8/6.
 */

public class Token {




    public static   Model.Token  GetSignToken(String Name,String password)
    {
        String tokenApi="http://59.39.64.214:8011/api/Service/GetToken";
        String staffName=Name;
        HashMap<String,String>  parames=new HashMap<String,String>();
        parames.put("staffName",String.valueOf( staffName));
        parames.put("Password",String.valueOf( password));
        HashMap<String,String> parameters=GetQueryString(parames);
        String token=ServerGetPostUtil.sendGet(tokenApi,parameters.keySet().toString(),parameters.values().toString(),staffName,password,false);
        List<Model.Token> bList=new ArrayList<Model.Token>();
        try
        {
            JSONObject t=new JSONObject(token);
            JSONObject t1=new JSONObject(t.getString("Data"));

            Model.Token token1=new Model.Token();
            Model.Token.DataBean databean=new Model.Token.DataBean();

            databean.setExpireTime(t1.getString("ExpireTime"));
            databean.setSignToken(t1.getString("SignToken"));
            databean.setStaffId(Integer.parseInt(t1.getString("StaffId")));

            token1.setInfo(t.getString("Info"));
            token1.setStatusCode(Integer.parseInt(t.getString("StatusCode")));
            token1.setData(databean);
            return token1;
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static HashMap<String,String> GetQueryString(HashMap<String,String> parames)
    {
        Map map=parames;
        Iterator iter=map.entrySet().iterator();
        StringBuilder query=new StringBuilder("");
        StringBuilder queryStr=new StringBuilder("");
        if (parames.size()==0)
            return new HashMap<String,String>();
        while(iter.hasNext())
        {
            Map.Entry entry=(Map.Entry)iter.next();
            String key=entry.getKey().toString();
            String val=entry.getValue().toString();
            if(!key.isEmpty())
            {
                query.append(key).append(val);
                queryStr.append("&").append(key).append("=").append(val);
            }
        }
        HashMap<String,String> map1=new HashMap<String,String>();
        map1.put(query.toString(),queryStr.toString().substring(1,queryStr.length()));
        return map1;
    }

    public  static  String GetTimeStamp()
    {
        Calendar datetime =Calendar.getInstance();
        datetime.setTime(new java.util.Date());
        Calendar calendar=Calendar.getInstance();
        calendar.set(1970,1,1,0,0,0);
        String betwwen=String.valueOf(datetime.getTime().getTime()-calendar.getTime().getTime());
        return  betwwen;
    }

    public static  String GetRandom()
    {
        Random rd=new Random(Long.valueOf(String.valueOf(new java.util.Date().getTime())));
        int num=rd.nextInt(Integer.MAX_VALUE);
        return Integer.toString(num);
    }

    public static String GetSignature(String timeStamp,String nonce,String staffName,String password,String date)
    {
        Model.Token token=null;
        Model.Token resultMsg= GetSignToken(staffName,password);
        if(resultMsg!=null)
        {
            if(resultMsg.getStatusCode()!= StatusCodeEnum.Success.tostring())
            {
                try {
                    throw new Exception(resultMsg.getData().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                token=resultMsg;
            }
        }
        else
        {
            try {
                throw new Exception("token为null，员工名为：" +staffName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String signStr=timeStamp+nonce+  token.getData().getSignToken()+staffName+date;
        String[] sin=signStr.toUpperCase().split("");
        Arrays.sort(sin,String.CASE_INSENSITIVE_ORDER);
        String ss="";
        for(String str:sin)
        {
            ss =ss +str;
        }
        return encode(ss).toUpperCase();
    }

    ///MD5加密 非静态
    public  String encodeg(String str) {
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes("UTF-8"));
            for (byte b : digest) {
                int x = b & 0xff;  // 将byte转换2位的16进制int类型数
                String s = Integer.toHexString(x); // 将一个int类型的数转为2位的十六进制数
                if (s.length() == 1) {
                    s = "0" + s;
                }
                buffer.append(s);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    ///MD5加密
    public static String encode(String str) {
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes("UTF-8"));
            for (byte b : digest) {
                int x = b & 0xff;  // 将byte转换2位的16进制int类型数
                String s = Integer.toHexString(x); // 将一个int类型的数转为2位的十六进制数
                if (s.length() == 1) {
                    s = "0" + s;
                }
                buffer.append(s);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
