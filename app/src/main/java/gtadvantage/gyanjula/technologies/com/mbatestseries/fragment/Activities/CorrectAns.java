package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Adapter.adapter_correct_ans;

import java.util.ArrayList;
import java.util.List;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;

public class CorrectAns extends AppCompatActivity {

    List<String> ques=new ArrayList<String>();
    String correct_ans[],user_ans[];
    RecyclerView mrecyclerView;
    LinearLayoutManager mlinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_ans);

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
}
