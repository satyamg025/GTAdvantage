package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import gtadvantage.gyanjula.technologies.com.mbatestseries.R;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Network.NetworkCheck;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Response.FileUploadService;
import gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.Util.ServiceGenerator2;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    Spinner spinner,spinner2,spinner3;
    String PREF_NAME,nam,mobile;
    EditText name,email,city,profile,cv;
    EditText ten_per,ten_board,twel_per,twel_board,dob,grad_per,grad_board;
    List<String> study_options=new ArrayList<String>(),gen=new ArrayList<String>(),find_us=new ArrayList<String>();
    ArrayAdapter<String> adapter,adapter2,adapter3;
    int pos,pos2,pos3;
    Button done;
    SharedPreferences sharedPreferences2;
    String filepath,filename,filename2,filepath2,responseString=null;
    int m;
    private int STORAGE_PERMISSION_CODE = 23;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        name=(EditText)findViewById(R.id.reg_name);
        email=(EditText)findViewById(R.id.reg_email);
        city=(EditText)findViewById(R.id.reg_city);
        dob=(EditText)findViewById(R.id.reg_dob);
        ten_per=(EditText)findViewById(R.id.ten_per);
        profile=(EditText)findViewById(R.id.profile_img);
        cv=(EditText)findViewById(R.id.cv);
        ten_board=(EditText)findViewById(R.id.ten_board);
        twel_per=(EditText)findViewById(R.id.twel_per);
        twel_board=(EditText)findViewById(R.id.twel_board);
        grad_per=(EditText)findViewById(R.id.grad_per);
        grad_board=(EditText)findViewById(R.id.grad_board);
        done=(Button)findViewById(R.id.done);
        spinner=(Spinner)findViewById(R.id.study);
        spinner2=(Spinner)findViewById(R.id.gender);
        spinner3=(Spinner)findViewById(R.id.find_us);

        if(!getIntent().getExtras().getString("name").equals("")){
            spinner3.setVisibility(View.GONE);
            profile.setVisibility(View.GONE);
            cv.setVisibility(View.GONE);
            spinner2.setVisibility(View.GONE);
            done.setText("Update");
            get_details(getIntent().getExtras().getString("mobile"));
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel(dob);

            }

        };


        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Registration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m= 0;
                if(isReadStorageAllowed()) {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
                }
                else{
                    requestStoragePermission();
                }
            }
        });

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m=1;
                if(isReadStorageAllowed()) {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
                }
                else{
                    requestStoragePermission();
                }
            }
        });

        study_options.add("Choose Your qualification");
        study_options.add("B.TECH");
        study_options.add("B.COM");
        study_options.add("BSC");
        study_options.add("BCA");
        study_options.add("12th");
        study_options.add("10th");
        study_options.add("WORKING");

        adapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,study_options);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gen.add("Gender");
        gen.add("Male");
        gen.add("Female");
        gen.add("Others");
        adapter2=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,gen);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos2=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        find_us.add(("Where did you find us?"));
        find_us.add("Facebook");
        find_us.add("Friends");
        find_us.add("Newspaper");
        find_us.add("Others");

        adapter3=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,find_us);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos3=i;
                if(pos3==(find_us.size()-1)) {
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.find);
                    til.setVisibility(View.VISIBLE);
                }
                else{
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.find);
                    til.setVisibility(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sharedPreferences2=getSharedPreferences("reg_status", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor1=sharedPreferences2.edit();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                    String na = name.getText().toString();
                    String em = email.getText().toString();
                    String ci = city.getText().toString();
                    String db=dob.getText().toString();
                    String ten_p=ten_per.getText().toString();
                    String ten_b=ten_board.getText().toString();
                    String twel_p=twel_per.getText().toString();
                    String twel_b=twel_board.getText().toString();
                    String grad_p=grad_per.getText().toString();
                    String grad_b=grad_board.getText().toString();


                    if(profile.getVisibility()==View.VISIBLE) {

                        if (na.equals("") || em.equals("") || ci.equals("") || db.equals("") || ten_p.equals("") || ten_b.equals("") || twel_p.equals("") || twel_b.equals("") || grad_p.equals("") || grad_b.equals("") || pos == 0 || pos2 == 0 || pos3 == 0 || filename.equals("") || filename2.equals("")) {
                            Snackbar.make(findViewById(android.R.id.content), "Please fill all entries", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        } else {
                            String grad = study_options.get(pos);
                            String ge = gen.get(pos2);
                            EditText fu = (EditText) findViewById(R.id.find_desc);


                            if (pos3 == (find_us.size() - 1)) {
                                String fi = fu.getText().toString();
                                if (fi.equals("")) {
                                    Snackbar.make(findViewById(android.R.id.content), "Please fill all entries", Snackbar.LENGTH_LONG)
                                            .setActionTextColor(Color.RED)
                                            .show();
                                } else {
                                    Log.e("details5", na + " " + em + " " + ci + " " + db + " " + ten_p + " " + ten_b + " " + twel_p + " " + twel_b + " " + grad_p + " " + grad_b + " " + grad + " " + ge + " " + fi);
                                    register(na, em, ci, db, ten_p, ten_b, twel_p, twel_b, grad_p, grad_b, grad, ge, getIntent().getExtras().getString("mobile", ""), fi);
                                }
                            } else {
                                String fi = find_us.get(pos3);
                                Log.e("details5", na + " " + em + " " + ci + " " + db + " " + ten_p + " " + ten_b + " " + twel_p + " " + twel_b + " " + grad_p + " " + grad_b + " " + grad + " " + ge + " " + fi);
                                register(na, em, ci, db, ten_p, ten_b, twel_p, twel_b, grad_p, grad_b, grad, ge, getIntent().getExtras().getString("mobile", ""), fi);
                            }
                        }
                    }
                    else{
                        if (na.equals("") || em.equals("") || ci.equals("") || db.equals("") || ten_p.equals("") || ten_b.equals("") || twel_p.equals("") || twel_b.equals("") || grad_p.equals("") || grad_b.equals("") || pos == 0) {
                            Snackbar.make(findViewById(android.R.id.content), "Please fill all entries", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        } else {
                            String grad = study_options.get(pos);

                            Log.e("details5", na + " " + em + " " + ci + " " + db + " " + ten_p + " " + ten_b + " " + twel_p + " " + twel_b + " " + grad_p + " " + grad_b + " " + grad + " ");
                            update(na, em, ci, db, ten_p, ten_b, twel_p, twel_b, grad_p, grad_b, grad,getIntent().getExtras().getString("mobile", ""));

                        }
                    }
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                            .show();

                }

            }
        });

    }


    private void uploadFile(Uri fileUri) {
        FileUploadService service =
                ServiceGenerator2.createService(FileUploadService.class);
          // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        //File file = FileUtils.getFile(String.valueOf(fileUri));
        File myFile = new File(fileUri.toString());
        filepath2 = getFilPath(fileUri);
       // uploadFile(uri);
        Toast.makeText(getApplicationContext(),String.valueOf(fileUri),Toast.LENGTH_SHORT).show();
        File sdCardRoot = Environment.getExternalStorageDirectory();
        File file = new File(sdCardRoot, filepath2);
        filename2 = file.getName();
       // cv.setText(filename2);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }




    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if(m==1) {
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        File myFile = new File(uri.toString());
                        filepath2 = getFilPath(uri);
                       // uploadFile(uri);
                        File sdCardRoot = Environment.getExternalStorageDirectory();
                        File yourDir = new File(sdCardRoot, filepath2);
                        filename2 = yourDir.getName();
                        cv.setText(filename2);
                        uploadFile(filepath2, filename2, (mobile + "cv"));

                    }
                    break;
                }
                else{
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        File myFile = new File(uri.toString());
                        filepath = getFilPath(uri);
                        //uploadFile(uri);
                        File sdCardRoot = Environment.getExternalStorageDirectory();
                        File yourDir = new File(sdCardRoot, filepath);
                        filename = yourDir.getName();
                        profile.setText(filename);
                        uploadFile(filepath, filename, (mobile + "prof"));

                    }
                    break;
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("NewApi")
    public String getFilPath(Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn( contentUri, null, null);
            }
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

                }


                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn( contentUri, selection, selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }




    public String getDataColumn(Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }





    private void updateLabel(EditText edittext) {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }


    public void register(final String na,final String em,final String ci,final String db,final String ten_p,final String ten_b,final String twel_p,final String twel_b,final String grad_p,final String grad_b,final String grad,final String ge,final String pho,final String fi){
        class RegisterAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Registration.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String name = params[0];
                String email=params[1];
                String city=params[2];
                String db=params[3];
                String tp=params[4];
                String tb=params[5];
                String twp=params[6];
                String twb=params[7];
                String gp=params[8];
                String gb=params[9];
                String study=params[10];
                String gen=params[11];
                String phone=params[12];
                String find=params[13];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("email",email));
                nameValuePairs.add(new BasicNameValuePair("city", city));
                nameValuePairs.add(new BasicNameValuePair("dob", db));
                nameValuePairs.add(new BasicNameValuePair("ten_per", tp));
                nameValuePairs.add(new BasicNameValuePair("ten_board",tb));
                nameValuePairs.add(new BasicNameValuePair("twel_per",twp));
                nameValuePairs.add(new BasicNameValuePair("twel_board", twb));
                nameValuePairs.add(new BasicNameValuePair("grad_per",gp));
                nameValuePairs.add(new BasicNameValuePair("grad_cllg",gb));
                nameValuePairs.add(new BasicNameValuePair("study",study));
                nameValuePairs.add(new BasicNameValuePair("gen",gen));
                nameValuePairs.add(new BasicNameValuePair("phone",phone));
                nameValuePairs.add(new BasicNameValuePair("find_us",find));
                nameValuePairs.add(new BasicNameValuePair("profile",filename));
                nameValuePairs.add(new BasicNameValuePair("cv",filename2));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/registration2");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                Log.e("regis",s);
                loadingDialog.dismiss();
                Intent intent=new Intent(Registration.this,ApplyJobs.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("mobile",mobile);
                intent.putExtra("reg_status",true);
                startActivity(intent);
                finish();

            }
        }

        RegisterAsync la = new RegisterAsync();
        la.execute(na,em,ci,db,ten_p,ten_b,twel_p,twel_b,grad_p,grad_b,grad,ge,pho,fi);

    }



    public void update(final String na,final String em,final String ci,final String db,final String ten_p,final String ten_b,final String twel_p,final String twel_b,final String grad_p,final String grad_b,final String grad,final String phone){
        class UpdateAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loadingDialog = ProgressDialog.show(Registration.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String name = params[0];
                String email=params[1];
                String city=params[2];
                String db=params[3];
                String tp=params[4];
                String tb=params[5];
                String twp=params[6];
                String twb=params[7];
                String gp=params[8];
                String gb=params[9];
                String study=params[10];
                String phone=params[11];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("email",email));
                nameValuePairs.add(new BasicNameValuePair("city", city));
                nameValuePairs.add(new BasicNameValuePair("dob", db));
                nameValuePairs.add(new BasicNameValuePair("ten_per", tp));
                nameValuePairs.add(new BasicNameValuePair("ten_board",tb));
                nameValuePairs.add(new BasicNameValuePair("twel_per",twp));
                nameValuePairs.add(new BasicNameValuePair("twel_board", twb));
                nameValuePairs.add(new BasicNameValuePair("grad_per",gp));
                nameValuePairs.add(new BasicNameValuePair("grad_cllg",gb));
                nameValuePairs.add(new BasicNameValuePair("study",study));
                nameValuePairs.add(new BasicNameValuePair("phone",phone));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/update_details");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                Log.e("regis123",s);

                Toast.makeText(getApplication(),"Profile Updated Successfully....",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Registration.this,ApplyJobs.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("mobile",getIntent().getExtras().getString("mobile"));
                intent.putExtra("reg_status",true);
                startActivity(intent);
                finish();

            }
        }

        UpdateAsync la = new UpdateAsync();
        la.execute(na,em,ci,db,ten_p,ten_b,twel_p,twel_b,grad_p,grad_b,grad,phone);

    }







    private void uploadFile(final String filePath, final String fileName,final String mobile) {
        class UF extends AsyncTask<String, String, String> {
            InputStream inputStream;
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
              //  loadingDialog = ProgressDialog.show(Registration.this, "", "Uploading...");
            }

            @Override
            protected String doInBackground(String... params) {

                String mob=params[2];
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mobile", mob));


                String fp = params[0];
                String fn = params[1];
                String mobile=params[2];
                try {
                    HttpPost httpPost;

                    HttpClient httpClient = new DefaultHttpClient();
                    if(m==1) {
                        httpPost = new HttpPost("http://gyanjulatechnologies.com/Upload.php");
                    }
                    else{
                        httpPost = new HttpPost("http://gyanjulatechnologies.com/Upload2.php");
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    File file = new File(fp);

                    FileBody fileBody = new FileBody(file);
                    MultipartEntity multipartEntity = new MultipartEntity(
                            HttpMultipartMode.BROWSER_COMPATIBLE);

                    multipartEntity.addPart("file", fileBody);
                    httpPost.setEntity(multipartEntity);

                    HttpResponse httpResponse = httpClient.execute(httpPost);


                    HttpEntity entity = httpResponse.getEntity();
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        responseString = EntityUtils.toString(entity);
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }

                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                } catch (IOException e) {
                    responseString = e.toString();
                }

                return responseString;

            }

            @Override
            protected void onPostExecute(String result) {

                Log.e("TAG", "Response from server: " + result);

                super.onPostExecute(result);
                String s = result.trim();
                Log.e("TAG", "Response from server: " + s);

            }
        }



        UF l = new UF();
        l.execute(filePath,fileName,mobile);


    }




    public void get_details(final String phone){
        class DetailsAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Registration.this, "", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String  phone=params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", phone));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://gyanjulatechnologies.com/MbaTestSeries/index.php/TestSeries/get_details");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                Log.e("details",s);
                loadingDialog.dismiss();
                try {
                    JSONObject jo=new JSONObject(s);

                    if(!jo.getBoolean("error")){
                        name.setText(jo.getString("name"));
                        email.setText(jo.getString("email"));
                        city.setText(jo.getString("city"));
                        dob.setText(jo.getString("dob"));
                        ten_per.setText(jo.getString("ten_per"));
                        ten_board.setText(jo.getString("ten_board"));
                        twel_per.setText(jo.getString("twel_per"));
                        twel_board.setText(jo.getString("twel_board"));
                        grad_per.setText(jo.getString("grad_per"));
                        grad_board.setText(jo.getString("grad_cllg"));
                        int id=adapter.getPosition(jo.getString("study"));
                        pos=id;
                        spinner.setSelection(id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            String na = name.getText().toString();
                            String em = email.getText().toString();
                            String ci = city.getText().toString();
                            String db=dob.getText().toString();
                            String ten_p=ten_per.getText().toString();
                            String ten_b=ten_board.getText().toString();
                            String twel_p=twel_per.getText().toString();
                            String twel_b=twel_board.getText().toString();
                            String grad_p=grad_per.getText().toString();
                            String grad_b=grad_board.getText().toString();


                            if(profile.getVisibility()==View.VISIBLE) {

                                if (na.equals("") || em.equals("") || ci.equals("") || db.equals("") || ten_p.equals("") || ten_b.equals("") || twel_p.equals("") || twel_b.equals("") || grad_p.equals("") || grad_b.equals("") || pos == 0 || pos2 == 0 || pos3 == 0 || filename.equals("") || filename2.equals("")) {
                                    Snackbar.make(findViewById(android.R.id.content), "Please fill all entries", Snackbar.LENGTH_LONG)
                                            .setActionTextColor(Color.RED)
                                            .show();
                                } else {
                                    String grad = study_options.get(pos);
                                    String ge = gen.get(pos2);
                                    EditText fu = (EditText) findViewById(R.id.find_desc);


                                    if (pos3 == (find_us.size() - 1)) {
                                        String fi = fu.getText().toString();
                                        if (fi.equals("")) {
                                            Snackbar.make(findViewById(android.R.id.content), "Please fill all entries", Snackbar.LENGTH_LONG)
                                                    .setActionTextColor(Color.RED)
                                                    .show();
                                        } else {
                                            Log.e("details5", na + " " + em + " " + ci + " " + db + " " + ten_p + " " + ten_b + " " + twel_p + " " + twel_b + " " + grad_p + " " + grad_b + " " + grad + " " + ge + " " + fi);
                                            register(na, em, ci, db, ten_p, ten_b, twel_p, twel_b, grad_p, grad_b, grad, ge, getIntent().getExtras().getString("mobile", ""), fi);
                                        }
                                    } else {
                                        String fi = find_us.get(pos3);
                                        Log.e("details5", na + " " + em + " " + ci + " " + db + " " + ten_p + " " + ten_b + " " + twel_p + " " + twel_b + " " + grad_p + " " + grad_b + " " + grad + " " + ge + " " + fi);
                                        register(na, em, ci, db, ten_p, ten_b, twel_p, twel_b, grad_p, grad_b, grad, ge, getIntent().getExtras().getString("mobile", ""), fi);
                                    }
                                }
                            }
                            else{
                                if (na.equals("") || em.equals("") || ci.equals("") || db.equals("") || ten_p.equals("") || ten_b.equals("") || twel_p.equals("") || twel_b.equals("") || grad_p.equals("") || grad_b.equals("") || pos == 0) {
                                    Snackbar.make(findViewById(android.R.id.content), "Please fill all entries", Snackbar.LENGTH_LONG)
                                            .setActionTextColor(Color.RED)
                                            .show();
                                } else {
                                    String grad = study_options.get(pos);

                                    Log.e("details5", na + " " + em + " " + ci + " " + db + " " + ten_p + " " + ten_b + " " + twel_p + " " + twel_b + " " + grad_p + " " + grad_b + " " + grad + " ");
                                    update(na, em, ci, db, ten_p, ten_b, twel_p, twel_b, grad_p, grad_b, grad,getIntent().getExtras().getString("mobile", ""));

                                }
                            }
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "No Network Connection", Snackbar.LENGTH_LONG)
                                    .show();

                        }


                    }
                });



            }
        }

        DetailsAsync la = new DetailsAsync();
        la.execute(phone);

    }



    private SharedPreferences getPrefs() {
        return getSharedPreferences(PREF_NAME,MODE_PRIVATE);
    }


}