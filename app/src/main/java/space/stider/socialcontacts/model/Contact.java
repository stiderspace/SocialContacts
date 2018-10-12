package space.stider.socialcontacts.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;


/**
 * Contact model class
 */
public class Contact extends SugarRecord {

    private String name;
    private String phone;
    private String email;
    private byte[] image;

    public Contact(){}


    public Contact(String name, String phone, String email, byte[] image) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getImage() {
        return image;
    }



}
