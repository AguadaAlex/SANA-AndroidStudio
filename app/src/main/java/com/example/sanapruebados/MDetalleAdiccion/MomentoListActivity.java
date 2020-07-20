package com.example.sanapruebados.MDetalleAdiccion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sanapruebados.R;

import com.example.sanapruebados.altaMomentos;
import com.example.sanapruebados.daoMomento;
import com.example.sanapruebados.entidades.Momento;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

/**
 * An activity representing a list of Momentos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MomentoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MomentoListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private ArrayList<Momento> listaMomentos;
    private daoMomento daoM;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momento_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(), altaMomentos.class);
                startActivity(i2);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        if (findViewById(R.id.momento_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.momento_list);
        assert recyclerView != null;
        daoM=new daoMomento(this);
        listaMomentos=daoM.listaMomentosDB();
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,listaMomentos, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final MomentoListActivity mParentActivity;
        private final ArrayList<Momento> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Momento item = (Momento) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable("objeto",item);
                    MomentoDetailFragment fragment = new MomentoDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.momento_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MomentoDetailActivity.class);
                    Bundle bun=new Bundle();
                    bun.putSerializable("objeto",item);
                    intent.putExtras(bun);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(MomentoListActivity parent,
                                      ArrayList<Momento> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.momento_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.usuario.setText(mValues.get(position).getUsuario());
            holder.estado.setText(mValues.get(position).getEstado());
            holder.hora.setText(mValues.get(position).getFecha());
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
            final TextView usuario;
            final TextView estado;
            final TextView hora;
            final CircularImageView imagen;
            ViewHolder(View view) {
                super(view);
                usuario = (TextView) view.findViewById(R.id.ETUsuM);
                estado = (TextView) view.findViewById(R.id.ETEstadoM);
                hora = (TextView) view.findViewById(R.id.ETHoraM);
                imagen=(CircularImageView) view.findViewById(R.id.ImageCirM);
            }
        }
    }
}
