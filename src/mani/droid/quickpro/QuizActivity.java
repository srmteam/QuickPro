package mani.droid.quickpro;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import mani.droid.db.DBHelper;
import mani.droid.db.Quest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class QuizActivity extends Activity {

	ArrayList<Quest> questions = new ArrayList<Quest>();
	int correctAns = 0;
	Quest curQuest;
	TextView ques;
	Button[] opts = new Button[4];
	Button submit;
	boolean answered = false;
	int curSel;
	ViewFlipper vf;
	View curView;
	int curViewId = 0;
	DBHelper dbh;
	String[] prefix = new String[]{ "a) ", "b) ", "c) ", "d) " };
	TextView score, quesno;
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String msg = "Welcome back " + sp.getString("username", "Guest") + ", All the Best for your TEST";
		new AlertDialog.Builder(this)
							.setTitle("QuickPro")
							.setMessage(msg)
							.setPositiveButton("Continue", null).create().show();
		
		dbh = new DBHelper(this);
		questions = dbh.getQuestions();
		
		vf = (ViewFlipper) this.findViewById(R.id.vfQuiz);
		submit = (Button) this.findViewById(R.id.quizSubmit);
		
		vf.setInAnimation(this, R.anim.fadeout);
		vf.setOutAnimation(this, R.anim.fadein);
		
		score = (TextView) this.findViewById(R.id.quizScore);
		quesno = (TextView) this.findViewById(R.id.quizQno);
		assignViews();
	}
	
	public void assignViews()
	{
		curView = vf.getChildAt(vf.getDisplayedChild());
		ques = (TextView) curView.findViewById(R.id.quizQues);
		opts[0] = (Button) curView.findViewById(R.id.quizOpt0);
		opts[1] = (Button) curView.findViewById(R.id.quizOpt1);
		opts[2] = (Button) curView.findViewById(R.id.quizOpt2);
		opts[3] = (Button) curView.findViewById(R.id.quizOpt3);
		
		// setting question and options..
		curQuest = questions.get(curViewId);
		ques.setText(curQuest.getQues());
		String[] stropt = curQuest.getOptions();
		for(int i = 0; i < 4; i++)
			opts[i].setText(prefix[i] + stropt[i]);
		quesno.setText((curViewId + 1) + "/10");
		answered = false;
	}
	
	public void resetOptions(int drawable)
	{
		for(int i = 0; i < 4; i++)
			opts[i].setBackgroundResource(drawable);
	}
	
	public void onSubmit(View v)
	{
		Button btn = (Button) v;
		// submitting the answer...
		if(btn.getText().toString().equalsIgnoreCase("submit"))
		{
			if(answered)
			{
				// checking answer..
				this.resetOptions(R.drawable.btn_wrong);
				opts[curQuest.getAns()].setBackgroundResource(R.drawable.btn_correct);
				
				for(int i = 0; i < 4; i++)
					opts[i].setClickable(false);
				if(curSel == curQuest.getAns())
				{
					correctAns++;
					score.setText(Integer.toString(correctAns * 10));
				}
				if(curViewId == 9)
					btn.setText("Result");
				else
					btn.setText("Next");
			}
			else
				Commons.showToast("No answere selected", true);
			
		}
		// showing next question...
		else if(btn.getText().toString().equalsIgnoreCase("next"))
		{
			if(curViewId < 9)
			{
				vf.showNext();
				btn.setText("Submit");
				curViewId = vf.getDisplayedChild();
				curQuest = questions.get(curViewId);
				assignViews();
			}
		}
		// Show final Result with data...
		else
		{
			dbh.addResult(correctAns * 10);
			XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
			dataset.addSeries(dbh.getGraphValue());
			
			XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
			XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
			
			renderer.setColor(Color.BLUE);
			renderer.setDisplayChartValues(true);
			renderer.setPointStyle(PointStyle.DIAMOND);
			renderer.setFillPoints(true);
			
			mRenderer.addSeriesRenderer(renderer);
			mRenderer.setShowAxes(true);
			mRenderer.setShowLegend(true);
			mRenderer.setShowLabels(true);
			
			Intent intent = ChartFactory.getLineChartIntent(this, dataset, mRenderer, "Line Graph Title");
			startActivity(intent);
			finish();
		}
	}
	
	public void onAnswer(View v)
	{
		resetOptions(R.drawable.btn_options);
		switch(v.getId())
		{
			case R.id.quizOpt0:
				curSel = 0;
				break;
				
			case R.id.quizOpt1:
				curSel = 1;
				break;
				
			case R.id.quizOpt2:
				curSel = 2;
				break;
				
			case R.id.quizOpt3:
				curSel = 3;
				break;
		}
		v.setBackgroundResource(R.drawable.btn_purple);
		answered = true;
	}
}