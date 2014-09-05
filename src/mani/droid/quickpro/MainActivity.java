package mani.droid.quickpro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	Intent navi;
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String msg = "Welcome back " + sp.getString("username", "Guest") + ", You are here to learn how to programming with C#";
		new AlertDialog.Builder(this)
							.setTitle("QuickPro")
							.setMessage(msg)
							.setPositiveButton("Continue", null).create().show();
		
		navi = new Intent(this, ContentActivity.class);
	}
	
	public void onUserTypeClick(View v)
	{
		String usertype = ((TextView) v).getText().toString();
		navi.putExtra("usertype", usertype);
		startActivity(navi);
	}
}
