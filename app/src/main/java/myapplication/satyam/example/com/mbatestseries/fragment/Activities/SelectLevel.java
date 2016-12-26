package myapplication.satyam.example.com.mbatestseries.fragment.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import myapplication.satyam.example.com.mbatestseries.R;

public class SelectLevel extends AppCompatActivity {
    Button basic,intermediate,advanced;
    SharedPreferences sharedPreferences;
    String level="level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        basic=(Button)findViewById(R.id.btn_basic);
        intermediate=(Button)findViewById(R.id.btn_intermediate);
        advanced=(Button)findViewById(R.id.btn_advanced);

        sharedPreferences = getSharedPreferences(level, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),Sublevels.class);
                editor.putString(level,"1");
                editor.commit();
                startActivity(intent);
            }
        });
        intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),Sublevels.class);
                editor.putString(level,"2");
                editor.commit();
                startActivity(intent);
            }
        });
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),Sublevels.class);
                editor.putString(level,"3");
                editor.commit();
                startActivity(intent);
            }
        });


    }
}
