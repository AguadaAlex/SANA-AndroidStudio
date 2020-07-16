package com.example.sanapruebados.MDetalleAdiccion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sanapruebados.R;

import com.example.sanapruebados.MDetalleAdiccion.dummy.DummyContent;
import com.example.sanapruebados.daoAdiccion;
import com.example.sanapruebados.entidades.Adiccion;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of adicciones. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link AdiccionDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class AdiccionListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private ArrayList<Adiccion> listaAdicciones;
    private daoAdiccion daoA;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiccion_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (findViewById(R.id.adiccion_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //DRAWEER
        View recyclerView = findViewById(R.id.adiccion_list);
        assert recyclerView != null;
        daoA=new daoAdiccion(this);
        listaAdicciones=daoA.listaAdiccionesDB();
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,listaAdicciones, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final AdiccionListActivity mParentActivity;
        private final ArrayList<Adiccion> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adiccion item = (Adiccion) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable("objeto",item);
                    AdiccionDetailFragment fragment = new AdiccionDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.adiccion_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AdiccionDetailActivity.class);
                    Bundle bun=new Bundle();
                    bun.putSerializable("objeto",item);
                    intent.putExtras(bun);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(AdiccionListActivity parent,
                                      ArrayList<Adiccion>items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adiccion_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.nombre.setText("Adicción: "+mValues.get(position).getNombre());
            byte[] imageAdic=mValues.get(position).getImage();
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageAdic,0,imageAdic.length);
            holder.imagen.setImageBitmap(bitmap);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            /*final TextView mIdView;
            final TextView mContentView;*/
            final TextView nombre;

            final ImageView imagen;
            ViewHolder(View view) {
                super(view);
                nombre = (TextView) view.findViewById(R.id.id_text);

                imagen=(ImageView)view.findViewById(R.id.imageLis);
            }
        }
    }
}
