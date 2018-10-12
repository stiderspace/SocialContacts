package space.stider.socialcontacts.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.InputStream;

import space.stider.socialcontacts.R;
import space.stider.socialcontacts.model.Contact;
import space.stider.socialcontacts.util.helper.ImageHelper;
import space.stider.socialcontacts.util.helper.ValidationHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewContactFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName(); //Logger tag

    private static final int GET_IMAGE = 1;


    private OnFragmentInteractionListener mListener;

    private TextInputLayout nameEditText;
    private TextInputLayout phoneEditText;
    private TextInputLayout emailEditText;

    private Bitmap bitmapImage;
    private ImageView contactImageView;
    private Button saveButton;

    //default constructor
    public NewContactFragment() {

    }


    public static NewContactFragment newInstance() {
        NewContactFragment fragment = new NewContactFragment();
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
        search.setVisible(false); //hiding search menuitem when fragment if visible
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_new_contact, container, false);
        container.removeAllViews();


        nameEditText = rootView.findViewById(R.id.name_text_input);
        phoneEditText = rootView.findViewById(R.id.phone_text_input);
        emailEditText = rootView.findViewById(R.id.email_text_input);
        contactImageView = rootView.findViewById(R.id.imageView_contact);
        saveButton = rootView.findViewById(R.id.saveButton);


        setupValidation();
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean noErrors = true;
                if (nameEditText.getEditText().getText().toString().isEmpty())
                {
                    nameEditText.setError(getResources().getString(R.string.error_empty_string));
                    noErrors = false;
                }
                if (emailEditText.getEditText().getText().toString().isEmpty())
                {
                    emailEditText.setError(getResources().getString(R.string.error_empty_string));
                    noErrors = false;
                }
                if (phoneEditText.getEditText().getText().toString().isEmpty())
                {
                    phoneEditText.setError(getResources().getString(R.string.error_empty_string));
                    noErrors = false;
                }
                //if no errors presist  the contact can be safed
                if(noErrors){
                    createContact(); //create and save contact

                    //open the contact fragment and replace it with the current
                    Fragment ContactFragment = new ContactListFragment();
                    ContactListFragment.OnFragmentInteractionListener listener = (ContactListFragment.OnFragmentInteractionListener) getActivity();
                    listener.changeFragment(ContactFragment);
                }
            }
        });

        contactImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(view);
            }
        });


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


    //function for creating a new contact and saving it to the database
    public void createContact()
    {
        String name =  nameEditText.getEditText().getText().toString();
        String email = emailEditText.getEditText().getText().toString();
        String phone = phoneEditText.getEditText().getText().toString();
        byte[] image;
        if (bitmapImage == null)
        {
            image = null;
        }else
        {
            image = ImageHelper.getBytes(bitmapImage);
        }
        Contact newContact = new Contact(name, phone,email,image);
        newContact.save();

    }

    //function for validating the newContact Form
    public void setupValidation()
    {
        //Validation of phone numbers (wont prevent saving if phone number is not valid)
        phoneEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                phoneEditText.setError(null);
                boolean noError = ValidationHelper.isPhoneNumber(phoneEditText.getEditText(),true);
                if (!noError)
                {
                    phoneEditText.setError(getResources().getString(R.string.error_not_valid_phone));
                }
            }
        });
        //Validation of email address (wont prevent saving if Email is not valid)
        emailEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                emailEditText.setError(null);
                boolean noError = ValidationHelper.isEmail(emailEditText.getEditText(), true);
                if (!noError)
                {
                    emailEditText.setError(getResources().getString(R.string.error_not_valid_email));
                }
            }
        });
    }




    //image choser for contact profile picture
    public void chooseImage(View view)
    {
        Intent intentGalery = new Intent();
        intentGalery.setType("image/*");
        intentGalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGalery, "Select profile picture"), GET_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == GET_IMAGE && data != null && data.getData() != null){
            Uri imageUri = data.getData();

            InputStream inputStream;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                bitmapImage = ImageHelper.scaleDown(image,200, true);//crop image and save to variable
                contactImageView.setImageBitmap(bitmapImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "error when  loading image", Toast.LENGTH_LONG).show();
            }


            Log.d(TAG, imageUri.toString());

        }
    }

    // fragment interface
    public interface OnFragmentInteractionListener {
        //interface methode to change fragments
        public void changeFragment(Fragment fragment);
    }
}
