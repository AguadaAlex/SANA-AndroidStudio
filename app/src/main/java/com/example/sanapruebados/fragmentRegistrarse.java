package com.example.sanapruebados;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanapruebados.Utilidades.utilidades;
import com.example.sanapruebados.entidades.Usuario;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentRegistrarse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentRegistrarse extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     private View vista;
     private Integer rol;
    private EditText user;
    private EditText pass;
    private EditText nombre;
    private EditText apellido;
    private EditText correo;
    private Button registro;
    private TextInputLayout tusuario,tnombre,tapellido,tcontraseña,tcorreo;
    daoUsuario dao;

    public fragmentRegistrarse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentRegistrarse.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentRegistrarse newInstance(String param1, String param2) {
        fragmentRegistrarse fragment = new fragmentRegistrarse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_registrarse, container, false);
        user=(EditText)vista.findViewById(R.id.ETUsuario);
        pass=(EditText)vista.findViewById(R.id.ETContraseña);
        nombre=(EditText)vista.findViewById(R.id.ETNombre);
        apellido=(EditText)vista.findViewById(R.id.ETApellido);
        correo=(EditText)vista.findViewById(R.id.ETCorreo);
        registro=(Button)vista.findViewById(R.id.BTRegistro);
        tusuario=(TextInputLayout) vista.findViewById(R.id.tilUsuario);
        tcontraseña=(TextInputLayout)vista.findViewById(R.id.tilContraseña);
        tnombre=(TextInputLayout)vista.findViewById(R.id.tilNombre);
        tapellido=(TextInputLayout)vista.findViewById(R.id.tilApellido);
        tcorreo=(TextInputLayout)vista.findViewById(R.id.tilCorreo);

        rol=0;
        dao=new daoUsuario(getActivity().getApplicationContext());
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // VALIDAR CAMPOS DEL REGISTRO
                validarCampos();


            }
        }

        );
        // Inflate the layout for this fragment
        return vista;
    }
    private void validarCampos(){
        if (!ValidarEditText(user,tusuario,R.string.ErrorUsuario)){
            return;
        }
            if (!ValidarEditText(nombre,tnombre,R.string.ErrorNombre)){
                return;
            }
            if (!ValidarEditText(apellido,tapellido,R.string.ErrorApellido)){
                return;
            }
            if (!ValidarEditText(pass,tcontraseña,R.string.ErrorContraseña)){
            return;
        }
        if (!ValidarEditText(correo,tcorreo,R.string.ErrorCorreo)){
            return;
        }
        validarCorreo();


    }
    private void validarCorreo(){
        final String compruebaemail = correo.getEditableText().toString().trim();

        final String regex = "(?:[^<>()\\[\\].,;:\\s@\"]+(?:\\.[^<>()\\[\\].,;:\\s@\"]+)*|\"[^\\n\"]+\")@(?:[^<>()\\[\\].,;:\\s@\"]+\\.)+[^<>()\\[\\]\\.,;:\\s@\"]{2,63}";

        if (!compruebaemail.matches(regex))
        {
            //SIMULO TEXTO VACIO PARA TIRAR ERROR
            correo.setText("");
            if (!ValidarEditText(correo,tcorreo,R.string.ErrorCorreo)){
                return;
            }
        }else {
            //UNA VEZ QUE PASO TODAS LAS VALIDACIONES REGISTRO
            regUsuario();
            user.setText("");
            nombre.setText("");
            apellido.setText("");
            pass.setText("");
            correo.setText("");
        }
    }
    private boolean ValidarEditText(EditText editText,TextInputLayout textInputLayout,int errorString){
        if (editText.getText().toString().trim().isEmpty()){
            textInputLayout.setError(getString(errorString));
            return false;
        }else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    private  void  regUsuario(){
        Usuario u=new Usuario();
        u.setRol(rol);
        u.setNombreUsuario(user.getText().toString());
        u.setNombre(nombre.getText().toString());
        u.setApellido(apellido.getText().toString());
        u.setPassword(pass.getText().toString());
        u.setMail(correo.getText().toString());
        if (!u.inNull()){
            Toast.makeText(getActivity().getApplicationContext(),"ERROR:Campos vacios",Toast.LENGTH_SHORT).show();
        }else if (dao.insertUsuario(u)){
            Toast.makeText(getActivity().getApplicationContext(),"REGISTRO EXITOSO!!!",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity().getApplicationContext(),"USUARIO YA REGISTRADO!!!",Toast.LENGTH_SHORT).show();
        }

    }
    private void registrarUsuarios(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getActivity(),"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_ROL,rol);
        values.put(utilidades.CAMPO_NOMBREUSUARIO,user.getText().toString());
        values.put(utilidades.CAMPO_NOMBRE,nombre.getText().toString());
        values.put(utilidades.CAMPO_APELLIDO,apellido.getText().toString());
        values.put(utilidades.CAMPO_PASSWORD,pass.getText().toString());
        values.put(utilidades.CAMPO_MAIL,correo.getText().toString());
        Long  idResultado=db.insert(utilidades.TABLA_USUARIO,null,values);
        Toast.makeText(getActivity().getApplicationContext(),"ID REGISTRO"+idResultado,Toast.LENGTH_SHORT).show();

      /*String insert="INSERT INTO "+utilidades.TABLA_USUARIO
              +"("+utilidades.CAMPO_ID+","+utilidades.CAMPO_ROL
              +","+utilidades.CAMPO_NOMBREUSUARIO+","+utilidades.CAMPO_NOMBRE
              +","+utilidades.CAMPO_APELLIDO+","+utilidades.CAMPO_PASSWORD
              +","+utilidades.CAMPO_MAIL+")"
              +"VALUES('',0,'"+user.getText().toString()+"','"+nombre.getText().toString()+"','"+apellido.getText().toString()
              +"','"+pass.getText().toString()+"','"+correo.getText().toString()+"')";
      db.execSQL(insert);
      db.close();*/
    }
}
