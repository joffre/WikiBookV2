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
import com.pje.def.wikibook.bdd.DatabaseHandler;
import com.pje.def.wikibook.fragment.BlankFragment;
import com.pje.def.wikibook.fragment.BookCollectionFragment;
import com.pje.def.wikibook.fragment.BookCreatorFragment;
import com.pje.def.wikibook.fragment.BookFilterCreatorFragment;
import com.pje.def.wikibook.fragment.BookFilterCatalogFragment;
import com.pje.def.wikibook.bdd.BookCollection;
import com.pje.def.wikibook.bdd.BookFilterCollection;
import com.pje.def.wikibook.bdd.ImageCollection;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    public static BookCollection books;
    public static BookFilterCollection filters;

    private static DatabaseHandler databaseHandler = null;
    private static boolean isInitialized;


    /**
     * Initialize all the elements of the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHelper();
        setContentView(R.layout.activity_main);
        if(!isInitialized) {
           ImageCollection.init();
           isInitialized = true;
        }
        // Initializing Toolbar and setting it as the actionbar
        if(getResources().getConfiguration().orientation == 2) {
            toolbar = (Toolbar) findViewById(R.id.toolbarLand);
            setSupportActionBar(toolbar);
        } else {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
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
                        if(getResources().getConfiguration().orientation == 2) {

                            BlankFragment blankFragment = new BlankFragment();

                            getSupportFragmentManager().beginTransaction().replace(R.id.frameDetail, blankFragment).commit();
                        }
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.add_book:
                        BookCreatorFragment fragmentBookCreator = new BookCreatorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookCreator, "BOOK_CREATOR").commit();
                        return true;
                    case R.id.filter_list:
                        BookFilterCatalogFragment fragmentBookFilterCatalog = new BookFilterCatalogFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookFilterCatalog).commit();
                        return true;
                    case R.id.create_filter:
                        BookFilterCreatorFragment fragmentBookFilterCreator = new BookFilterCreatorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookFilterCreator).commit();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        BookCollectionFragment fragmentBookCollection = new BookCollectionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookCollection).commit();
        if(getResources().getConfiguration().orientation == 2) {
            BlankFragment blankFragment = new BlankFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameDetail, blankFragment).commit();
        }

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

    /**
     * Initialize the dataBase
     */
    public void initHelper(){
        if(databaseHandler == null){
            databaseHandler = OpenHelperManager.getHelper(this, DatabaseHandler.class);
        }
    }

    public static DatabaseHandler getHelper(){
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
