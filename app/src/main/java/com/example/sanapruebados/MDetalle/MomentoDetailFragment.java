package com.example.sanapruebados.MDetalle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.sanapruebados.MainActivity;
import com.example.sanapruebados.MapsMomentos;
import com.example.sanapruebados.altaMomentos;
import com.example.sanapruebados.daoMomento;
import com.example.sanapruebados.miInicio;
import com.example.sanapruebados.updateMomento;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanapruebados.R;
import com.example.sanapruebados.MDetalle.dummy.DummyContent;
import com.example.sanapruebados.entidades.Momento;

/**
 * A fragment representing a single Momento detail screen.
 * This fragment is either contained in a {@link MomentoListActivity}
 * in two-pane mode (on tablets) or a {@link MomentoDetailActivity}
 * on handsets.
 */
public class MomentoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private Momento momento;
    private TextView estado;
    private Button botonmapa;
    private ImageView imagen;
    private daoMomento daoM;
 /*   private ImageView imagen;*/

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("objeto")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
           /* mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));*/
            Bundle objetoMomento=getArguments();
            momento=null;
            momento=(Momento) objetoMomento.getSerializable("objeto");

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(momento.getUsuario());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.momento_detail, container, false);
        imagen=(ImageView)rootView.findViewById(R.id.IVMoment);
        // Show the dummy content as text in a TextView.
        /*if (mItem != null) {*/


        botonmapa=(Button)rootView.findViewById(R.id.BTMapsM);
        botonmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsMomentos.class);
                Bundle bun=new Bundle();
                bun.putSerializable("objeto",momento);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });
        byte[] imageMon=momento.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(imageMon,0,imageMon.length);
        //holder.imagen.setImageBitmap(bitmap);
       /* ((ImageView) rootView.findViewById(R.id.IVMoment)).setImageBitmap(bitmap);*/
       /* MENU DE DESCARGA  EDICION Y BORRAR*/
        imagen.setImageBitmap(bitmap);
        imagen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuABM();
                Toast.makeText(getContext(),"aqui edicion",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        ((TextView) rootView.findViewById(R.id.TVDirecM)).setText("Dirección: "+momento.getDireccion());
            ((TextView) rootView.findViewById(R.id.txEsDetM)).setText(momento.getEstado());
     /*   }*/

        return rootView;
    }

    /*MENU ALTA BAJAS Y MODIFICACIONES*/
    private void menuABM(){
        final CharSequence[] opciones = {"Descargar imagen", "Editar", "Borrar"};
        final AlertDialog.Builder alertopciones = new AlertDialog.Builder(getActivity());
        alertopciones.setTitle("Seleccione una Opción");
        alertopciones.setItems(opciones, (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Editar")) {
                    Intent i7 = new Intent(getContext(), updateMomento.class);
                    Bundle mom=new Bundle();
                    mom.putSerializable("objeto",momento);
                    i7.putExtras(mom);
                    startActivity(i7);




                } else {
                    if (opciones[which].equals("Borrar")) {

                        daoM=new daoMomento(getContext());
                        daoM.deleteMomento(momento);
                        Intent i6 = new Intent(getContext(), MomentoListActivity.class);
                        startActivity(i6);

                    } else {
                        dialog.dismiss();
                    }
                }
            }
        }


        ));
        alertopciones.show();
    }
}
