package com.gurus.mybooklist.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gurus.mybooklist.R;
import com.gurus.mybooklist.adapter.BooksAdapter;
import com.gurus.mybooklist.databinding.ActivityMainBinding;
import com.gurus.mybooklist.model.BookRepository;
import com.gurus.mybooklist.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements IMainView, BooksAdapter.OnRecordEventListener {

    ActivityMainBinding activityMainBinding;
    BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext());
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initHeader();
        activityMainBinding.bookList.setLayoutManager(new LinearLayoutManager(this));
        booksAdapter = new BooksAdapter(new BookRepository().getBookList(),this);
        activityMainBinding.bookList.setAdapter(booksAdapter);
        activityMainBinding.btnSortRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksAdapter.sortRandomRating();
            }
        });

    }


    private void initHeader() {
        activityMainBinding.textView.setText("Best Sellers");
        activityMainBinding.btnSortRandom.setText("Random Rating");
    }

    @Override
    public void setRandomRating() {
        booksAdapter.sortRandomRating();

    }

    @Override
    public void onRatingBarChange() {
        activityMainBinding.bookList.post(new Runnable() {
            @Override
            public void run() {
                booksAdapter.notifyDataSetChanged();
            }
        });
    }
}
