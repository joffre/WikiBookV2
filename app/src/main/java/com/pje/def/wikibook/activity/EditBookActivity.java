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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.fragment.BookDetailFragment;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.model.BookCollection;

/**
 * Created by David on 07/10/2015.
 */
public class EditBookActivity extends Activity {
    private ImageSwitcher switcher;
    private Button b1, b2;
    private int[] drawables = new int[]{R.drawable.icone, R.drawable.icone2};
    private int cpt = 0;
    public static final String BOOK_TO_EDIT = "book_edit";
    int lastItemChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

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
        lastItemChecked = intent.getIntExtra(BOOK_TO_EDIT,-1);
        Book book = BookCollection.getBooks().get(lastItemChecked);

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
        EditText genre = (EditText)findViewById(R.id.EditGenre);
        genre.setText(book.getGender());
        EditText isbn = (EditText)findViewById(R.id.EditIsbn);
        isbn.setText(book.getIsbn());

    }

    public void editBook(View view)
    {
        EditText title = (EditText)findViewById(R.id.EditTitle);
        EditText author = (EditText)findViewById(R.id.EditAuthor);
        EditText description = (EditText)findViewById(R.id.EditDescription);
        EditText year = (EditText)findViewById(R.id.EditYear);
        EditText genre = (EditText)findViewById(R.id.EditGenre);
        EditText isbn = (EditText)findViewById(R.id.EditIsbn);

        Book newBook = new Book(title.getText().toString(), author.getText().toString(), genre.getText().toString(), year.getText().toString(), description.getText().toString(), isbn.getText().toString(), drawables[cpt]);


        BookCollection.getBooks().remove(lastItemChecked);
        BookCollection.getBooks().add(lastItemChecked, newBook);

        title.getText().clear();
        author.getText().clear();
        description.getText().clear();
        year.getText().clear();
        genre.getText().clear();
        isbn.getText().clear();

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
