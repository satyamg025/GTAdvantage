package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter.adapter_correct_ans;

public class CorrectAns extends AppCompatActivity {

    List<String> ques=new ArrayList<String>();
    String correct_ans[],user_ans[];
    RecyclerView mrecyclerView;
    LinearLayoutManager mlinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_ans);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Answers");
        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        setTitleColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ques=getIntent().getExtras().getStringArrayList("ques");
        correct_ans=getIntent().getExtras().getStringArray("correct_ans");
        user_ans=getIntent().getExtras().getStringArray("user_ans");

        mrecyclerView=(RecyclerView)findViewById(R.id.recycler);
        assert mrecyclerView != null;
        mrecyclerView.setHasFixedSize(true);
        mlinearLayoutManager=new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mlinearLayoutManager);
        RecyclerView.Adapter mAdapter;
        mAdapter=new adapter_correct_ans(ques,correct_ans,user_ans,this);
        mrecyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
