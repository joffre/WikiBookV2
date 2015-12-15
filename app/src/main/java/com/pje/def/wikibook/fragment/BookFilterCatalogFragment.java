package com.pje.def.wikibook.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.activity.FiltredCollectionActivity;
import com.pje.def.wikibook.model.BookFilter;
import com.pje.def.wikibook.model.BookFilterCollection;
import com.pje.def.wikibook.activity.EditBookFilterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookFilterCatalogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookFilterCatalogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFilterCatalogFragment extends Fragment {
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
     * @return A new instance of fragment BookFilterCatalogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFilterCatalogFragment newInstance(String param1, String param2) {
        BookFilterCatalogFragment fragment = new BookFilterCatalogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BookFilterCatalogFragment() {
        // Required empty public constructor
    }

    private int lastItemClicked = -1;
    private String itemClickedName;
    private ListView bookFilterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //lastItemClicked = -1;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_filter_catalog, container, false);
        bookFilterList = (ListView) view.findViewById(R.id.bookFilterList);
        getActivity().setTitle("My Filters");
        majFilterBookList();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("On Resume" + getActivity().getLocalClassName());
        majFilterBookList();
    }

    public void majFilterBookList(){
        List<Map<String, String>> l_filter = new ArrayList<Map<String, String>>();
        for (BookFilter bookFilter : BookFilterCollection.getBookFilters()) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("name", bookFilter.getName());
            bookMap.put("author", bookFilter.getCriterion(BookFilter.FilterType.AUTHOR));
            bookMap.put("title", bookFilter.getCriterion(BookFilter.FilterType.TITLE));
            bookMap.put("gender", bookFilter.getCriterion(BookFilter.FilterType.GENDER));
            bookMap.put("isbn", bookFilter.getCriterion(BookFilter.FilterType.ISBN));
            bookMap.put("year", bookFilter.getCriterion(BookFilter.FilterType.YEAR));
            bookMap.put("description", bookFilter.getCriterion(BookFilter.FilterType.DESCRIPTION));
            l_filter.add(bookMap);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(getActivity().getBaseContext(), l_filter, R.layout.book_filter_detail,
                new String[] {"name", "author", "title", "gender","isbn", "year", "description"},
                new int[] {R.id.filterName, R.id.filterAuthor, R.id.filterTitle, R.id.filterGender, R.id.filterIsbn, R.id.filterYear, R.id.filterDescription});
        bookFilterList.setAdapter(listAdapter);
        bookFilterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                lastItemClicked = position;
                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                itemClickedName = map.get("name");
                checkAndModifyMenuItemsVisibility();
                System.out.println(position + "   " + id);
            }
        });
        checkAndModifyMenuItemsVisibility();
    }

    private void checkAndModifyMenuItemsVisibility(){
        ActionMenuItemView menuItemDel = (ActionMenuItemView) getActivity().findViewById(R.id.filter_action_del);
        ActionMenuItemView menuItemEdit = (ActionMenuItemView) getActivity().findViewById(R.id.filter_action_edit);
        ActionMenuItemView menuItemSee = (ActionMenuItemView) getActivity().findViewById(R.id.filter_action_see);
        if(menuItemDel != null && menuItemEdit != null && menuItemSee != null){
            if(BookFilterCollection.getBookFilters().isEmpty() ||  lastItemClicked == -1){
                menuItemDel.setVisibility(View.INVISIBLE);
                menuItemEdit.setVisibility(View.INVISIBLE);
                menuItemSee.setVisibility(View.INVISIBLE);
                getActivity().setTitle("My Filters");
            } else {
                menuItemDel.setVisibility(View.VISIBLE);
                menuItemEdit.setVisibility(View.VISIBLE);
                menuItemSee.setVisibility(View.VISIBLE);
                getActivity().setTitle(itemClickedName);
            }
        } else {
            System.out.println("NULLLLLLLLLLLL");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           // mListener = (OnFragmentInteractionListener) activity;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_book_filter_catalog, menu);
        //this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.filter_action_see) {
            actionDisplay();
            return true;
        }

        if (id == R.id.filter_action_edit) {
            actionEdit();
            return true;
        }

        if (id == R.id.filter_action_del) {
            actionDelete();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void actionDelete(){
        if(lastItemClicked != -1) {
            BookFilterCollection.removeBookFilter(itemClickedName);
            majFilterBookList();
        }
        lastItemClicked = -1;
        checkAndModifyMenuItemsVisibility();
    }

    private void actionDisplay(){
        if(lastItemClicked != -1) {
            Intent intent = new Intent(getActivity(), FiltredCollectionActivity.class);
            intent.putExtra(FiltredCollectionActivity.FILTER_NAME, itemClickedName);
            startActivity(intent);
        }
    }

    private void actionEdit(){
        if(lastItemClicked != -1) {
            Intent intent = new Intent(getActivity(), EditBookFilterActivity.class);
            intent.putExtra(EditBookFilterActivity.FILTER_TO_EDIT, itemClickedName);
            startActivity(intent);
        }
    }
}
