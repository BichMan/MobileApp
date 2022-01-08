package com.dumv.appdoctruyentranh.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dumv.appdoctruyentranh.R;
import com.dumv.appdoctruyentranh.object.TruyenTranh;
import com.dumv.appdoctruyentranh.view.ChapActivity;
import com.dumv.appdoctruyentranh.view.MainActivity;

import java.util.List;

public class AdapterYeuThich extends RecyclerView.Adapter<AdapterYeuThich.Viewholider> {

    Context context;
    List<TruyenTranh> truyenTranhs;

    public AdapterYeuThich(Context context, List<TruyenTranh> truyenTranhs) {
        this.context = context;
        this.truyenTranhs = truyenTranhs;
    }

    @NonNull
    @Override
    public Viewholider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View studentView = inflater.inflate(R.layout.item_yeuthich, parent, false);
        Viewholider viewHolder = new Viewholider(studentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholider holder, int position) {
        TruyenTranh truyenTranh = truyenTranhs.get(position);

        Glide.with(context)
                .load(truyenTranh.getLinkAnh())
                .error(R.mipmap.ic_launcher)
                .into(holder.imghinh);

        holder.txtName.setText(truyenTranh.getTenTruyen());
        holder.txtChap.setText(truyenTranh.getTenChap());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putSerializable("truyen", truyenTranh);
                Intent intent = new Intent(context, ChapActivity.class);
                intent.putExtra("data", b);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return truyenTranhs.size();
    }

    public class Viewholider extends RecyclerView.ViewHolder {
        ImageView imghinh;
        TextView txtName, txtChap;

        public Viewholider(@NonNull View itemView) {
            super(itemView);
            imghinh = itemView.findViewById(R.id.imgAnhTruyenYeuThich);
            txtName = itemView.findViewById(R.id.txvTenTruyenYeuThich);
            txtChap = itemView.findViewById(R.id.txvTenChapYeuThich);
        }
    }
}
