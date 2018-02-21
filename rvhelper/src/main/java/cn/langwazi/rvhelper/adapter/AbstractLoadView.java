package cn.langwazi.rvhelper.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xyin on 2017/12/11.
 * 加载更多时底部加载中，加载失败，加载完成时的视图容器.
 */

public abstract class AbstractLoadView {

    private View loadingView;
    private View completeView;
    View failView;


    /**
     * 初始化加载更多视图.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @return load container.
     */
    final View inflateLoadContainer(ViewGroup parent) {
        View view = createLoadView(parent);
        loadingView = findLoadingView(view);
        completeView = findCompleteView(view);
        failView = findLoadFailView(view);
        loadEnd();
        return view;
    }

    final void loading() {
        changeState(View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
    }

    final void loadComplete() {
        changeState(View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
    }

    final void loadEnd() {
        changeState(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
    }

    final void loadFail() {
        changeState(View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
    }

    private void changeState(int loading, int complete, int fail) {
        if (loadingView != null) {
            loadingView.setVisibility(loading);
        }
        if (completeView != null) {
            completeView.setVisibility(complete);
        }
        if (failView != null) {
            failView.setVisibility(fail);
        }
    }

    /**
     * 创建底部加载更多视图.
     *
     * @return View
     */
    @NonNull
    public abstract View createLoadView(ViewGroup parent);

    /**
     * 找到加载失败的视图,你可以通过{@link View#findViewById(int)}找到失败的视图.
     *
     * @param rootView 见{@link #createLoadView(ViewGroup)}的返回值
     * @return 找到的指定视图
     */
    public abstract View findLoadFailView(View rootView);

    /**
     * 查找完成视图{@link #findLoadFailView(View)}.
     *
     * @param rootView 见{@link #createLoadView(ViewGroup)}的返回值
     * @return 找到的指定视图
     */
    public abstract View findCompleteView(View rootView);

    /**
     * 查找加载中视图{@link #findLoadFailView(View)}.
     *
     * @param rootView 见{@link #createLoadView(ViewGroup)}的返回值
     * @return 找到的指定视图
     */
    public abstract View findLoadingView(View rootView);

}

