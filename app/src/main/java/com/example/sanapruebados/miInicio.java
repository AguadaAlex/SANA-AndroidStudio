package com.example.sanapruebados;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.res.Configuration;
        import android.graphics.Color;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.RequiresApi;
        import android.support.v4.app.Fragment;
        import android.util.TypedValue;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Menu;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.design.widget.NavigationView;

        import androidx.navigation.NavController;
        import androidx.navigation.Navigation;
        import androidx.navigation.ui.AppBarConfiguration;
        import androidx.navigation.ui.NavigationUI;

        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.sanapruebados.Utilidades.CambiarColor;
        import com.example.sanapruebados.Utilidades.Reproductor;
        import com.example.sanapruebados.entidades.Usuario;

        import java.util.Locale;

public class miInicio extends AppCompatActivity {
    int id=0;
    private AppBarConfiguration mAppBarConfiguration;
    Usuario u;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            CambiarColor.preferencias(this);
            setContentView(R.layout.activity_mi_inicio);
            Toolbar toolbar = findViewById(R.id.toolbar);
            TypedValue typedValue = new  TypedValue();
            getApplicationContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            final  int color = typedValue.data;
            toolbar.setBackgroundColor(color);
            setSupportActionBar(toolbar);
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
          /*  nombre=(TextView)findViewById(R.id.TTnombre);
            nombre.setText("bienvenido");*/
            Bundle b=getIntent().getExtras();
            id=b.getInt("ID");

            dao=new daoUsuario(this);
            u=dao.getUsuarioById(id);
            if(u.getRol()==1){
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.altaAdiccionesFragment,R.id.altaCentrosFragment,R.id.altaEstablecimientoFragment  )
                        .setDrawerLayout(drawer)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mi_inicio, menu);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Locale locale;
        String tema="ACTUAL";
        SharedPreferences mprefs;
        mprefs=getSharedPreferences("TEMA_PREFERENCES", Context.MODE_PRIVATE);
        Reproductor.Reproducir(this);
        switch(id) {
            case (R.id.ingles):
                Toast.makeText(this, "Cambiado a ingles", Toast.LENGTH_SHORT).show();
                locale = new Locale("chr-rUS");
                Configuration config = new Configuration(getResources().getConfiguration());
                Locale.setDefault(locale);
                config.setLocale(locale);
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                break;
            case (R.id.azul):
                Toast.makeText(this, "Cambiado a Azul", Toast.LENGTH_SHORT).show();
                mprefs.edit().putString(tema,"Azul").apply();
                recreate();
                break;
            case (R.id.verde):
                Toast.makeText(this, "Cambiado a Verde", Toast.LENGTH_SHORT).show();
                mprefs.edit().putString(tema,"Verde").apply();
                recreate();
                break;
            case (R.id.rojo):
                Toast.makeText(this, "Cambiado a Rojo", Toast.LENGTH_SHORT).show();
                mprefs.edit().putString(tema,"Rojo").apply();
                recreate();
                break;
            case (R.id.salir):
                fragmentLogin fragmentito=new fragmentLogin();
                fragmentito.cambiarEstadoButton(this,false);
                Intent i2 = new Intent(miInicio.this, MainActivity.class);
                startActivity(i2);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
