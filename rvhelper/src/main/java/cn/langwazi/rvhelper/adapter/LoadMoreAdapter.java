package cn.langwazi.rvhelper.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by langwa on 2017/12/10.
 * 自动加载更多adapter.
 */

public abstract class LoadMoreAdapter<T> extends RecyclerView.Adapter {

    private static final int LOADING_MORE = 0x00001111; //加载更多布局

    //数据源
    private final List<T> mDatas = new ArrayList<>();

    //加载更多视图
    private AbstractLoadView mLoadView;

    //listener
    private OnRequestLoadListener mOnRequestLoadListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemClickListener mOnItemClickListener;

    //load more setting
    private boolean mLoadMoreEnable = false;  //加载控制开关
    private boolean mIsLoading = false; //标记是否在加载中
    private int mPreLoadNumber = 1; //提前mPreLoadNumber个item加载更多

    @LayoutRes
    private int layoutResId;

    public LoadMoreAdapter(@LayoutRes int layoutResId) {
        this.layoutResId = layoutResId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_MORE) {
            if (mLoadView == null) {
                mLoadView = new DefaultLoadView();
            }
            return new LoadViewHolder(mLoadView.inflateLoadContainer(parent));
        } else {
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
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        atuoLoadMore(position);
        int viewType = holder.getItemViewType();
        if (viewType != LOADING_MORE && holder instanceof HelperHolder) {
            T data = mDatas.get(position);
            HelperHolder helperHolder = (HelperHolder) holder;
            helperHolder.bind(position, data);
            convert(helperHolder, position, data);
        }
    }

    /**
     * adapter进行数据绑定的方法.
     *
     * @param holder   viewHolder helper
     * @param position 点击的位置
     * @param data     点击位置的数据
     */
    public abstract void convert(HelperHolder holder, int position, T data);

    @Override
    public int getItemViewType(int position) {
        if (mOnRequestLoadListener != null
                && (getItemCount() - 1) == position) {
            return LOADING_MORE;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        if (mOnRequestLoadListener != null) {
            //在底部增加一个加载更多视图
            return mDatas.size() + 1;
        } else {
            return mDatas.size();
        }
    }

    /**
     * 自动加载更多.
     */
    private void atuoLoadMore(int position) {
        if (!mLoadMoreEnable || mOnRequestLoadListener == null || mIsLoading) {
            return;
        }
        int count = mDatas.size();
        if (count == 0) {
            //数据为空
            return;
        }
        if (position < count - 1 - mPreLoadNumber) {
            return;
        }
        mIsLoading = true;
        if (mLoadView != null) {
            mLoadView.loading();
        }
        mOnRequestLoadListener.onRequestLoadMore();
    }

    /**
     * 添加更多数据.
     *
     * @param newestData 最新请求的数据
     */
    public void addData(List<T> newestData) {
        if (newestData != null && !newestData.isEmpty()) {
            loadEnd();
            mDatas.addAll(newestData);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置显示的数据,加载第一页数据时调用该方法.
     *
     * @param newestData 数据源
     */
    public void setData(List<T> newestData) {
        if (newestData != null && !newestData.isEmpty()) {
            mLoadMoreEnable = mOnRequestLoadListener != null;
            loadEnd();
            mDatas.clear();
            mDatas.addAll(newestData);
            notifyDataSetChanged();
        }
    }

    /**
     * 某次加载完成,但还有更多数据可加载.
     */
    private void loadEnd() {
        if (mLoadView != null) {
            mLoadView.loadEnd();
        }
        mIsLoading = false;
    }

    /**
     * 加载完成没有更多数据可加载.
     */
    public void loadComplete() {
        if (mLoadView != null) {
            mLoadView.loadComplete();
        }
        mLoadMoreEnable = false;
        mIsLoading = false;
    }

    /**
     * 显示加载失败时视图.
     */
    public void loadFail() {
        if (mLoadView != null) {
            mLoadView.loadFail();
        }
        mLoadMoreEnable = false;
        mIsLoading = false;
    }

    /**
     * 请求失败重新加载数据.
     */
    private void reLoad() {
        if (mIsLoading || mOnRequestLoadListener == null) {
            return;
        }
        mIsLoading = true;  //重新加载
        mLoadMoreEnable = true;
        mLoadView.loading();
        mOnRequestLoadListener.onRequestLoadMore();
    }

    /**
     * 开关加载更多功能,当下拉刷新时应该设置为false禁用.
     *
     * @param loadEnable true打开加载更多功能,fasle关闭功能
     */
    public void setEnableLoadMore(boolean loadEnable) {
        this.mLoadMoreEnable = loadEnable;
    }

    /**
     * 设置底部加载更多LoadView.
     *
     * @param loadView loadView
     */
    public void setLoadView(AbstractLoadView loadView) {
        this.mLoadView = loadView;
    }

    /**
     * 设置回调 {@link OnRequestLoadListener#onRequestLoadMore()} 触发个数.
     *
     * @param preNumber 提前加载的触发个数,他的值必须大于1.
     */
    public void setPreLoadNumber(int preNumber) {
        if (preNumber >= 1) {
            this.mPreLoadNumber = preNumber;
        }
    }

    /**
     * 设置加载更多回调.
     *
     * @param onRequestLoadListener 回调
     */
    public void setOnRequestLoadListener(OnRequestLoadListener onRequestLoadListener) {
        this.mOnRequestLoadListener = onRequestLoadListener;
    }

    /**
     * 设置item长按监听事件.
     *
     * @param onItemLongClickListener 长按事件
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 设置item点击事件.
     *
     * @param onItemClickListener 点击事件
     */
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 底部加载更多ViewHolder.
     */
    private class LoadViewHolder extends RecyclerView.ViewHolder {

        LoadViewHolder(View itemView) {
            super(itemView);
            if (mLoadView == null) {
                return;
            }
            if (mLoadView.failView != null) {
                mLoadView.failView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reLoad();
                    }
                });
            }
        }
    }


}
