package com.rock.app;

import com.rock.app.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by AT.
 */

public class Delete extends Activity implements OnClickListener{

	TextView textView1;
	EditText editText1;
	AlertDialog.Builder build;
	AlertDialog alert;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        textView1 = (TextView)findViewById(R.id.DisplayToDelete);
        textView1 = (TextView)findViewById(R.id.DisplayToDelete);
        MYSqliteHelper sqlview = new MYSqliteHelper(this);
		sqlview.open();
		String data = sqlview.getdata();
		textView1.setText(data);
		sqlview.close();
		
		build = new AlertDialog.Builder(this);
		build.setMessage("Do you really want to Delete?");
		build.setCancelable(false);
		build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				deleteall();
			}
		});
		
		build.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		alert = build.create();
		
		View Deleteall = findViewById(R.id.buttonDeleteAll);
		Deleteall.setOnClickListener(this);
        
		View delete = findViewById(R.id.buttonDeleteSelected);
		delete.setOnClickListener(this);
		
		editText1 = (EditText)findViewById(R.id.editTextdelete);
    }
	
	public void deleteall()
	{
		boolean worked =true;
		try
		{
			MYSqliteHelper sql = new MYSqliteHelper(this);
			sql.open();
			sql.deleteallEntry();
			sql.close();
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
				d.setTitle("All are Deleted");
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
	
	public void onClick(View v) 
	{
		
			switch (v.getId()) 
			{
				case R.id.buttonDeleteAll:
					alert.show();
					
				case R.id.buttonDeleteSelected:
					if(editText1.getText().toString().length()!=0)
					{
						boolean worked1 =true;
						try
						{
							MYSqliteHelper sql1 = new MYSqliteHelper(this);
							sql1.open();
							sql1.deleteEntry(editText1.getText().toString());
							sql1.close();
							break;
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
							if(worked1)
							{
								d.setTitle("Deleted");
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
		
	}
}
