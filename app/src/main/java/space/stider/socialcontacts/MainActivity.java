package space.stider.socialcontacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orm.SugarContext;

import space.stider.socialcontacts.fragments.ContactListFragment;
import space.stider.socialcontacts.fragments.MyContactInfoFragment;
import space.stider.socialcontacts.fragments.NewContactFragment;

public class MainActivity extends AppCompatActivity implements ContactListFragment.OnFragmentInteractionListener, NewContactFragment.OnFragmentInteractionListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SugarContext.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        if (id == R.id.myContactInfo) {
            Fragment fragment = new MyContactInfoFragment();
            changeFragment(fragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //method is called when mail address is clicked in the app
    public void mailContact(View view) {
        //opens default Email client of the phone and puts the contact email as recipient
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:"+ ((TextView) view.findViewById(R.id.textView_Email)).getText());
        mailIntent.setData(data);
        startActivity(mailIntent);

    }

    //methode is called when phone number is clicked in the app
    public void callContact(View view) {

        //opens default phone application
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + ((TextView) view.findViewById(R.id.textView_PhoneNumber)).getText());
        intent.setData(data);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        Fragment fragment = new ContactListFragment();
        changeFragment(fragment);
    }


    //fragment interface method is called in the interface to switch between fragments
    @Override
    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.main_fragment, fragment);
        fragmentTransaction.addToBackStack(null);


        fragmentTransaction.commit();
    }

}
