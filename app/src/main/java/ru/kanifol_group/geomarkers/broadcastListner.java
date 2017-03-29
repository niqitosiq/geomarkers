package ru.kanifol_group.geomarkers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class broadcastListner extends BroadcastReceiver {
    public broadcastListner() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context,Handwheel.class));
    }
}
