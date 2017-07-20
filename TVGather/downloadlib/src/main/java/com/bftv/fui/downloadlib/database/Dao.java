package com.bftv.fui.downloadlib.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bftv.fui.downloadlib.entity.DownloadEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KNothing on 2017/7/19.
 * 数据库记录操作类
 */
public class Dao {

	private DBHelper mDBHelper = null;

	private static Dao dao=null;

	public Dao(Context context) {
		mDBHelper = new DBHelper(context);
	}

//	public void getInstance(Context context){
//		if(dao==null){
//			dao=new Dao(context);
//		}
//		return dao;
//	}

	public  SQLiteDatabase getConnection() {
		SQLiteDatabase sqliteDatabase = null;
		try { 
			sqliteDatabase= mDBHelper.getReadableDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqliteDatabase;
	}

	/**
	 * 查看数据库中是否有数据
	 */
	public synchronized boolean isHasInfors(String urlstr) {
		SQLiteDatabase database = getConnection();
		int count = -1;
		Cursor cursor = null;
		try {
			String sql = "select count(*)  from download_info where url=?";
			cursor = database.rawQuery(sql, new String[] { urlstr });
			if (cursor.moveToFirst()) {
				count = cursor.getInt(0);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}
		return count == 0;
	}

	/**
	 * 保存 下载的具体信息
	 */
	public synchronized void saveInfos(List<DownloadEntity> infos) {
		SQLiteDatabase database = getConnection();
		try {
			for (DownloadEntity info : infos) {
				String sql = "insert into download_info(thread_id,start_pos, end_pos,compelete_size,url) values (?,?,?,?,?)";
				Object[] bindArgs = { info.getThreadId(), info.getStartPos(),
						info.getEndPos(), info.getCompeleteSize(),
						info.getUrl() };
				database.execSQL(sql, bindArgs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}

	/**
	 * 得到下载具体信息
	 */
	public synchronized List<DownloadEntity> getInfos(String urlstr) {
		List<DownloadEntity> list = new ArrayList<DownloadEntity>();
		SQLiteDatabase database = getConnection();
		Cursor cursor = null;
		try {
			String sql = "select thread_id, start_pos, end_pos,compelete_size,url from download_info where url=?";
			cursor = database.rawQuery(sql, new String[] { urlstr });
			while (cursor.moveToNext()) {
				DownloadEntity info = new DownloadEntity(cursor.getInt(0),
						cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
						cursor.getString(4));
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}
		return list;
	}

	/**
	 * 更新数据库中的下载信息
	 */
	public synchronized void updataInfos(int threadId, int compeleteSize, String urlstr) {
		SQLiteDatabase database = getConnection();
		try {
			String sql = "update download_info set compelete_size=? where thread_id=? and url=?";
			Object[] bindArgs = { compeleteSize, threadId, urlstr };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}

	/**
	 * 下载完成后删除数据库中的数据
	 */
	public synchronized void delete(String url) {
		SQLiteDatabase database = getConnection();
		try {
			database.delete("download_info", "url=?", new String[] { url });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
		}
	}
}