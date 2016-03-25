package com.rock.app;

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

public class Edit extends Activity implements OnClickListener{

	EditText editText1;
	EditText editText2;
	EditText editText3;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        
        editText1 = (EditText)findViewById(R.id.editTexttitleview);
        editText2 = (EditText)findViewById(R.id.editTexttimeview);
        editText3 = (EditText)findViewById(R.id.editTextdetailview);
        
        View buttonselect = findViewById(R.id.Saveedit);
		buttonselect.setOnClickListener(this);
        
    }
	
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.Saveedit:
				boolean worked =true;
				try
				{
					if(validate())
					{
						//Create.myappointment.setDate(CW2Activity.Date);
						Create.myappointment.setTime(editText2.getText().toString());
						Create.myappointment.setTitle(editText1.getText().toString());
						Create.myappointment.setDetail(editText3.getText().toString());
						
						MYSqliteHelper sql = new MYSqliteHelper(this);
						sql.open();
						sql.updateEntry();
						sql.close();
						break;
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
						d.setTitle("Edited");
						//tv1.setText("success");
						//d.setContentView(tv1);
						d.show();
					}
					else
					{
						
					}
					Intent i = new Intent(this,  Main.class);
					startActivity(i);
					finish();
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
			if((a>1)||(p>1)||(m>1)||(a==1&&p==1)||(number>4))
			{
				return false;
			}
			return true;
		}
	}
}
