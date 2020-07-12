package com.example.sanapruebados;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanapruebados.entidades.Usuario;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentLogin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText usuario;
    private  EditText password;
    private Button inicio;
    private View vista;
    daoUsuario dao;
    public fragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentLogin newInstance(String param1, String param2) {
        fragmentLogin fragment = new fragmentLogin();
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
        vista=inflater.inflate(R.layout.fragment_login, container, false);
        usuario=(EditText)vista.findViewById(R.id.LUsuario);
        password=(EditText)vista.findViewById(R.id.LPassword);
        inicio=(Button)vista.findViewById(R.id.BInicio);
        dao=new daoUsuario(getActivity().getApplicationContext());
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logueo();
            }
        });
        // Inflate the layout for this fragment
        return vista;
    }
    public void logueo(){
        String u=usuario.getText().toString();
        String p=password.getText().toString();
        if (u.equals("")&&p.equals("")){
            Toast.makeText(getContext().getApplicationContext(),"ERROR CAMPOS VACIOS",Toast.LENGTH_SHORT).show();
        }else if (dao.login(u,p)==1){
            //USUARIO LOGUEADO
            Usuario ux=dao.getUsuario(u,p);
            Toast.makeText(getContext().getApplicationContext(),"DATOS CORRECTOS",Toast.LENGTH_SHORT).show();
            Intent i2=new Intent(getActivity(),miInicio.class);
            i2.putExtra("ID",ux.getId());
            startActivity(i2);
            getActivity().finish();
        }else {
            Toast.makeText(getContext().getApplicationContext(),"USUARIO Y/O DATOS INCORRECTOS",Toast.LENGTH_SHORT).show();
        }
    }
}
