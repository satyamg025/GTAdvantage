package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;

/**
 * Created by satyam on 1/8/17.
 */
@SuppressLint("ValidFragment")
public class Fragment_sponsor extends DialogFragment {
    String sponImg;
    View view;
    ImageView img;
    public Fragment_sponsor(String sponImg) {
        this.sponImg=sponImg;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater=getActivity().getLayoutInflater();
        view=inflater.inflate(R.layout.dialog_sponsor,null);
        builder.setView(view);

        img=(ImageView)view.findViewById(R.id.dialog_sponImg);
        Picasso
                .with(getContext())
                .load("http://gyanjulatechnologies.com/"+sponImg)
                .into(img);
        return builder.create();
    }
}
