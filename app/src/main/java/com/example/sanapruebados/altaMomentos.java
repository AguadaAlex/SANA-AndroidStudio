package com.example.sanapruebados;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;



import static android.app.Activity.RESULT_OK;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class altaMomentos extends AppCompatActivity {
    private Uri photoURI;
    private Button bseleccion,bguardar;
    private EditText estado;
    private ImageView imagen;
    final int REQUEST_CODE_GALLERY=999;
    final int COD_FOTO=10;
    private String currentPhotoPath="";
    final int PERMISSIONS_REQUEST_CAMERA=9;
    static final int REQUEST_TAKE_PHOTO = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_momentos);
        imagen=(ImageView)findViewById(R.id.IMCamara);
        estado=(EditText)findViewById(R.id.ETEstado);
        bseleccion=(Button)findViewById(R.id.BTSImagen);
        bguardar=(Button)findViewById(R.id.BTGuardar);
        bseleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarImagen();
            }

            private void CargarImagen() {
                final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
                final AlertDialog.Builder alertopciones=new AlertDialog.Builder(altaMomentos.this);
                alertopciones.setTitle("Seleccione una Opción");
                alertopciones.setItems(opciones,(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which].equals("Tomar Foto")){

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

                            Toast.makeText(getApplicationContext(),"Tomar Foto",Toast.LENGTH_SHORT).show();
                        }else {
                            if (opciones[which].equals("Cargar Imagen")){
                                ActivityCompat.requestPermissions(altaMomentos.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);


                                Toast.makeText(getApplicationContext(),"Cargar Imagen",Toast.LENGTH_SHORT).show();
                            }else {
                                dialog.dismiss();
                            }
                        }
                    }
                }


                ));
                alertopciones.show();
            }


        });

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
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if (requestCode==REQUEST_CODE_GALLERY){
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Intent intent=new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,REQUEST_CODE_GALLERY);
                }else {
                    Toast.makeText(getApplicationContext(),"AGREGAANDO IMAGEN",Toast.LENGTH_SHORT).show();
                }
            return;

        }else if (requestCode==PERMISSIONS_REQUEST_CAMERA ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String LOG_TAG="mal";
                //El permiso se acepto.
                Log.d(LOG_TAG, "Write Permission Failed");
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                finish();

            } else {

                //El permiso NO fue aceptado.

            }
            return;
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
                intent.putExtra("return-data",true);
                startActivityForResult(intent,REQUEST_TAKE_PHOTO);
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==REQUEST_CODE_GALLERY && resultCode ==RESULT_OK && data !=null){
            Uri uri=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imagen.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }else if (requestCode == REQUEST_TAKE_PHOTO && resultCode ==RESULT_OK  ) {
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                imagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode==5) {
            Bitmap image=(Bitmap) data.getExtras().get("data");
            imagen.setImageBitmap(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
