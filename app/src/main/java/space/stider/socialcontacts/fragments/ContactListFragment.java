package space.stider.socialcontacts.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import space.stider.socialcontacts.contactlistview.ContactListAdapter;
import space.stider.socialcontacts.R;
import space.stider.socialcontacts.model.Contact;


public class ContactListFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private OnFragmentInteractionListener mListener;

    private ListView contactListview;
    private ContactListAdapter adapter;
    private View rootView;
    private SearchView searchView; //search bar in the action bar
    private List<Contact> contacts;
    private List<Contact> allContacts; //default/unfiltered list of contacts


    public ContactListFragment() {

    }


    public static ContactListFragment newInstance(String param1, String param2) {
        ContactListFragment fragment = new ContactListFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        searchView = (SearchView) search.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.Search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchContacts(s);
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        super.onCreate(savedInstanceState);

        contacts = Contact.listAll(Contact.class,"name");
        allContacts = Contact.listAll(Contact.class,"name");
        contactListview = (ListView) rootView.findViewById(R.id.ListView_ContactCards);
        adapter = new ContactListAdapter(rootView.getContext(), contacts);

        contactListview.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newContactFragment = new NewContactFragment();
                OnFragmentInteractionListener listener = (OnFragmentInteractionListener) getActivity();
                listener.changeFragment(newContactFragment);
            }
        });


        this.rootView = rootView;

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //method to search through contacts
    public void searchContacts(String query)
    {

        List<Contact> filtered  = new ArrayList<>();
        Log.d(TAG, query);
        if (searchView !=null || !query.isEmpty())
        {
            for(Contact contact : allContacts)
            {
                if(contact.getName().toLowerCase().contains(query.toLowerCase()) || contact.getEmail().toLowerCase().contains(query.toLowerCase()))
                {
                    filtered.add(contact);
                }
            }
        }else
        {
            filtered = contacts;
        }
        adapter.getData().clear();
        adapter.getData().addAll(filtered);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });


    }


    public interface OnFragmentInteractionListener {
        public void changeFragment(Fragment fragment);
    }
}
