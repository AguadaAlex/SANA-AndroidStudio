package com.example.sanapruebados.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sanapruebados.MDetalle.AdiccionListActivity;
import com.example.sanapruebados.MDetalle.MomentoListActivity;
import com.example.sanapruebados.MDetalle.centroListActivity;
import com.example.sanapruebados.R;
import com.example.sanapruebados.daoUsuario;
import com.example.sanapruebados.entidades.Usuario;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView nombre;
    Usuario u;
    daoUsuario dao;
    private Button Badicciones,Bespacios,Bmomentos;
    int id=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
        Bmomentos=(Button)root.findViewById(R.id.BTMoment);
        Badicciones=(Button)root.findViewById(R.id.b_adiccion);
        Badicciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(getActivity(), AdiccionListActivity.class);
                startActivity(i3);
            }
        });
        Bespacios=(Button)root.findViewById(R.id.b_espacio);
        Bespacios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent i2=new Intent(getActivity(),centroListActivity.class);
                        startActivity(i2);
                        
                    }
                });
        Bmomentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4=new Intent(getActivity(), MomentoListActivity.class);
                startActivity(i4);
            }
        });
            }
        });


        return root;
    }
}
