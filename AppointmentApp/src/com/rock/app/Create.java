package com.rock.app;

//import visa.com.myandroid.R;
//import visa.com.myandroid.MyandroidprojectActivity;
import com.rock.app.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by AT.
 */

public class Create extends Activity implements OnClickListener{
	 
	public static Appointment myappointment = new Appointment();
	EditText editText1;
	EditText editText2;
	EditText editText3;
	TextView textView1;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        editText1 = (EditText)findViewById(R.id.editTextTitle);
        editText2 = (EditText)findViewById(R.id.editTextTime);
        editText3 = (EditText)findViewById(R.id.editTextDetail);
        textView1 = (TextView)findViewById(R.id.textViewerrorMessage);
        
        View save = findViewById(R.id.Save);
        save.setOnClickListener(this);
    }
	
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.Save:
				boolean worked =true;
				try{
					if(validate())
					{
						MYSqliteHelper sql = new MYSqliteHelper(this);
						sql.open();
						if(sql.checkname(editText1.getText().toString()))
						{
							myappointment.setDate(Main.Date1);
							myappointment.setTime(editText2.getText().toString());
							myappointment.setTitle(editText1.getText().toString());
							myappointment.setDetail(editText3.getText().toString());
							
							sql.createEntry();
						}
						else
						{
							Dialog k = new Dialog(this);
							k.setTitle("Already Exists");
							worked = false;
						}
						sql.close();
					}
					else
					{
						worked = false;
					}
					
				}
				catch(Exception e)
				{
					worked = false;
				}
				finally
				{ 
					Dialog d = new Dialog(this);
					TextView tv1 = new TextView(this);
					if(worked)
					{
						d.setTitle("Created");
						//tv1.setText("success");
						//d.setContentView(tv1);
						d.show();
						Intent i = new Intent(this,  Main.class);
						startActivity(i);
						finish();
					}
					else
					{
						d.setTitle("Error");
						d.show();
					}
					
				}
		}
	}
	
	public boolean validate()
	{
		if((editText1.getText().toString().length()==0)||(editText2.getText().toString().length()==0)||(editText3.getText().toString().length()==0))
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
			for(int i=0 ; i<editText2.getText().toString().length() ; i++)
			{
				if(editText2.getText().charAt(i)=='a')
				{
					a++;
				}
				if(editText2.getText().charAt(i)=='p')
				{
					p++;
				}
				if(editText2.getText().charAt(i)=='m')
				{
					m++;
				}
				if(editText2.getText().charAt(i)>='0'&&editText2.getText().charAt(i)<='9')
				{
					number++;
				}
			}
			if((a>1)||(p>1)||(m>1)||(a==1&&p==1))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
}
