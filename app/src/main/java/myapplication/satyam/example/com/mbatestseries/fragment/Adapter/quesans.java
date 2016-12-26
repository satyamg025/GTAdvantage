package myapplication.satyam.example.com.mbatestseries.fragment.Adapter;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import myapplication.satyam.example.com.mbatestseries.R;

/**
 * Created by satyam on 12/23/16.
 */
public class quesans extends RecyclerView.Adapter<quesans.ViewHolder2> {

   public List<String> ques = new ArrayList<String>(),quesId=new ArrayList<String>();
    public List<List<String>> options=new ArrayList<List<String>>();
    public List<String> op=new ArrayList<String>();
    Context context;
    public List<String> ans=new ArrayList<String>();
    String nj=null;
    int j;

    public quesans(List<String> ques, List<String> quesId,List<List<String>> options, Context co) {
        super();
        this.ques=ques;
        this.quesId=quesId;
        this.options=options;
        this.context=co;

    }

    @Override

    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_quesans, parent, false);
        ViewHolder2 viewholder=new ViewHolder2(v);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder2 viewHolder, final int i) {
        viewHolder.ques.setText(ques.get(i));

        op=options.get(i);
        viewHolder.opt1.setText(op.get(0));
        viewHolder.opt2.setText(op.get(1));
        viewHolder.opt3.setText(op.get(2));
        viewHolder.opt4.setText(op.get(3));

        viewHolder.option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                j=viewHolder.getAdapterPosition();
                RadioButton radioButton = (RadioButton)viewHolder.itemView.findViewById(checkedId);
                nj= (String) radioButton.getText();
                ans.add(j,nj);
                Toast.makeText(context,ans.get(j),Toast.LENGTH_SHORT).show();
            }

            }

        );


    }
    @Override
    public int getItemCount() {
        return ques.size();
    }

    class ViewHolder2 extends RecyclerView.ViewHolder{
        public TextView ques;
        public RadioButton opt1,opt2,opt3,opt4;
        public RadioGroup option;
        public ViewHolder2(View itemView) {
            super(itemView);
            this.option=(RadioGroup)itemView.findViewById(R.id.options);
            this.ques=(TextView)itemView.findViewById(R.id.ques);
            this.opt1=(RadioButton)itemView.findViewById(R.id.option1);
            this.opt2=(RadioButton)itemView.findViewById(R.id.option2);
            this.opt3=(RadioButton)itemView.findViewById(R.id.option3);
            this.opt4=(RadioButton)itemView.findViewById(R.id.option4);
        }

    }
}