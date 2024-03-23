package com.tag.scan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tag.scan.R;
import com.tag.scan.SpUtil;
import com.tag.scan.URLUtil;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    protected TextView title;

    /**
     * 筹备电子卡生成
     */
    private Button prepare,assemble,groundConnection,withstandVoltage,ut,cardClearing,netSet;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);

        String url = SpUtil.getInstace().getString("url", "");
        if (!TextUtils.isEmpty(url)) {
            URLUtil.SERVER_URL = url;
        } else {
            URLUtil.SERVER_URL = URLUtil.SERVER;
        }

        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("电子小票系统");
        linearLayout = (LinearLayout) findViewById(R.id.ll_back_b);
        linearLayout.setVisibility(View.GONE);
        prepare = (Button) findViewById(R.id.prepare);
        assemble = (Button) findViewById(R.id.assemble);
        groundConnection = (Button) findViewById(R.id.ground_connection);
        withstandVoltage = (Button) findViewById(R.id.withstand_voltage);
        ut = (Button) findViewById(R.id.ut);
        cardClearing = (Button) findViewById(R.id.card_clearing);
        netSet = (Button) findViewById(R.id.net_set);

        prepare.setOnClickListener(this);
        assemble.setOnClickListener(this);
        groundConnection.setOnClickListener(this);
        withstandVoltage.setOnClickListener(this);
        ut.setOnClickListener(this);
        cardClearing.setOnClickListener(this);
        netSet.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prepare:
                Intent intentPrepare = new Intent(this, PrepareActivity.class);
                startActivity(intentPrepare);
                break;
            case R.id.assemble:
                Intent intentAssemble = new Intent(this, AssembleActivity.class);
                startActivity(intentAssemble);
                break;
            case R.id.ground_connection:
                Intent intentGroundConnection = new Intent(this, GroundConnectionActivity.class);
                startActivity(intentGroundConnection);
                break;
            case R.id.withstand_voltage:
                Intent intentWithstandVoltage = new Intent(this, WithstandVoltageActivity.class);
                startActivity(intentWithstandVoltage);
                break;
            case R.id.ut:
                Intent intentUt = new Intent(this, UTActivity.class);
                startActivity(intentUt);
                break;
            case R.id.card_clearing:
                Intent intentCardClearing = new Intent(this, CardClearingActivity.class);
                startActivity(intentCardClearing);
                break;
            case R.id.net_set:
                Intent intentNet = new Intent(this, NetActivity.class);
                startActivity(intentNet);
                break;
        }
    }

}
