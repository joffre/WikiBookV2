package com.pje.def.wikibook;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.bdd.DatabaseHandler;
import com.pje.def.wikibook.bdd.FilterDetails;
import com.pje.def.wikibook.fragment.BookCollectionFragment;
import com.pje.def.wikibook.fragment.BookCreatorFragment;
import com.pje.def.wikibook.fragment.BookFilterCreatorFragment;
import com.pje.def.wikibook.fragment.BookFilterCatalogFragment;
import com.pje.def.wikibook.fragment.ContentFragment;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.model.BookCollection;
import com.pje.def.wikibook.model.BookFilter;
import com.pje.def.wikibook.model.BookFilterCatalog;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    public static BookCollection books;
    public static BookFilterCatalog filters;

    private static DatabaseHandler databaseHandler = null;
    private static boolean isInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isInitialized) {
            try {
                List<BookDetails> booksDetails = getHelper().getBookDao().queryForAll();
                for(BookDetails bookDetails : booksDetails){
                    books.addBook(new Book(bookDetails, R.drawable.icone));
                }
               /* books.addBook(new Book("Oui-Oui à la cantine", "Oui-oui Himself", "Jeunesse", "1994", "Oui-Oui mange à la cantine", "00001", R.drawable.icone));
                //books.addBook(new Book("Kamasutra","God Himself","Chasse","-870","Recueil","00002",R.drawable.icone));
                books.addBook(new Book("Harry Potter et à l'école des sorciers", "J.K. Rowling", "Jeunesse", "1992", "Un jeune sorcier découvre la magie", "00003", R.drawable.icone));
                books.addBook(new Book("Harry Potter et la chambre des secrets", "J.K. Rowling", "Jeunesse", "1994", "La chambre des secrets est ouverte ...", "00004", R.drawable.icone));
                books.addBook(new Book("Harry Potter et le prisonnier d'Askaban", "J.K. Rowling", "Jeunesse", "1999", "Harry rencontre son oncle...", "00005", R.drawable.icone));
                books.addBook(new Book("Titeuf", "Zep", "Jeunesse", "2005", "Tchô !!", "00006", R.drawable.icone));
                books.addBook(new Book("Asterix", "Uderzo", "Tout public", "1999", "Ils sont fou ces romains", "00007", R.drawable.icone));
               */

                List<FilterDetails> filtersDetails = getHelper().getFilterDao().queryForAll();
                for(FilterDetails filterDetails : filtersDetails){
                    filters.addBookFilter(new BookFilter(filterDetails));
                }
                isInitialized = true;
            } catch(SQLException exception){

            }
        }
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.collection:
                        Toast.makeText(getApplicationContext(),"Collection Selected",Toast.LENGTH_SHORT).show();
                        BookCollectionFragment fragmentBookCollection = new BookCollectionFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookCollection).commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.add_book:
                        Toast.makeText(getApplicationContext(), "Add Book Selected", Toast.LENGTH_SHORT).show();
                        BookCreatorFragment fragmentBookCreator = new BookCreatorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookCreator).commit();
                        return true;
                    case R.id.filter_list:
                        Toast.makeText(getApplicationContext(),"Filter List Selected",Toast.LENGTH_SHORT).show();
                        BookFilterCatalogFragment fragmentBookFilterCatalog = new BookFilterCatalogFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookFilterCatalog).commit();
                        return true;
                    case R.id.create_filter:
                        Toast.makeText(getApplicationContext(),"Create Filter Selected",Toast.LENGTH_SHORT).show();
                        BookFilterCreatorFragment fragmentBookFilterCreator = new BookFilterCreatorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookFilterCreator).commit();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public DatabaseHandler getHelper(){
        if(databaseHandler == null){
            databaseHandler = OpenHelperManager.getHelper(this, DatabaseHandler.class);
        }
        return databaseHandler;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(databaseHandler != null){
            OpenHelperManager.releaseHelper();
            databaseHandler = null;
        }
    }

}
