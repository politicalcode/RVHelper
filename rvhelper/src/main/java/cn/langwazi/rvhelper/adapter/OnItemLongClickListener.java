package cn.langwazi.rvhelper.adapter;

/**
 * Created by langwa on 2018/2/15.
 * item长按事件.
 */

public interface OnItemLongClickListener<T> {
    /**
     * item长按监听事件.
     *
     * @param position 点击的item的position
     * @param data     item对应的数据.
     * @return 如果消费了该事件则返回true，否则返回false
     */
    boolean onItemLongClick(int position, T data);
}
