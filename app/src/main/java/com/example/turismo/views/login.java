package com.example.turismo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    private ImageView imglogin;
    private EditText etUsuario, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imglogin= findViewById(R.id.imglogin);
        imglogin.setImageResource(R.drawable.turismo);
        etUsuario= findViewById(R.id.etUsuario);
        etPassword= findViewById(R.id.etPassword);
    }

    public void signin(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = web.RAIZ_WS + web.AUTH_USER;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.isEmpty()){

                            String nombre="", apellido="", correo="";
                            int idusuario=0;
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                idusuario= jsonObject.getInt("id_usuario");
                                nombre = jsonObject.getString("nombre");
                                apellido = jsonObject.getString("apellido");
                                correo= jsonObject.getString("correo");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("idglobal", idusuario);
                            editor.putString("nombreglobal", nombre);
                            editor.putString("apellidoglobal", apellido);
                            editor.putString("correoglobal", correo);
                            editor.commit();

                            Intent main= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(main);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrecto", Toast.LENGTH_LONG).show();
                            etPassword.setText("");
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

                params.put("correo", etUsuario.getText().toString());
                params.put("pass", etPassword.getText().toString());

                return params;
            }
        };

        queue.add(stringRequest);

    }

    public void signup(View view) {
        Intent newuser= new Intent(this, signup.class);
        startActivity(newuser);

    }
}