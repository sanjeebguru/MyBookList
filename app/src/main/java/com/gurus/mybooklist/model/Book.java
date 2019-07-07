package com.gurus.mybooklist.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.icu.text.CompactDecimalFormat;
import android.support.annotation.NonNull;

public class Book extends BaseObservable implements Comparable {
    private String bookName;
    private float bookCost;
    private float bookRating;

    public Book(String bookName, float bookCost, float bookRating) {
        this.bookName = bookName;
        this.bookCost = bookCost;
        this.bookRating = bookRating;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getBookCost() {
        return bookCost;
    }

    public void setBookCost(float bookCost) {
        this.bookCost = bookCost;
    }

    @Bindable
    public float getBookRating() {
        return bookRating;
    }

    public void setBookRating(float bookRating) {
        this.bookRating = bookRating;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        float compareTo=(float)((Book )o).getBookRating();
        /* For Ascending order*/
        if (this.getBookRating() > compareTo) {
            return 1;
        }
        else if (this.getBookRating() < compareTo) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
