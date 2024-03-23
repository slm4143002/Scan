package com.tag.scan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tag.scan.MainActivity;
import com.tag.scan.R;
import com.tag.scan.URLUtil;
import com.tag.scan.WaitScreen;
import com.tag.scan.application.ScanApplication;
import com.tag.scan.bean.PrepareResp;
import com.tag.scan.net.OkHttpManager;
import com.tag.scan.net.Param;
import com.tag.scan.net.RequestResult;


public class BatchCompletionFinishActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView title, right;
    private LinearLayout back;
    private TextView txtScanBatchNo;
    private Button  btnScanBatchNo,btnFinish;
    private String batchNumber;
    PopupWindow popupWindow;
    private WaitScreen waitScreen;
    /**
     * 本批量完成信息
     */

    BroadcastReceiver scanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_batch_completion_finish);
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("本批量完成信息");
        back = (LinearLayout) findViewById(R.id.ll_back_b);
        right = (TextView) findViewById(R.id.tv_right);
        back.setOnClickListener(this);

        txtScanBatchNo = (TextView) findViewById(R.id.txt_scan_batch_no);
        btnScanBatchNo = (Button) findViewById(R.id.btn_scan_batch_no);// TODO 可能是传过来的
        btnFinish = (Button) findViewById(R.id.btn_finish);

        batchNumber = getIntent().getStringExtra("batchNumber");
        txtScanBatchNo.setText(batchNumber);

        btnScanBatchNo.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

//        registerBroadcast();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_batch_no:
                /* 开始扫描 */
                ScanApplication.getiScanInterface().scan_start();
                ScanApplication.getiScanInterface().setMultiBarEnable(true);
                ScanApplication.getiScanInterface().setOutputMode(1);
                break;
            case R.id.btn_finish:
                finish();
                break;
            case R.id.ll_back_b:
                finish();
                break;
        }
    }

    private void registerBroadcast(){
        //扫描结果广播监听注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ScanApplication.RES_ACTION);

        //注册广播接受者
        scanReceiver = new ScannerResultReceiver();
        registerReceiver(scanReceiver, intentFilter);
    }

    /**
     * 扫描结果广播接收
     */
    //*********重要
    private class ScannerResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("idata","intent.getAction()-->"+intent.getAction());//
            if (intent.getAction().equals(ScanApplication.RES_ACTION)){
                String scan_data = intent.getStringExtra(ScanApplication.RES_LABEL);
                if(scan_data!=null){
                    Log.e("idata","recv = " + scan_data);
                    //获取扫描结果
                    txtScanBatchNo.setText(scan_data);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpManager httpManager = ScanApplication.getOkHttpManager();

        /* 停止扫描 */
        ScanApplication.getiScanInterface().scan_stop();
        ScanApplication.getiScanInterface().setMultiBarEnable(false);

        //注销iScanInterface 数据回调监听
//        unregisterReceiver(scanReceiver);
    }

    private void maskShow() {
        waitScreen=new WaitScreen(this);
        popupWindow=waitScreen.show();
    }

    private void maskDismiss() {
        waitScreen.dismiss();
    }

}
