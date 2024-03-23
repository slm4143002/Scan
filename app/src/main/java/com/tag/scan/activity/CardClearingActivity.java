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
import com.tag.scan.net.OkHttpManager;
import com.tag.scan.net.Param;
import com.tag.scan.net.RequestResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class CardClearingActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView title, right;
    private LinearLayout back;
    private EditText txtScanBatchNo1,txtScanBatchNo2,txtScanBatchNo3,txtScanBatchNo4,txtScanBatchNo5,txtScanBatchNo6,txtScanBatchNo7,txtScanBatchNo8,txtScanBatchNo9,txtScanBatchNo10;
    private Button btnScanBatchNo, btnClearAll;
    private Button clear1,clear2,clear3,clear4,clear5,clear6,clear7,clear8,clear9,clear10;
    private List<String> object = new ArrayList<>();
    PopupWindow popupWindow;
    private WaitScreen waitScreen;
    /**
     * 电子卡片清除
     */

    BroadcastReceiver scanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_clearing);
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("电子卡片清除");
        back = (LinearLayout) findViewById(R.id.ll_back_b);
        right = (TextView) findViewById(R.id.tv_right);
        back.setOnClickListener(this);

        txtScanBatchNo2 = (EditText) findViewById(R.id.txt_scan_batch_no2);
        txtScanBatchNo2.requestFocus();
        txtScanBatchNo3 = (EditText) findViewById(R.id.txt_scan_batch_no3);
        txtScanBatchNo3.requestFocus();
        txtScanBatchNo4 = (EditText) findViewById(R.id.txt_scan_batch_no4);
        txtScanBatchNo4.requestFocus();
        txtScanBatchNo5 = (EditText) findViewById(R.id.txt_scan_batch_no5);
        txtScanBatchNo5.requestFocus();
        txtScanBatchNo6 = (EditText) findViewById(R.id.txt_scan_batch_no6);
        txtScanBatchNo6.requestFocus();
        txtScanBatchNo7 = (EditText) findViewById(R.id.txt_scan_batch_no7);
        txtScanBatchNo7.requestFocus();
        txtScanBatchNo8 = (EditText) findViewById(R.id.txt_scan_batch_no8);
        txtScanBatchNo8.requestFocus();
        txtScanBatchNo9 = (EditText) findViewById(R.id.txt_scan_batch_no9);
        txtScanBatchNo9.requestFocus();
        txtScanBatchNo10 = (EditText) findViewById(R.id.txt_scan_batch_no10);
        txtScanBatchNo10.requestFocus();
        txtScanBatchNo1 = (EditText) findViewById(R.id.txt_scan_batch_no1);
        txtScanBatchNo1.requestFocus();

        clear1 = (Button) findViewById(R.id.clear1);
        clear2 = (Button) findViewById(R.id.clear2);
        clear3 = (Button) findViewById(R.id.clear3);
        clear4 = (Button) findViewById(R.id.clear4);
        clear5 = (Button) findViewById(R.id.clear5);
        clear6 = (Button) findViewById(R.id.clear6);
        clear7 = (Button) findViewById(R.id.clear7);
        clear8 = (Button) findViewById(R.id.clear8);
        clear9 = (Button) findViewById(R.id.clear9);
        clear10 = (Button) findViewById(R.id.clear10);

        btnScanBatchNo = (Button) findViewById(R.id.btn_scan_batch_no);
        btnClearAll = (Button) findViewById(R.id.btn_clear_all);

        btnScanBatchNo.setOnClickListener(this);
        btnClearAll.setOnClickListener(this);
        clear1.setOnClickListener(this);
        clear2.setOnClickListener(this);
        clear3.setOnClickListener(this);
        clear4.setOnClickListener(this);
        clear5.setOnClickListener(this);
        clear6.setOnClickListener(this);
        clear7.setOnClickListener(this);
        clear8.setOnClickListener(this);
        clear9.setOnClickListener(this);
        clear10.setOnClickListener(this);

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
            case R.id.clear1:
                if (!TextUtils.isEmpty(txtScanBatchNo1.getText().toString().trim())) {
                    object.remove(txtScanBatchNo1.getText().toString().trim());
                    txtScanBatchNo1.setText("");
                    return;
                }
                break;
            case R.id.clear2:
                if (!TextUtils.isEmpty(txtScanBatchNo2.getText().toString().trim())) {
                    object.remove(txtScanBatchNo2.getText().toString().trim());
                    txtScanBatchNo2.setText("");
                    return;
                }
                break;
            case R.id.clear3:
                if (!TextUtils.isEmpty(txtScanBatchNo3.getText().toString().trim())) {
                    object.remove(txtScanBatchNo3.getText().toString().trim());
                    txtScanBatchNo3.setText("");
                    return;
                }
                break;
            case R.id.clear4:
                if (!TextUtils.isEmpty(txtScanBatchNo4.getText().toString().trim())) {
                    object.remove(txtScanBatchNo4.getText().toString().trim());
                    txtScanBatchNo4.setText("");
                    return;
                }
                break;
            case R.id.clear5:
                if (!TextUtils.isEmpty(txtScanBatchNo5.getText().toString().trim())) {
                    object.remove(txtScanBatchNo5.getText().toString().trim());
                    txtScanBatchNo5.setText("");
                    return;
                }
                break;
            case R.id.clear6:
                if (!TextUtils.isEmpty(txtScanBatchNo6.getText().toString().trim())) {
                    object.remove(txtScanBatchNo6.getText().toString().trim());
                    txtScanBatchNo6.setText("");
                    return;
                }
                break;
            case R.id.clear7:
                if (!TextUtils.isEmpty(txtScanBatchNo7.getText().toString().trim())) {
                    object.remove(txtScanBatchNo7.getText().toString().trim());
                    txtScanBatchNo7.setText("");
                    return;
                }
                break;
            case R.id.clear8:
                if (!TextUtils.isEmpty(txtScanBatchNo8.getText().toString().trim())) {
                    object.remove(txtScanBatchNo8.getText().toString().trim());
                    txtScanBatchNo8.setText("");
                    return;
                }
                break;
            case R.id.clear9:
                if (!TextUtils.isEmpty(txtScanBatchNo9.getText().toString().trim())) {
                    object.remove(txtScanBatchNo9.getText().toString().trim());
                    txtScanBatchNo9.setText("");
                    return;
                }
                break;
            case R.id.clear10:
                if (!TextUtils.isEmpty(txtScanBatchNo10.getText().toString().trim())) {
                    object.remove(txtScanBatchNo10.getText().toString().trim());
                    txtScanBatchNo10.setText("");
                    return;
                }
                break;
            case R.id.btn_clear_all:

                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                JsonObject json = null;
                object = new ArrayList<>();
                if (!TextUtils.isEmpty(txtScanBatchNo1.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo1.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo1.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo1.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo2.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo2.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo2.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo2.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo3.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo3.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo3.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo3.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo4.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo4.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo4.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo4.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo5.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo5.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo5.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo5.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo6.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo6.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo6.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo6.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo7.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo7.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo7.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo7.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo8.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo8.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo8.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo8.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo9.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo9.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo9.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo9.getText().toString().trim());
                }
                if (!TextUtils.isEmpty(txtScanBatchNo10.getText().toString().trim())) {
                    for (int i=0; i<object.size(); i++) {
                        if (txtScanBatchNo10.getText().toString().trim().equals(object.get(i).toString())) {
                            Toast.makeText(this, "有重复扫码！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    object.add(txtScanBatchNo10.getText().toString().trim());
                    jsonArray.add(txtScanBatchNo10.getText().toString().trim());
                }
                jsonObject.add("cardInfoList", jsonArray);

                if (object.size() == 0) {
                    Toast.makeText(this, "请先扫码", Toast.LENGTH_LONG).show();
                    return;
                }
                clearAll(jsonObject);
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
                    if (object.size() > 0) {
                        Iterator iterator = object.iterator();
                        while (iterator.hasNext()) {
                            if (scan_data.equals(iterator.next().toString())) {
                                Toast.makeText(CardClearingActivity.this, "此码已在列表中", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }
                    if (TextUtils.isEmpty(txtScanBatchNo1.getText().toString())) {
                        txtScanBatchNo1.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo2.getText().toString())) {
                        txtScanBatchNo2.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo3.getText().toString())) {
                        txtScanBatchNo3.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo4.getText().toString())) {
                        txtScanBatchNo4.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo5.getText().toString())) {
                        txtScanBatchNo5.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo6.getText().toString())) {
                        txtScanBatchNo6.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo7.getText().toString())) {
                        txtScanBatchNo7.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo8.getText().toString())) {
                        txtScanBatchNo8.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo9.getText().toString())) {
                        txtScanBatchNo9.setText(scan_data);
                        object.add(scan_data);
                    } else if (TextUtils.isEmpty(txtScanBatchNo10.getText().toString())) {
                        txtScanBatchNo10.setText(scan_data);
                        object.add(scan_data);
                    }
                }

            }
        }
    }

    public void clearAll(JsonObject jsonObject) {
        maskShow();
        OkHttpManager httpManager = ScanApplication.getOkHttpManager();

        httpManager.requestAsyncPostJson(URLUtil.SERVER_URL + URLUtil.CARD_CLEARING, new RequestResult<PrepareResp>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onSuccessful(PrepareResp entity) {
                maskDismiss();
                Toast.makeText(CardClearingActivity.this, "电子卡片清除成功！", Toast.LENGTH_LONG).show();
                reset();
                txtScanBatchNo1.requestFocus();
            }

            @Override
            public void onFailure(String errorMsg) {
                maskDismiss();
                Toast.makeText(CardClearingActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }

        }, PrepareResp.class, jsonObject);
    }

    private void reset() {
        txtScanBatchNo1.setText("");
        txtScanBatchNo2.setText("");
        txtScanBatchNo3.setText("");
        txtScanBatchNo4.setText("");
        txtScanBatchNo5.setText("");
        txtScanBatchNo6.setText("");
        txtScanBatchNo7.setText("");
        txtScanBatchNo8.setText("");
        txtScanBatchNo9.setText("");
        txtScanBatchNo10.setText("");
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
