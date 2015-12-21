package com.qf.lingshixiaomaio.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的收藏 数据库
 * 
 * @author JoshuaJan
 * 
 */
public class CollectionDBHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "myCollection.db";
	private SQLiteDatabase db;

	public CollectionDBHelper(Context context) {
		super(context, DBNAME, null, 3);
		getConnection();
	}

	private void getConnection() {
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(DBNAME, "--------onCreate---------");
		db.execSQL("create table if not exists subject(_id integer primary key autoincrement,id integer not null,title text not null,hotindex integer not null,img_url text not null)");
		db.execSQL("create table if not exists goodses(_id integer primary key autoincrement,id integer not null,title text not null,img_url)");
		db.execSQL("create table if not exists shoppingCar(_id integer primary key autoincrement,id integer not null,title,img_url,prime,current)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			db.execSQL("drop table if exists subject");
			db.execSQL("drop table if exists goodses");
			db.execSQL("drop table if exists shoppingCar");
			onCreate(db);
		}
	}

	/**
	 * 插入专题收藏到数据库
	 * 
	 * @param db
	 * @param values
	 * @return
	 */
	public long insertDataSubject(SQLiteDatabase db, ContentValues values) {
		return db.insert("subject", null, values);

	}

	/**
	 * 插入商品收藏到数据库
	 * 
	 * @param db
	 * @param values
	 * @return
	 */
	public long insertDataGoodses(SQLiteDatabase db, ContentValues values) {
		return db.insert("goodses", null, values);
	}

	/**
	 * 插入到收购物车数据库
	 * 
	 * @param db
	 * @param values
	 * @return
	 */
	public long insertDataShoppingCar(SQLiteDatabase db, ContentValues values) {
		return db.insert("shoppingCar", null, values);
	}

	public Cursor selectCursor(String sql, String[] selectionArgs) {
		return db.rawQuery(sql, selectionArgs);
	}

	// 删除shoppingCar数据
	public int deleteDataShoppingCar(String whereClause, String[] whereArgs) {
		return db.delete("shoppingCar", whereClause, whereArgs);
	}

	public int selectCount(String sql, String[] selectionArgs) {
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		if (cursor != null) {
			int count = cursor.getCount();
			cursor.close();
			return count;
		}
		return 0;
	}

	public List<Map<String, Object>> cursorToList(Cursor cursor) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] arrCols = cursor.getColumnNames();
		while (cursor.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();

			for (int i = 0; i < arrCols.length; i++) {
				int type = cursor.getType(i);
				Object value = null;
				switch (type) {
				case 1:
					value = cursor.getInt(i);
					break;
				case 2:
					value = cursor.getFloat(i);
					break;
				case 3:
					value = cursor.getString(i);
					break;
				case 4:
					value = cursor.getBlob(i);
					break;
				default:
					break;
				}
				map.put(arrCols[i], value);
			}
			list.add(map);
		}
		return list;
	}

	public void destroy() {
		if (db != null) {
			db.close();
		}
	}
}
