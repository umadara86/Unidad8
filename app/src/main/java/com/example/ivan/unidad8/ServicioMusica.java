package com.example.ivan.unidad8;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Ivan on 6/11/16.
 */

public class ServicioMusica extends Service {

    MediaPlayer reproductor;
    private static final int ID_NOTIFICACION_CREAR = 1;

    @Override public void onCreate() {
        Toast.makeText(this,"Servicio creado",
            Toast.LENGTH_SHORT).show();
        reproductor = MediaPlayer.create(this, R.raw.audio);
    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {

        //NOTIFICACION
        NotificationCompat.Builder notific = new NotificationCompat.Builder(this)
                .setContentTitle("Creando Servicio de Música")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("información adicional");

        PendingIntent intencionPendiente = PendingIntent.getActivity( this, 0, new Intent(this, MapsActivity.class), 0);
        notific.setContentIntent(intencionPendiente);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICACION_CREAR, notific.build());


        //SERVICIO
        Toast.makeText(this,"Servicio arrancado "+ idArranque, Toast.LENGTH_SHORT).show();
                reproductor.start();
        return START_STICKY; }


    @Override public void onDestroy() {
        Toast.makeText(this,"Servicio detenido",  Toast.LENGTH_SHORT).show();
            reproductor.stop();
    }


    @Override public IBinder onBind(Intent intencion) {
        return null; }
}

