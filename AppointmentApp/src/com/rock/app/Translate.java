package com.rock.app;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

import com.rock.app.R;



import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.
OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * Created by AT.
 */

public class Translate extends Activity implements OnClickListener
{
	private static final String TAG = "TranslateTask";
	private Spinner fromSpinner;
	private Spinner toSpinner;
	private EditText origText;
	private TextView transText;
	private String fromLang;
	private String toLang;
	private TextWatcher textWatcher;
	private OnItemSelectedListener itemListener;
	public String data;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate);
        findViews();
        setAdapters();
        try
        {
	        MYSqliteHelper sqlview = new MYSqliteHelper(this);
			sqlview.open();
			data = sqlview.returndetail();			
			sqlview.close();
        }
        catch(Exception e)
        {
        	Dialog a = new Dialog(this);
			a.setTitle(e.toString());
			a.show();
        }
        //setListeners();
	}
	
	public void onClick(View v) 
	{
		
	}
	
	private void findViews() 
	{
		fromSpinner = (Spinner) findViewById(
		R.id.from_language);
		toSpinner = (Spinner) findViewById(
		R.id.to_language);
		origText = (EditText) findViewById(
		R.id.original_text);
		transText = (TextView) findViewById(
		R.id.translated_text);
	}
	
	private void setAdapters() 
	{
		// Spinner list comes from a resource,
		ArrayAdapter<CharSequence> adapter =
		ArrayAdapter.createFromResource(
		this, R.array.languages,
		android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(
		android.R.layout.simple_spinner_dropdown_item);
		fromSpinner.setAdapter(adapter);
		toSpinner.setAdapter(adapter);
		// Automatically select two spinner items
		fromSpinner.setSelection(8); // English (en)
		toSpinner.setSelection(11); // French (fr)
		origText.setText(data);
	}
	private void setListeners() 
	{
		textWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s,int start,int count,int after) { }
		
		public void onTextChanged(CharSequence s,int start,int before,int count) 
		{
			String translation = doTranslate(origText.getText().toString().trim(),fromLang, toLang);
			transText.setText(translation);
		}
		
		public void afterTextChanged(Editable s) { }
		};
		
		origText.addTextChangedListener(textWatcher);
		itemListener = new OnItemSelectedListener() 
		{
			public void onItemSelected(AdapterView parent,View v, int position,	long id) 
			{
				fromLang = getLang(fromSpinner);
				toLang = getLang(toSpinner);
				String translation = doTranslate(origText.getText().toString().trim(),fromLang, toLang);
				transText.setText(translation);
			}
		
			public void onNothingSelected(AdapterView parent)
			{
			/* Do nothing */
			}
		}; // end of OnItemSelectedListener()
			
		fromSpinner.setOnItemSelectedListener(itemListener);
			
		toSpinner.setOnItemSelectedListener(itemListener);
	}
	

	private String getLang(Spinner spinner) 
	{
		String result = spinner.getSelectedItem().toString();

		int lparen = result.indexOf('(');
		int rparen = result.indexOf(')');
		result = result.substring(lparen + 1, rparen);
		return result;
	}
	
	private String doTranslate(String original,String from,String to) 
	{
			String result = getResources().getString(
			R.string.translation_error);
			HttpURLConnection con = null;
			try {
			// Build RESTful query for Google API
				String q = URLEncoder.encode(original, "UTF-8");
				URL url = new URL(
						"http://ajax.googleapis.com/ajax/"
						+ "services/language/translate"
						+ "?v=1.0" + "&q=" + q + "&langpair=" + from
						+ "%7C" + to);
						con = (HttpURLConnection) url.openConnection();
						con.setReadTimeout(10000 /* milliseconds */);
						con.setConnectTimeout(15000 /* milliseconds */);
						con.setRequestMethod("GET");
						con.addRequestProperty("Referer",
						"http://www.pragprog.com/hello-android");
						con.setDoInput(true);
						// Start the query
						con.connect();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(con.getInputStream(),
								"UTF-8"));
								String payload = reader.readLine();
								reader.close();
								// Parse to get translated text
								JSONObject jsonObject = new JSONObject(payload);
								result = jsonObject.getJSONObject("responseData")
								.getString("translatedText")
								.replace("&#39;", "’")
								.replace("&amp;", "&");
								}
			catch (IOException e) {
				Log.e(TAG, "IOException", e);
				}
				catch (JSONException e) {
				Log.e(TAG, "JSONException", e);
				}
				finally {
				if (con != null) {
				con.disconnect();
				}
				}
				return result;
				}
		
}
