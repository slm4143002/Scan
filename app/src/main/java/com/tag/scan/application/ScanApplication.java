package com.tag.scan.application;

import android.app.Application;
import android.content.Context;
import android.widget.PopupWindow;

import com.example.iscandemo.iScanInterface;
import com.tag.scan.WaitScreen;
import com.tag.scan.net.OkHttpManager;


public class ScanApplication extends Application {

	private static Context mContext;

	public static OkHttpManager okHttpManager;

	//iScan接口
	public static iScanInterface miScanInterface;

	//iscan 默认扫描结果广播
	public static final String RES_ACTION = "android.intent.action.SCANRESULT";

	public static final String RES_LABEL = "value";

	PopupWindow popupWindow;
	public static WaitScreen waitScreen;

	public void onCreate() {
		super.onCreate();
		mContext = this;

		//创建iScanInterface实例化对象
		miScanInterface = new iScanInterface(this);

		okHttpManager = new OkHttpManager();
		ScanApplication.getiScanInterface().setOutputMode(0);

		/* 配置扫描成功是否播放声音 */
//		getiScanInterface().enablePlayBeep(true);
//		/* 是否启用扫描按键 */
//		ScanApplication.getiScanInterface().lockScanKey(true);

	}

	public static OkHttpManager getOkHttpManager(){
		return okHttpManager;
	}

	// 获取全局上下文
	public static Context getContext() {
		return mContext;
	}

	// 获取全局上下文
	public static WaitScreen getWaitScreen() {
		return new WaitScreen(mContext);
	}

	public static iScanInterface getiScanInterface(){
		return miScanInterface;
	}

}
