package com.sinaapp.inbbs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @创建者 xcai
 * @创建时间 2016-1-2 上午10:11:35
 * @描述 将时间戳转换为正常的时间格式
 * 
 * @版本 $Rev$
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 TODO
 */
public class GetDateUtils {
	public static String getCreateTime(long time) {

		String beginDate = (time * 1000) + "";

		String createTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(beginDate)));
		
		return createTime;
	}
}
