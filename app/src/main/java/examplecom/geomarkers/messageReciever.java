package examplecom.geomarkers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DN on 09.08.2016.
 */
public class messageReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       context.startService(new Intent(context, dome.class));
    }
}