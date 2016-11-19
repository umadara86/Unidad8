package com.example.ivan.unidad8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ivan on 6/11/16.
 */

public class ReceptorSMS extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

      Intent i = new Intent(context, ServicioMusica.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(i);
    }





}

