package com.pje.def.wikibook.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.FilterDetails;
import com.pje.def.wikibook.model.BookFilter;
import com.pje.def.wikibook.model.BookFilterCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookFilterCreatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookFilterCreatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFilterCreatorFragment extends Fragment implements View.OnClickListener {
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
     * @return A new instance of fragment BookFilterCreatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFilterCreatorFragment newInstance(String param1, String param2) {
        BookFilterCreatorFragment fragment = new BookFilterCreatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BookFilterCreatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle("Filter Creator");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_filter_creator, container, false);
        Button bC = (Button) view.findViewById(R.id.btn_create_filter);
        bC.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_filter:
                createBookFilter();
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

    private void createBookFilter() {
        Map<BookFilter.FilterType, String> criteria = new HashMap<BookFilter.FilterType, String>();
        EditText name = (EditText) getActivity().findViewById(R.id.CriterionName);
        EditText title = (EditText) getActivity().findViewById(R.id.CriterionTitle);
        EditText author = (EditText) getActivity().findViewById(R.id.CriterionAuthor);
        EditText description = (EditText) getActivity().findViewById(R.id.CriterionDescription);
        EditText year = (EditText) getActivity().findViewById(R.id.CriterionYear);
        EditText genre = (EditText) getActivity().findViewById(R.id.CriterionGenre);
        EditText isbn = (EditText) getActivity().findViewById(R.id.CriterionIsbn);

        criteria.put(BookFilter.FilterType.TITLE, title.getText().toString());
        criteria.put(BookFilter.FilterType.AUTHOR, author.getText().toString());
        criteria.put(BookFilter.FilterType.DESCRIPTION, description.getText().toString());
        criteria.put(BookFilter.FilterType.YEAR, year.getText().toString());
        criteria.put(BookFilter.FilterType.GENDER, genre.getText().toString());
        criteria.put(BookFilter.FilterType.ISBN, isbn.getText().toString());

        FilterDetails newBookFilterDetails = new FilterDetails(name.getText().toString(), title.getText().toString(), author.getText().toString(), year.getText().toString(), genre.getText().toString(), description.getText().toString(), isbn.getText().toString());

        CharSequence text;
        String s_name = name.getText().toString().trim();
        if (s_name == null || s_name.isEmpty()) {
            text = "Name not found.";
        } else if (BookFilterCollection.getBookFilter(s_name) == null) {
            if (BookFilterCollection.addBookFilter(newBookFilterDetails)) {
                text = "Your book filter has been created";
                name.getText().clear();
                title.getText().clear();
                author.getText().clear();
                description.getText().clear();
                year.getText().clear();
                genre.getText().clear();
                isbn.getText().clear();
            } else {
                text = "Your book filter can't be created";
            }
        } else {
            text = "Your book filter already exist. Change the filter name !";
        }
        Context context = getActivity().getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
