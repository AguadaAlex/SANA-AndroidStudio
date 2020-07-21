package com.example.sanapruebados;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sanapruebados.Utilidades.CambiarColor;

public class establecimientos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CambiarColor.preferencias(this);
        setContentView(R.layout.activity_establecimientos);
    }
}
