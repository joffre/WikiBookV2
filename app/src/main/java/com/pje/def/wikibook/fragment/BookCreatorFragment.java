package com.pje.def.wikibook.fragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pje.def.wikibook.activity.FormationCameraActivity;
import com.pje.def.wikibook.MainActivity;
import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.BookCollection;
import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.bdd.ImageCollection;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.bdd.GenreCollection;
import com.pje.def.wikibook.model.Genre;
import com.pje.def.wikibook.scan.FragmentIntentIntegrator;
import com.pje.def.wikibook.scan.HttpRequest;
import com.pje.def.wikibook.scan.JSONParser;
import com.pje.def.wikibook.utility.DownloadImageTask;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookCreatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookCreatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookCreatorFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final int STATIC_INTEGER_VALUE =10;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public View vCF;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookCreatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookCreatorFragment newInstance(String param1, String param2) {
        BookCreatorFragment fragment = new BookCreatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BookCreatorFragment() {
        // Required empty public constructor
    }

    private EditText addGender;
    private Spinner genreSpinner;
    private DownloadImageTask dlITask;
    private Bitmap pictureTaken;
    private TextView hideTitle;
    private Button b3;
    public int cpt = 0;
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(getActivity()!=null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dlITask = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_book_creator, container, false);
        // Inflate the layout for this fragment
        b3 = (Button) v.findViewById(R.id.btn_picture);

        ImageView image = (ImageView) v.findViewById(R.id.EditImage);
        image.setImageResource(R.drawable.icone);

        hideTitle = (TextView) v.findViewById(R.id.hideTitle);
        hideTitle.setVisibility(View.GONE);

        addGender = (EditText)v.findViewById(R.id.addGender);
        addGender.setVisibility(View.GONE);

        Button bC = (Button) v.findViewById(R.id.btn_create);
        bC.setOnClickListener(this);

        ImageButton bS = (ImageButton) v.findViewById(R.id.scanBtn);
        bS.setOnClickListener(this);
        getActivity().setTitle("Book Creator");

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FormationCameraActivity.class);
                startActivityForResult(intent, STATIC_INTEGER_VALUE);
            }
        });

        genreSpinner = (Spinner)v.findViewById(R.id.spinner1);

        final List<String> arrayGenre = GenreCollection.getGendersToString();
        arrayGenre.add("Add a new gender");
        ArrayAdapter my_adapter = new ArrayAdapter(getActivity(), R.layout.spinner_row, arrayGenre);
        genreSpinner.setAdapter(my_adapter);
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == arrayGenre.size() - 1){
                    addGender.setVisibility(View.VISIBLE);
                    hideTitle.setVisibility(View.INVISIBLE);
                } else {
                    addGender.setVisibility(View.GONE);
                    hideTitle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vCF = v;

        if(GenreCollection.getGenres().size() > 0) {
            Log.d("TEST", GenreCollection.getGenres().get(1).getGenreTitle().toString());
            System.out.println(GenreCollection.getGenres().get(1).getGenreTitle().toString());
        }
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create:
                createBook();
                break;
            case R.id.scanBtn:
                scanBook();
                break;

        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void onPause(){
        if(getActivity()!=null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onPause();
    }

    public void addGenre(){
        //New intent : GenreCretor
    }

    public void scanBook ()
    {
        //instantiate ZXing integration class
        IntentIntegrator scanIntegrator = new FragmentIntentIntegrator(this);

        //start scanning
        scanIntegrator.initiateScan();

    }

    public void remplirChamps(JSONParser parser){
       /* BookCreatorFragment fragmentBookCreator = new BookCreatorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookCreator, "BOOK_CREATOR").commit();
*/
        View v = vCF;
        if(v != null) {
            EditText title = (EditText) v.findViewById(R.id.EditTitle);
            title.setText(parser.getTitle());
            EditText author = (EditText) v.findViewById(R.id.EditAuthor);
            author.setText(parser.getAuthor());
            EditText year = (EditText)v.findViewById(R.id.EditYear);
            year.setText(parser.getYear());
            EditText description = (EditText)v.findViewById(R.id.EditDescription);
            description.setText(parser.getDescription());

            /*EditText genre = (EditText)findViewById(R.id.EditGenre);
            genre.setText(book.getGender());*/
            EditText isbn = (EditText)v.findViewById(R.id.EditIsbn);
            isbn.setText(parser.getIsbn());
            Log.v("TEST", parser.getAuthor());
            Log.v("TEST", parser.getTitle());
            Log.v("TEST", parser.getYear());
            if(parser.getThumbnail() != null && !parser.getThumbnail().isEmpty()) {
                dlITask = new DownloadImageTask((ImageView) v.findViewById(R.id.EditImage));
                dlITask.execute(parser.getThumbnail());
            }
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Fragment not enable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(getActivity()!=null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case (STATIC_INTEGER_VALUE) : {
                if (resultCode == Activity.RESULT_OK) {
                    byte[] picture = intent.getByteArrayExtra(FormationCameraActivity.DATA_PICTURE_TAKEN);
                    ImageView image = (ImageView) v.findViewById(R.id.EditImage);
                    pictureTaken = BitmapFactory.decodeByteArray(picture,0,picture.length);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    pictureTaken = Bitmap.createBitmap(pictureTaken, 0, 0, pictureTaken.getWidth(), pictureTaken.getHeight(), matrix, true);
                    image.setImageBitmap(pictureTaken);
                }
                break;
            }
            default : {
                //check we have a valid result
                if (scanningResult != null) {
                    //get content from Intent Result
                    String scanContent = scanningResult.getContents();
                    //get format name of data scanned
                    String scanFormat = scanningResult.getFormatName();
                    // result
                    Log.v("SCAN", "content: " + scanContent + " - format: " + scanFormat);

                    if(scanContent!=null && scanFormat!=null && scanFormat.equalsIgnoreCase("EAN_13")){
                        //book search
                        String bookSearchString = "https://www.googleapis.com/books/v1/volumes?"+
                                "q=isbn:"+scanContent+"&key=AIzaSyBuNGyHC_um1Me1ezISiSmrHW2Tsvk2mqo";

                        HttpRequest httpRequest = new HttpRequest();
                        httpRequest.doHttpRequest(bookSearchString, (MainActivity) getActivity(), this);
                    }
                    else{
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                "Not a valid scan!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    //invalid scan data or scan canceled
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            "No book scan data received!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }

    }
    private void createBook()
    {
        EditText title = (EditText)getActivity().findViewById(R.id.EditTitle);
        EditText author = (EditText)getActivity().findViewById(R.id.EditAuthor);
        EditText description = (EditText)getActivity().findViewById(R.id.EditDescription);
        EditText year = (EditText)getActivity().findViewById(R.id.EditYear);
        //EditText genre = (EditText)getActivity().findViewById(R.id.EditGenre);
        EditText isbn = (EditText)getActivity().findViewById(R.id.EditIsbn);
        Spinner spinner = (Spinner)getActivity().findViewById(R.id.spinner1);

        String s_genre;
        String s_title = (!title.getText().toString().isEmpty()) ? title.getText().toString() : getResources().getString(R.string.u_title);
        String s_author = (!author.getText().toString().isEmpty()) ? author.getText().toString() : getResources().getString(R.string.u_author);
        String s_description = (!description.getText().toString().isEmpty()) ? description.getText().toString() : getResources().getString(R.string.u_description);
        String s_year= (!year.getText().toString().isEmpty()) ? year.getText().toString() : getResources().getString(R.string.u_year);
        String s_isbn = (!isbn.getText().toString().isEmpty()) ? isbn.getText().toString() : getResources().getString(R.string.u_isbn);

        if(addGender.getText().toString().trim().length() != 0)
        {
            s_genre = addGender.getText().toString().trim();
            List<Genre> l_genre = GenreCollection.getGenres();
            int newId = l_genre.get(l_genre.size() - 1).getGenreId() + 1;
            GenreCollection.addGender(new Genre(newId, s_genre));
        } else {
            s_genre = (!spinner.getSelectedItem().toString().isEmpty()) ? spinner.getSelectedItem().toString() : getResources().getString(R.string.u_genre);
        }
        BookDetails newBookDetails = new BookDetails(s_isbn, s_title, s_author, s_year, s_genre, s_description);
        Book newBook = new Book( newBookDetails);
        CharSequence text;
        if(s_isbn == null || s_isbn.isEmpty() || s_isbn.charAt(0)=='U'){
            text = "Isbn not found or incorrect.";
        } else if(BookCollection.getBook(s_isbn) == null) {
            text = "Your book has been created : ("+s_isbn+")";
            if (BookCollection.addBook(newBook)) {
                title.getText().clear();
                author.getText().clear();
                description.getText().clear();
                year.getText().clear();
                isbn.getText().clear();
                spinner.setSelection(0);
                if(dlITask != null){
                    ImageCollection.addImage(s_isbn, dlITask.getImage());
                    dlITask = null;
                    ImageView cover = (ImageView) v.findViewById(R.id.EditImage);
                    cover.setImageResource(R.drawable.icone);
                } else if(pictureTaken != null) {
                    ImageCollection.addImage(s_isbn, pictureTaken);
                    pictureTaken = null;
                    ImageView cover = (ImageView) v.findViewById(R.id.EditImage);
                    cover.setImageResource(R.drawable.icone);
                }
            } else {
                text = "Your book can't be create";
            }
        } else {
            text = "This isbn already exist.";
        }
        Context context = getActivity().getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}