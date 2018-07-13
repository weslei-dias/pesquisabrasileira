package com.domain.androidcrud;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PopbeRequest extends AsyncTask<Void, Void, String> {
    private WeakReference<MainActivity> activity;

    public PopbeRequest(MainActivity activity ){
        this.activity = new WeakReference<>( activity );
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.get().lockFields( true );
    }

    @Override
    protected String doInBackground(Void... voids) {

        try{
            OkHttpClient client = new OkHttpClient();

            String url = "http://localhost:8080/demo/";

            Request.Builder builder = new Request.Builder();

            builder.url(url);

            MediaType mediaType =
                    MediaType.parse("application/json; charset=utf-8");

            RequestBody body = RequestBody.create(mediaType, "teste");
            builder.post(body);

            Request request = builder.build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                String jsonDeResposta = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String address) {
        super.onPostExecute(address);
    }
}
