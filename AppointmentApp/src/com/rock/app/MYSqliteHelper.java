package com.rock.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AT.
 */

public class MYSqliteHelper {
	public static final String TABLE_COMMENTS = "comments";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";

	private static final String DATABASE_NAME = "Appointment.db";
	private static final String DATABASE_TABLE = "shedule";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "+DATABASE_TABLE
			+  "( " + "_ID"
			+ " integer primary key autoincrement, " + "Title"
			+ " text not null, "+ "Date"
			+ " text not null, "+ "Time"
			+ " text not null, "+ "Detail"
			+ " text not null);";

	private Dbhelper ourhelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	public MYSqliteHelper(Context c)
	{
		ourContext = c;	
	}
	
	private static class Dbhelper extends SQLiteOpenHelper {
		public Dbhelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	public MYSqliteHelper open()throws SQLException
	{
		ourhelper = new Dbhelper(ourContext);
		ourDatabase = ourhelper.getWritableDatabase();
		return this;
	}

	public void close()throws SQLException
	{
		ourhelper.close();
	}

	public long createEntry() throws SQLException{
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put("Title", Create.myappointment.getTitle());
		cv.put("Date", Create.myappointment.getDate());
		cv.put("Time", Create.myappointment.getTime());
		cv.put("Detail", Create.myappointment.getDetail());
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getdata() throws SQLException{
		// TODO Auto-generated method stub
		
		Log.w("error", Create.myappointment.getDate());
		String[] coloumns = new String[]{"_ID","Time","Title"};
		Log.w("error", "Date = "+ Create.myappointment.getDate());
		Cursor c = ourDatabase.query(DATABASE_TABLE, coloumns,"Date = \""+ Create.myappointment.getDate() + "\"", null, null, null, null);
		
		String result="";
		if(c!=null)
		{
			int iRow = c.getColumnIndex("_ID");
			int iTitle = c.getColumnIndex("Title");
			int iTime = c.getColumnIndex("Time");
			
			for(c.moveToFirst() ; !c.isAfterLast() ; c.moveToNext())
			{
				result += c.getString(iRow) + " : " + c.getString(iTime) + " : " + c.getString(iTitle)+"\n";
			}
		}
		
		
		return result;
	}
	
	public String returndetail() throws SQLException
	{
		String[] coloumns = new String[]{"_ID","Time","Title"};
		Cursor c = ourDatabase.query(DATABASE_TABLE, coloumns,"_ID = "+Viewing._IDUserSelection, null, null, null, null);
		String result="";
		if(c!=null)
		{
			int iDetail = c.getColumnIndex("Detail");
			
			
			result = c.getString(iDetail);
			
		}
		
		
		return result;
	}
	
	public boolean checkname(String s) throws SQLException
	{
		Cursor c = ourDatabase.query(DATABASE_TABLE, null,"Title like '%"+s+"%'", null, null, null, null);
		if(c!=null)
		{
			int x=0;
			for(c.moveToFirst() ; !c.isAfterLast() ; c.moveToNext())
			{
				x++;
			}
			if(x<1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}
	
	public void updateEntry()throws SQLException
	{
		String _ID = Viewing._IDUserSelection;
		String[] coloumns = new String[]{"Time","Title","Detail","Date"};
		ContentValues cv = new ContentValues();
		cv.put("Time", Create.myappointment.getTime());
		cv.put("Title", Create.myappointment.getTitle());
		cv.put("Detail", Create.myappointment.getDetail());
		cv.put("Date", Create.myappointment.getDate());
		ourDatabase.update(DATABASE_TABLE, cv,"_ID = "+_ID, null);
	}
	
	public void deleteEntry(String _ID)throws SQLException
	{
		ourDatabase.delete(DATABASE_TABLE, "_ID = "+_ID, null);
	}
	
	public void deleteallEntry()throws SQLException
	{
		//"Date = "+Create.myappointment.getDate()
		Cursor c = ourDatabase.query(DATABASE_TABLE, null,"Date = "+Create.myappointment.getDate(), null, null, null, null);
		for(c.moveToFirst() ; !c.isAfterLast() ; c.moveToNext())
		{
			ourDatabase.delete(DATABASE_TABLE, "Date = "+Create.myappointment.getDate(), null);
		}
		
	}
	
	public String Search(String s)throws SQLException
	{
		Cursor c = ourDatabase.query(DATABASE_TABLE, null,null, null, null, null, null);
		String result="";
		if(c!=null)
		{
			int iRow = c.getColumnIndex("_ID");
			int iTitle = c.getColumnIndex("Title");
			int iTime = c.getColumnIndex("Time");
			int iDate = c.getColumnIndex("Date");
			int iDetail = c.getColumnIndex("Detail");
			
			for(c.moveToFirst() ; !c.isAfterLast() ; c.moveToNext())
			{
				if(c.getString(iTitle).contains(s)||c.getString(iDetail).contains(s))
				{
					result += c.getString(iRow) + " : " + c.getString(iDate) + " : " + c.getString(iTime)+ " : " + c.getString(iTitle)+"\n";
				}
			}
		}
		else
		{
			result = Create.myappointment.getDate();
		}
		
		return result;
		
	}
	
	/*public void addrow(String title, String Detail , String Date , int time)
	{
		SQLiteDatabase db = CW2Activity.event.getWritableDatabase();
		ContentValues value = new ContentValues();
		//value.put();
	}*/

}
