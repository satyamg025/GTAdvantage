package myapplication.satyam.example.com.mbatestseries.fragment.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import myapplication.satyam.example.com.mbatestseries.R;
import myapplication.satyam.example.com.mbatestseries.fragment.Activities.QuesPaper;

/**
 * Created by satyam on 12/22/16.
 */
public class Advanced extends android.support.v4.app.Fragment {
    private View view;
    SharedPreferences sharedPreferences;
    String paperId="paperId";
    Button l1,l2,l3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_advanced,container,false);
        l1=(Button)view.findViewById(R.id.advanced_l1);
        l2=(Button)view.findViewById(R.id.advanced_l2);
        l3=(Button)view.findViewById(R.id.advanced_l3);
        sharedPreferences=view.getContext().getSharedPreferences(paperId, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),QuesPaper.class);
                editor.putString(paperId,"advanced_l1");
                editor.commit();
                startActivity(intent);

            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),QuesPaper.class);
                editor.putString(paperId,"advanced_l2");
                editor.commit();
                startActivity(intent);

            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),QuesPaper.class);
                editor.putString(paperId,"advanced_l3");
                editor.commit();
                startActivity(intent);

            }
        });

        return view;
    }
}
