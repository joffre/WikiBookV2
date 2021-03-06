package com.pje.def.wikibook.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.bdd.BookCollection;
import com.pje.def.wikibook.model.BookFilter;
import com.pje.def.wikibook.bdd.BookFilterCollection;
import com.pje.def.wikibook.utility.MySimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity which shows the result of a filter, a list of book
 */
public class FiltredCollectionActivity extends AppCompatActivity {

    BookFilter filter;

    public static final String FILTER_NAME = "filter_name";

    /**
     * Initialize the list of books filtered
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // get the filter name
        String filter_name = intent.getStringExtra(FILTER_NAME);

        setContentView(R.layout.activity_filtred_book_collection);
        ListView filtredBookList = (ListView) findViewById(R.id.filtredBookList);

        List<Map<String, String>> l_filtred_books = new ArrayList<Map<String, String>>();

        // Get the list of book corresponding with the following filter
        filter = BookFilterCollection.getBookFilter(filter_name);
        for (Book book : BookCollection.getBooks(filter)) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("author", book.getAuthor());
            bookMap.put("title", book.getTitle());
            bookMap.put("gender", book.getGender());
            bookMap.put("isbn", book.getIsbn());
            bookMap.put("year", book.getYear());
            bookMap.put("description", book.getDescription());
            l_filtred_books.add(bookMap);
        }

        MySimpleAdapter listAdapter = new MySimpleAdapter(this.getBaseContext(), l_filtred_books, R.layout.book_detail,
                /*ajout gender*/
                new String[] {"img", "author", "title", "gender", "isbn", "year", "description"},
                new int[] {R.id.img_cover, R.id.author, R.id.title, R.id.gender, R.id.isbn, R.id.year, R.id.description});

        filtredBookList.setAdapter(listAdapter);
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context,l_filtred_books.size() + " books.", duration);
        toast.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.setTitle(filter.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtred_book_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
