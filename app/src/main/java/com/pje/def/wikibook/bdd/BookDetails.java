package com.pje.def.wikibook.bdd;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Represent an table element of table of the same name
 * Created by Geoffrey on 22/10/2015.
 */
public class BookDetails implements Serializable {

    @DatabaseField(id = true, generatedId = false, columnName = "book_isbn")
    protected String bookIsbn;

    @DatabaseField(columnName = "book_title")
    protected String bookTitle;

    @DatabaseField(columnName = "book_author")
    protected String bookAuthor;

    @DatabaseField(columnName = "book_year")
    protected String bookYear;

    @DatabaseField(columnName = "book_genre")
    protected String bookGenre;

    @DatabaseField(columnName = "book_description")
    protected String bookDescription;

    /**
     * Empty constructor used by orm framework
     */
    public BookDetails(){

    }

    /**
     * BookDetail constructor
     */
    public BookDetails(String bookIsbn, String bookTitle, String bookAuthor, String bookYear, String bookGenre, String bookDescription) {
        this.bookIsbn = bookIsbn;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookYear = bookYear;
        this.bookGenre = bookGenre;
        this.bookDescription = bookDescription;
    }

    /**
     * Getters & Setters
     */

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookYear() {
        return bookYear;
    }

    public void setBookYear(String bookYear) {
        this.bookYear = bookYear;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }
}
