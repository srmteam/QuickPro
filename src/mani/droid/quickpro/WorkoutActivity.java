package mani.droid.quickpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkoutActivity extends Activity {

	ListView lv;
	String[] works;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout);
		
		works = this.getResources().getStringArray(R.array.workouts);
		
		lv = (ListView) this.findViewById(R.id.lvWork);
		lv.setAdapter(new ArrayAdapter<String>(this,
				R.layout.content_item,
				R.id.txtTitle,
				works));
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> aView, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				Intent workout = new Intent(WorkoutActivity.this, WorkActivity.class);
				workout.putExtra("workno", pos + 1);
				startActivity(workout);
			}
		});
	}
}