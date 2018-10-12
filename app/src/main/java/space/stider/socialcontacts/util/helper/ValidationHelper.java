package space.stider.socialcontacts.util.helper;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Validation helper class for validating Email addresses, phone numbers.
 *
 *
 */

public class ValidationHelper {
    private static final String PHONE_REGEX = "(^\\+[0-9]{2}|^\\+[0-9]{2}\\(0\\)|^\\(\\+[0-9]{2}\\)\\(0\\)|^00[0-9]{2}|^0)([0-9]{9}$|[0-9\\-\\s]{10}$)";
    private static final String EMAIl_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";


    private static final String EMAIL_MSG = "Example@gmail.com";
    private static final String PHONE_MSG = "(+***)(0)*-********";




    //check if field is has valid values
    public static boolean isValid(EditText editText, String regex, String errorMsg, boolean required){

        String text = editText.getText().toString().trim();

        if(required && !hasText(editText))return false;
        if (required && !Pattern.matches(regex, text)){
            editText.setError(errorMsg);
            return false;
        }
        return true;
    }

    //check if emails is valid with Email regex
    public static boolean isEmail(EditText editText, boolean required){
        return isValid(editText, EMAIl_REGEX, EMAIL_MSG, required);
    }

    //check if phone is valid with Phone regex
    public static boolean isPhoneNumber(EditText editText, boolean required){
        return isValid(editText, PHONE_REGEX,PHONE_MSG, required);
    }

    //check is field has text
    public static boolean hasText(EditText editText){
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if(text.length() ==0 )

        {
            //editText.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }


}
