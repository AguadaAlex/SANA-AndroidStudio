package com.example.sanapruebados.Utilidades;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sanapruebados.R;
import android.media.MediaPlayer;

public class Reproductor{
    static MediaPlayer  reproductor;
    public static void Reproducir(Context act){
        if(reproductor==null){
            reproductor= android.media.MediaPlayer.create(act, R.raw.frog);
            reproductor.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                    noReproducir();
                }
            });
        }

        reproductor.start();
    }
    public static void noReproducir(){
        if(reproductor!=null){
            reproductor.release();
            reproductor=null;
        }
    }
}
