package com.example.armando.descarga;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBarHorizontal, progressBarCircular;
    Button btnProgress;
    public static final String URLL = "https://es.wikipedia.org/wiki/The_Dark_Side_of_the_Moon#/media/File:Optical-dispersion.png";
    ImageView  imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imagen);

        aniadirVistas();

        btnProgress.setOnClickListener(new Button.OnClickListener() {


            /*@Override
            public void onClick(View v) {
                new AsyncTask_load().execute();
                btnProgress.setClickable(false);
            }*/

            @Override
            public void onClick(View v){
                AsyncTask_load as = new AsyncTask_load();
                as.execute(new String[] {URLL});
            }

        });

    }



    private void aniadirVistas() {

        btnProgress = (Button)findViewById(R.id.btn1);

        progressBarHorizontal = (ProgressBar)findViewById(R.id.progressBarHorizontal);
        // progressBarHorizontal.setProgress(0);

        progressBarCircular = (ProgressBar)findViewById(R.id.progressBarCircular);
        // progressBarCircular.setProgress(0);

    }


    public class AsyncTask_load extends AsyncTask<String, Integer, Bitmap> {

        int progreso;


        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Descargando...", Toast.LENGTH_LONG).show();
            progreso = 0;
            progressBarCircular.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {



            while(progreso < 100){
                progreso++;
                publishProgress(progreso);
                SystemClock.sleep(50);
            }

            Bitmap map = null;
            for (String url : params) {
                map = downloadImage(url);
            }

            return map;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

            progressBarHorizontal.setProgress(values[0]);
            progressBarCircular.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            btnProgress.setClickable(true);
            progressBarCircular.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(result);
            Toast.makeText(MainActivity.this, "Imagen Descargada", Toast.LENGTH_LONG).show();

        }

        private Bitmap downloadImage(String url) {

            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap=BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            }catch (IOException e1) {
                e1.printStackTrace();
            }

            return bitmap;
        }

        private InputStream getHttpConnection(String urlString) throws IOException {

            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() ==HttpURLConnection.HTTP_OK ){
                    stream = httpConnection.getInputStream();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }

            return stream;
        }
    }
}
