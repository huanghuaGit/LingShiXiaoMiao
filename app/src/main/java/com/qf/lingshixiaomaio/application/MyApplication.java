package com.qf.lingshixiaomaio.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 自定义Application
 * 封装Volley下载与设置图片缓存
 * @author JoshuaJan
 *
 */
public class MyApplication extends Application {
	private static SharedPreferences share;
	private static SharedPreferences.Editor editor;
	private static ImageLoader imageLoader;
	private HashMap<String, Bitmap> bitmapContainer = new HashMap<String, Bitmap>();
	private static RequestQueue requestQueue;

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	/**
	 * 在应用程序被启动的时候调用
	 */
	@Override
	public void onCreate() {
		share  = getSharedPreferences("config", Context.MODE_PRIVATE);
		editor = share.edit();
		initVolley();
		initImageLoader();
	}
	public static void putBoolean(String key, Boolean value) {
		Log.i("---putBoolean---", key + "-----" + value);
		editor.putBoolean(key, value);
		editor.commit();
	}


	public static boolean getBoolean(String key) {
		return share.getBoolean(key, true);
	}


	private void initImageLoader() {
		imageLoader = new ImageLoader(requestQueue, imageCache);
	}

	private void initVolley() {
		requestQueue = Volley.newRequestQueue(this);
	}

	/**
	 * 下载字符串
	 * @param url
	 * @param listener
	 * @param errorListener
	 */
	public void downLoadJson(String url, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, listener, errorListener);
		requestQueue.add(jsonObjectRequest);
	}
	/**
	 * 图片缓存
	 */
	private ImageCache imageCache = new ImageCache() {

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			bitmapContainer.put(url, bitmap);
		}
		@Override
		public Bitmap getBitmap(String url) {
			return bitmapContainer.get(url);
		}
	};
	/**
	 * 下载图片请求
	 * 

	 */
	public static void setImageUrl(String url, NetworkImageView image) {
		image.setImageUrl(url, imageLoader);
	}

	/**
	 * 字符串请求连接
	 * 
	 * @param url
	 * @param succListener
	 * @param errorListener
	 */
	public static void requestString(String url,
			Response.Listener<String> succListener,
			Response.ErrorListener errorListener) {
		StringRequest stringRequest = new StringRequest(url, succListener,
				errorListener);
		requestQueue.add(stringRequest);
	}
}
