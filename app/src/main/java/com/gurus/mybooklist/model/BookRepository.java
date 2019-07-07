package com.gurus.mybooklist.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BookRepository {

    public Book[] BOOKS = {MILKMAN, BELONGING, OVERSTORY, FAIRVIEW, FRED, AMATEURS, FORBIDDEN, THEOUTSIDER, EMISSARY, TIME};


    public HashMap<String, Book> BOOK_MAP = new HashMap<>();

    public BookRepository() {


    }
    public static final Book MILKMAN = new Book("Milkman: A Novel", 11.18f, 5.0f);
    public static final Book BELONGING = new Book("Belonging: A German Reckons with History and Home", 16.44f, 4.0f);
    public static final Book OVERSTORY = new Book("The Overstory: A Novel", 11.37f, 2.0f);
    public static final Book FAIRVIEW = new Book("FairView", 10.47f, 5.0f);
    public static final Book FRED = new Book("Frederick Douglass: Prophet of Freedom", 14.00f, 4.0f);
    public static final Book AMATEURS = new Book("The Amateurs", 10.70f, 2.0f);
    public static final Book FORBIDDEN = new Book("Forbidden River (The Legionnaires)", 2.99f, 3.0f);
    public static final Book THEOUTSIDER = new Book("The Outsider: A Novel", 17.17f, 4.0f);
    public static final Book EMISSARY = new Book("The Emissary", 11.65f, 2.0f);
    public static final Book TIME = new Book("A Brief History of Time", 14.39f, 2.0f);

    public List<Book> getBookList(){
        return Arrays.asList(BOOKS);

    }


}
