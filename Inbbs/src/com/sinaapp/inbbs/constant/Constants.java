package com.sinaapp.inbbs.constant;

/**
 * 
 * @创建者 xcai
 * @创建时间 2016-1-1 上午9:50:10
 * @描述 TODO
 * 
 * @版本 $Rev$
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 TODO
 */
public class Constants {
	public static final class MyURL {
		// 服务器地址
		public static final String SERVICE_URL = "http://inbbs.sinaapp.com/";

		// 首页的URL----"http://inbbs.sinaapp.com/home/"
		public static final String HOME_URL = SERVICE_URL + "home/";

		// 返回json数据的基本地址----"http://inbbs.sinaapp.com/home/api/"
		public static final String JSON_BEAN_URL = HOME_URL + "api/";

		// 图片的基本URL地址
		// http://inbbs-uploads.stor.sinaapp.com/image/文件名（含后缀名）
		public static final String PIC_BEAN_URL = "http://inbbs-uploads.stor.sinaapp.com/image/";

		// 分页
		// 例如（第2页）：http://inbbs.sinaapp.com/api/index/p/2
		public static final String PAGE_BEAN_URL = SERVICE_URL + "api/index/p/";

		// 获取一共有多少篇文章：
		// http://inbbs.sinaapp.com/Home/Api/count/moudle/index
		public static final String ARTICLE_COUNT = JSON_BEAN_URL + "count/moudle/index";

		// 文章
		// http://www.inbbs.sinaapp.com/t/3.html
		public static final String ARTICLE_DETAILS = SERVICE_URL + "t/";

		// 公告
		public static final String NOTICE = SERVICE_URL + "api/notice";
		// 音乐资源地址
		public static final String MUSIC = "http://inbbs-uploads.stor.sinaapp.com/music/";
		// 音乐url地址
		public static final String MUSIC_URL = SERVICE_URL+"api/music";
		

	}
}
