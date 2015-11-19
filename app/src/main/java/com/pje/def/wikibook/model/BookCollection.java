package com.pje.def.wikibook.model;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.pje.def.wikibook.BookDetail;
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
                Book book = new Book(bookDetails, R.drawable.icone);
                books.add(book);
                System.out.println(book.getIsbn());
            }
        } catch (SQLException e){

        }
        return books;
    }

    public static Book getBook(String isbn){
        List<Book> books = new ArrayList<>();
        for(Book book : books){
            if(book.getIsbn().equals(isbn)) return book;
        }
        return null;
    }

    public static Boolean addBook(Book book) {
        try{
            MainActivity.getHelper().getBookDao().create(book.getBookDetails());
            return true;
        } catch(SQLException exception){
            return false;
        }
    }

    public static boolean removeBook(String isbn){

        try{
            System.out.println("Suppression de  : " + isbn);
            DeleteBuilder<BookDetails, Integer> deleteBuilder = MainActivity.getHelper().getBookDao().deleteBuilder();
            deleteBuilder.where().eq("book_isbn", isbn);
            deleteBuilder.delete();
            return true;
        } catch(SQLException exception){
            return false;
        }
    }

    public static boolean removeAll(){
        for(Book book : getBooks()){
               removeBook(book.getIsbn());
        }
        return true;
    }
}
