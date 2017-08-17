package com.example.twj.myandroidcommunicationdemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.HashMap;

import GetPost.GetPostUtil;
import Socket.SocketClinet;
import Token.ServerGetPostUtil;

public class MainActivity extends AppCompatActivity {

    Handler handler =new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what==0x123)
            {
                show.setText(response.toString()+"\n"+msg.toString());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show =(TextView) findViewById(R.id.TextViewShow);
        btnJump=(Button) findViewById(R.id.btnJump);
        //Socket 需要用到的
     /*   input =(EditText) findViewById(R.id.input);
        send =(Button) findViewById(R.id.send);*/
        //Activity Jump
        btnJump.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。

                //page1为先前已添加的类，并已在AndroidManifest.xml内添加活动事件(<activity android:name="page1"></activity>),在存放资源代码的文件夹下下，
                //Intent i = new Intent();
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                // i.setAction(Intent.ACTION_MAIN);
                //i.addCategory(Intent.CATEGORY_HOME);
                ////启动
                //startActivity(i);
            }
        });

        btnGet=(Button) findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        ServerGetPostUtil get = new ServerGetPostUtil();
                        show.setText(get.sendGet("","","",0,false));
                        handler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });

        btnPost=(Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                       /* GetPostUtil get = new GetPostUtil();
                        show.setText(get.sendPost());
                        handler.sendEmptyMessage(0x123);*/
                       Token.Token ken=new Token.Token();
                       Model.Token ee=  ken.GetSignToken(10);
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("ID", "1");
                        params.put("Name", "admin");
                        HashMap<String,String> parames= ken.GetQueryString(params);
                        Object Obj=ServerGetPostUtil.sendGet("http://192.168.3.26:6100/api/emp/Get",parames.keySet().toString(),parames.values().toString(),10000,true);
                        String Json="{id=1,Name=\"安慕希\",Cont=10,Price=58.5}";
                        Object Obj2=ServerGetPostUtil.sendPost("http://192.168.3.26:6100/api/emp/Post",Json,10000);
                    }
                }.start();
            }
        });

        ///Socket 按钮
        btnSocket=(Button) findViewById(R.id.btnSocket);
        btnSocket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
/*
                handler =new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        if(msg.what==0x123)
                        {
                            show.append("\n"+msg.obj.toString());
                        }
                    }
                };*/
                socketClinet = new SocketClinet(handler);
                new Thread(socketClinet).start();
                send.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            Message msg=new Message();
                            msg.what=0x345;
                            msg.obj =input.getText().toString();
                            socketClinet.revHandler.sendMessage(msg);
                            input.setText("");
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    String response;
    private Button btnJump;
    private Button btnGet;
    private Button btnPost;
    private Button btnSocket;
    private TextView show;
    private SocketClinet socketClinet;
    private EditText input;
    private Button send;
}
