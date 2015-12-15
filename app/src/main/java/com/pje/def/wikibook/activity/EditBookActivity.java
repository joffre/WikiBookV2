package com.pje.def.wikibook.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.fragment.BookDetailFragment;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.model.BookCollection;
import com.pje.def.wikibook.model.GenderCollection;
import com.pje.def.wikibook.model.Genre;

import java.util.List;

/**
 * Created by David on 07/10/2015.
 */
public class EditBookActivity extends Activity {
    private ImageSwitcher switcher;
    private Button b1, b2;
    private int[] drawables = new int[]{R.drawable.icone, R.drawable.icone2};
    private int cpt = 0;
    public static final String BOOK_TO_EDIT = "book_edit";
    private TextView hideGenre;
    private EditText newGenre;
    private Spinner spinner;
    private List<String> arrayGenre;

    String book_isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        hideGenre = (TextView)findViewById(R.id.textEditGenre);
        newGenre = (EditText)findViewById(R.id.addGenderEdit);
        spinner = (Spinner)findViewById(R.id.spinnerEdit);

        switcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);

        //init the Image switcher

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                return myView;
            }
        });
        Intent intent = getIntent();
        book_isbn = intent.getStringExtra(BOOK_TO_EDIT);
        Book book = BookCollection.getBook(book_isbn);

        switcher.setImageResource(book.id_img);

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);

        //button next/previous image
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDrawable("previous");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDrawable("next");
            }
        });



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

        arrayGenre = GenderCollection.getGendersToString();
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

    public int findGenrePosition(Book book)
    {
        int cpt = 0;
        List<Genre> l_genre = GenderCollection.getGenders();

        for(int i = 0; i<l_genre.size(); i++){
            if(l_genre.get(i).getGenreTitle().equals(book.genre))
                return i;
        }
        return -1;
    }

    public void editBook(View view)
    {
        String s_genre;
        if(newGenre.getText().toString().trim().length() != 0)
        {
            s_genre = newGenre.getText().toString().trim();
            List<Genre> l_genre = GenderCollection.getGenders();
            int newId = l_genre.get(l_genre.size() - 1).getGenreId() + 1;
            GenderCollection.addGender(new Genre(newId, s_genre));
        } else {
            s_genre = (!spinner.getSelectedItem().toString().isEmpty()) ? spinner.getSelectedItem().toString() : getResources().getString(R.string.u_genre);
        }

        EditText title = (EditText)findViewById(R.id.EditTitle);
        EditText author = (EditText)findViewById(R.id.EditAuthor);
        EditText description = (EditText)findViewById(R.id.EditDescription);
        EditText year = (EditText)findViewById(R.id.EditYear);
        EditText isbn = (EditText)findViewById(R.id.EditIsbn);

        Book newBook = new Book(title.getText().toString(), author.getText().toString(), s_genre, year.getText().toString(), description.getText().toString(), isbn.getText().toString(), drawables[cpt]);

        BookCollection.removeBook(book_isbn);
        BookCollection.addBook(newBook);

        title.getText().clear();
        author.getText().clear();
        description.getText().clear();
        year.getText().clear();
        spinner.setSelection(0);
        isbn.getText().clear();

        arrayGenre = GenderCollection.getGendersToString();
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

    public void setDrawable(String type)
    {
        if(type.equals("previous")) {
            if (this.cpt - 1 >= 0) {
                this.cpt--;

            } else {
                this.cpt = drawables.length -1;
            }

        } else {
            if (this.cpt + 1 < drawables.length) {
                this.cpt++;

            } else {
                this.cpt = 0;
            }
        }
        switcher.setImageResource(drawables[this.cpt]);
    }
}
