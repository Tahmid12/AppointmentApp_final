package com.rock.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rock.app.R;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CalendarView;

public class Main extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	public static MYSqliteHelper event;
	public static int selection = 0;
	public static String Date1="";
	CalendarView cal;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View Create = findViewById(R.id.buttonCreate);
        Create.setOnClickListener(this);
        
		View edit = findViewById(R.id.buttonView);
		edit.setOnClickListener(this);
		
		View Delete = findViewById(R.id.buttonDelete);
		Delete.setOnClickListener(this);
		
		View Move = findViewById(R.id.buttonMove);
        Move.setOnClickListener(this);
        
		View Search = findViewById(R.id.buttonSearch);
		Search.setOnClickListener(this);
		
		View Translate = findViewById(R.id.buttonTranslate);
		Translate.setOnClickListener(this);
		
		cal = (CalendarView) findViewById(R.id.calendarView1);
		//Date = String.valueOf(cal.getDate());
		
		
    }
    
    public void setdatemethod()
    {
    	Date selectedDate = new Date(cal.getDate());
    	SimpleDateFormat format = new SimpleDateFormat("EEE d MMM yyyy");
    	Date1 = format.format(selectedDate);
    	//Date1 = (String)android.text.format.DateFormat.format("MMMM YYYY", 0);
    	//Date1 = String.valueOf(cal.getDate());
    }
    
    
    public void onClick(View v) 
	{
    	
    	//Date1 = String.valueOf(cal.getDate());

		switch (v.getId()) 
		{
			case R.id.buttonCreate:
				setdatemethod();
				Intent i = new Intent(this, Create.class);
				startActivity(i);
				break;
			case R.id.buttonView:
				setdatemethod();
				selection = 1;
				Intent j = new Intent(this,Viewing.class);
				startActivity(j);
				break;
			case R.id.buttonDelete:
				setdatemethod();
				Intent k = new Intent(this, Delete.class);
				startActivity(k);
				break;
				
			case R.id.buttonMove:
				setdatemethod();
				selection = 2;
				Intent l = new Intent(this, Viewing.class);
				startActivity(l);
				break;
			case R.id.buttonSearch:
				setdatemethod();
				Intent m = new Intent(this, Search.class);
				startActivity(m);
				break;
			case R.id.buttonTranslate:
				setdatemethod();
				selection = 3;
				Intent n = new Intent(this, Viewing.class);
				startActivity(n);
				break;
		}
	}
}