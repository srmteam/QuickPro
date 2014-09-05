package mani.droid.quickpro;

import java.io.IOException;

import mani.droid.db.DBHelper;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;

public class SplashActivity extends Activity {

	protected boolean _active = true;
    protected int _splashTime = 5000;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Commons.context = this;
		Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                	DBHelper dbh = new DBHelper(SplashActivity.this);
                	dbh.createDatabase();
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        	}
                    	}
                	}
                catch(InterruptedException e) {
                    // do nothing
                	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                finally {
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                	}
            	}
        	};
        	splashThread.start();
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
    
    @Override
    public void onBackPressed()
    {
    	
    }
}
