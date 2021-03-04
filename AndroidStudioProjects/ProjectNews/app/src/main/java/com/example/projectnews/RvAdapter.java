package com.example.projectnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class RvAdapter extends RecyclerView.Adapter<RvAdapter.viewholder> {

ArrayList<String> titles;
ArrayList<String> ImageUrls;
Context context;
ItemClicked mlistener;

RvAdapter(ArrayList<String> titles, Context context,ArrayList<String> ImageUrls){
    this.titles=titles;
    this.ImageUrls=ImageUrls;
    this.context=context;
}

public void OnItemClicked(ItemClicked mlistener){
    this.mlistener=mlistener;
}

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.itemview,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.tv.setText(titles.get(position).toString());
        Picasso.get()
                .load(ImageUrls.get(position))
                .error(R.mipmap.no)
                .placeholder(R.mipmap.no)
                .into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        public viewholder(@NonNull View itemView) {
            super(itemView);
        tv=itemView.findViewById(R.id.tv);
        iv=itemView.findViewById(R.id.iv);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onNewsClicked(getAdapterPosition());
            }
        });
        }
    }


    interface  ItemClicked
    {
        void onNewsClicked(int position);
    }
}
