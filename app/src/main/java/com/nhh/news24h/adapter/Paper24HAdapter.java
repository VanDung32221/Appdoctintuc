package com.nhh.news24h.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nhh.news24h.R;
import com.nhh.news24h.listener.ILoadMore;
import com.nhh.news24h.listener.OnItemClick;
import com.nhh.news24h.model.Paper24H;

import java.util.List;

public class Paper24HAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Paper24H> paper24HList;
    private Context context;
    private OnItemClick onItemClick;
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ILoadMore loadMore;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public Paper24HAdapter(RecyclerView recyclerView, List<Paper24H> paper24HList, Context context, OnItemClick onItemClick) {

        this.paper24HList = paper24HList;
        this.context = context;
        this.onItemClick = onItemClick;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount(); // Lấy tổng số lượng item đang có
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition(); // Lấy vị trí của item cuối cùng
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) { // Nếu không phải trạng thái loading và tổng số lượng item bé hơn hoặc bằng vị trí item cuối + số lượng item tối đa hiển thị
                    if (loadMore != null) {
                        loadMore.onLoadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return paper24HList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM; // So sánh nếu item được get tại vị trí này là null thì view đó là loading view , ngược lại là item
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_paper, viewGroup, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.footerview, viewGroup, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ItemViewHolder) {
            Paper24H paper24H = paper24HList.get(i);
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

            itemViewHolder.tvTitle.setText(paper24H.getTitle());
            itemViewHolder.tvDate.setText(paper24H.getPubDate());
            if (paper24H.getDescription().getUrlImage() == null || paper24H.getDescription().getUrlImage().isEmpty()) {
                Glide.with(context).load(R.drawable.icon24h).into(itemViewHolder.imgPaper);
            } else {
                Glide.with(context).load(paper24H.getDescription().getUrlImage()).into(itemViewHolder.imgPaper);

            }
            itemViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClick(i);
                }
            });

        } else if (viewHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
//        Paper24H paper24H = paper24HList.get(i);
//
//        viewHolder.tvTitle.setText(paper24H.getTitle());
//        viewHolder.tvDate.setText(paper24H.getPubDate());
//        if (paper24H.getDescription().getUrlImage() == null || paper24H.getDescription().getUrlImage().isEmpty()) {
//            Glide.with(context).load(R.drawable.icon24h).into(viewHolder.imgPaper);
//        } else {
//            Glide.with(context).load(paper24H.getDescription().getUrlImage()).into(viewHolder.imgPaper);
//
//        }
//        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClick.onClick(i);
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return paper24HList.size();
    }

    public void setData(List<Paper24H> paper24HList) {
        this.paper24HList = paper24HList;
        notifyDataSetChanged();
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {
    ImageView imgPaper;
    TextView tvTitle, tvDate;
    LinearLayout llItem;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imgPaper = itemView.findViewById(R.id.img_title);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDate = itemView.findViewById(R.id.tv_date);
        llItem = itemView.findViewById(R.id.ll_item_paper);
    }
}

class LoadingViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}
