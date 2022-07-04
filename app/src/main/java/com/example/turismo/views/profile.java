package com.example.turismo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import com.example.turismo.R;

public class profile extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences pref = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
        int idglobal = pref.getInt("idglobal", 0);
        String nombreglobal =  pref.getString("nombreglobal", "");
        String apellidoglobal =  pref.getString("apellidoglobal", "");
        String correoglobal =  pref.getString("correoglobal", "");

        etNombre= findViewById(R.id.txtName);
        etApellido= findViewById(R.id.txtApellidos);
        etCorreo= findViewById(R.id.txtCorreo);

        etNombre.setText(nombreglobal);
        etApellido.setText(apellidoglobal);
        etCorreo.setText(correoglobal);

        etNombre.setEnabled(false);
        etNombre.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });

        etApellido.setEnabled(false);
        etApellido.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });

        etCorreo.setEnabled(false);
        etCorreo.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end,
                                       Spanned dst, int dstart, int dend) {
                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
            }
        } });

    }
}