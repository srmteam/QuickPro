package mani.droid.quickpro;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkActivity extends Activity {

	int workno;
	TextView workTit, workCont;
	ImageView workSol;
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String msg = "Welcome " + sp.getString("username", "Guest") + ", Do this Workout Yourself, Dont look Solution before Doing yourself";
		new AlertDialog.Builder(this)
							.setTitle("QuickPro")
							.setMessage(msg)
							.setPositiveButton("Continue", null).create().show();
		
		workno = getIntent().getExtras().getInt("workno");
		
		workTit = (TextView) this.findViewById(R.id.workTitle);
		workCont = (TextView) this.findViewById(R.id.workContent);
		workSol = (ImageView) this.findViewById(R.id.workSol);
		
		workTit.setText(workTit.getText().toString() + workno);
		workCont.setText(this.getResourceId("work_title" + workno, R.string.class));
		workSol.setImageResource(this.getResourceId("work" + workno, R.drawable.class));
	}
	
	public void onShow(View v)
	{
		v.setVisibility(View.GONE);
		workSol.setVisibility(View.VISIBLE);
	}
	
	@SuppressWarnings("rawtypes")
	public int getResourceId(String name, Class cls)
	{
		try {
			Field field = cls.getField(name);
		    int Id = field.getInt(null);
		    return Id;
		}
		catch (Exception e) {
		    return 0;
		}
	}
}
