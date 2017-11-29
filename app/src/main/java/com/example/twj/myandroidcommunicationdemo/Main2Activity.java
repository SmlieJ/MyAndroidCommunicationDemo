package com.example.twj.myandroidcommunicationdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

import Token.ServerGetPostUtil;

public class Main2Activity extends AppCompatActivity {

    //缓存
    public static Context getAppContext() {
        return Main2Activity.context;
    }
    private static   Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextName=(TextView)findViewById(R.id.editText1) ;
        TextPassword=(TextView)findViewById(R.id.editText2) ;



        btnPost=(Button) findViewById(R.id.button1);
        btnPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        //信息机制
                        Message msg= new Message();
                        msg.what=0x123;
                        MeggShow="";
                        //缓存
                        Main2Activity.context = getApplicationContext();

                         Token.Token ken=new Token.Token();
                         String Name=TextName.getText().toString().toUpperCase();
                        String Password=ken.encodeg(TextPassword.getText().toString());
                        //登陆获取token
                        Model.Token ee=  ken.GetSignToken(Name.toString(),Password.toString());
                        String megg=ee.getData().getSignToken().toString();
                        MeggShow=megg.toString();
                        Looper.prepare();

                       /* myhandler.handleMessage(msg);
                        Looper.loop();*/

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("line", "137");
                        params.put("date", "2017-11-28");
                        HashMap<String,String> parames= ken.GetQueryString(params);
                       /* Object Obj=ServerGetPostUtil.sendGet("http://3ae65f21.ngrok.io/api/product/getproduct",parames.keySet().toString()
                                ,parames.values().toString(),Name,Password.toString(),true);*/
                        Object Obj=ServerGetPostUtil.sendGet("http://c7233309.ngrok.io/api/Line/GetTimes",parames.keySet().toString()
                                ,parames.values().toString(),Name,Password.toString(),true);
                        MeggShow=MeggShow+"Get:"+Obj.toString()+"\n\r";

                       /* Looper.prepare();
                        myhandler.handleMessage(msg);
                        Looper.loop();*/

                   /*     String Json="{\"id\":1,\"Name\":\"安慕希\",\"Cont\":10,\"Price\":58.5}";*/
                        String Json="{\"line\":137,\"date\":\"2017-11-28\"}";
/*                        Object Obj2=ServerGetPostUtil.sendPost("http://3ae65f21.ngrok.io/api/product/addProduct",Json,
                                Name ,Password.toString());*/
                        Object Obj2=ServerGetPostUtil.sendPost("http://c7233309.ngrok.io/api/Line/PostTimes",Json,
                                Name ,Password.toString());
                        MeggShow=MeggShow+"Post:"+Obj2.toString()+"\n\r";
                       /* Looper.prepare();*/
                        myhandler.handleMessage(msg);
                        Looper.loop();
                    }
                }.start();
            }
        });
    }

    final Handler myhandler = new Handler()  {
        @Override
        public void handleMessage(Message msg) {
           if(msg.what==0x123)
           {
               Toast.makeText(Main2Activity.this,MeggShow.toString(),Toast.LENGTH_LONG).show();
           }
                //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
             /*   AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(Main2Activity.this).
                        setTitle("系统提示").setMessage(MeggShow.toString());
                setPositiveButton(alertdialogbuilder).
                        create().
                        show();*/
        }        ;
    };


    private  String MeggShow;
    private  TextView TextName;
    private TextView TextPassword;
    private Button btnPost;
}
