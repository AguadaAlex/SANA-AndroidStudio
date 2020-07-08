package com.example.sanapruebados.Utilidades;

public class utilidades {
    //CONSTANTES CAMPOS TABLA DE USUARIO
    public static final String TABLA_USUARIO="usuarios";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_ROL="rol";
    public static final String CAMPO_NOMBREUSUARIO="nombreUsuario";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_APELLIDO="apellido";
    public static final String CAMPO_PASSWORD="password";
    public static final String CAMPO_MAIL="mail";


    public  static final String CREAR_TABLA_USUARIO="CREATE TABLE "+""+TABLA_USUARIO+"("+CAMPO_ID+""+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CAMPO_ROL+" INTEGER,"+CAMPO_NOMBREUSUARIO+" TEXT,"+CAMPO_NOMBRE+" TEXT,"+CAMPO_APELLIDO+" TEXT,"+CAMPO_PASSWORD+" TEXT,"+CAMPO_MAIL+" TEXT)";
}
