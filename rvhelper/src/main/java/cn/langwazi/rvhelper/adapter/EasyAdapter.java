package cn.langwazi.rvhelper.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by langwa on 2018/1/3.
 * 该adapter适用场景为一般性的列表展示和点击事件.
 */

public abstract class EasyAdapter<T> extends RecyclerView.Adapter<HelperHolder> {

    //数据源
    protected final List<T> mDatas = new ArrayList<>();

    //listener
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    @LayoutRes
    private int layoutResId;

    public EasyAdapter(@LayoutRes int layoutResId) {
        this.layoutResId = layoutResId;
    }

    @Override
    public HelperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
        HelperHolder holder = new HelperHolder(view);
        if (mOnItemClickListener != null) {
            holder.setOnItemClickListener(mOnItemClickListener);
        }
        if (mOnItemLongClickListener != null) {
            holder.setOnItemLongClickListener(mOnItemLongClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(HelperHolder holder, int position) {
        T data = mDatas.get(position);
        holder.bind(position, data);
        convert(holder, position, data);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * adapter进行数据绑定的方法.
     *
     * @param holder   viewHolder helper
     * @param position 点击的位置
     * @param data     点击位置的数据
     */
    public abstract void convert(HelperHolder holder, int position, T data);

    /**
     * 设置item点击事件.
     *
     * @param onItemClickListener listener
     */
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置item长按事件.
     *
     * @param onItemLongClickListener listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 添加新的数据到列表中.
     *
     * @param newestData 新的数据集
     */
    public void addData(List<T> newestData) {
        if (newestData != null && !newestData.isEmpty()) {
            mDatas.addAll(newestData);
            notifyDataSetChanged();
        }
    }

    /**
     * 重新设置数据集.
     *
     * @param newestData 需要设置的数据
     */
    public void resetData(List<T> newestData) {
        mDatas.clear();
        if (newestData != null && !newestData.isEmpty()) {
            mDatas.addAll(newestData);
        }
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }




}
