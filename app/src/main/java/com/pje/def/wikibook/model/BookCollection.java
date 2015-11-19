package com.pje.def.wikibook.model;

import com.pje.def.wikibook.MainActivity;
import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.BookDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 24/09/2015.
 */
public class BookCollection {

    public BookCollection() {

            }

    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();
        try{
            List<BookDetails> booksDetails = MainActivity.getHelper().getBookDao().queryForAll();
            for(BookDetails bookDetails : booksDetails){
                books.add(new Book(bookDetails, R.drawable.icone));
            }
        } catch (SQLException e){

        }
        return books;
    }

    public static Boolean addBook(Book book) {
        try{
            MainActivity.getHelper().getBookDao().create(book.getBookDetails());
            return true;
        } catch(SQLException exception){
            return false;
        }
    }

    public static void removeBook(Book book){

    }
}
