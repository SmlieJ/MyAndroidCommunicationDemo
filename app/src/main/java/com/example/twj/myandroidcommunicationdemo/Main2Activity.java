package com.example.twj.myandroidcommunicationdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

import Token.ServerGetPostUtil;

public class Main2Activity extends AppCompatActivity {

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
                       // GetPostUtil get = new GetPostUtil();
                       // show.setText(get.sendPost());
                       // handler.sendEmptyMessage(0x123);
                        Message msg= new Message();
                        msg.what=0x123;

                         Token.Token ken=new Token.Token();
                         String Name=TextName.getText().toString().toUpperCase();
                        String Password=ken.encodeg(TextPassword.getText().toString());
                        Model.Token ee=  ken.GetSignToken(Name.toString(),Password.toString());
                        String megg=ee.getData().getSignToken().toString();
                        MeggShow=megg.toString();
                        Looper.prepare();
                       /* myhandler.handleMessage(msg);
                        Looper.loop();*/

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("id", "1");
                        params.put("name", "wahaha");
                        HashMap<String,String> parames= ken.GetQueryString(params);
                        Object Obj=ServerGetPostUtil.sendGet("http://59.39.64.214:8011/api/product/getproduct",parames.keySet().toString()
                                ,parames.values().toString(),Name,Password.toString(),true);
                        MeggShow=MeggShow+"Get:"+Obj.toString()+"\n\r";
                       /* Looper.prepare();
                        myhandler.handleMessage(msg);
                        Looper.loop();*/

                        String Json="{\"id\":1,\"Name\":\"安慕希\",\"Cont\":10,\"Price\":58.5}";
                        Object Obj2=ServerGetPostUtil.sendPost("http://59.39.64.214:8011/api/product/addProduct",Json,
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


    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder)
    {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
    private  String MeggShow;
    private  TextView TextName;
    private TextView TextPassword;
    private Button btnPost;
}
