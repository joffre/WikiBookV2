package com.pje.def.wikibook.bdd;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.pje.def.wikibook.MainActivity;
import com.pje.def.wikibook.bdd.GenreDetails;
import com.pje.def.wikibook.model.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the Genre collection
 * Created by Sim on 03/12/2015.
 */
public class GenreCollection {

    public GenreCollection() {

    }

    /**
     * request in the db, get all the genre
     * @return
     */
    public static List<Genre> getGenres(){
        List<Genre> gender = new ArrayList<Genre>();
        try{
            List<GenreDetails> genresDetails = MainActivity.getHelper().getGenreDao().queryForAll();
            for(GenreDetails genreDetails : genresDetails){
                Genre genre = new Genre(genreDetails);
                gender.add(genre);
            }
        } catch (SQLException e){

        }
        return gender;
    }

    /**
     * get a gender with its id
     * @param genreId
     * @return
     */
    public static Genre getGender(int genreId)
    {
        try{
            QueryBuilder<GenreDetails, Integer> queryBuilder = MainActivity.getHelper().getGenreDao().queryBuilder();
            queryBuilder.where().eq("genre_id", genreId);
            PreparedQuery<GenreDetails> preparedQuery = queryBuilder.prepare();

            List<GenreDetails> genderList = MainActivity.getHelper().getGenreDao().query(preparedQuery);
            if(!genderList.isEmpty()) return new Genre(genderList.get(0));
        } catch (SQLException e){

        }
        return null;
    }

    /**
     * add a genre in the db
     * @param genre
     * @return
     */
    public static Boolean addGender(Genre genre){
        try{
            MainActivity.getHelper().getGenreDao().create(genre.getBookDetails());
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    /**
     * remove a genre with the following id
     * @param genreId
     * @return
     */
    public static boolean removeGender(int genreId){
        try{
            DeleteBuilder<GenreDetails, Integer> deleteBuilder = MainActivity.getHelper().getGenreDao().deleteBuilder();
            deleteBuilder.where().eq("genre_id", genreId);
            deleteBuilder.delete();
            return true;
        } catch(SQLException exception){
            return false;
        }
    }

    public static ArrayList<String> getGendersToString(){
        ArrayList<String> my_array = new ArrayList<String>();
        for(Genre genre : getGenres()) {
            my_array.add(genre.genreTitle);
        }
        return my_array;
    }


    public static boolean removeAll(){
        for(Genre genre : getGenres()){
            removeGender(genre.getGenreId());
        }
        return true;
    }
}
