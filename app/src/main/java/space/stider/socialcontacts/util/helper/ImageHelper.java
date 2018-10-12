package space.stider.socialcontacts.util.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/*
image helper class to handle bitmaps for storage to database
 */
public class ImageHelper {

    //bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
        return stream.toByteArray();
    }

    //byte array to bitmap
    public static Bitmap getImage(byte[] image)
    {
        if (image != null){
            return BitmapFactory.decodeByteArray(image, 0, image.length);

        }
        return null;

    }

    //method to change image size to keep small enough to keep in data base, can also be used to increase size
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
}
