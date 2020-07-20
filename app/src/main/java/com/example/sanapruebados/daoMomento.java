package com.example.sanapruebados;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sanapruebados.Utilidades.utilidades;
import com.example.sanapruebados.entidades.Adiccion;
import com.example.sanapruebados.entidades.Momento;
import com.example.sanapruebados.entidades.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class daoMomento {
    Context c;
    Momento u;
    Usuario usuario;
    ArrayList<Momento> Lista;
    SQLiteDatabase db;
    public daoMomento(Context c) {
        this.c = c;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(c,"bd_momentos",null,1);
        db=conn.getWritableDatabase();
        u=new Momento();

    }
    public Boolean insertMomento(Momento u){

        SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMMM 'del' yyyy");
        Date date = new Date();
        String fechita=dateFormat.format(date);
        ContentValues values= new ContentValues();
        values.put(utilidades.CAMPO_USUARIO_MOMENTO,u.getUsuario());
        values.put(utilidades.CAMPO_ESTADO_MOMENTO,u.getEstado());
        values.put(utilidades.CAMPO_DIRECCION_MOMENTO,u.getDireccion());
        values.put(utilidades.CAMPO_LATITUD_MOMENTO,u.getLatitud());
        values.put(utilidades.CAMPO_LONGITUD_MOMENTO,u.getLongitud());
        values.put(utilidades.CAMPO_IMAGEN_MOMENTO,u.getImage());
        values.put(utilidades.CAMPO_FECHA_MOMENTO,fechita);

        return (db.insert(utilidades.TABLA_MOMENTO,null,values)>0);


    }

    //OBTENGO LISTA CON TODOS LOS USUARIOS DE LA BD
    public ArrayList<Momento> listaMomentosDB(){
        ArrayList<Momento>lista=new ArrayList<Momento>();
        lista.clear();
        Cursor cr=db.rawQuery("select * from momentos",null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                Momento u=new Momento();
                u.setId(cr.getInt(0));
                u.setUsuario(cr.getString(1));
                u.setEstado(cr.getString(2));
                u.setDireccion(cr.getString(3));
                u.setLatitud(cr.getString(4));
                u.setLongitud(cr.getString(5));
                u.setImage(cr.getBlob(6));
                u.setFecha(cr.getString(7));
                lista.add(u);
            }while (cr.moveToNext());
        }
        return lista;
    }
}

