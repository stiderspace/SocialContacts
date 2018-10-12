package space.stider.socialcontacts.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import space.stider.socialcontacts.R;


public class MyContactInfoFragment extends Fragment {

    // quote textview
    private TextView randomQuote;

    public MyContactInfoFragment() {

    }

    // turning off menu items when fragement is visible
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        MenuItem myProfile = menu.findItem(R.id.myContactInfo);
        myProfile.setVisible(false);
        search.setVisible(false);
    }

    public static MyContactInfoFragment newInstance() {
        MyContactInfoFragment fragment = new MyContactInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_my_contact_info, container, false); //fragment view
        container.removeAllViews();

        randomQuote = rootView.findViewById(R.id.textView_Quote);
        getQuote();

        container.removeAllViews();
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /*
    simple methode for retreaving code from the Quote Rest API
     */
    public void getQuote()
    {
        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://quotes.rest/qod.json"; // 10 requests perhour

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("QUOTE", response.toString());

                try {
                    String quote = "";
                    String author = "";
                    JSONObject contentObject = response.getJSONObject("contents");
                    JSONArray contentArray = contentObject.getJSONArray("quotes");

                    for(int i = 0; i < contentArray.length(); i++)
                    {
                        quote = contentArray.getJSONObject(i).getString("quote").toString();
                        author = contentArray.getJSONObject(i).getString("author").toString();
                    }

                    randomQuote.setText('"'+ quote + '"' + " -" + author);
                } catch (JSONException e) {
                    randomQuote.setText(getResources().getString(R.string.quoteError));
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }
}
