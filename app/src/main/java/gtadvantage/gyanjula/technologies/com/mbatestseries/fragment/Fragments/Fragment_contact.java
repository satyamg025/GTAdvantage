package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.ApplyJobs;

/**
 * Created by satyam on 2/2/17.
 */
public class Fragment_contact extends DialogFragment {
    View view;
    ImageView fb,google,linkedin,insta,twitter;
    Button close;

    public static Fragment_contact instance(String mobile, String name) {
        Fragment_contact fragment_contact=new Fragment_contact();
        Bundle b=new Bundle();
        b.putString("mobile",mobile);
        b.putString("name",name);
        fragment_contact.setArguments(b);
        return fragment_contact;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_contact_us, null);
        builder.setView(view);

        fb=(ImageView)view.findViewById(R.id.fb);
        google=(ImageView)view.findViewById(R.id.google_plus);
        linkedin=(ImageView)view.findViewById(R.id.linkedin);
        insta=(ImageView)view.findViewById(R.id.instagram);
        twitter=(ImageView)view.findViewById(R.id.twitter);
        close=(Button)view.findViewById(R.id.close);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/gyanjulatechno.in/photos/a.917338505040536.1073741827.820231218084599/1052657368175315/?type=3"));
                startActivity(intent);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/techgyanjula/"));
                startActivity(intent);
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/gyanjula-technologies"));
                startActivity(intent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/TechGyanjula"));
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/115545388100613864987/about"));
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ApplyJobs.class);
                intent.putExtra("mobile",getArguments().getString("mobile"));
                intent.putExtra("name",getArguments().getString("name"));
                startActivity(intent);
            }
        });
        return builder.create();
    }

}
