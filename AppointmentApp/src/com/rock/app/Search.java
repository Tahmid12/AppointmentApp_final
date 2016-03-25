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

public class Search extends Activity implements OnClickListener{

	TextView textView1;
	EditText editText1;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
      textView1 = (TextView)findViewById(R.id.textsearch);
      
      editText1 = (EditText)findViewById(R.id.editTextsearch);
      
      View buttonselect = findViewById(R.id.Search);
      buttonselect.setOnClickListener(this);
        
    }
	
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.Search:
				boolean worked =true;
				try
				{
					MYSqliteHelper sql = new MYSqliteHelper(this);
					sql.open();
					String s=sql.Search(editText1.getText().toString());
					sql.close();
					textView1.setText(s);
					break;
				}
				catch(Exception e)
				{
					Dialog a = new Dialog(this);
					a.setTitle(e.toString());
					a.show();
				}
				
		}
	}
}
