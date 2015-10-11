package com.pje.def.wikibook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.model.BookCollection;
import com.pje.def.wikibook.model.BookFilter;
import com.pje.def.wikibook.model.BookFilterCatalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiltredCollectionActivity extends AppCompatActivity {

    BookFilter currentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtred_book_collection);
        ListView filtredBookList = (ListView) findViewById(R.id.filtredBookList);
        List<Map<String, String>> l_filtred_books = new ArrayList<Map<String, String>>();
        currentFilter = BookFilterCatalog.getSelectedBookFilter();
        for (Book book : BookCollection.getBooks()) {
            if(currentFilter.isSelected(book)) {
                Map<String, String> bookMap = new HashMap<String, String>();
                bookMap.put("img", String.valueOf(book.getId_img())); // use available img
                bookMap.put("author", book.getAuthor());
                bookMap.put("title", book.getTitle());
                bookMap.put("gender", book.getGender());
                bookMap.put("isbn", book.getIsbn());
                bookMap.put("year", book.getYear());
                bookMap.put("description", book.getDescription());
                l_filtred_books.add(bookMap);
            }
        }

        SimpleAdapter listAdapter = new SimpleAdapter(this.getBaseContext(), l_filtred_books, R.layout.book_detail,
                /*ajout gender*/
                new String[] {"img", "author", "title", "gender", "isbn", "year", "description"},
                new int[] {R.id.img_cover, R.id.author, R.id.title, R.id.gender, R.id.isbn, R.id.year, R.id.description});

        filtredBookList.setAdapter(listAdapter);
       /* bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.show();
                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                actionBar.setTitle(map.get("title"));
                lastItemClicked = position;
                System.out.println(position + "   " + id);
            }
        });*/
    }

    @Override
    public void onResume(){
        super.onResume();
        this.setTitle(currentFilter.getName());
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
