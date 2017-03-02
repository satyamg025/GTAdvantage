package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Response;

/**
 * Created by satyam on 2/2/17.
 */

import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.POJO.analysisPOJO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface analysisResponse {
    @GET("analysis")
    Call<analysisPOJO>requestResponse(@Query("mobile") String mobile);


}
