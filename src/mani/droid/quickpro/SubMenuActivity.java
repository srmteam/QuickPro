package mani.droid.quickpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SubMenuActivity extends Activity {

	String utype;
	Intent navi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_main);
		
		View rl = this.findViewById(R.id.layHome);
		rl.getBackground().setAlpha(80);
	}
	
	public void onBtnClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btnCourse:
				navi = new Intent(this, MainActivity.class);
				break;
				
			case R.id.btnQuiz:
				navi = new Intent(this, QuizActivity.class);
				break;
				
			case R.id.btnWorkout:
				navi = new Intent(this, WorkoutActivity.class);
				break;
		}
		startActivity(navi);
	}
}