package com.rock.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rock.app.R;



import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by AT.
 */
public class Move extends Activity implements OnClickListener{

	//TextView textView1;
	TextView textView1;
	EditText editText1;
	CalendarView cal;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move);
        
        editText1 = (EditText)findViewById(R.id.editTextmove);
        
        View buttonselect = findViewById(R.id.Movethis);
        buttonselect.setOnClickListener(this);
        
        cal = (CalendarView) findViewById(R.id.calendarViewMove);
    }
	
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.Movethis:
				boolean worked =true;
				try
				{
					if(validate())
					{
						//Date selectedDate = new Date(cal.getDate());
				    	//SimpleDateFormat format = new SimpleDateFormat("EEE d MMM yyyy");
				    	//CW2Activity.Date1 = format.format(selectedDate);
						Main.Date1 = String.valueOf(cal.getDate());
						Create.myappointment.setDate(Main.Date1);
						MYSqliteHelper sql = new MYSqliteHelper(this);
						sql.open();
						Create.myappointment.setDate(Create.myappointment.getDate());
						Create.myappointment.setTime(editText1.getText().toString());
						sql.updateEntry();
						sql.close();
						
					}
					else
					{
						worked = false;
					}
				}
				catch(Exception e)
				{
					Dialog a = new Dialog(this);
					a.setTitle(e.toString());
					a.show();
				}
				finally
				{
					Dialog d = new Dialog(this);
					TextView tv1 = new TextView(this);
					if(worked)
					{
						d.setTitle("Moved");
						//tv1.setText("success");
						//d.setContentView(tv1);
						d.show();
					}
					else
					{
						d.setTitle("Not working");
						d.show();
					}
					Intent i = new Intent(this,  Main.class);
					startActivity(i);
					finish();
				}
		}
	}
	public boolean validate()
	{
		if(editText1.getText().toString().length()==0)
		{
			Dialog a = new Dialog(this);
			a.setTitle("Fill in all the details");
			return false;
		}
		else 
		{
			int a = 0;
			int p=0;
			int m=0;
			int number = 0;
			for(int i=0 ; i<editText1.getText().toString().length() ; i++)
			{
				if(editText1.getText().charAt(i)=='a')
				{
					a++;
				}
				if(editText1.getText().charAt(i)=='p')
				{
					p++;
				}
				if(editText1.getText().charAt(i)=='m')
				{
					m++;
				}
				if(editText1.getText().charAt(i)>='0'&&editText1.getText().charAt(i)<='9')
				{
					number++;
				}
			}
			if((a>1)||(p>1)||(m>1)||(a==1&&p==1)||(number>4))
			{
				return false;
			}
			return true;
		}
	}
}
