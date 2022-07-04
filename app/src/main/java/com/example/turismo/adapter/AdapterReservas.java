package com.example.turismo.adapter;

import static com.example.turismo.service.web.RAIZ_WS_IMG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismo.R;
import com.example.turismo.bean.Reservas;
import com.example.turismo.views.RegisterReservas;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterReservas extends RecyclerView.Adapter<AdapterReservas.ReservasHolder> {

    private List<Reservas> reservas;
    private Context context;

    public AdapterReservas(List<Reservas> reservas, Context context){
        this.reservas= reservas;
        this.context= context;
    }

    @NonNull
    @Override
    public AdapterReservas.ReservasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_reservas, parent, false);
        return new ReservasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReservas.ReservasHolder holder, int position) {
        Reservas reserv= reservas.get(position);
        holder.tvNombreLugar.setText(reserv.nombrelugar);
        holder.tvFechaHora.setText("Fecha de reserva: "+ reserv.fecha_hora);
        holder.tvCostoLugar.setText("Total: "+reserv.costo);

        Picasso.get().load(RAIZ_WS_IMG + reserv.imagen).into(holder.ivImgLugar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Información de reserva");

                alert.setMessage("Nombre de lugar: " + reserv.nombrelugar+"\n\n" +
                                "Total a pagar: "+ reserv.costo+"\n\n" +
                                "Fecha y hora de reserva: "+ reserv.fecha_hora+"\n\n" +
                                "Estado de reserva: "+ reserv.estado+"\n\n" +
                                "Notas: "+ reserv.notas);
                // alert.setMessage("Message");

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Your action here
                    }
                });

                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public static class ReservasHolder extends RecyclerView.ViewHolder{

        private ImageView ivImgLugar;
        private TextView tvNombreLugar, tvFechaHora, tvCostoLugar;

        public ReservasHolder(@NonNull View itemView) {
            super(itemView);
            ivImgLugar= itemView.findViewById(R.id.ivImgLugar);
            tvNombreLugar= itemView.findViewById(R.id.tvNombreLugar);
            tvFechaHora= itemView.findViewById(R.id.tvFechaHora);
            tvCostoLugar= itemView.findViewById(R.id.tvCostoLugar);
        }
    }

}