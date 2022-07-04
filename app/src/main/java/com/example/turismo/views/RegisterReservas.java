package com.example.turismo.views;

import static com.example.turismo.service.web.RAIZ_WS_IMG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.turismo.R;
import com.example.turismo.service.web;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class RegisterReservas extends AppCompatActivity {

    private EditText etNombreLugar, etUbigeoLugar, etCostoLugar, etNombresUsuario, etCorreoUsuario, notas;
    private ImageView ivImagenLugar;
    private int id_usuario, id_lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reservas);

        SharedPreferences pref = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        id_usuario = pref.getInt("idglobal", 0);
        String nombreglobal =  pref.getString("nombreglobal", "");
        String apellidoglobal =  pref.getString("apellidoglobal", "");
        String correoglobal =  pref.getString("correoglobal", "");

        etNombreLugar= findViewById(R.id.etNombreLugar);
        etUbigeoLugar= findViewById(R.id.etUbigeo);
        etCostoLugar= findViewById(R.id.etCosto);
        etNombresUsuario= findViewById(R.id.etNombresUsuario);
        etCorreoUsuario= findViewById(R.id.etCorreoUsuario);
        notas= findViewById(R.id.notas);
        ivImagenLugar= findViewById(R.id.ivImagenLugarReserva);

        id_lugar= getIntent().getIntExtra("id_lugar", 0);
        etNombreLugar.setText(getIntent().getStringExtra("nombre_lugar"));
        etUbigeoLugar.setText(getIntent().getStringExtra("ubigeo_lugar"));
        etCostoLugar.setText(getIntent().getStringExtra("costo_lugar"));
        etNombresUsuario.setText(nombreglobal + " "+apellidoglobal);
        etCorreoUsuario.setText(correoglobal);

        String imagen= getIntent().getStringExtra("imagen_lugar");
        Picasso.get().load(RAIZ_WS_IMG + imagen).into(ivImagenLugar);

        etNombreLugar.setEnabled(false);
        etNombreLugar.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });

        etUbigeoLugar.setEnabled(false);
        etUbigeoLugar.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });

        etCostoLugar.setEnabled(false);
        etCostoLugar.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });

        etNombresUsuario.setEnabled(false);
        etNombresUsuario.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });

        etCorreoUsuario.setEnabled(false);
        etCorreoUsuario.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });
    }

    public void reservar(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = web.RAIZ_WS + web.REGISTER_RESERVA;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("1")){
                            Toast.makeText(getApplicationContext(), "Reserva exitosa!", Toast.LENGTH_LONG).show();
                            Intent main= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(main);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Algo ocurrió, ups", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR: "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params= new HashMap<String, String>();

                params.put("idusuario", id_usuario+ "");
                params.put("idlugar", id_lugar+ "");
                params.put("notas", notas.getText().toString());

                return params;
            }
        };

        queue.add(stringRequest);

    }
}