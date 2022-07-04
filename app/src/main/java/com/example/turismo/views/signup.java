package com.example.turismo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.turismo.R;
import com.example.turismo.service.web;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etNombre= findViewById(R.id.etNombre);
        etApellido= findViewById(R.id.etApellido);
        etCorreo= findViewById(R.id.etCorreo);
        etPassword= findViewById(R.id.etPassword);
    }

    public void signup(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = web.RAIZ_WS + web.SIGNUP_USER;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("1")){
                            Toast.makeText(getApplicationContext(), "Registro exitoso! Ahora inicie sesión", Toast.LENGTH_LONG).show();
                            Intent main= new Intent(getApplicationContext(), login.class);
                            startActivity(main);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Algo ocurrió, ups", Toast.LENGTH_LONG).show();
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

                params.put("nombre", etNombre.getText().toString());
                params.put("apellido", etApellido.getText().toString());
                params.put("correo", etCorreo.getText().toString());
                params.put("pass", etPassword.getText().toString());

                return params;
            }
        };

        queue.add(stringRequest);

    }
}