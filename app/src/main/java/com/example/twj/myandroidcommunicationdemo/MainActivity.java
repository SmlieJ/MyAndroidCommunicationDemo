package com.example.twj.myandroidcommunicationdemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button) findViewById(R.id.btnJump);
        btn1.setOnClickListener(new View.OnClickListener(){
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
    }
    private Button btn1;
}
