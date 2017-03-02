package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by satyam on 2/6/17.
 */
public class ServerResponse {

    // variable name should be same as in the json response from php    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

}