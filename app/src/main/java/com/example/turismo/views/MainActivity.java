package com.example.turismo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.turismo.R;
import com.example.turismo.adapter.AdapterLugares;
import com.example.turismo.bean.Lugares;
import com.example.turismo.service.web;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListaLugares; //lista grafica

    private List<Lugares> lista_lugares; // lista de lugares
    private AdapterLugares adaptadorLugar; // objeto adapter
    private LinearLayoutManager layoutManager; //controlador

    private void EnlazarVistas(){
        rvListaLugares= findViewById(R.id.rvListaLugares);
    }

    private void InicializarObjetos(){
        lista_lugares= new ArrayList<>();

        layoutManager= new LinearLayoutManager(this);
        rvListaLugares.setLayoutManager(layoutManager);

        adaptadorLugar= new AdapterLugares(lista_lugares, this);
        rvListaLugares.setAdapter(adaptadorLugar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnlazarVistas();

        InicializarObjetos();

        getLugares();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mnu01:
                startActivity(new Intent(this, profile.class));
                return true;
            case R.id.mnu02:
                startActivity(new Intent(this, Myreserves.class));
                return true;
            case R.id.mnu03:
                startActivity(new Intent(this, login.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getLugares(){

        lista_lugares.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = web.RAIZ_WS + web.GET_LUGARES;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array= new JSONArray(response);

                            if(array.length()>0){

                                for (int i=0; i< array.length(); i++){

                                    JSONObject obj= (JSONObject) array.get(i);
                                    Lugares cliente= new Lugares(obj.getInt("id_lugar"),
                                            obj.getString("nombre"),
                                            obj.getString("ubigeo"),
                                            obj.getString("costo"),
                                            obj.getString("imagen")
                                    );
                                    lista_lugares.add(cliente);
                                }

                                adaptadorLugar.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "EXCP: "+ e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR: "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void myprofile(View view) {
        Intent perfil= new Intent(this, profile.class);
        startActivity(perfil);
    }
}