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

public class Viewing extends Activity implements OnClickListener
{

	public static String _IDUserSelection ="";
	TextView textView1;
	EditText editText1;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        textView1 = (TextView)findViewById(R.id.displayContent);
        try
        {
	        MYSqliteHelper sqlview = new MYSqliteHelper(this);
			sqlview.open();
			String data = sqlview.getdata();
			textView1.setText(data);
			sqlview.close();
        }
        catch(Exception e)
        {
        	Dialog a = new Dialog(this);
			a.setTitle(e.getMessage());
			a.show();
        }
		editText1 = (EditText)findViewById(R.id.editTextSelect);
		
		View buttonselect = findViewById(R.id.buttonToView);
		buttonselect.setOnClickListener(this);
		
}
	
	public void onClick(View v) 
	{
		_IDUserSelection = editText1.getText().toString();
		switch (v.getId()) 
		{
			case R.id.buttonToView:
				try
				{
					if(Main.selection == 1)
					{
						Intent i = new Intent(this, Edit.class);
						startActivity(i);
					}
					else if(Main.selection == 2)
					{
						Intent i2 = new Intent(this, Move.class);
						startActivity(i2);
					}
					else if(Main.selection == 3)
					{
						Intent i3 = new Intent(this, Translate.class);
						startActivity(i3);
					}	
				}
				catch (Exception e)
				{
					
				}
				break;
		}
	}
}
