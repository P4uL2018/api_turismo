package com.example.turismo.adapter;

import static com.example.turismo.service.web.RAIZ_WS_IMG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismo.R;
import com.example.turismo.bean.Lugares;
import com.example.turismo.views.RegisterReservas;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterLugares extends RecyclerView.Adapter<AdapterLugares.LugaresHolder> {

    private List<Lugares> lugares;
    private Context context;

    public AdapterLugares(List<Lugares> lugares, Context context){
        this.lugares= lugares;
        this.context= context;
    }

    @NonNull
    @Override
    public AdapterLugares.LugaresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lugares, parent, false);
        return new LugaresHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLugares.LugaresHolder holder, int position) {
        Lugares lug= lugares.get(position);
        holder.tvNombreLugar.setText(lug.nombre);
        holder.tvUbigeoLugar.setText(lug.ubigeo);
        holder.tvCostoLugar.setText("Precio - lugar: "+ lug.costo);

        Picasso.get().load(RAIZ_WS_IMG + lug.imagen).into(holder.ivImgLugar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, cli.nombre, Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, RegisterReservas.class);
                intent.putExtra("id_lugar", lug.idlugar);
                intent.putExtra("nombre_lugar", lug.nombre);
                intent.putExtra("ubigeo_lugar", lug.ubigeo);
                intent.putExtra("costo_lugar", lug.costo);
                intent.putExtra("imagen_lugar", lug.imagen);

                ((Activity)context).startActivityForResult(intent, 100);

                //context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lugares.size();
    }

    public static class LugaresHolder extends RecyclerView.ViewHolder{

        private ImageView ivImgLugar;
        private TextView tvNombreLugar, tvUbigeoLugar, tvCostoLugar;

        public LugaresHolder(@NonNull View itemView) {
            super(itemView);
            ivImgLugar= itemView.findViewById(R.id.ivImgLugar);
            tvNombreLugar= itemView.findViewById(R.id.tvNombreLugar);
            tvUbigeoLugar= itemView.findViewById(R.id.tvUbigeoLugar);
            tvCostoLugar= itemView.findViewById(R.id.tvCostoLugar);
        }
    }

}