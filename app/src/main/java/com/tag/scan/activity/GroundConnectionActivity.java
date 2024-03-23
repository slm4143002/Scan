package com.tag.scan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.tag.scan.bean.BatchProccessResult;
import com.tag.scan.bean.PrepareResp;
import com.tag.scan.net.OkHttpManager;
import com.tag.scan.net.Param;
import com.tag.scan.net.RequestResult;


public class GroundConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView title, right;
    private LinearLayout back;
    private EditText txtScanBatchNo;
    private Button  btnScanBatchNo,btnOK,btnLo,btnUpload;
    private String checkResult = "";
    PopupWindow popupWindow;
    private WaitScreen waitScreen;
    /**
     * 接地工位
     */

    BroadcastReceiver scanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ground_connection);
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("接地工位");
        back = (LinearLayout) findViewById(R.id.ll_back_b);
        right = (TextView) findViewById(R.id.tv_right);
        back.setOnClickListener(this);

        txtScanBatchNo = (EditText) findViewById(R.id.txt_scan_batch_no);
        txtScanBatchNo.requestFocus();

        btnScanBatchNo = (Button) findViewById(R.id.btn_scan_batch_no);
        btnOK = (Button) findViewById(R.id.btn_ok);
        btnLo = (Button) findViewById(R.id.btn_lo);
        btnUpload = (Button) findViewById(R.id.btn_upload);

        btnScanBatchNo.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnLo.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

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
            case R.id.btn_ok:
                btnOK.setTextColor(getResources().getColor(R.color.red));
                btnLo.setTextColor(getResources().getColor(R.color.white));
                checkResult = "1";
                break;
            case R.id.btn_lo:
                btnOK.setTextColor(getResources().getColor(R.color.white));
                btnLo.setTextColor(getResources().getColor(R.color.red));
                checkResult = "0";
                break;
            case R.id.btn_upload:
                if (TextUtils.isEmpty(txtScanBatchNo.getText().toString().trim())) {
                    Toast.makeText(this, "请先扫码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(checkResult)) {
                    Toast.makeText(this, "请选择OK品或L/O品", Toast.LENGTH_LONG).show();
                    return;
                }
                sendUpload(txtScanBatchNo.getText().toString().trim(), checkResult); // 1：ok 0：lo
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

    public void sendUpload(String num1, String num2) {
        maskShow();
        OkHttpManager httpManager = ScanApplication.getOkHttpManager();

        httpManager.requestAsyncPost(URLUtil.SERVER_URL + URLUtil.GROUND_CONNECTION_UPLOAD, new RequestResult<BatchProccessResult>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onSuccessful(BatchProccessResult entity) {
                maskDismiss();
                Toast.makeText(GroundConnectionActivity.this, "操作成功！", Toast.LENGTH_LONG).show();
                txtScanBatchNo.setText("");
                txtScanBatchNo.requestFocus();
                btnOK.setTextColor(getResources().getColor(R.color.white));
                btnLo.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onFailure(String errorMsg) {
                maskDismiss();
                Toast.makeText(GroundConnectionActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }

        }, BatchProccessResult.class, new Param("cardInfo", num1), new Param("checkResult", num2));
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
