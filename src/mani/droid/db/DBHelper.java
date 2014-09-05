package mani.droid.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.achartengine.model.TimeSeries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;

public class DBHelper extends SQLiteOpenHelper{

	private static String DB_PATH = "/data/data/mani.droid.quickpro/databases/";
	private static String DB_NAME = "quickproDB"; 
	private final Context myContext;
	SQLiteDatabase db;
	private static String TB_QUES = "question";
	private static String TB_RES = "result";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}
	
	public void createDatabase()throws IOException {
		boolean dbExist = checkDataBase();
		if(dbExist)
		{
			// do nothing
		}
		else
		{
			this.getReadableDatabase();
			try
			{
				copyDataBase();
			}
			catch(Exception e)
			{
				throw new Error("Error Copying Database");
			}
			this.close();
		}
	}
	public void copyDataBase()throws IOException {
		InputStream mydb = myContext.getAssets().open(DB_NAME);
		String dbname = DB_PATH + DB_NAME;
		OutputStream newdb = new FileOutputStream(dbname);
		byte[] buff = new byte[1024];
		int len = buff.length;
		while ((len = mydb.read(buff))>0){
    		newdb.write(buff, 0, len);
    	}
		newdb.flush();
		mydb.close();
		newdb.close();
	}
	private boolean checkDataBase() {
		File dbname = new File(DB_PATH + DB_NAME);
		return dbname.exists();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Quest> getQuestions()
	{
		ArrayList<Quest> ques = new ArrayList<Quest>();
		SparseArray<Quest> mapQues = new SparseArray<Quest>();
		db = this.getReadableDatabase();
		Cursor cur;
		Random rand = new Random();
		String sel = "quesno = ?";
		while(mapQues.size() <= 10)
		{
			int rnd = rand.nextInt(50) + 1;
			Log.v("Rnd", "" + rnd);
			String[] selArgs = new String[]{ Integer.toString(rnd) };
			cur = db.query(TB_QUES, null, sel, selArgs, null, null, null);
			if(cur.getCount() > 0)
			{
				cur.moveToFirst();
				Quest tmp = new Quest();
				tmp.setQuesno(rnd);
				tmp.setQues(cur.getString(cur.getColumnIndex("ques")));
				String[] opts = new String[4];
				opts[0] = cur.getString(cur.getColumnIndex("opt1"));
				opts[1] = cur.getString(cur.getColumnIndex("opt2"));
				opts[2] = cur.getString(cur.getColumnIndex("opt3"));
				opts[3] = cur.getString(cur.getColumnIndex("opt4"));
				tmp.setOptions(opts);
				tmp.setAns(cur.getInt(cur.getColumnIndex("ans")));
				mapQues.put(rnd, tmp);
			}
		}
		db.close();
		for(int i = 0; i < 10; i++)
			ques.add(mapQues.get(mapQues.keyAt(i)));
		return ques;
	}
	
	public void addResult(int score)
	{
		db = this.getReadableDatabase();
		String date = DateFormat.format("dd/MM/yyyy", new Date()).toString();
		ContentValues cv = new ContentValues();
		cv.put("date", date);
		cv.put("score", score);
		db.insert(TB_RES, null, cv);
		db.close();
	}
	
	public TimeSeries getGraphValue()
	{
		TimeSeries ts = new TimeSeries("Score");
		db = this.getReadableDatabase();
		Cursor cur = db.query(TB_RES, null, null, null, null, null, null);
		if(cur.getCount() > 0)
		{
			cur.moveToFirst();
			for(cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext())
				ts.add(cur.getInt(cur.getColumnIndex("resId")), cur.getDouble(cur.getColumnIndex("score")));
		}
		return ts;
	}
}