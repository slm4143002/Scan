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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tag.scan.MainActivity;
import com.tag.scan.R;
import com.tag.scan.URLUtil;
import com.tag.scan.WaitScreen;
import com.tag.scan.application.ScanApplication;
import com.tag.scan.bean.PrepareResp;
import com.tag.scan.bean.RestBatchNumber;
import com.tag.scan.net.OkHttpManager;
import com.tag.scan.net.Param;
import com.tag.scan.net.RequestResult;


public class AssembleActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView title, right;
    private LinearLayout back;
    private EditText txtScanNo;
    private TextView machineCategoryName,machineCount;
    private Button btnScanNo, btnSearchNo;
    private EditText txtScanBatchNo;
    private Button  clear,btnScanBatchNo,btnCreate;
    private static String buttonFlag = "";
    private TextView total1;
    private static Integer total = 0;
    private int count = 1;
    private TextView writeDate;
    PopupWindow popupWindow;
    private WaitScreen waitScreen;
    /**
     * 组装电子卡生成
     */

    BroadcastReceiver scanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_assemble);
        super.onCreate(savedInstanceState);

        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("组装电子卡生成");
        back = (LinearLayout) findViewById(R.id.ll_back_b);
        right = (TextView) findViewById(R.id.tv_right);
        back.setOnClickListener(this);

        txtScanBatchNo = (EditText) findViewById(R.id.txt_scan_batch_no);
//        txtScanBatchNo.requestFocus();
        txtScanNo = (EditText) findViewById(R.id.txt_scan_no);
        txtScanNo.requestFocus();

        machineCategoryName = (TextView) findViewById(R.id.machine_category_name);
        machineCount = (TextView) findViewById(R.id.machine_count);

        btnScanNo = (Button) findViewById(R.id.btn_scan_no);
        btnSearchNo = (Button) findViewById(R.id.btn_search_no);

        clear = (Button) findViewById(R.id.clear);
        btnScanBatchNo = (Button) findViewById(R.id.btn_scan_batch_no);
        btnCreate = (Button) findViewById(R.id.btn_create);

        btnScanNo.setOnClickListener(this);
        btnSearchNo.setOnClickListener(this);
        clear.setOnClickListener(this);
        btnScanBatchNo.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        total1 = (TextView) findViewById(R.id.total1);
        writeDate = (TextView) findViewById(R.id.write_date);

//        registerBroadcast();
    }

    private String toSplit(String s) {
        if (!TextUtils.isEmpty(s)) {
            return s.split("/")[0];
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_no:
                buttonFlag = "1";
                /* 开始扫描 */
                ScanApplication.getiScanInterface().scan_start();
                ScanApplication.getiScanInterface().setMultiBarEnable(true);
                ScanApplication.getiScanInterface().setOutputMode(1);
                break;
            case R.id.btn_scan_batch_no:
                buttonFlag = "2";
                /* 开始扫描 */
                ScanApplication.getiScanInterface().scan_start();
                ScanApplication.getiScanInterface().setMultiBarEnable(true);
                ScanApplication.getiScanInterface().setOutputMode(1);
                break;
            case R.id.btn_search_no:
                if (TextUtils.isEmpty(txtScanNo.getText().toString().trim())) {
                    Toast.makeText(AssembleActivity.this, "请先扫码", Toast.LENGTH_LONG).show();
                    return;
                }
                search(txtScanNo.getText().toString().trim());
                break;
            case R.id.clear:
                if (!TextUtils.isEmpty(txtScanBatchNo.getText().toString())) {
                    txtScanBatchNo.setText("");
                    return;
                }
                break;
            case R.id.btn_create:
                if (TextUtils.isEmpty(txtScanBatchNo.getText().toString().trim())) {
                    Toast.makeText(this, "请先扫码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (count > total) {
                    Toast.makeText(this, "写入失败，超过台数！", Toast.LENGTH_LONG).show();
                    return;
                }

                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                JsonObject json = null;
                jsonObject.addProperty("batchNumber", txtScanNo.getText().toString().trim());
                jsonObject.addProperty("machineCategoryName", machineCategoryName.getText().toString());
                jsonObject.addProperty("machineCount", machineCount.getText().toString());
                jsonObject.addProperty("writeDate", writeDate.getText().toString());
                if (!TextUtils.isEmpty(txtScanBatchNo.getText().toString())) {
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo.getText().toString());
//                    json.addProperty("cardCount", toSplit(total1.getText().toString()));
                    json.addProperty("cardCount", total1.getText().toString());
//                    jsonArray.add(json);
                }
                jsonObject.add("restCardInfo", json);
                create(jsonObject);
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
                    if ("1".equals(buttonFlag)) {
                        txtScanNo.setText(scan_data);
                    } else if ("2".equals(buttonFlag)) {
                        txtScanBatchNo.setText(scan_data);
                    }
                }
            }
        }
    }

    public void search(String num) {
        maskShow();
        OkHttpManager httpManager = ScanApplication.getOkHttpManager();
        httpManager.requestAsyncGetEnqueue(URLUtil.SERVER_URL + URLUtil.ASSEMBLE_SEARCH, "", new RequestResult<RestBatchNumber>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onSuccessful(RestBatchNumber entity) {
                        maskDismiss();
                        txtScanBatchNo.requestFocus();

                        count= entity.getMachineNum();
                        total = entity.getMachineCount();
                        total1.setText(count+"/"+total);
                        machineCategoryName.setText(entity.getMachineCategoryName());
                        machineCount.setText(entity.getMachineCount()+"");
                        writeDate.setText(entity.getWriteDate());
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        maskDismiss();
                        Toast.makeText(AssembleActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        reset();
                    }

                }, RestBatchNumber.class, new Param("batchNumber", num));
    }

    public void create(JsonObject jsonObject) {
        maskShow();
        OkHttpManager httpManager = ScanApplication.getOkHttpManager();
        httpManager.requestAsyncPostJson(URLUtil.SERVER_URL + URLUtil.ASSEMBLE_CREATE, new RequestResult<RestBatchNumber>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onSuccessful(RestBatchNumber entity) {
                maskDismiss();
                Toast.makeText(AssembleActivity.this, "写入成功！", Toast.LENGTH_LONG).show();

                txtScanNo.setText("");
                txtScanNo.requestFocus();
                total1.setText("0/0");
                reset();

//                count++;
//                if (count <= total) {
//                    txtScanBatchNo.setText("");
//                    total1.setText(count+"/"+total);
//                } else {
//                    txtScanNo.setText("");
//                    reset();
//                    count = 0;
//                    total1.setText(count+"/"+total);
//                }
            }

            @Override
            public void onFailure(String errorMsg) {
                maskDismiss();
                Toast.makeText(AssembleActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }

        }, RestBatchNumber.class, jsonObject);
    }

    private void reset() {
        count = 0;
        total = 0;
        machineCategoryName.setText("");
        machineCount.setText("");
        writeDate.setText("");
        txtScanBatchNo.setText("");
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
