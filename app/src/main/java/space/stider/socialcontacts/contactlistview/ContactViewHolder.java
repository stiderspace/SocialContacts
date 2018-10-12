package space.stider.socialcontacts.contactlistview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class ContactViewHolder{

    private ImageView imageView;
    private TextView name;
    private TextView email;
    private TextView phone;


    public ContactViewHolder(ImageView image, TextView name, TextView phone, TextView email) {
        this.imageView =    image;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public TextView getName() {
        return name;
    }

    public TextView getEmail() {
        return email;
    }

    public TextView getPhone() {
        return phone;
    }

}
