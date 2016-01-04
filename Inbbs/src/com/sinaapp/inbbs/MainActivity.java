package com.sinaapp.inbbs;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sinaapp.inbbs.bean.HomePageItemBean;
import com.sinaapp.inbbs.constant.Constants.MyURL;
import com.sinaapp.inbbs.utils.GetDateUtils;

/**
 * 
 * @创建者 xcai
 * @描述 主界面
 * @创建时间 2016-1-1 上午9:50:23
 * 
 * @版本 $Rev$
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述
 */
public class MainActivity extends Activity implements OnItemClickListener, OnClickListener {

	/** 声明上拉加载数据的ListView组件 */
	private PullToRefreshListView mUpPullLoading;

	/** 文章信息集合 */
	private List<HomePageItemBean> mArticleInfo;

	/** 设置图片的工具类对象 */
	private BitmapUtils mBitmapUtils;

	/** 文章列表的Adapter */
	private ArticleListAdapter mArticleListAdapter;

	/**	访问服务器的url	*/
	private String mUrl;

	/** 服务器返回的json数据 */
	private String mJsonResult;
	
	/** 通过文章数/10，获取页数。即可上次刷新的次数 */
	private int mPageCount = 0;

	/** 通知栏控件 */
	private TextView mNotice;

	/** 音乐播放器控件 */
	private Button mPlayMusic;
	
	/** 音乐播放器组件 */
	private MediaPlayer mMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化控件
		initView();
		// 加载公告
		initNotice(MyURL.NOTICE);
		// 加载数据
		initData(mUrl);
		// 初始化上拉加载更多监听事件
		initListener();

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		// 加载视图
		setContentView(R.layout.activity_main);
		// 获取公告栏组件
		mNotice = (TextView) findViewById(R.id.tv_notice);
		mPlayMusic = (Button) findViewById(R.id.btn_play);
		// 获取下拉刷新ListView组件
		mUpPullLoading = (PullToRefreshListView) findViewById(R.id.up_pull_loading_listview);

		mMediaPlayer = new MediaPlayer();
		mBitmapUtils = new BitmapUtils(MainActivity.this);

		// 设置仅支持上拉刷新
		mUpPullLoading.setMode(Mode.PULL_FROM_END);

		// 音乐播放器
		mPlayMusic.setOnClickListener(this);
		// 文章监听事件
		mUpPullLoading.setOnItemClickListener(this);

