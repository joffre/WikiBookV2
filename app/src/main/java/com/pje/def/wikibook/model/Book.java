package com.pje.def.wikibook.model;

import com.pje.def.wikibook.BookDetail;
import com.pje.def.wikibook.bdd.BookDetails;

import java.io.Serializable;

/**
 * Created by S on 24/09/2015.
 */
public class Book implements Serializable{

    public String title, author, genre, year, description, isbn;

    public Book(String title, String author, String genre, String year, String description, String isbn) {
        this.title = title.trim();
        this.author = author.trim();
        this.genre = genre.trim();
        this.year = year.trim();
        this.description = description.trim();
        this.isbn = isbn.trim();
    }

    public Book(BookDetails details) {
        this.title = details.getBookTitle();
        this.author = details.getBookAuthor();
        this.genre = details.getBookGenre();
        this.year = details.getBookYear();
        this.description = details.getBookDescription();
        this.isbn = details.getBookIsbn();
    }

    public BookDetails getBookDetails(){
        return new BookDetails(this.isbn, this.title, this.author, this.year, this.genre, this.description);
    }

    public String getGenre() {
        return genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAutor(String autor) {
        this.author = autor;
    }

    public String getGender() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
