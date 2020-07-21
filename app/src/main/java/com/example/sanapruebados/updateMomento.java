package com.example.sanapruebados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sanapruebados.MDetalle.MomentoListActivity;
import com.example.sanapruebados.entidades.Momento;

public class updateMomento extends AppCompatActivity {
    private EditText estado;
    private Button bguardarestado;
    private Momento momento;
    private daoMomento daoM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_momento);
        estado=(EditText)findViewById(R.id.ETUpdateM);
        bguardarestado=(Button)findViewById(R.id.BTGuardarMom);
        bguardarestado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle objetoMomento=getIntent().getExtras();
                momento=null;
                momento=(Momento) objetoMomento.getSerializable("objeto");
                daoM=new daoMomento(updateMomento.this);
                daoM.updateMomento(momento,estado.getText().toString());
                Intent i6 = new Intent(updateMomento.this, MomentoListActivity.class);
                startActivity(i6);
            }
        });
        DisplayMetrics medidasVentana=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho=medidasVentana.widthPixels;
        int largo=medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85),(int)(largo * 0.5));
    }
}
