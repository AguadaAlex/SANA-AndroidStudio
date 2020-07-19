package com.example.sanapruebados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sanapruebados.Utilidades.utilidades;
import com.example.sanapruebados.entidades.Usuario;

import java.util.ArrayList;

public class daoUsuario {
    Context c;
    Usuario u;
    ArrayList<Usuario>Lista;
    SQLiteDatabase db;
    public daoUsuario(){}
    public daoUsuario(Context c) {
        this.c = c;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(c,"bd_usuarios",null,1);
        db=conn.getWritableDatabase();
        u=new Usuario();
    }
    public Boolean insertUsuario(Usuario u){
    if (buscar(u.getNombreUsuario())==0){
        ContentValues values= new ContentValues();
        values.put(utilidades.CAMPO_ROL,u.getRol());
        values.put(utilidades.CAMPO_NOMBREUSUARIO,u.getNombreUsuario());
        values.put(utilidades.CAMPO_NOMBRE,u.getNombre());
        values.put(utilidades.CAMPO_APELLIDO,u.getApellido());
        values.put(utilidades.CAMPO_PASSWORD,u.getPassword());
        values.put(utilidades.CAMPO_MAIL,u.getMail());
        return (db.insert(utilidades.TABLA_USUARIO,null,values)>0);

    }else {
        return false;
    }

    }
    public int buscar(String u){
        int x=0;
        Lista=selectUsuario();
        //VERIFICAR Q NO EXISTA OTRO USUARIO REGISTRADO
        for (Usuario us:Lista){
            if (us.getNombreUsuario().equals(u)){
                x++;
            }

        }
        return x;
    }
    //OBTENGO LISTA CON TODOS LOS USUARIOS DE LA BD
    public ArrayList<Usuario> selectUsuario(){
        ArrayList<Usuario>lista=new ArrayList<Usuario>();
        lista.clear();
        Cursor cr=db.rawQuery("select * from usuarios",null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                Usuario u=new Usuario();
                u.setId(cr.getInt(0));
                u.setRol(cr.getInt(1));
                u.setNombreUsuario(cr.getString(2));
                u.setNombre(cr.getString(3));
                u.setApellido(cr.getString(4));
                u.setPassword(cr.getString(5));
                u.setMail(cr.getString(6));
                lista.add(u);
            }while (cr.moveToNext());
        }
        return lista;
    }
    public int login(String u,String p){
        int a=0;
        Cursor cr=db.rawQuery("select * from usuarios",null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                if (cr.getString(2).equals(u)&&cr.getString(5).equals(p)){
                    a++;
                }
            }while (cr.moveToNext());
        }
        return a;
    }
    //RETORNAR USUARIO DE LA DB
    public Usuario getUsuario(String u,String p){
        Lista=selectUsuario();
        for (Usuario us:Lista){
            if (us.getNombreUsuario().equals(u)&&us.getPassword().equals(p)){
                return us;
            }
        }
    return null;
    }
    public Usuario getUsuarioById(int id){
        Lista=selectUsuario();
        for (Usuario us:Lista){
            if (us.getId()==id){
                return us;
            }
        }
        return null;
    }
}
