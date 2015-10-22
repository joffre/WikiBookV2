package com.pje.def.wikibook.bdd;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Geoffrey on 22/10/2015.
 */
public class BookDetails {

    @DatabaseField(generatedId=true, columnName= "book_id")
    protected int book_id;

    @DatabaseField(columnName= "book_title")
    protected String book_title;

    @DatabaseField(columnName= "book_author")
    protected String book_author;

    @DatabaseField(columnName= "book_year")
    protected String book_year;

    @DatabaseField(columnName= "book_genre")
    protected String book_genre;

    @DatabaseField(columnName= "book_description")
    protected String book_description;

    @DatabaseField(columnName= "book_isbn")
    protected String book_isbn;

    public BookDetails(int book_id, String book_title, String book_author, String book_year, String book_genre, String book_description, String book_isbn) {
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_year = book_year;
        this.book_genre = book_genre;
        this.book_description = book_description;
        this.book_isbn = book_isbn;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_year() {
        return book_year;
    }

    public void setBook_year(String book_year) {
        this.book_year = book_year;
    }

    public String getBook_genre() {
        return book_genre;
    }

    public void setBook_genre(String book_genre) {
        this.book_genre = book_genre;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }
}
