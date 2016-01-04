package com.sinaapp.inbbs.utils;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 
 * @创建者 xcai
 * @创建时间 2016-1-2 下午3:31:04
 * @描述 TODO
 * 
 * @版本 $Rev$
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 TODO
 */
public class GetJsonString {
	static String jsonResult;
	public String getJsonString(String url) {
		// 连接服务器，发送请求
		HttpUtils httpConnect = new HttpUtils();
		httpConnect.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			// 连接成功，执行此方法
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 获得服务器返回的json数据
				GetJsonString.jsonResult = responseInfo.result;
				Log.e("ceshi", "jsonResult:"+jsonResult);
			}

			// 连接失败，执行此方法
			@Override
			public void onFailure(HttpException e, String msg) {
				jsonResult = "";
				// 输出连接失败消息
				Log.e("ceshi", e.toString());
			}
		});
		return GetJsonString.jsonResult;
	}

}
