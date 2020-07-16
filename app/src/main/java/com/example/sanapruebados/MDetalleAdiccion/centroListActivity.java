package com.example.sanapruebados.MDetalleAdiccion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.example.sanapruebados.R;

import com.example.sanapruebados.MDetalleAdiccion.dummy.DummyContent;
import com.example.sanapruebados.daoAdiccion;
import com.example.sanapruebados.daoCentro;
import com.example.sanapruebados.entidades.Adiccion;
import com.example.sanapruebados.entidades.Centro;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of centros. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link centroDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class centroListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ArrayList<Centro>listaCentros;
    private daoCentro daoC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centro_list);

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

        if (findViewById(R.id.centro_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.centro_list);
        assert recyclerView != null;
        daoC=new daoCentro(this);
        listaCentros=daoC.listaCentrosDB();
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, listaCentros, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final centroListActivity mParentActivity;
        private final ArrayList<Centro> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Centro item = (Centro) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable("objeto",item);
                    centroDetailFragment fragment = new centroDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.centro_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, centroDetailActivity.class);
                    Bundle bun=new Bundle();
                    bun.putSerializable("objeto",item);
                    intent.putExtras(bun);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(centroListActivity parent,
                                      ArrayList<Centro> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.centro_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.nombre.setText("Centro: "+mValues.get(position).getNombre());
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
            final TextView nombre;

            final ImageView imagen;

            ViewHolder(View view) {
                super(view);
                nombre = (TextView) view.findViewById(R.id.id_textCen);

                imagen=(ImageView)view.findViewById(R.id.imageLisCen);
            }
        }
    }
}
