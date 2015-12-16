package com.pje.def.wikibook.scan;

import android.app.Activity;
import android.widget.EditText;

import com.pje.def.wikibook.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sim on 18/11/2015.
 */
public class JSONParser {

    public JSONObject result;
    private String object, title, author, year, isbn, description, genre;
    private String thumbnail;
    public JSONParser (String httpresult)
    {
        try {
            result = new JSONObject(httpresult);
        } catch (JSONException ex){
        }
        object = "";
        title = "";
        author = "";
        year = "";
        isbn = "";
        description = "";
        genre = "";
    }

    public void parseJSON (){
        try {
            JSONArray bookArray = this.result.getJSONArray("items");
            JSONObject bookObject = bookArray.getJSONObject(0);
            object = bookObject.toString();

            JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");

            title = volumeObject.getString("title"); //TITLE

            JSONArray authorsArray = volumeObject.getJSONArray("authors");
            author = authorsArray.get(0).toString(); // AUTHORS

            year = volumeObject.getString("publishedDate"); //YEAR

            JSONArray indIDArray = volumeObject.getJSONArray("industryIdentifiers");
            for(int i = 0; i < indIDArray.length(); i++){
                JSONObject indObject = indIDArray.getJSONObject(i);
                if(indObject.getString("type").equals("ISBN_13")){
                    isbn = indObject.getString("identifier");
                }
            }

            description = volumeObject.getString("description");

        } catch (JSONException ex){

        }
    }

    public String getObject() {
        return object;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription(){
        return description;
    }

    public String getGenre(){
        return genre;
    }
}
