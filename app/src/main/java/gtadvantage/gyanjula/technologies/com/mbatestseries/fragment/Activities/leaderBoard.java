package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter.adapter;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.POJO.leaderboardPOJO;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Response.leaderboardRequest;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Util.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class leaderBoard extends AppCompatActivity {
    List<Integer> basic,intermediate,advanced,score;
    int max,itemp,myScore;String stemp;
    RecyclerView leader; RecyclerView.Adapter ada; List<String> name;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("LeaderBoard");
        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        setTitleColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        leader=(RecyclerView)findViewById(R.id.leaderboard_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        leader.setLayoutManager(linearLayoutManager);
        leader.setHasFixedSize(true);
        name=new ArrayList<>();
        basic=new ArrayList<>();
        intermediate=new ArrayList<>();
        advanced=new ArrayList<>();
        score=new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        leaderboardRequest request= ServiceGenerator.createService(leaderboardRequest.class);
        Call<leaderboardPOJO> call=request.request();
        call.enqueue(new Callback<leaderboardPOJO>() {
            @Override
            public void onResponse(Call<leaderboardPOJO> call, Response<leaderboardPOJO> response) {
                progressDialog.dismiss();

                name=response.body().getNAME();
                basic=response.body().getBASIC();
                intermediate=response.body().getINTERMEDIATE();
                advanced=response.body().getADVANCED();
                max=response.body().getMaxTotal();
                for(int i=0;i<basic.size();i++){
                    score.add((int)Math.ceil((basic.get(i)+intermediate.get(i)+advanced.get(i))*100/max));

                }
                for(int i=0;i<name.size()-1;i++)
                {
                    for(int j=0;j<name.size()-i-1;j++)
                    {
                        if(score.get(j)<score.get(j+1)){

                            Collections.swap(score,j,j+1);
                            Collections.swap(name,j,j+1);
                        }
                    }

                }
                Log.e("Score",score.toString());
                Log.e("Basic",basic.toString());
                ada=new adapter(name,score);
                leader.setAdapter(ada);

            }

            @Override
            public void onFailure(Call<leaderboardPOJO> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}

