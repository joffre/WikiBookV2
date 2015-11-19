package com.pje.def.wikibook.fragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.pje.def.wikibook.MainActivity;
import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.model.BookCollection;
import com.pje.def.wikibook.scan.HttpRequest;
import com.pje.def.wikibook.scan.JSONParser;

import java.sql.SQLException;


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

    private ImageSwitcher switcher;
    private Button b1, b2;
    private int[] drawables = new int[]{R.drawable.icone, R.drawable.icone2};
    public int cpt = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_book_creator, container, false);
        // Inflate the layout for this fragment
        switcher = (ImageSwitcher)v.findViewById(R.id.imageSwitcher1);
        b1 = (Button) v.findViewById(R.id.button);
        b2 = (Button) v.findViewById(R.id.button2);

        Button bC = (Button) v.findViewById(R.id.btn_create);
        bC.setOnClickListener(this);

        ImageButton bS = (ImageButton) v.findViewById(R.id.scanBtn);
        bS.setOnClickListener(this);
        //init the Image switcher

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView myView = new ImageView(getActivity().getApplicationContext());
                return myView;
            }
        });

        switcher.setImageResource(drawables[cpt]);

        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
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
        getActivity().setTitle("Book Creator");
        vCF = v;
       return v;
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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

    public void scanBook ()
    {
        //instantiate ZXing integration class
        IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity())/* {
        @Override
        protected void startActivityForResult(Intent intent, int code) {
            this.startActivityForResult(intent, 312); // REQUEST_CODE override
        }
    };*/;

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
        /*EditText description = (EditText)findViewById(R.id.EditDescription);
        description.setText(parser.getDescription());

        EditText genre = (EditText)findViewById(R.id.EditGenre);
        genre.setText(book.getGender());
        EditText isbn = (EditText)findViewById(R.id.EditIsbn);
        isbn.setText(book.getIsbn());*/
            Log.v("TEST", parser.getAuthor());
            Log.v("TEST", parser.getTitle());
            Log.v("TEST", parser.getYear());
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Fragment not enable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
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

    private void createBook()
    {
        EditText title = (EditText)getActivity().findViewById(R.id.EditTitle);
        EditText author = (EditText)getActivity().findViewById(R.id.EditAuthor);
        EditText description = (EditText)getActivity().findViewById(R.id.EditDescription);
        EditText year = (EditText)getActivity().findViewById(R.id.EditYear);
        EditText genre = (EditText)getActivity().findViewById(R.id.EditGenre);
        EditText isbn = (EditText)getActivity().findViewById(R.id.EditIsbn);

        String s_title = (!title.getText().toString().isEmpty()) ? title.getText().toString() : getResources().getString(R.string.u_title);
        String s_author = (!author.getText().toString().isEmpty()) ? author.getText().toString() : getResources().getString(R.string.u_author);
        String s_description = (!description.getText().toString().isEmpty()) ? description.getText().toString() : getResources().getString(R.string.u_description);
        String s_year= (!year.getText().toString().isEmpty()) ? year.getText().toString() : getResources().getString(R.string.u_year);
        String s_genre = (!genre.getText().toString().isEmpty()) ? genre.getText().toString() : getResources().getString(R.string.u_genre);
        String s_isbn = (!isbn.getText().toString().isEmpty()) ? isbn.getText().toString() : getResources().getString(R.string.u_isbn);

        BookDetails newBookDetails = new BookDetails(s_isbn, s_title, s_author, s_year, s_genre, s_description);
        Book newBook = new Book( newBookDetails, drawables[cpt]);
        CharSequence text;
        if(BookCollection.getBook(s_isbn) == null) {
            text = "Your book has been created";
            if (BookCollection.addBook(newBook)) {
                title.getText().clear();
                author.getText().clear();
                description.getText().clear();
                year.getText().clear();
                genre.getText().clear();
                isbn.getText().clear();
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
