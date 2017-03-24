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

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;

/**
 * Created by satyam on 2/5/17.
 */
public class Fragment_update extends DialogFragment {
    View view;
    Button btn;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);

        btn=(Button)view.findViewById(R.id.playstore);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store"));
                startActivity(intent);
            }
        });
        return builder.create();
    }




    }
