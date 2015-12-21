package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.view.LayoutInflater;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

/**美味详情中间图片布局
 * @author ZL
 *
 */
public class ItemDetailCenterWebView extends LinearLayout {
	private WebView webView_img;//显示图片的webview
	private String image_datas;// 美味详情中间图片的地址数据
	
	public ItemDetailCenterWebView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.view_item_detail_center_webview, this, true);
		webView_img = (WebView)findViewById(R.id.webview_center);
	}

	public void loadData(JSONObject response){
		System.out.println(response);
		image_datas = JsonUtils.parseItemDetailCenterWebViewJson(response);
		webView_img.getSettings().setDefaultTextEncodingName("utf-8") ;
		
		//设置这个两个属性避免跳转到浏览器
		webView_img.setWebChromeClient(new WebChromeClient() {
	            @Override
	            public void onProgressChanged(WebView view, int newProgress) {
	                super.onProgressChanged(view, newProgress);
	            }

	            @Override
	            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
	                return super.onJsAlert(view, url, message, result);
	            }
	        });
		webView_img.setWebViewClient(new WebViewClient() {
	            @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	                return true;
	            }
	        });
		
		webView_img.loadData(image_datas, "text/html", "utf-8") ;
		
		
	}
}
