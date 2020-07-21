package com.example.sanapruebados;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sanapruebados.MDetalle.MomentoListActivity;
import com.example.sanapruebados.entidades.Momento;
import com.example.sanapruebados.entidades.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import static android.app.Activity.RESULT_OK;

public class altaMomentos extends AppCompatActivity {
    private Uri photoURI;
    daoMomento daoM;
    private String latid,longt,direct;
    private Button bseleccion, bguardar;
    private EditText estado;
    private ImageView imagen;
    final int REQUEST_CODE_GALLERY = 999;
    final int COD_FOTO = 10;
    private String currentPhotoPath = "";
    final int PERMISSIONS_REQUEST_CAMERA = 9;
    private LocationManager ubicacion;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String STRING_PREFERENCES = "sanapruebados.entidades.Usuario";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_momentos);
        daoM = new daoMomento(getApplicationContext());
        imagen = (ImageView) findViewById(R.id.IMCamara);
        estado = (EditText) findViewById(R.id.ETEstado);
        bseleccion = (Button) findViewById(R.id.BTSImagen);
        bguardar = (Button) findViewById(R.id.BTGuardar);
        bseleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarImagen();
            }

            private void CargarImagen() {
                final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
                final AlertDialog.Builder alertopciones = new AlertDialog.Builder(altaMomentos.this);
                alertopciones.setTitle("Seleccione una Opción");
                alertopciones.setItems(opciones, (new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which].equals("Tomar Foto")) {

                            /*   PERMISOS PARA CAMARA Y ALMACENAMIENTO*/

                            if (ContextCompat.checkSelfPermission(altaMomentos.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(altaMomentos.this,
                                    Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {


                                if (ActivityCompat.shouldShowRequestPermissionRationale(altaMomentos.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                } else {
                                    ActivityCompat.requestPermissions(altaMomentos.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            225);
                                }


                                if (ActivityCompat.shouldShowRequestPermissionRationale(altaMomentos.this,
                                        Manifest.permission.CAMERA)) {

                                } else {
                                    ActivityCompat.requestPermissions(altaMomentos.this,
                                            new String[]{Manifest.permission.CAMERA},
                                            226);
                                }
                            } else {

                                /*FUNCION TOMAR FOTO*/

                                TomarFoto();
                            }

                            Toast.makeText(getApplicationContext(), "Tomar Foto", Toast.LENGTH_SHORT).show();
                        } else {
                            if (opciones[which].equals("Cargar Imagen")) {
                                ActivityCompat.requestPermissions(altaMomentos.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);


                                Toast.makeText(getApplicationContext(), "Cargar Imagen", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }
                }


                ));
                alertopciones.show();
            }


        });
        //GEOLOCALIZACION

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        // ONCLICK GUARDAR
        bguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regMomento();
            }
        });

    }

    private void regMomento() {
        try {
            //FECHA Y USUARIO SE INSERTAN EN DAO
            Momento ad = new Momento();
            //OBTENGO SHARED PREFERENCE DEL USUARIO GUARDADO
            int usu = obtenerUsuarioLogueado();
            daoUsuario daoU = new daoUsuario(this);
            Usuario miUser = new Usuario();
            miUser = daoU.getUsuarioById(usu);
            //
            ad.setUsuario(miUser.getNombreUsuario());
            ad.setEstado(estado.getText().toString());
            ad.setDireccion(direct);
            ad.setLatitud(latid);
            ad.setLongitud(longt);
            ad.setImage(imageViewToByte(imagen));
            Toast.makeText(getApplicationContext(), "AGREGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            daoM.insertMomento(ad);
            estado.setText("");
            imagen.setImageResource(R.mipmap.ic_launcher);
            Intent i5 = new Intent(altaMomentos.this, MomentoListActivity.class);
            startActivity(i5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    public int obtenerUsuarioLogueado() {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getInt("id", 7);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "BACKUP" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "AGREGAANDO IMAGEN", Toast.LENGTH_SHORT).show();
            }
            return;

        } else if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String LOG_TAG = "mal";
                //El permiso se acepto.
                Log.d(LOG_TAG, "Write Permission Failed");
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                finish();

            } else {

                //El permiso NO fue aceptado.

            }
            return;
        }else if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void TomarFoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {


            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                 /*photoURI = FileProvider.getUriForFile(altaMomentos.this,
                        "com.example.sanapruebados",
                        photoFile);*/
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                imagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 5) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imagen.setImageBitmap(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //GEOLOCALIZACION

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direct=DirCalle.getAddressLine(0);
                    latid=String.valueOf(loc.getLatitude());
                    longt=String.valueOf(loc.getLongitude());

                   /* mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    /*    mensaje1.setText("Localización agregada");
        mensaje2.setText("");*/
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        altaMomentos altaM;

        public altaMomentos getMainActivity() {
            return altaM;
        }

        public void setMainActivity(altaMomentos altaM) {
            this.altaM = altaM;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();

            this.altaM.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
          /*  mensaje1.setText("GPS Desactivado");*/
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
         /*   mensaje1.setText("GPS Activado");*/
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
