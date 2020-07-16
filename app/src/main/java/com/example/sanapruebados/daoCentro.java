package com.example.sanapruebados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sanapruebados.Utilidades.utilidades;
import com.example.sanapruebados.entidades.Centro;

import java.util.ArrayList;

public class daoCentro {
    Context c;
    Centro u;
    ArrayList<Centro> Lista;
    SQLiteDatabase db;

    public daoCentro(Context c) {
        this.c = c;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(c,"bd_centros",null,1);
        db=conn.getWritableDatabase();
        u=new Centro();
    }
    public Boolean insertCentro(Centro u){

        ContentValues values= new ContentValues();
        values.put(utilidades.CAMPO_NOMBRE_CENTRO,u.getNombre());
        values.put(utilidades.CAMPO_DESCRIPCION_CENTRO,u.getDescripcion());
        values.put(utilidades.CAMPO_IMAGEN_CENTRO,u.getImage());
        values.put(utilidades.CAMPO_DIRECCION_CENTRO,u.getDireccion());
        return (db.insert(utilidades.TABLA_CENTRO,null,values)>0);


    }
    //OBTENGO LISTA CON TODOS LOS CENTROS DE LA BD
    public ArrayList<Centro> listaCentrosDB(){
        ArrayList<Centro>lista=new ArrayList<Centro>();
        lista.clear();
        Cursor cr=db.rawQuery("select * from centros",null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                Centro u=new Centro();
                u.setId(cr.getInt(0));
                u.setNombre(cr.getString(1));
                u.setDescripcion(cr.getString(2));
                u.setImage(cr.getBlob(3));
                u.setDireccion(cr.getString(4));

                lista.add(u);
            }while (cr.moveToNext());
        }
        return lista;
    }
}
