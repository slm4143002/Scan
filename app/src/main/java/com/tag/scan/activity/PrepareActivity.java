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
import com.tag.scan.R;
import com.tag.scan.URLUtil;
import com.tag.scan.WaitScreen;
import com.tag.scan.application.ScanApplication;
import com.tag.scan.bean.PrepareResp;
import com.tag.scan.bean.RestBatchNumber;
import com.tag.scan.net.OkHttpManager;
import com.tag.scan.net.Param;
import com.tag.scan.net.RequestResult;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PrepareActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView title, right;
    private LinearLayout back;
    private EditText txtScanNo;
    private Button btnScanNo, btnSearchNo;
    private Button clear;
    private EditText txtScanBatchNo1,txtScanBatchNo2,txtScanBatchNo3,txtScanBatchNo4,txtScanBatchNo5,txtScanBatchNo6,txtScanBatchNo7,txtScanBatchNo8,txtScanBatchNo9,txtScanBatchNo10;
    private TextView total1,total2,total3,total4,total5,total6,total7,total8,total9,total10;
    private Button btnScanBatchNo, btnCreate;
    private TextView machineCategoryName,machineCount,carCount,writeDate;
    private Button clear1,clear2,clear3,clear4,clear5,clear6,clear7,clear8,clear9,clear10;
    private static String buttonFlag = "";
    private static Integer total = 0;
    private Set<String> object = new HashSet<>();
    PopupWindow popupWindow;
    private WaitScreen waitScreen;
    /**
     * 筹备电子卡生成
     */

    BroadcastReceiver scanReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_prepare);
        super.onCreate(savedInstanceState);

        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("筹备电子卡生成");
        back = (LinearLayout) findViewById(R.id.ll_back_b);
        right = (TextView) findViewById(R.id.tv_right);
        back.setOnClickListener(this);

        btnScanNo = (Button) findViewById(R.id.btn_scan_no);
        btnSearchNo = (Button) findViewById(R.id.btn_search_no);

        txtScanBatchNo1 = (EditText) findViewById(R.id.txt_scan_batch_no1);
//        txtScanBatchNo1.requestFocus();// TODO
        txtScanBatchNo2 = (EditText) findViewById(R.id.txt_scan_batch_no2);
//        txtScanBatchNo2.requestFocus();
        txtScanBatchNo3 = (EditText) findViewById(R.id.txt_scan_batch_no3);
//        txtScanBatchNo3.requestFocus();
        txtScanBatchNo4 = (EditText) findViewById(R.id.txt_scan_batch_no4);
//        txtScanBatchNo4.requestFocus();
        txtScanBatchNo5 = (EditText) findViewById(R.id.txt_scan_batch_no5);
//        txtScanBatchNo5.requestFocus();
        txtScanBatchNo6 = (EditText) findViewById(R.id.txt_scan_batch_no6);
//        txtScanBatchNo6.requestFocus();
        txtScanBatchNo7 = (EditText) findViewById(R.id.txt_scan_batch_no7);
//        txtScanBatchNo7.requestFocus();
        txtScanBatchNo8 = (EditText) findViewById(R.id.txt_scan_batch_no8);
//        txtScanBatchNo8.requestFocus();
        txtScanBatchNo9 = (EditText) findViewById(R.id.txt_scan_batch_no9);
//        txtScanBatchNo9.requestFocus();
        txtScanBatchNo10 = (EditText) findViewById(R.id.txt_scan_batch_no10);
//        txtScanBatchNo10.requestFocus();

        txtScanNo = (EditText) findViewById(R.id.txt_scan_no);
        // 获取焦点
        txtScanNo.requestFocus();

        total1 = (TextView) findViewById(R.id.total1);
        total2 = (TextView) findViewById(R.id.total2);
        total3 = (TextView) findViewById(R.id.total3);
        total4 = (TextView) findViewById(R.id.total4);
        total5 = (TextView) findViewById(R.id.total5);
        total6 = (TextView) findViewById(R.id.total6);
        total7 = (TextView) findViewById(R.id.total7);
        total8 = (TextView) findViewById(R.id.total8);
        total9 = (TextView) findViewById(R.id.total9);
        total10 = (TextView) findViewById(R.id.total10);

        btnScanBatchNo = (Button) findViewById(R.id.btn_scan_batch_no);

        machineCategoryName = (TextView) findViewById(R.id.machine_category_name);
        machineCount = (TextView) findViewById(R.id.machine_count);
        carCount = (TextView) findViewById(R.id.car_count);
        writeDate = (TextView) findViewById(R.id.write_date);

        clear = (Button) findViewById(R.id.clear);
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
        btnCreate = (Button) findViewById(R.id.btn_create);

        clear.setOnClickListener(this);
        btnScanNo.setOnClickListener(this);
        btnScanBatchNo.setOnClickListener(this);
        btnSearchNo.setOnClickListener(this);
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
        btnCreate.setOnClickListener(this);

