package com.example.twj.myandroidcommunicationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

                         Token.Token ken=new Token.Token();
                         String Name=TextName.getText().toString().toUpperCase();
                        String Password=ken.encodeg(TextPassword.getText().toString());
                        Model.Token ee=  ken.GetSignToken(Name.toString(),Password.toString());
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("id", "1");
                        params.put("name", "wahaha");
                        HashMap<String,String> parames= ken.GetQueryString(params);
                        Object Obj=ServerGetPostUtil.sendGet("http://7a4c3864.ngrok.io/api/product/getproduct",parames.keySet().toString()
                                ,parames.values().toString(),Name,Password.toString(),true);
                        String Json="{\"id\":1,\"Name\":\"安慕希\",\"Cont\":10,\"Price\":58.5}";
                        Object Obj2=ServerGetPostUtil.sendPost("http://7a4c3864.ngrok.io/api/product/addProduct",Json,
                                Name ,Password.toString());
                    }
                }.start();
            }
        });
    }

    private  TextView TextName;
    private TextView TextPassword;
    private Button btnPost;
}
