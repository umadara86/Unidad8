package com.example.ivan.unidad8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationListener {

    private GoogleMap mapa;

    //EJERCICIO REDES
    private static final long TIEMPO_MIN = 10 * 1000; // 10 segundos
    private static final long DISTANCIA_MIN = 5; // 5 metros
    private static final int SOLICITUD_PERMISO_ACCURACY_FINE = 0;

    private LocationManager manejador;
    private String proveedor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarMapa();
    }

    private void iniciarMapa(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obtenemos el mapa de forma asíncrona (notificará cuando esté listo)
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
            mapFragment.getMapAsync(this);

            //EJERCICIO REDES
            manejador = (LocationManager) getSystemService(LOCATION_SERVICE);

            Criteria criterio = new Criteria();
            criterio.setCostAllowed(false);
            criterio.setAltitudeRequired(false);
            criterio.setAccuracy(Criteria.ACCURACY_FINE);
            proveedor = manejador.getBestProvider(criterio, true);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        } else {
            solicitarPermisoLocaclizacion();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        //LatLng UPV = new LatLng(39.481106, -0.340987); //Nos ubicamos en la UPV
        //mapa.addMarker(new MarkerOptions().position(UPV).title("Marker UPV"));
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(false);
            mapa.getUiSettings().setCompassEnabled(true);

        } else {
            Toast toast1 = Toast.makeText(getApplicationContext(), "la aplicación necesita los permisos de posicionamiento", Toast.LENGTH_LONG);
            toast1.show();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

 /*       public void animateCamera(View view) {
            if (mapa.getMyLocation() != null) mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mapa.getMyLocation().getLatitude(),

                            mapa.getMyLocation().getLongitude()), 15));
        }*/

    //METODOS DEL CICLO DE VIDA

    @Override
    protected void onResume() {
        super.onResume();
        manejador.requestLocationUpdates(proveedor, TIEMPO_MIN, DISTANCIA_MIN, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        manejador.removeUpdates(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    void solicitarPermisoLocaclizacion() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission. ACCESS_FINE_LOCATION },SOLICITUD_PERMISO_ACCURACY_FINE);
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, SOLICITUD_PERMISO_ACCURACY_FINE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_ACCURACY_FINE) {
            if (grantResults.length== 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast toast2 = Toast.makeText(getApplicationContext(), "Permisos de posicionamiento concedidos.", Toast.LENGTH_LONG);
                toast2.show();
                iniciarMapa();
            } else {
                Toast toast2 = Toast.makeText(getApplicationContext(), "Permisos de posicionamiento denagados.", Toast.LENGTH_LONG);
                toast2.show();
            }
        }
    }

}

