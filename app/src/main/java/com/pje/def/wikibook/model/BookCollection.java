package com.pje.def.wikibook.model;

import java.util.ArrayList;

/**
 * Created by S on 24/09/2015.
 */
public class BookCollection {

    public static ArrayList<Book> l_books = new ArrayList<Book>();

    public BookCollection() {
        l_books = new ArrayList<Book>();
    }

    public static ArrayList<Book> getBooks() {
        return l_books;
    }

    public static void addBook(Book book) {
        l_books.add(book);
    }

    public void set_books(ArrayList<Book> l_books) {
        this.l_books = l_books;
    }
}
