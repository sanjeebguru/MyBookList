package com.gurus.mybooklist.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.gurus.mybooklist.R;
import com.gurus.mybooklist.adapter.BooksAdapter;
import com.gurus.mybooklist.databinding.ActivityMainBinding;
import com.gurus.mybooklist.model.Book;
import com.gurus.mybooklist.model.BookRepository;
import com.gurus.mybooklist.presenter.MainActivityPresenter;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Observer;

public class MainActivity extends AppCompatActivity implements IMainView, BooksAdapter.OnRecordEventListener {

    ActivityMainBinding activityMainBinding;
    BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext());
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initHeader();
        activityMainBinding.bookList.setHasFixedSize(true);
        activityMainBinding.bookList.setLayoutManager(new LinearLayoutManager(this));
        booksAdapter = new BooksAdapter(this);
        activityMainBinding.bookList.setAdapter(booksAdapter);
        activityMainBinding.btnSortRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setRandomRating();
            }
        });
        setBookListObservable(new BookRepository().getBookList());
    }

    private void setBookListObservable(List<Book> bookListObservable) {
        Observable<List<Book>> observable = Observable.just(bookListObservable);

        observable.subscribe(new Observer<List<Book>>() {
            @Override
            public void onCompleted() {
                Log.d("Mybooklist", "onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Mybooklist", "onError()",e);
            }

            @Override
            public void onNext(List<Book> data) {
               booksAdapter.setBooks(data);
            }
        });
    }


    private void initHeader() {
        activityMainBinding.textView.setText("Best Sellers");
        activityMainBinding.btnSortRandom.setText("Random Rating");
    }

    @Override
    public void setRandomRating() {
        Random random = new Random();
        List<Book> bookList = new BookRepository().getBookList();
        int i = 0;
        for (Book book : bookList) {
            book.setBookRating(random.nextFloat() * 5);
            i++;
        }
        Collections.sort(bookList);
        setBookListObservable(bookList);
        //booksAdapter.sortRandomRating();

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
