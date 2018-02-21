package cn.langwazi.rvhelper.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by langwa on 2018/2/15.
 * 顶级adapter.
 */

public class HelperHolder extends RecyclerView.ViewHolder {
    //保存需要绑定的view
    private final SparseArray<View> views;

    private View itemView;
    private int mPosition;
    private Object mData;

    public HelperHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
        this.itemView = itemView;
    }

    @SuppressWarnings("unchecked")
    final void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null && itemView != null) {
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(mPosition, mData);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    final void setOnItemLongClickListener(final OnItemLongClickListener onItemLongClickListener) {
        if (onItemLongClickListener != null && itemView != null) {
            this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onItemLongClick(mPosition, mData);
                }
            });
        }
    }

    final <T> void bind(int position, T data) {
        this.mPosition = position;
        this.mData = data;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (V) view;
    }

}
