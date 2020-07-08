package com.example.sanapruebados;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sanapruebados.entidades.Usuario;

public class inicio extends AppCompatActivity {
Button btsalir;
TextView nombre;
int id=0;
Usuario u;
daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        nombre=(TextView)findViewById(R.id.TTnombre);
        btsalir=(Button)findViewById(R.id.salir);

        btsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(inicio.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
        Bundle b=getIntent().getExtras();
        id=b.getInt("ID");
        dao=new daoUsuario(this);
        u=dao.getUsuarioById(id);
        nombre.setText("BIENVENIDO "+u.getNombre()+" "+u.getApellido());


    }
}
