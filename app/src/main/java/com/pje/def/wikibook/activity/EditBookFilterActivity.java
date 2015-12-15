package com.pje.def.wikibook.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.FilterDetails;
import com.pje.def.wikibook.model.BookFilter;
import com.pje.def.wikibook.model.BookFilterCollection;

public class EditBookFilterActivity extends AppCompatActivity {

    private String filterName;

    public static final String FILTER_TO_EDIT = "filter_to_edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_filter);

        Intent intent = getIntent();
        filterName = intent.getStringExtra(FILTER_TO_EDIT);
        BookFilter bookFilter = BookFilterCollection.getBookFilter(filterName);

        EditText name = (EditText)findViewById(R.id.CriterionName);
        name.setText(bookFilter.getName());

        EditText title = (EditText)findViewById(R.id.CriterionTitle);
        title.setText(bookFilter.getCriterion(BookFilter.FilterType.TITLE));

        EditText author = (EditText)findViewById(R.id.CriterionAuthor);
        author.setText(bookFilter.getCriterion(BookFilter.FilterType.AUTHOR));

        EditText description = (EditText)findViewById(R.id.CriterionDescription);
        description.setText(bookFilter.getCriterion(BookFilter.FilterType.DESCRIPTION));

        EditText year = (EditText)findViewById(R.id.CriterionYear);
        year.setText(bookFilter.getCriterion(BookFilter.FilterType.YEAR));

        EditText genre = (EditText)findViewById(R.id.CriterionGenre);
        genre.setText(bookFilter.getCriterion(BookFilter.FilterType.GENDER));

        EditText isbn = (EditText)findViewById(R.id.CriterionIsbn);
        isbn.setText(bookFilter.getCriterion(BookFilter.FilterType.ISBN));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_book_filter, menu);
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

    public void editBookFilter(View view){
        EditText name = (EditText)findViewById(R.id.CriterionName);
        EditText title = (EditText)findViewById(R.id.CriterionTitle);
        EditText author = (EditText)findViewById(R.id.CriterionAuthor);
        EditText description = (EditText)findViewById(R.id.CriterionDescription);
        EditText year = (EditText)findViewById(R.id.CriterionYear);
        EditText genre = (EditText)findViewById(R.id.CriterionGenre);
        EditText isbn = (EditText)findViewById(R.id.CriterionIsbn);

        FilterDetails newBookFilterDetails = new FilterDetails(name.getText().toString(), title.getText().toString(), author.getText().toString(), year.getText().toString(), genre.getText().toString(), description.getText().toString(), isbn.getText().toString());

        if(!name.getText().toString().toLowerCase().trim().equals(filterName.toLowerCase().trim()) && BookFilterCollection.getBookFilter(name.getText().toString().trim()) != null){
            Context context = getApplicationContext();
            CharSequence text = "Your book filter already exist. Change the filter name !";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            CharSequence text = "Your book filter can't be modified.";
            if(BookFilterCollection.removeBookFilter(filterName) && BookFilterCollection.addBookFilter(newBookFilterDetails)) {
                text = "Your book filter has been modified.";
                name.getText().clear();
                title.getText().clear();
                author.getText().clear();
                description.getText().clear();
                year.getText().clear();
                genre.getText().clear();
                isbn.getText().clear();
            }
            Context context = getApplicationContext();

            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            finish();
        }
    }
}
