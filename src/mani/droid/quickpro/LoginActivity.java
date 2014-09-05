	package mani.droid.quickpro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	View book;
	AnimationDrawable ad;
	SharedPreferences sp;
	Editor spedi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
		book = findViewById(R.id.viewBook);
		ad = (AnimationDrawable) book.getBackground();
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		spedi = sp.edit();
	}
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus)
	{
		ad.start();
	}
	
	public void onSettings(View v)
	{
		final EditText name = new EditText(this);
		name.setSingleLine();
		name.setText(sp.getString("username", ""));
		AlertDialog dialog = new AlertDialog.Builder(this)
			.setTitle("Enter your Name:")
			.setView(name)
    		.setPositiveButton("Confirm", new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					spedi.putString("username", name.getText().toString());
					spedi.commit();
				}
    		}).create();
    	dialog.show();
	}
	
	public void onStart(View v)
	{
		String uname = sp.getString("username", "");
		if(!uname.equals(""))
		{
			Intent navi = new Intent(this, SubMenuActivity.class);
			startActivity(navi);
		}
		else
			Commons.showToast("Please set User name before Start", true);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		onQuit(null);
	}
	
	public void onQuit(View v)
	{
		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Exit")
	    		.setMessage("Are you sure do you want to Exit")
	    		.setPositiveButton("Yes", newdialog)
	    		.setNegativeButton("No", null).create();
	    	dialog.show();
	}
	
	DialogInterface.OnClickListener newdialog = new DialogInterface.OnClickListener() {

		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			finish();
		}		
	};
}