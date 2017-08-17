package Token;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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



    public static   Model.Token  GetSignToken(int ID)
    {
        String tokenApi="http://192.168.3.26:6100/api/Service/GetToken";
        int staffId=10000;
        HashMap<String,String>  parames=new HashMap<String,String>();
        parames.put("staffId",String.valueOf( staffId));
        HashMap<String,String> parameters=GetQueryString(parames);
        String token=ServerGetPostUtil.sendGet(tokenApi,parameters.keySet().toString(),parameters.values().toString(),staffId,false);
        List<Model.Token> bList=new ArrayList<Model.Token>();
        try
        {
            JSONObject t=new JSONObject(token);
            JSONObject t1=new JSONObject(t.getString("Date"));

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
        map1.put(query.toString(),queryStr.toString().substring(1,queryStr.length()-1));
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
        Random rd=new Random();
        int num=rd.nextInt(65535);
        return Integer.toString(num);
    }

    public static String GetSignature(String timeStamp,String nonce,int staffID,String date)
    {
        Model.Token token=null;
        Model.Token resultMsg= GetSignToken(staffID);
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
                throw new Exception("token为null，员工编号为：" +staffID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String signStr=timeStamp+nonce+staffID+  token.getData().getSignToken()+date;
        return encode(signStr);
    }

    public static String encode(String str) {
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
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
        }
        return buffer.toString();
    }
}
