package com.example.vcanteen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DifferentRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<orderListData> mList;
    public DifferentRowAdapter(List<orderListData> list) {
        this.mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_layout, parent, false);
                return new CookingViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_done_layout, parent, false);
                return new DoneViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        orderListData object = mList.get(position);
        if (object != null) {
            switch (object.getType()) {
                case 0:
                    ((CookingViewHolder) holder).orderName.setText(object.getOrderName());
                    ((CookingViewHolder) holder).orderStatus.setText(object.getOrderStatus());

                    break;
                case 1:
                    ((DoneViewHolder) holder).orderName.setText(object.getOrderName());
                    ((DoneViewHolder) holder).orderStatus.setText(object.getOrderStatus());
                    break;
            }
        }
    }
    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            orderListData object = mList.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }
    public static class CookingViewHolder extends RecyclerView.ViewHolder {
        private TextView orderName;
        private TextView orderStatus;
        public CookingViewHolder(View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.orderName);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }
    }
    public static class DoneViewHolder extends RecyclerView.ViewHolder {
        private TextView orderName;
        private TextView orderStatus;
        public DoneViewHolder(View itemView) {
            super(itemView);
            orderName = (TextView) itemView.findViewById(R.id.orderName);
            orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
        }
    }
}