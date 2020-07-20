package com.example.sanapruebados.MDetalleAdiccion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.sanapruebados.MainActivity;
import com.example.sanapruebados.mapaMomento;
import com.example.sanapruebados.miInicio;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sanapruebados.R;
import com.example.sanapruebados.MDetalleAdiccion.dummy.DummyContent;
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
    private Button btMapa;
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

        // Show the dummy content as text in a TextView.
        /*if (mItem != null) {*/
        ((TextView) rootView.findViewById(R.id.TVDirecM)).setText(momento.getDireccion());
            ((TextView) rootView.findViewById(R.id.txEsDetM)).setText(momento.getEstado());
     /*   }*/
        btMapa=(Button)rootView.findViewById(R.id.BTDetailM);
        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getActivity(), mapaMomento.class);
                startActivity(i2);
            }
        });

        return rootView;
    }
}
