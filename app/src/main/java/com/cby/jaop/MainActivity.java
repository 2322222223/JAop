package com.cby.jaop;

import android.Manifest;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cby.aspectj.annotation.JIntercept;
import com.cby.aspectj.annotation.JPermission;
import com.cby.aspectj.annotation.JSingleClick;
import com.cby.aspectj.annotation.JTrace;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @JTrace
    private void init() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        textView = findViewById(R.id.text);
    }


    int i = 0;

    // 默认1000ms
    @JSingleClick(3000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                i++;
                break;
            case R.id.btn2:
                toast();
                break;
            case R.id.btn3:
                login("");
                break;
            case R.id.btn4:
                login2();
                break;
        }
        textView.setText(String.valueOf(i));
    }

    // 拦截
    @JTrace
    @JIntercept(JApplication.InterceptorType.TYPE_1)
    private void toast() {
        Toast.makeText(this, "(～￣▽￣)～", Toast.LENGTH_SHORT).show();
    }

    // 权限申请+拦截
    @JPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE})
    @JIntercept(JApplication.InterceptorType.TYPE_0)
    private void login(String a) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    // 权限申请
    @JPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private void login2() {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
