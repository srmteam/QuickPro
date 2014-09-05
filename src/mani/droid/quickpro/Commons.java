package mani.droid.quickpro;

import android.content.Context;
import android.widget.Toast;

public class Commons {
	
	public static Context context;
	public static Toast toast;
		
	public static void showToast(String msg, boolean isShort) {
		
		int dur;
		if(isShort)
			dur = Toast.LENGTH_SHORT;
		else
			dur = Toast.LENGTH_LONG;
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, msg, dur);
		toast.show();
	}
}