package com.pje.def.wikibook.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pje.def.wikibook.MainActivity;
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
 * {@link BookCollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookCollectionFragment extends Fragment {
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
     * @return A new instance of fragment BookCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookCollectionFragment newInstance(String param1, String param2) {
        BookCollectionFragment fragment = new BookCollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    int lastItemClicked = -1;
    final String BOOK_TO_EDIT = "book_edit";
    private ListView bookList;

    public BookCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        System.out.println("On create");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_collection, container, false);
        bookList = (ListView) view.findViewById(R.id.bookList);
       // Menu menu = (Menu) view.findViewById(R.menu.menu_book_collection);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                //getActivity().setTitle(map.get("title"));
                lastItemClicked = position;
                System.out.println(position + "   " + id);

                Bundle args = new Bundle();
                args.putSerializable(BookDetailFragment.BOOK_PARAM, BookCollection.getBooks().get(lastItemClicked));

                BookDetailFragment fragmentBookDetail = new BookDetailFragment();
                fragmentBookDetail.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentBookDetail).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).addToBackStack(null).commit();
            }
        });

        bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
        getActivity().setTitle("My Collection");
        majListBook();
        System.out.println("On create view");
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("On resume");
        majListBook();
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           // mListener = (OnFragmentInteractionListener) activity;
           // actionBar=((AppCompatActivity)activity).getSupportActionBar();
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

    private void majListBook() {
        List<Map<String, String>> l_books = new ArrayList<Map<String, String>>();

        for (Book book : BookCollection.getBooks()) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("img", String.valueOf(book.getId_img())); // use available img
            bookMap.put("author", book.getAuthor());
            bookMap.put("title", book.getTitle());
            l_books.add(bookMap);
        }

        SimpleAdapter listAdapter = new SimpleAdapter(getActivity().getBaseContext(), l_books, R.layout.book_detail,
                /*ajout gender*/
                new String[] {"img", "author", "title"},
                new int[] {R.id.img_cover, R.id.author, R.id.title});
        bookList.setAdapter(null);
        bookList.setAdapter(listAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_book_collection, menu);
        //this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_del) {
            deleteAction();
            return true;
        }
        if(id == R.id.action_edit) {
            editAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteAction() {
        if(lastItemClicked != -1) {
            BookCollection.getBooks().remove(lastItemClicked);
            majListBook();
            getActivity().setTitle("MyCollection");
            if (BookCollection.getBooks().size() == 0) {
                ActionMenuItemView menuItem = (ActionMenuItemView) getActivity().findViewById(R.id.action_del);
                menuItem.setVisibility(View.INVISIBLE);
                menuItem = (ActionMenuItemView) getActivity().findViewById(R.id.action_edit);
                menuItem.setVisibility(View.INVISIBLE);
            }
            lastItemClicked = -1;
        }
    }

    public void editAction() {
        if(lastItemClicked != -1) {
            Intent intent = new Intent(this.getActivity(), EditBookActivity.class);
            intent.putExtra(BOOK_TO_EDIT, lastItemClicked);
            startActivity(intent);
        }
    }
}
