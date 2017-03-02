package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by satyam on 2/6/17.
 */
public interface FileUploadService {
    @Multipart
    @POST("Upload.php")
    Call<ResponseBody> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );
}