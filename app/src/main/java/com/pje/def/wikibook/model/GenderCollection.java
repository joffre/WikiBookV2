package com.pje.def.wikibook.model;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.pje.def.wikibook.MainActivity;
import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.bdd.GenderDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sim on 03/12/2015.
 */
public class GenderCollection {

    public GenderCollection() {

    }

    public static List<Genre> getGenders(){
        List<Genre> gender = new ArrayList<Genre>();
        try{
            List<GenderDetails> gendersDetails = MainActivity.getHelper().getGenderDao().queryForAll();
            for(GenderDetails genderDetails : gendersDetails){
                Genre genre = new Genre(genderDetails);
                gender.add(genre);
            }
        } catch (SQLException e){

        }
        return gender;
    }

    public static Genre getGender(int genderId)
    {
        try{
            QueryBuilder<GenderDetails, Integer> queryBuilder = MainActivity.getHelper().getGenderDao().queryBuilder();
            queryBuilder.where().eq("gender_id", genderId);
            PreparedQuery<GenderDetails> preparedQuery = queryBuilder.prepare();

            List<GenderDetails> genderList = MainActivity.getHelper().getGenderDao().query(preparedQuery);
            if(!genderList.isEmpty()) return new Genre(genderList.get(0));
        } catch (SQLException e){

        }
        return null;
    }

    public static Boolean addGender(Genre genre){
        try{
            MainActivity.getHelper().getGenderDao().create(genre.getBookDetails());
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public static boolean removeGender(int genreId){
        try{
            System.out.println("Suppression de  : " + genreId);
            DeleteBuilder<GenderDetails, Integer> deleteBuilder = MainActivity.getHelper().getGenderDao().deleteBuilder();
            deleteBuilder.where().eq("gender_id", genreId);
            deleteBuilder.delete();
            return true;
        } catch(SQLException exception){
            return false;
        }
    }

    public static ArrayList<String> getGendersToString(){
        ArrayList<String> my_array = new ArrayList<String>();
        for(Genre genre : getGenders()) {
            my_array.add(genre.genreTitle);
        }
        return my_array;
    }


    public static boolean removeAll(){
        for(Genre genre : getGenders()){
            removeGender(genre.getGenreId());
        }
        return true;
    }
}
