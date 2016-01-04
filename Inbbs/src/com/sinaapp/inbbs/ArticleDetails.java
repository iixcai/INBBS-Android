package com.sinaapp.inbbs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * @创建者 xcai
 * @创建时间 2016-1-3 上午12:02:08
 * @描述 通过内置浏览器，访问文章
 * 
 * @版本 $Rev$
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 
 */
public class ArticleDetails extends Activity {
	private WebView mWebView;
	private String mArticleUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		initWebView();
	}

	/**
	 * 初始化数据
	 */
	private void init() {
		requestWindowFeature(Window.FEATURE_PROGRESS);// 让进度条显示在标题栏上
		setContentView(R.layout.activity_article_details);
		mWebView = (WebView) findViewById(R.id.wv_article_details);
		// 接收上一个Activity传递过来的数据：即文章对应的url
		mArticleUrl = getIntent().getStringExtra("url");
	}

	/**
	 * 内置浏览器
	 */
	private void initWebView() {
		WebSettings settings = mWebView.getSettings();
		settings.setUseWideViewPort(true); // 自适应
		settings.setBuiltInZoomControls(true); // 设置缩放
		settings.setLoadWithOverviewMode(true); // 设置双击放大
		settings.setJavaScriptEnabled(true); // 设置支持js

		// 监听webview的加载进度
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// 设置进度，当完全加载完成后，进度条消失
				ArticleDetails.this.setProgress(newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}
		});

		/**
		 * 使用内置浏览器访问，使其不调用系统浏览器访问
		 */
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});

		// 加载url
		mWebView.loadUrl(mArticleUrl);
	}
}
