package com.pje.def.wikibook.fragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public void scanBook (View view)
    {
        //check for scan button
        if(view.getId()==R.id.scanBtn) {
            //instantiate ZXing integration class
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
            //start scanning
            scanIntegrator.initiateScan();
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

        BookDetails newBookDetails = new BookDetails(0, s_title, s_author, s_year, s_genre, s_description, s_isbn);
        Book newBook = new Book( newBookDetails, drawables[cpt]);


        BookCollection.addBook(newBook);
        CharSequence text = "Your book has been created";
        try{
            ((MainActivity) this.getActivity()).getHelper().getBookDao().create(newBookDetails);
            title.getText().clear();
            author.getText().clear();
            description.getText().clear();
            year.getText().clear();
            genre.getText().clear();
            isbn.getText().clear();


        } catch(SQLException exception){
            text = "Your book can't be create";
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
