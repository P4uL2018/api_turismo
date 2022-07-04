package com.example.turismo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.turismo.R;
import com.example.turismo.adapter.AdapterLugares;
import com.example.turismo.adapter.AdapterReservas;
import com.example.turismo.bean.Lugares;
import com.example.turismo.bean.Reservas;
import com.example.turismo.service.web;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Myreserves extends AppCompatActivity {

    private RecyclerView rvListaReservas; //lista grafica

    private List<Reservas> lista_reservas; // lista de reservas
    private AdapterReservas adaptadorReserva; // objeto adapter
    private LinearLayoutManager layoutManager; //controlador
    private int idglobal;

    private void EnlazarVistas(){
        rvListaReservas= findViewById(R.id.rvListaReservas);
    }

    private void InicializarObjetos(){
        lista_reservas= new ArrayList<>();

        layoutManager= new LinearLayoutManager(this);
        rvListaReservas.setLayoutManager(layoutManager);

        adaptadorReserva= new AdapterReservas(lista_reservas, this);
        rvListaReservas.setAdapter(adaptadorReserva);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreserves);

        SharedPreferences pref = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        idglobal = pref.getInt("idglobal", 0);

        EnlazarVistas();

        InicializarObjetos();

        getMisReservas();

    }

    private void getMisReservas(){

        lista_reservas.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = web.RAIZ_WS + web.GET_RESERVAS+ "?idusuario="+idglobal;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.isEmpty()) {

                            try {
                                JSONArray array = new JSONArray(response);

                                if (array.length() > 0) {

                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject obj = (JSONObject) array.get(i);
                                        Reservas reservas = new Reservas(obj.getInt("id_reserva"),
                                                obj.getString("nombre_lugar"),
                                                obj.getString("costo"),
                                                obj.getString("imagen"),
                                                obj.getString("fecha_hora"),
                                                obj.getString("estado"),
                                                obj.getString("notas")
                                        );
                                        lista_reservas.add(reservas);
                                    }

                                    adaptadorReserva.notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "EXCP: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "SIN RESERVAS REGISTRADAS", Toast.LENGTH_LONG).show();
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
}