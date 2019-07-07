package com.gurus.mybooklist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.gurus.mybooklist.databinding.BookBinding;
import com.gurus.mybooklist.model.Book;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> implements RatingBar.OnRatingBarChangeListener {


    private int layoutId;
    private List<Book> books;
    private LayoutInflater layoutInflater;
    private int position;
    private OnRecordEventListener mClickListener;

    public BooksAdapter(List<Book> bookList,OnRecordEventListener listener) {
        Collections.sort(bookList);
        this.books = bookList;
        this.mClickListener = listener;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        BookBinding bookBinding = BookBinding.inflate(layoutInflater, parent, false);

        // LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new ViewHolder(bookBinding,mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book bookMmodel = books.get(position);
        holder.bind(bookMmodel);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setBooks(List<Book> book) {
        this.books = book;
    }

    public void sortRandomRating() {
        Random random = new Random();
        for (Book book : books) {
            book.setBookRating(random.nextFloat() * 5);
        }
        Collections.sort(books);

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private BookBinding bookBinding;


        public ViewHolder(final BookBinding bookBinding, final OnRecordEventListener listener) {
            super(bookBinding.getRoot());
            this.bookBinding = bookBinding;
            this.bookBinding.bookRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    if (books.get(getLayoutPosition()).getBookRating()!=v) {
                        books.get(getLayoutPosition()).setBookRating(v);
                        Collections.sort(books);
                        listener.onRatingBarChange();
                    }
                }
            });
        }

        public void bind(Book bookModel) {
            this.bookBinding.setBook(bookModel);

        }

        public BookBinding getBookBinding() {
            return bookBinding;
        }
    }
    public interface OnRecordEventListener  {
        void onRatingBarChange();
    }
}