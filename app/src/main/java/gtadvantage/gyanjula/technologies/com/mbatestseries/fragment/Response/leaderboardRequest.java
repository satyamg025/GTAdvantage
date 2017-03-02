package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Response;

/**
 * Created by satyam on 2/2/17.
 */

import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.POJO.leaderboardPOJO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface leaderboardRequest {
    @GET("leaderboard")
    Call<leaderboardPOJO> request();

}
