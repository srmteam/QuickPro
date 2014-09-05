package mani.droid.quickpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContentActivity extends Activity {

	ListView lv;
	String utype;
	String[] contents;
	TextView cntTit;
	int selChap = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);

		utype = this.getIntent().getExtras().getString("usertype");
		if(utype.equalsIgnoreCase("Beginner"))
			contents = this.getResources().getStringArray(R.array.beginner_content);
		else if(utype.equalsIgnoreCase("Inter Mediate"))
		{
			selChap += 14; // setting initial chapter for medium 
			contents = this.getResources().getStringArray(R.array.medium_content);
		}
			
		else if(utype.equalsIgnoreCase("Expert"))
		{
			selChap += 24; // setting initial chapter for expert
			contents = this.getResources().getStringArray(R.array.expert_content);
		}
		
		cntTit = (TextView) this.findViewById(R.id.txtCntTitle);
		cntTit.setText(cntTit.getText().toString() + utype);
		
		lv = (ListView) this.findViewById(R.id.lvCont);
		lv.setAdapter(new ArrayAdapter<String>(this,
				R.layout.content_item,
				R.id.txtTitle,
				contents));
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> aView, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				Intent navi = new Intent(ContentActivity.this, CourseActivity.class);
				navi.putExtra("chapno", selChap + pos);
				navi.putExtra("chapname", contents[pos]);
				startActivity(navi);
			}
		});
	}
}