//        registerBroadcast();

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
                    Toast.makeText(this, "请先扫码", Toast.LENGTH_LONG).show();
                    return;
                }
                search(txtScanNo.getText().toString().trim());
//                search("6922646107230");
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
            case R.id.btn_create:
//                if (object.size() == 0) {
//                    Toast.makeText(this, "请先扫码", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (object.size() < total) {
//                    Toast.makeText(this, "有未扫码卡片", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (object.size() > total) {
//                    Toast.makeText(this, "已经扫码完成，请生成", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                JsonObject json = null;
                jsonObject.addProperty("batchNumber", txtScanNo.getText().toString().trim());
                jsonObject.addProperty("machineCategoryName", machineCategoryName.getText().toString());
                jsonObject.addProperty("machineCount", machineCount.getText().toString());
                jsonObject.addProperty("carCount", carCount.getText().toString());
                jsonObject.addProperty("writeDate", writeDate.getText().toString());

                object = new HashSet<>();
                if (!TextUtils.isEmpty(txtScanBatchNo1.getText().toString().trim())) {
                    object.add(txtScanBatchNo1.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo1.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total1.getText().toString()));
                    json.addProperty("cardCount", total1.getText().toString().trim());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo2.getText().toString().trim())) {
                    object.add(txtScanBatchNo2.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo2.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total2.getText().toString().trim()));
                    json.addProperty("cardCount", total2.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo3.getText().toString().trim())) {
                    object.add(txtScanBatchNo3.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo3.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total3.getText().toString().trim()));
                    json.addProperty("cardCount", total3.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo4.getText().toString().trim())) {
                    object.add(txtScanBatchNo4.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo4.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total4.getText().toString().trim()));
                    json.addProperty("cardCount", total4.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo5.getText().toString().trim())) {
                    object.add(txtScanBatchNo5.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo5.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total5.getText().toString().trim()));
                    json.addProperty("cardCount", total5.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo6.getText().toString().trim())) {
                    object.add(txtScanBatchNo6.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo6.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total6.getText().toString().trim()));
                    json.addProperty("cardCount", total6.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo7.getText().toString().trim())) {
                    object.add(txtScanBatchNo7.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo7.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total7.getText().toString().trim()));
                    json.addProperty("cardCount", total7.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo8.getText().toString().trim())) {
                    object.add(txtScanBatchNo8.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo8.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total8.getText().toString().trim()));
                    json.addProperty("cardCount", total8.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo9.getText().toString().trim())) {
                    object.add(txtScanBatchNo9.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo9.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total9.getText().toString().trim()));
                    json.addProperty("cardCount", total9.getText().toString());
                    jsonArray.add(json);
                }
                if (!TextUtils.isEmpty(txtScanBatchNo10.getText().toString().trim())) {
                    object.add(txtScanBatchNo10.getText().toString().trim());
                    json = new JsonObject();
                    json.addProperty("cardInfo", txtScanBatchNo10.getText().toString().trim());
//                    json.addProperty("cardCount", toSplit(total10.getText().toString().trim()));
                    json.addProperty("cardCount", total10.getText().toString());
                    jsonArray.add(json);
                }
                jsonObject.add("cardInfoList", jsonArray);

                if (object.size() == 0) {
                    Toast.makeText(this, "请先扫码", Toast.LENGTH_LONG).show();
                    return;
                }

                if (object.size() < total) {
                    Toast.makeText(this, "有重复扫码或未扫码卡片", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.e("", "=======111:"+jsonObject.toString());
                create(jsonObject);
                break;
            case R.id.clear:
                txtScanNo.setText("");
                break;
            case R.id.ll_back_b:
                finish();
                break;

        }
    }

    private void maskShow() {
        waitScreen=new WaitScreen(this);
        popupWindow=waitScreen.show();
    }

    private void maskDismiss() {
        waitScreen.dismiss();
    }

    private String toSplit(String s) {
        if (!TextUtils.isEmpty(s)) {
            return s.split("/")[0];
        }
        return "";
    }

    private void reset() {
        total = 0;
        machineCategoryName.setText("");
        machineCount.setText("");
        carCount.setText("");
        writeDate.setText("");
        total1.setText("0/0");
        total2.setText("0/0");
        total3.setText("0/0");
        total4.setText("0/0");
        total5.setText("0/0");
        total6.setText("0/0");
        total7.setText("0/0");
        total8.setText("0/0");
        total9.setText("0/0");
        total10.setText("0/0");
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
//        txtScanBatchNo1.setEnabled(false);
        txtScanBatchNo2.setEnabled(false);
        txtScanBatchNo3.setEnabled(false);
        txtScanBatchNo4.setEnabled(false);
        txtScanBatchNo5.setEnabled(false);
        txtScanBatchNo6.setEnabled(false);
        txtScanBatchNo7.setEnabled(false);
        txtScanBatchNo8.setEnabled(false);
        txtScanBatchNo9.setEnabled(false);
        txtScanBatchNo10.setEnabled(false);
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

                        machineCategoryName.setText("");
                        machineCount.setText("");
                        carCount.setText("");
                        writeDate.setText("");
                    } else if ("2".equals(buttonFlag)) {
                        if (object.size() > 0) {
                            Iterator iterator = object.iterator();
                            while (iterator.hasNext()) {
                                if (scan_data.equals(iterator.next().toString())) {
                                    Toast.makeText(PrepareActivity.this, "此码已在列表中", Toast.LENGTH_LONG).show();
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
    }

    public void search(String num) {
        maskShow();
        OkHttpManager httpManager = ScanApplication.getOkHttpManager();

        httpManager.requestAsyncGetEnqueue(URLUtil.SERVER_URL + URLUtil.PREPARE_SEARCH, "", new RequestResult<RestBatchNumber>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onSuccessful(RestBatchNumber entity) {
                        maskDismiss();
                        txtScanBatchNo1.requestFocus();

                        txtScanNo.setText(entity.getBatchNumber());
                        machineCategoryName.setText(entity.getMachineCategoryName());
                        machineCount.setText(entity.getMachineCount()+"");
                        carCount.setText(entity.getCarCount()+"");
                        writeDate.setText(entity.getWriteDate());

                        total = entity.getCarCount();
                        for (int i=1; i<=total; i++) {
                            if (i==1) {
                                total1.setText(i+"/"+total);
//                                txtScanBatchNo1.setEnabled(true);
                            } else if (i==2) {
                                total2.setText(i+"/"+total);
                                txtScanBatchNo2.setEnabled(true);
                            } else if (i==3) {
                                total3.setText(i+"/"+total);
                                txtScanBatchNo3.setEnabled(true);
                            } else if (i==4) {
                                total4.setText(i+"/"+total);
                                txtScanBatchNo4.setEnabled(true);
                            } else if (i==5) {
                                total5.setText(i+"/"+total);
                                txtScanBatchNo5.setEnabled(true);
                            } else if (i==6) {
                                total6.setText(i+"/"+total);
                                txtScanBatchNo6.setEnabled(true);
                            } else if (i==7) {
                                total7.setText(i+"/"+total);
                                txtScanBatchNo7.setEnabled(true);
                            } else if (i==8) {
                                total8.setText(i+"/"+total);
                                txtScanBatchNo8.setEnabled(true);
                            } else if (i==9) {
                                total9.setText(i+"/"+total);
                                txtScanBatchNo9.setEnabled(true);
                            } else if (i==10) {
                                total10.setText(i+"/"+total);
                                txtScanBatchNo10.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        maskDismiss();
                        Toast.makeText(PrepareActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        reset();
                    }

                }, RestBatchNumber.class, new Param("batchNumber", num));
    }

    public void create(JsonObject jsonObject) {
        maskShow();
        OkHttpManager httpManager = ScanApplication.getOkHttpManager();

        httpManager.requestAsyncPostJson(URLUtil.SERVER_URL + URLUtil.PREPARE_CREATE, new RequestResult<PrepareResp>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onSuccessful(PrepareResp entity) {
                maskDismiss();
                Toast.makeText(PrepareActivity.this, "写入成功！", Toast.LENGTH_LONG).show();
                txtScanNo.setText("");
                reset();
            }

            @Override
            public void onFailure(String errorMsg) {
                maskDismiss();
                Toast.makeText(PrepareActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }

        }, PrepareResp.class, jsonObject);
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

}
