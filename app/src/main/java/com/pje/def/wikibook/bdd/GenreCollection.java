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
 * Created by Sim on 03/12/2015.
 */
public class GenreCollection {

    public GenreCollection() {

    }

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

    public static Genre getGender(int genderId)
    {
        try{
            QueryBuilder<GenreDetails, Integer> queryBuilder = MainActivity.getHelper().getGenreDao().queryBuilder();
            queryBuilder.where().eq("gender_id", genderId);
            PreparedQuery<GenreDetails> preparedQuery = queryBuilder.prepare();

            List<GenreDetails> genderList = MainActivity.getHelper().getGenreDao().query(preparedQuery);
            if(!genderList.isEmpty()) return new Genre(genderList.get(0));
        } catch (SQLException e){

        }
        return null;
    }

    public static Boolean addGender(Genre genre){
        try{
            MainActivity.getHelper().getGenreDao().create(genre.getBookDetails());
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public static boolean removeGender(int genreId){
        try{
            System.out.println("Suppression de  : " + genreId);
            DeleteBuilder<GenreDetails, Integer> deleteBuilder = MainActivity.getHelper().getGenreDao().deleteBuilder();
            deleteBuilder.where().eq("gender_id", genreId);
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