		// 设置首页的URL地址:http://inbbs.sinaapp.com/Home/Api/index
		mUrl = MyURL.JSON_BEAN_URL + "index";
	}

	/**
	 * 加载数据
	 */
	private void initData(String url) {
		// 连接服务器，发送请求
		HttpUtils httpConnect = new HttpUtils();
		httpConnect.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			// 连接成功，执行此方法
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				mJsonResult = responseInfo.result;
				Log.i("ceshi", mJsonResult);
				// 解析json数据，并展示数据
				display(mJsonResult);
			}

			// 连接失败，执行此方法
			@Override
			public void onFailure(HttpException e, String msg) {
				// 输出连接失败消息
				Log.e("ceshi", e.toString());
			}
		});
	}

	/**
	 * 加载公告栏数据
	 */
	private void initNotice(String url) {
		// 连接服务器，发送请求
		HttpUtils httpConnect = new HttpUtils();
		httpConnect.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			// 连接成功，执行此方法
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 获取返回的json数据
				String result = responseInfo.result;
				// 将截取json数据中的文章数
				String notice = result.substring(result.indexOf(":") + 2, result.lastIndexOf("\""));
				mNotice.setText(notice);
			}

			// 连接失败，执行此方法
			@Override
			public void onFailure(HttpException e, String msg) {
				// 输出连接失败消息
				Log.e("ceshi", e.toString());
			}
		});
	}

	/**
	 * 加载音乐
	 */
	private void initMusic(String url) {
		// 连接服务器，发送请求
		HttpUtils httpConnect = new HttpUtils();
		httpConnect.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			// 连接成功，执行此方法
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 获取返回的json数据
				String result = responseInfo.result;
				// 将截取json数据中的文章数
				String musicName = result.substring(result.indexOf(":") + 2, result.lastIndexOf("\""));
				String music = MyURL.MUSIC + musicName;
				try {
					mMediaPlayer.setDataSource(music);
					Log.e("ceshi", "ceshi-----------");

					mMediaPlayer.prepareAsync(); // 异步缓冲

					mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

						// 如果缓冲完毕，可以播放，就会调用这个方法
						@Override
						public void onPrepared(MediaPlayer mp) {
							Log.e("ceshi", "缓存完毕");
							mMediaPlayer.start();
						}
					});

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			// 连接失败，执行此方法
			@Override
			public void onFailure(HttpException e, String msg) {
				// 输出连接失败消息
				Log.e("ceshi", e.toString());
			}
		});
	}

	/**
	 * 初始化监听事件
	 */
	private void initListener() {
		HttpUtils httpUtils = new HttpUtils();
		String url = MyURL.ARTICLE_COUNT;

		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// 获取返回的json数据
				String result = responseInfo.result;
				// 将截取json数据中的文章数
				String articleCount = result.substring(result.indexOf(":") + 2, result.lastIndexOf("\""));
				// 通过文章数/10，获取页数。即可上次刷新的次数
				mPageCount = Integer.parseInt(articleCount) / 10;
			}

			@Override
			public void onFailure(HttpException error, String msg) {

			}
		});
		// 上拉加载更多
		mUpPullLoading.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				if (mPageCount > 0) {
					if (refreshView.isShownFooter()) {
						// 判断尾布局是否可见，如果可见执行上拉加载更多
						// 设置尾布局样式文字
						mUpPullLoading.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
						mUpPullLoading.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
						mUpPullLoading.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
						mPageCount--;
						loadingMoreArticle(MyURL.PAGE_BEAN_URL + 2);
					}
				} else {
					// 隐藏上拉提示
					mUpPullLoading.postDelayed(new Runnable() {
						@Override
						public void run() {
							mUpPullLoading.onRefreshComplete();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(MainActivity.this, "哇塞，你把全部文章的加载完了耶^_^", Toast.LENGTH_SHORT).show();
								}
							});
						}
					}, 1000);
				}
			}
		});
	}

	/**
	 * 上拉加载更多的任务
	 */
	private void loadingMoreArticle(final String url) {
		// 创建一个任务，用于加载更多
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
					HttpUtils httpConnect = new HttpUtils();
					httpConnect.send(HttpMethod.GET, url, new RequestCallBack<String>() {
						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String result = responseInfo.result;
							Gson gson = new Gson();

							List<HomePageItemBean> homePageItemBean = gson.fromJson(result,
									new TypeToken<List<HomePageItemBean>>() {
									}.getType());

							mArticleInfo.addAll(homePageItemBean);
							mArticleListAdapter.notifyDataSetChanged();

							/**
							 * 在子线程中更新UI，提示加载成功
							 */
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
								}
							});
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
								}
							});
						}
					});

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				// 完成对下拉刷新ListView的更新操作
				mArticleListAdapter.notifyDataSetChanged();
				// 将下拉视图收起
				mUpPullLoading.onRefreshComplete();
			}
		}.execute();
	}

	/**
	 * 解析数据，并展示
	 * 
	 * @param jsonResult
	 */
	protected void display(String jsonResult) {

		// 创建解析器
		Gson gson = new Gson();
		List<HomePageItemBean> homePageItemBean = gson.fromJson(jsonResult, new TypeToken<List<HomePageItemBean>>() {
		}.getType());
		// 获取首页的集合
		mArticleInfo = homePageItemBean;

		mArticleListAdapter = new ArticleListAdapter();
		mUpPullLoading.setAdapter(mArticleListAdapter);
	}

	/** 展示文章列表的适配器 */
	private class ArticleListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			if (mArticleInfo != null) {
				return mArticleInfo.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mArticleInfo != null) {
				return mArticleInfo.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 视图
			ViewHold viewHold = null;
			if (convertView == null) {
				viewHold = new ViewHold();
				convertView = View.inflate(MainActivity.this, R.layout.item_list, null);
				viewHold.userIcon = (ImageView) convertView.findViewById(R.id.user_icon);
				viewHold.articleTitle = (TextView) convertView.findViewById(R.id.tv_article_title);
				viewHold.articleNode = (TextView) convertView.findViewById(R.id.tv_article_node);
				viewHold.articleAuthor = (TextView) convertView.findViewById(R.id.tv_article_author);
				viewHold.articleTime = (TextView) convertView.findViewById(R.id.tv_article_time);

				convertView.setTag(viewHold);
			} else {
				viewHold = (ViewHold) convertView.getTag();
			}

			// 获取数据
			HomePageItemBean itemBean = mArticleInfo.get(position);

			// 视图与数据进行绑定
			mBitmapUtils.display(viewHold.userIcon, MyURL.PIC_BEAN_URL + itemBean.image); // 设置头像
			viewHold.articleTitle.setText(itemBean.title);
			viewHold.articleNode.setText(itemBean.node);
			viewHold.articleAuthor.setText(itemBean.username);

			String createTime = GetDateUtils.getCreateTime(itemBean.creattime);
			viewHold.articleTime.setText(createTime);
			return convertView;
		}

		class ViewHold {
			// 用户的头像
			ImageView userIcon;
			// 文章的标题
			TextView articleTitle;
			// 文章的所在板块
			TextView articleNode;
			// 文章的作者
			TextView articleAuthor;
			// 发布的时间
			TextView articleTime;
		}
	}

	/**
	 * 点击文章后，跳到点击的文章详情
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 获取文章对象
		//列表是从1开始的，但集合是从0开始的，为了能对应用户点击的哪篇文章
		HomePageItemBean pageItemBean = mArticleInfo.get(position - 1);
		Intent intent = new Intent(MainActivity.this, ArticleDetails.class);
		// 将文章对应的url传递给下一个Activity
		intent.putExtra("url", MyURL.ARTICLE_DETAILS + pageItemBean.id + ".html");
		startActivity(intent);
	}

	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_play: // 在线音乐播放器
			if (mMediaPlayer.isPlaying()) { // 检查音乐是否正在播放
				mPlayMusic.setBackgroundResource(R.drawable.playing);
				mMediaPlayer.pause(); // 若正在播放，点击后，暂停播放
			} else { // 音乐未播放
				mPlayMusic.setBackgroundResource(R.drawable.stop);
				Toast.makeText(MainActivity.this, "嗨起来  :)", Toast.LENGTH_SHORT).show();
				initMusic(MyURL.MUSIC_URL); // 点击后，异步加载音乐
				mMediaPlayer.start(); // 播放
			}
			break;
		}
	}
}
