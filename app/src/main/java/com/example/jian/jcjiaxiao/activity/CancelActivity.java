package com.example.jian.jcjiaxiao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jian.jcjiaxiao.R;

import static com.example.jian.jcjiaxiao.activity.CoureseActivity.doit;

public class CancelActivity extends AppCompatActivity implements View.OnClickListener {
Button bt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.bt_cancel){

            doit=2;
            Toast.makeText(this,"取消成功",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent();
            intent.setClass(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }
}
