package mani.droid.quickpro;

import java.lang.reflect.Field;

import mani.droid.db.DBHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class CourseActivity extends Activity {

	TextView tit;
	TextView exp;
	String chapname;
	int chapno;
	ViewFlipper vf;
	RadioGroup rg;
	ImageView imgFlow, imgEx, imgSyn;
	DBHelper dbh;
	int hCount = 0;
	
	TextView expText1, expText2, expKey1, expKey2;
	View root;
	String curString;
	int curView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course);
		
		dbh = new DBHelper(this);
		
		vf = (ViewFlipper) this.findViewById(R.id.vfCourse);
		rg = (RadioGroup) this.findViewById(R.id.rgMenu);
		
		chapname = this.getIntent().getExtras().getString("chapname");
		chapno = this.getIntent().getExtras().getInt("chapno");
		
		tit = (TextView) this.findViewById(R.id.txtCourseTitle);
		exp = (TextView) vf.findViewById(R.id.txtCourseInfo);
		
		tit.setText("Chapter " + chapno + ": " + chapname);
		exp.setText(this.getResourceId("exp_chap" + chapno, R.string.class));
		exp.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup rg, int id) {
				// TODO Auto-generated method stub
				int sel = 0;
				switch(id)
				{
					case R.id.btnExp:
						sel = 0;
						break;
						
					case R.id.btnFlow:
						sel = 1;
						break;
						
					case R.id.btnSyn:
						sel = 2;
						break;
						
					case R.id.btnExmpl:
						sel = 3;
						break;
						
					case R.id.btnExperiment:
						sel = 4;
						break;
				}
				vf.setDisplayedChild(sel);
			}
		});
		
		// setting flow diagrams...
		int resflow = this.getResourceId("flow" + chapno, R.drawable.class);
		if(resflow != 0)
		{
			imgFlow = (ImageView) vf.findViewById(R.id.imgFlow);
			imgFlow.setImageResource(resflow);
		}
		else
		{
			rg.getChildAt(1).setVisibility(View.GONE);
			hCount++;
		}
		
		// setting syntax
		int ressyn = this.getResourceId("syn" + chapno, R.drawable.class);
		if(ressyn != 0)
		{
			imgSyn = (ImageView) vf.findViewById(R.id.imgSyn);
			imgSyn.setImageResource(ressyn);
		}
		else
		{
			rg.getChildAt(2).setVisibility(View.GONE);
			hCount++;
		}
		
		// setting example 
		int resex = this.getResourceId("ex" + chapno, R.drawable.class);
		if(resex != 0)
		{
			imgEx = (ImageView) vf.findViewById(R.id.imgEx);
			imgEx.setImageResource(resex);
		}
		else
		{
			rg.getChildAt(3).setVisibility(View.GONE);
			hCount++;
		}
		
		// setting experiment
		int resexp = this.getResourceId("experiment" + chapno, R.layout.class);
		if(resexp != 0)
		{
			root = getLayoutInflater().inflate(resexp, null); 
			vf.addView(root);
			
			// settings for Experiment...
			expText1 = (TextView) root.findViewById(R.id.expText1);
			expText2 = (TextView) root.findViewById(R.id.expText2);
			expKey1 = (TextView) root.findViewById(R.id.expKey1);
			expKey2 = (TextView) root.findViewById(R.id.expKey2);
			
			expKey1.setOnLongClickListener(onKeyLongPress);
			expKey2.setOnLongClickListener(onKeyLongPress);
			
			expText1.setOnDragListener(onKeyDrag);
			expText2.setOnDragListener(onKeyDrag);
		}
		else
		{
			rg.getChildAt(4).setVisibility(View.GONE);
			hCount++;
		}
			
		if(hCount >= 4)
			rg.setVisibility(View.GONE);
		
	}
	
	View.OnLongClickListener onKeyLongPress = new View.OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View view) {
			// TODO Auto-generated method stub
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadow = new View.DragShadowBuilder(view);
			view.startDrag(data, shadow, view, 0);
			return true;
		}
	};
	
	View.OnDragListener onKeyDrag = new View.OnDragListener() {
		
		@Override
		public boolean onDrag(View v, DragEvent event) {
			// TODO Auto-generated method stub
			
			switch (event.getAction())
			{
				case DragEvent.ACTION_DRAG_ENTERED:
					TextView text = (TextView) v;
					curString = text.getText().toString();
					curView = text.getId();
					break;
					
				case DragEvent.ACTION_DRAG_EXITED:
					curString = "";
					curView = 0;
					break;
					
				case DragEvent.ACTION_DRAG_ENDED:
					// do nothing..
					break;
					
		    	case DragEvent.ACTION_DROP:
		    		// Dropped, reassign View to ViewGroup
		    		TextView view = (TextView) event.getLocalState();
		    		String tmpStr = view.getText().toString();
		    		// checking entered text with key text;
		    		if(curString.equals(tmpStr))
		    			findViewById(curView).setBackgroundColor(Color.WHITE);
		    		break;
		    	default:
		      		break;
		    }
			return true;
		}
	};
	
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
	
	public void onCheck(View v)
	{
		// checking whether both keys are set..
		try{
			ColorDrawable dr1 = (ColorDrawable) expText1.getBackground();
			try
			{
				ColorDrawable dr2 = (ColorDrawable) expText2.getBackground();
				if(dr1.getColor() == Color.WHITE && dr2.getColor() == Color.WHITE)
				{
					String msg = "Success, You are Placed the Keywords perfectly";
					new AlertDialog.Builder(this)
					.setTitle("QuickPro")
					.setMessage(msg)
					.setPositiveButton("Continue", null).create().show();
				}
				else
					Commons.showToast("Sorry, Misplaced keywords", true);
			}
			catch(Exception ex) {
				Commons.showToast("Sorry, Misplaced keywords", true);
			}
		}
		catch(Exception ex) {
			Commons.showToast("Sorry, Misplaced keywords", true);
		}
	}
}
