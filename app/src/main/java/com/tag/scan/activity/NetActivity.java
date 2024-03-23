package com.tag.scan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tag.scan.R;
import com.tag.scan.SpUtil;
import com.tag.scan.URLUtil;


public class NetActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView title, right;
    private LinearLayout back;
    TextView path;
    Button set;
    Button reset;

    /**
     * 网络设置
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net);
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("网络设置");
        back = (LinearLayout) findViewById(R.id.ll_back_b);
        right = (TextView) findViewById(R.id.tv_right);
        back.setOnClickListener(this);

        path = (TextView)findViewById(R.id.path);
        set = (Button)findViewById(R.id.set);
        reset = (Button)findViewById(R.id.reset);
        path.setText(URLUtil.SERVER_URL);

        set.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set:
                SpUtil.getInstace().save("url", path.getText().toString().trim());
                URLUtil.SERVER_URL = path.getText().toString();
                Toast.makeText(this, "设置服务器地址成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.reset:
                SpUtil.getInstace().save("url",  URLUtil.SERVER);
                URLUtil.SERVER_URL = URLUtil.SERVER;
                path.setText(URLUtil.SERVER);
                Toast.makeText(this, "恢复默认成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_back_b:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
