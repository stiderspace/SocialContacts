package space.stider.socialcontacts.contactlistview;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import space.stider.socialcontacts.R;
import space.stider.socialcontacts.model.Contact;
import space.stider.socialcontacts.util.helper.ImageHelper;

/*
list adapter for the Contact cards
 */

public class ContactListAdapter  extends ArrayAdapter<Contact>{

    private final String TAG = this.getClass().getSimpleName(); //debug tag

    private  ContactViewHolder viewHolder;
    private Context context;
    private List<Contact> contacts;// list with contacts that are showing

    public ContactListAdapter(Context context, List<Contact> contactList) {
        super(context, R.layout.segment_contact_card, contactList);
        this.contacts = contactList;
        this.context = context;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.segment_contact_card, null);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageView_ContactImage);
            TextView name = (TextView) convertView.findViewById(R.id.textView_Name);
            TextView phone = (TextView) convertView.findViewById(R.id.textView_PhoneNumber);
            TextView email = (TextView) convertView.findViewById(R.id.textView_Email);
            viewHolder = new ContactViewHolder(image, name, phone, email);
            convertView.setTag(viewHolder);

        }else
        {
            viewHolder = (ContactViewHolder) convertView.getTag();
        }

        final Contact contact = contacts.get(position);
        viewHolder.getEmail().setText(contact.getEmail());

        viewHolder.getName().setText(contact.getName());
        viewHolder.getPhone().setText(contact.getPhone());


        if(contact.getImage()!= null) {
            viewHolder.getImageView().setImageBitmap(ImageHelper.getImage(contact.getImage()));
            Log.d(TAG, "image is not null");
        }else
        {
            Log.d(TAG, "its null");
        }

        return convertView;
    }

    public List<Contact> getData()
    {
        return contacts;
    }


}
