package com.pje.def.wikibook.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.BookCollection;
import com.pje.def.wikibook.bdd.GenreCollection;
import com.pje.def.wikibook.fragment.BookDetailFragment;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.model.Genre;

import java.util.List;

/**
 * Activity which manage the editing of a book
 * Created by David on 07/10/2015.
 */
public class EditBookActivity extends Activity {

    private int cpt = 0;
    public static final String BOOK_TO_EDIT = "book_edit";
    private TextView hideGenre;
    private EditText newGenre;
    private Spinner spinner;
    private List<String> arrayGenre;
    private String book_isbn;

    /**
     * Initialize all elements of the activity, Textview, EditText, Spinner and button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        hideGenre = (TextView)findViewById(R.id.textEditGenre);
        newGenre = (EditText)findViewById(R.id.addGenderEdit);
        spinner = (Spinner)findViewById(R.id.spinnerEdit);

        Intent intent = getIntent();
        book_isbn = intent.getStringExtra(BOOK_TO_EDIT);
        Book book = BookCollection.getBook(book_isbn);

        EditText title = (EditText)findViewById(R.id.EditTitle);
        title.setText(book.getTitle());
        EditText author = (EditText)findViewById(R.id.EditAuthor);
        author.setText(book.getAuthor());
        EditText description = (EditText)findViewById(R.id.EditDescription);
        description.setText(book.getDescription());
        EditText year = (EditText)findViewById(R.id.EditYear);
        year.setText(book.getYear());
        EditText isbn = (EditText)findViewById(R.id.EditIsbn);
        isbn.setText(book.getIsbn());

        arrayGenre = GenreCollection.getGendersToString();
        arrayGenre.add("Add a new gender");
        ArrayAdapter my_adapter = new ArrayAdapter(this, R.layout.spinner_row, arrayGenre);
        spinner.setAdapter(my_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == arrayGenre.size() - 1) {
                    newGenre.setVisibility(View.VISIBLE);
                    hideGenre.setVisibility(View.INVISIBLE);
                } else {
                    newGenre.setVisibility(View.GONE);
                    hideGenre.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setSelection(findGenrePosition(book));


    }

    /**
     * find the spinner position with the book gender
     * @param book
     * @return spinner index
     */
    public int findGenrePosition(Book book)
    {
        int cpt = 0;
        List<String> l_genre = GenreCollection.getGendersToString();

        for(int i = 0; i<l_genre.size(); i++){
            if(l_genre.get(i).equals(book.genre))
                return i;
        }
        return -1;
    }

    /**
     * Associate with the "Edit the Book" Button
     * Udpate the Book in the db with the new following informations
     * @param view
     */
    public void editBook(View view)
    {
        String s_genre;
        if(newGenre.getText().toString().trim().length() != 0)
        {
            s_genre = newGenre.getText().toString().trim();
            List<Genre> l_genre = GenreCollection.getGenres();
            int newId = l_genre.get(l_genre.size() - 1).getGenreId() + 1;
            GenreCollection.addGender(new Genre(newId, s_genre));
        } else {
            s_genre = (!spinner.getSelectedItem().toString().isEmpty()) ? spinner.getSelectedItem().toString() : getResources().getString(R.string.u_genre);
        }

        EditText title = (EditText)findViewById(R.id.EditTitle);
        EditText author = (EditText)findViewById(R.id.EditAuthor);
        EditText description = (EditText)findViewById(R.id.EditDescription);
        EditText year = (EditText)findViewById(R.id.EditYear);
        EditText isbn = (EditText)findViewById(R.id.EditIsbn);

        Book newBook = new Book(title.getText().toString(), author.getText().toString(), s_genre, year.getText().toString(), description.getText().toString(), isbn.getText().toString());

        BookCollection.removeBook(book_isbn);
        BookCollection.addBook(newBook);

        title.getText().clear();
        author.getText().clear();
        description.getText().clear();
        year.getText().clear();
        spinner.setSelection(0);
        isbn.getText().clear();

        arrayGenre = GenreCollection.getGendersToString();
        ArrayAdapter my_adapter = new ArrayAdapter(this, R.layout.spinner_row, arrayGenre);
        spinner.setAdapter(my_adapter);

        Context context = getApplicationContext();
        CharSequence text = "Your book has been edited";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        BookDetailFragment.bEdit = newBook;
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
