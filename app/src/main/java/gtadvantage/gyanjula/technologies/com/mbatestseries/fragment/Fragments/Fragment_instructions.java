package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities.QuesAns;

/**
 * Created by satyam on 2/5/17.
 */
public class Fragment_instructions extends DialogFragment {
    View view;
    Button btn;

    public static Fragment_instructions instance(String name, String mobile, String sponName, String sponImg, String sponInfo, String sponLink,String paperId) {
        Fragment_instructions fragment_instructions=new Fragment_instructions();
        Bundle b=new Bundle();
        b.putString("mobile",mobile);
        b.putString("name",name);
        b.putString("sponName",sponName);
        b.putString("sponImg",sponImg);
        b.putString("sponInfo",sponInfo);
        b.putString("sponLink",sponLink);
        b.putString("paperId",paperId);
        fragment_instructions.setArguments(b);
        return fragment_instructions;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_instructions, null);
        builder.setView(view);

        btn=(Button)view.findViewById(R.id.ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),QuesAns.class);
                intent.putExtra("mobile",getArguments().getString("mobile"));
                intent.putExtra("name",getArguments().getString("name"));
                intent.putExtra("sponName",getArguments().getString("sponName"));
                intent.putExtra("sponImg",getArguments().getString("sponImg"));
                intent.putExtra("sponInfo",getArguments().getString("sponInfo"));
                intent.putExtra("sponLink",getArguments().getString("sponLink"));
                intent.putExtra("paperId",getArguments().getString("paperId"));
                intent.putExtra("instruction","1");
                startActivity(intent);

            }
        });
        return builder.create();

    }
}
