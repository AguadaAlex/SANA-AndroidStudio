package com.example.sanapruebados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.sanapruebados.Utilidades.utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(  Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(utilidades.CREAR_TABLA_USUARIO);
    db.execSQL(utilidades.CREAR_TABLA_ADICCION);
    //USUARIO ADMIN
        db.execSQL("INSERT INTO usuarios(id,rol,nombreUsuario,nombre,apellido,password,mail)VALUES(NULL,1,'admin','alex','aguada','1234','alex@gmail.com')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+utilidades.TABLA_USUARIO);
    db.execSQL("DROP TABLE IF EXISTS "+utilidades.TABLA_ADICCION);
    onCreate(db);
    }
}
