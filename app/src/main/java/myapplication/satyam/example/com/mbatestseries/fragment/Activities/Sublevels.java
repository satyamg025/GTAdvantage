package myapplication.satyam.example.com.mbatestseries.fragment.Activities;

//import android.app.Fragment;
//import android.app.FragmentManager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Objects;

import myapplication.satyam.example.com.mbatestseries.R;
import myapplication.satyam.example.com.mbatestseries.fragment.Fragments.Advanced;
import myapplication.satyam.example.com.mbatestseries.fragment.Fragments.Basic;
import myapplication.satyam.example.com.mbatestseries.fragment.Fragments.Intermediate;

public class Sublevels extends AppCompatActivity {
    FrameLayout fl;
    String PREF_NAME="level";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sublevels);

        fl=(FrameLayout)findViewById(R.id.fl);

        Fragment fragment=null;
        String level=getPrefs().getString(PREF_NAME,"");

       if (Objects.equals(level, "1")) {
            Class fragmentClass = null;
            fragmentClass= Basic.class;
            FragmentManager fragmentManager = getSupportFragmentManager();
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragmentManager.beginTransaction().replace(R.id.fl, fragment).commit();

        }
        else if(Objects.equals(level, "2")){
            Class fragmentClass = null;
            fragmentClass= Intermediate.class;
            FragmentManager fragmentManager = getSupportFragmentManager();
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragmentManager.beginTransaction().replace(R.id.fl, fragment).commit();

        }
        else if(Objects.equals(level, "3")){
            Class fragmentClass = null;
            fragmentClass= Advanced.class;
            FragmentManager fragmentManager = getSupportFragmentManager();
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragmentManager.beginTransaction().replace(R.id.fl, fragment).commit();

        }

    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(PREF_NAME,MODE_PRIVATE);
    }
}
