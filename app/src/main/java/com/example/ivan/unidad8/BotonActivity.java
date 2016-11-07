package com.example.ivan.unidad8;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ivan on 7/11/16.
 */

public class BotonActivity extends Activity{

    public void volver (View view) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void detener (View view) {
        Intent i = new Intent(this, ServicioMusica.class);
        stopService(i);
    }


}

