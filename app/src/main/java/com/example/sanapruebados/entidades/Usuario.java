package com.example.sanapruebados.entidades;

public class Usuario {
private Integer id;
private Integer rol;
private  String nombreUsuario;
private  String nombre;
private  String apellido;
private  String password;
private  String mail;

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

   /* public Usuario(Integer id,Integer rol,String nombreUsuario, String nombre, String apellido, String password, String mail) {
        this.id = id;
        this.rol=rol;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.mail = mail;
    }*/
   public Usuario(){}
    public String toString(){
        return "Usuario{"+
                "id="+id+
                ",Nombre='"+nombre+'\''+
                ",Apellido='"+apellido+'\''+
                ",Usuario='"+nombreUsuario+'\''+
                ",Password='"+password+'\''+
                '}';

    }

    public  Boolean inNull(){
        if (nombreUsuario.equals("")&nombre.equals("")&apellido.equals("")&password.equals("")&mail.equals("")){
            return false;

        }else {
        return true;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
