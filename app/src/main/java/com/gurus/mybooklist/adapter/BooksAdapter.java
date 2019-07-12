package com.gurus.mybooklist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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
import java.util.Objects;
import java.util.Random;
import java.util.logging.Handler;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> implements RatingBar.OnRatingBarChangeListener {

    private OnRecordEventListener listner;
    private int layoutId;
    private List<Book> books;
    private LayoutInflater layoutInflater;
    private int position;
    private boolean onBind;
    private OnRecordEventListener mClickListener;
    private ViewHolder viewHolder;

    public BooksAdapter(OnRecordEventListener listener) {

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
        viewHolder = new ViewHolder(bookBinding, mClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book bookMmodel = books.get(position);
        holder.bind(bookMmodel);
        onBind = true;
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setBooks(final List<Book> bookList) {
        if (books == null) {
            Collections.sort(bookList);
            this.books = bookList;
            notifyItemRangeInserted(0, bookList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return books.size();
                }

                @Override
                public int getNewListSize() {
                    return bookList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return books.get(oldItemPosition).getBookRating() == bookList.get(newItemPosition).getBookRating();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Book newBook = bookList.get(newItemPosition);
                    Book oldBook = books.get(oldItemPosition);
                    return newBook.getBookRating() == oldBook.getBookRating() &&
                            Objects.equals(newBook.getBookName(), oldBook.getBookName()) &&
                            Objects.equals(newBook.getBookCost(), oldBook.getBookCost());
                }
            });
            books = bookList;
            result.dispatchUpdatesTo(this);
        }
    }

    //Random sort
    public void sortRandomRating() {
        Random random = new Random();
        int i = 0;
        for (Book book : books) {
            book.setBookRating(random.nextFloat() * 5);
            i++;
        }
        Collections.sort(books);
        listner.onRatingBarChange();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private BookBinding bookBinding;


        public ViewHolder(final BookBinding bookBinding, OnRecordEventListener listener) {
            super(bookBinding.getRoot());
            BooksAdapter.this.listner = listener;
            this.bookBinding = bookBinding;
            this.bookBinding.bookRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    if (books.get(getLayoutPosition()).getBookRating() != v) {
                        books.get(getLayoutPosition()).setBookRating(v);
                        Collections.sort(books);
                        BooksAdapter.this.listner.onRatingBarChange();
                    }
                }
            });
        }

        public int getPositionBook(Book book) {
            if (book == books.get(getLayoutPosition())) {
                return getLayoutPosition();
            }
            return 0;
        }

        public void bind(Book bookModel) {
            this.bookBinding.setBook(bookModel);

        }

        public BookBinding getBookBinding() {
            return bookBinding;
        }
    }

    public interface OnRecordEventListener {

        void onRatingBarChange();
    }
}