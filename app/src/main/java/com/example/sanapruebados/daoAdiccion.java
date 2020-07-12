package com.example.sanapruebados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sanapruebados.Utilidades.utilidades;
import com.example.sanapruebados.entidades.Adiccion;
import com.example.sanapruebados.entidades.Usuario;

import java.util.ArrayList;

public class daoAdiccion {
    Context c;
    Adiccion u;
    ArrayList<Adiccion> Lista;
    SQLiteDatabase db;
    public daoAdiccion(Context c) {
        this.c = c;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(c,"bd_adicciones",null,1);
        db=conn.getWritableDatabase();
        u=new Adiccion();
    }
    public Boolean insertAdiccion(Adiccion u){

            ContentValues values= new ContentValues();
            values.put(utilidades.CAMPO_NOMBRE_ADICCION,u.getNombre());
            values.put(utilidades.CAMPO_DESCRIPCION_ADICCION,u.getDescripcion());
            values.put(utilidades.CAMPO_IMAGEN_ADICCION,u.getImage());

            return (db.insert(utilidades.TABLA_ADICCION,null,values)>0);


        }
    //OBTENGO LISTA CON TODOS LOS USUARIOS DE LA BD
    public ArrayList<Adiccion> listaAdiccionesDB(){
        ArrayList<Adiccion>lista=new ArrayList<Adiccion>();
        lista.clear();
        Cursor cr=db.rawQuery("select * from adicciones",null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                Adiccion u=new Adiccion();
                u.setId(cr.getInt(0));
                u.setNombre(cr.getString(1));
                u.setDescripcion(cr.getString(2));
                u.setImage(cr.getBlob(3));
                lista.add(u);
            }while (cr.moveToNext());
        }
        return lista;
    }
}
