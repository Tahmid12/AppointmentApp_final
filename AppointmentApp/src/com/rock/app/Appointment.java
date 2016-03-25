package com.rock.app;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by AT.
 */

public class Appointment {
	private String MyDate;
	private String Time;
	private String Title;
	private String Detail;

	public String getDate() {
		return MyDate;
		
	}

	public void setDate(String MyDate1) 
	{
		this.MyDate = MyDate1;
		/*DateFormat formatter = new SimpleDateFormat("YYYYMMDDThhmm");
		Date fileDate=null;
		try 
		{
			fileDate = (Date)formatter.parse(MyDate1);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.MyDate = fileDate.toString();*/
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String Time) {
		this.Time = Time;
	}
	
	public String getDetail() {
		return Detail;
	}

	public void setDetail(String Detail) {
		this.Detail = Detail;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}
}
