package com.example.sanapruebados.Utilidades;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sanapruebados.R;

public class CambiarColor {
    static public SharedPreferences mprefs;
    static public String tema = "ACTUAL";

    public static void preferencias(Context context) {
        mprefs = context.getSharedPreferences("TEMA_PREFERENCES", Context.MODE_PRIVATE);
        switch (mprefs.getString(tema, "Verde")) {
            case "Rojo":

                context.setTheme(R.style.Rojo);
                break;
            case "Verde":
                context.setTheme(R.style.Verde);
                break;
            case "Azul":
                context.setTheme(R.style.Azul);
                break;
            default:
                break;
        }
    }
}