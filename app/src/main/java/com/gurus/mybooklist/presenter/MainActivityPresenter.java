package com.gurus.mybooklist.presenter;

import android.content.Context;

import com.gurus.mybooklist.activities.IMainView;

public class MainActivityPresenter
        implements IMainPresenter {
    private IMainView view;
    private Context ctx;

    public MainActivityPresenter(IMainView view, Context ctx) {
        this.view = view;
        this.ctx = ctx;
    }


    @Override
    public void setRandomRating() {
        view.setRandomRating();
    }
}
