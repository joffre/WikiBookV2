package com.pje.def.wikibook.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.activity.EditBookActivity;
import com.pje.def.wikibook.model.Book;
import com.pje.def.wikibook.model.BookCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
        * Activities that contain this fragment must implement the
        * {@link BookDetailFragment.OnFragmentInteractionListener} interface
* to handle interaction events.
        * Use the {@link BookDetailFragment#newInstance} factory method to
        * create an instance of this fragment.
        */
public class BookDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String BOOK_PARAM = "bookDetail";
    public static final String BOOK_PARAM_ID = "bookDetailId";
    public static Book bEdit;

    // TODO: Rename and change types of parameters
    private Book mParam1;
    private View v;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BookDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetailFragment newInstance(Book param1) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(BOOK_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public BookDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bEdit = null;
        if (getArguments() != null) {
            mParam1 = (Book) getArguments().getSerializable(BOOK_PARAM);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_detail, container, false);

        ImageView cover = (ImageView) v.findViewById(R.id.img_cover);
        cover.setImageResource(mParam1.getId_img());

        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(mParam1.getTitle());

        TextView author = (TextView) v.findViewById(R.id.author);
        author.setText(mParam1.getAuthor());

        TextView gender = (TextView) v.findViewById(R.id.gender);
        gender.setText(mParam1.getGender());

        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(mParam1.getDescription());

        TextView year = (TextView) v.findViewById(R.id.year);
        year.setText(mParam1.getYear());

        TextView isbn = (TextView) v.findViewById(R.id.isbn);
        isbn.setText(mParam1.getIsbn());
        this.v = v;
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                            BookCollectionFragment fragmentBookCollection = new BookCollectionFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookCollection).commit();
                            return true;
                        } else {
                            return false;
                    }
                }
                return false;

            }
        });
        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_book_detail, menu);
        //this.menu = menu;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_edit) {
            editAction();
            return true;
        } else if(id == R.id.action_del) {
            delAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void delAction() {
        BookCollection.removeBook(mParam1.getIsbn());
        BookCollectionFragment fragmentBookCollection = new BookCollectionFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookCollection).commit();
    }

    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);

        return;
    }
    public void editAction() {
            Intent intent = new Intent(this.getActivity(), EditBookActivity.class);
            intent.putExtra(EditBookActivity.BOOK_TO_EDIT, mParam1.getIsbn());
            startActivity(intent);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onResume(){
        super.onResume();
        System.out.println("On resume");
        if(bEdit != null)
        majInfoBook();
    }
    public void majInfoBook() {
        ImageView cover = (ImageView) v.findViewById(R.id.img_cover);
        cover.setImageResource(bEdit.getId_img());

        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(bEdit.getTitle());

        TextView author = (TextView) v.findViewById(R.id.author);
        author.setText(bEdit.getAuthor());

        TextView gender = (TextView) v.findViewById(R.id.gender);
        gender.setText(bEdit.getGender());

        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(bEdit.getDescription());

        TextView year = (TextView) v.findViewById(R.id.year);
        year.setText(bEdit.getYear());

        TextView isbn = (TextView) v.findViewById(R.id.isbn);
        isbn.setText(bEdit.getIsbn());
    };
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
}
