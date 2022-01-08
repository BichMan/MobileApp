package com.dumv.appdoctruyentranh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dumv.appdoctruyentranh.R;
import com.dumv.appdoctruyentranh.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Viewholider>{
    Context context;
    List<Message> list;

    public MessageAdapter(Context context, List<Message> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View studentView = inflater.inflate(R.layout.item_comment, parent, false);
        Viewholider viewHolder = new Viewholider(studentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholider holder, int position) {
       Message message = list.get(position);

        holder.txtMessager.setText(message.getMessage());
        holder.txtCommentDate.setText(message.getDate());
        holder.txtUserNameCommnet.setText(message.getNameUser());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholider extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        TextView txtMessager, txtUserNameCommnet, txtCommentDate;
        public Viewholider(@NonNull View itemView) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.imgUserComment);
            txtMessager = itemView.findViewById(R.id.txtComment);
            txtUserNameCommnet = itemView.findViewById(R.id.txtUserNameCommnet);
            txtCommentDate = itemView.findViewById(R.id.txtCommentDate);
        }
    }
}
