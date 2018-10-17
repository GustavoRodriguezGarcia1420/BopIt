package com.example.pc_36.bopit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    int accion, contador;
    boolean gizquierda, gderecha, agita, bopit;
    ImageView cambioImagen, imagenStart;
    TextView textResultado, orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager manager = (SensorManager)getSystemService( SENSOR_SERVICE );
        textResultado = findViewById(R.id.textResultado);
        orientation = findViewById(R.id.orientation);
        cambioImagen = (ImageView)findViewById(R.id.cambioImagen);

        manager.registerListener( this,
                manager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER ),
                SensorManager.SENSOR_DELAY_GAME );

        cambioImagen.setImageResource(R.drawable.logo);
        MediaPlayer voz = MediaPlayer.create( this, R.raw.bopit);
        voz.start();

        imagenStart = (ImageView)findViewById(R.id.imageStart);
        imagenStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador = 0;
                juego( true );
            }
        });

    }

    public void juego( boolean res ) {
        if( res ){
            try {
                Thread.sleep(1600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            accion = (int)(Math.random() * 3) + 1;

            if( accion == 1 ){
                cambioImagen.setImageResource(R.drawable.izquierda);
                MediaPlayer jarvis = MediaPlayer.create( this, R.raw.giraizq );
                jarvis.start();
                orientation.setText("Gira izquierda!");
                gizquierda = true;
                gderecha = false; agita = false;
            }
            }
            if( accion == 2 ){
                cambioImagen.setImageResource(R.drawable.derecha);
                MediaPlayer voz = MediaPlayer.create( this, R.raw.gderecha );
                voz.start();
                orientation.setText("Gira derecha!");
                gderecha = true;
                gizquierda = false; agita = false;
            }
            if( accion == 3 ){
                cambioImagen.setImageResource(R.drawable.agita);
                MediaPlayer voz = MediaPlayer.create( this, R.raw.agita );
                voz.start();
                orientation.setText("Agita!");
                agita = true;
                gderecha = false; gizquierda = false;
            }
            /*if( accion == 4 ){
                MediaPlayer voz = MediaPlayer.create( this, R.raw.bopit);
                voz.start();
                orientation.setText("Golpea!!");
                cambioImagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contador++;
                    }

                });

            }*/

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if( gizquierda ){
            int valor = (int) event.values[0];
            if ( valor > 10  ){
                contador++;
                textResultado.setText("ronda: " + contador );
                gizquierda = false;
                juego( true );
            }
        }
        if( gderecha ){
            int valor = (int) event.values[0];
            if ( valor < -10 ){
                contador++;
                textResultado.setText("ronda: " + contador );
                gderecha = false;
                juego( true );
            }
        }
        if( agita ){
            int valor = (int) event.values[1];
            if ( valor < 0 ){
                contador++;
                textResultado.setText("ronda: " + contador );
                agita = false;
                juego( true );
            }
        }
        /*if( bopit ){
            int valor = (int) event.values[1];
            if ( valor < 0 ){
                contador++;
                textResultado.setText("ronda: " + contador );
                bopit = false;
                juego( true );
            }
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
