package Token;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.Date;
import java.util.Calendar;
/**
 * Created by TOSHIBA on 2017/8/6.
 */

public class Token {



    public static void GetSignToken(int ID)
    {
        String tokenApi="http://localhost:7250/api/Service/GetToken";
        int staffId=10000;
        HashMap<String,String>  parames=new HashMap<String,String>();
        parames.put("staffId",String.valueOf( staffId));
        HashMap<String,String> parameters=GetQuerString(parames);
        String token=ServerGetPostUtil.sendGet(tokenApi,parameters.keySet().toString(),parameters.values().toString(),staffId,false);
        List<Model.Token> bList=new ArrayList<Model.Token>();
        try
        {
            Object t=new JSONObject(token);
            Model.Token token1;
      /*      token1.setData();*/
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }

    public static HashMap<String,String> GetQuerString(HashMap<String,String> parames)
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
            if(key.isEmpty())
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

    public static String GetSignaturn(String timeStamp,String nonce,int staffID,String date)
    {
          Token token=null;
     /*    Object resultMsg=GetSignToken(staffID);*/
     return "";
    }


}
