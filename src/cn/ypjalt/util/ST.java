package cn.ypjalt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ypjalt.entity.QueryResult;

public class ST {
	/** 判断参数是否为空，是空返回true 不空返回false */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

	/** 解决重新搜索nowPage>totalPage的问题 */
	public static boolean page(QueryResult result, Long page) {
		if (!(result.getTotalPage() >= page))
			return true;
		return false;
	}

	public static String getTime() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);

	}

}
