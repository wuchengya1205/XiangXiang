package com.xiang.main.listener;

/**
 * author : fengzhangwei
 * date : 2019/10/29
 */
public interface RefreshListener {

    interface top {
        void refresh();

        void loadMore();
    }

    interface bottom {
        void finishRefresh();

        void finishLoadMore();
    }


}
