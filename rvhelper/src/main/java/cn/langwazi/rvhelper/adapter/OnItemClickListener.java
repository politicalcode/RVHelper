package cn.langwazi.rvhelper.adapter;

/**
 * Created by langwa on 2018/2/12.
 * item点击回调.
 */

public interface OnItemClickListener<T> {
    void onItemClick(int position, T data);
}
