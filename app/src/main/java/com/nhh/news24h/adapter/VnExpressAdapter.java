package com.nhh.news24h.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nhh.news24h.R;
import com.nhh.news24h.listener.OnItemClick;
import com.nhh.news24h.model.VnExpress;


import java.util.List;

public class VnExpressAdapter extends RecyclerView.Adapter<VnExpressAdapter.ViewHolder> {
    private List<VnExpress> listVnexpress;
    private Context context;
    private OnItemClick onItemClick;

    public VnExpressAdapter(List<VnExpress> listVnexpress, Context context, OnItemClick onItemClick) {
        this.listVnexpress = listVnexpress;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paper, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        VnExpress vnExpress = listVnexpress.get(i);

        viewHolder.tvTitle.setText(vnExpress.getTitle());
        viewHolder.tvDate.setText(vnExpress.getPubDate());
        if (vnExpress.getDescription().getUrlImage() == null || vnExpress.getDescription().getUrlImage().isEmpty()) {
            Glide.with(context).load(R.drawable.vnexpressicon).into(viewHolder.imgPaper);
        } else {
            Glide.with(context).load(vnExpress.getDescription().getUrlImage()).into(viewHolder.imgPaper);

        }
        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVnexpress.size();
    }

    public void setData(List<VnExpress> vnExpressList) {
        this.listVnexpress = vnExpressList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPaper;
        TextView tvTitle, tvDate;
        LinearLayout llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPaper = itemView.findViewById(R.id.img_title);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            llItem = itemView.findViewById(R.id.ll_item_paper);

        }
    }
}
