package examplecom.geomarkers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by DN on 09.08.2016.
 */
public class messageReceiver extends BroadcastReceiver {
    Context mContext;
    public messageReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        context.startService(new Intent(context, dome.class));
        }
    }

