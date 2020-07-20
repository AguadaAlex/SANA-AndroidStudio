package com.example.sanapruebados.MDetalle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanapruebados.R;
import com.example.sanapruebados.MDetalle.dummy.DummyContent;
import com.example.sanapruebados.entidades.Adiccion;

/**
 * A fragment representing a single Adiccion detail screen.
 * This fragment is either contained in a {@link AdiccionListActivity}
 * in two-pane mode (on tablets) or a {@link AdiccionDetailActivity}
 * on handsets.
 */
public class AdiccionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private Adiccion adiccion;
    private TextView descripcion;
    private ImageView imagen;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AdiccionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("objeto")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Bundle objetoUsuario=getArguments();
            adiccion=null;
            adiccion=(Adiccion) objetoUsuario.getSerializable("objeto");

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(adiccion.getNombre());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adiccion_detail, container, false);
        /*descripcion=(TextView)rootView.findViewById(R.id.txDEsDet);
        descripcion.setText(adiccion.getDescripcion());*/
        // Show the dummy content as text in a TextView.
       /* if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.adiccion_detail)).setText(adiccion.getDescripcion());
        }*/
        ((TextView) rootView.findViewById(R.id.txDEsDet)).setText(adiccion.getDescripcion());
        byte[] imageAdic=adiccion.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(imageAdic,0,imageAdic.length);
        //holder.imagen.setImageBitmap(bitmap);
        ((ImageView) rootView.findViewById(R.id.imageView3)).setImageBitmap(bitmap);
        return rootView;
    }
}
