package com.example.mac.akiba;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akiba.data.model.Message;
import com.akiba.data.model.RegisterPostData;
import com.akiba.data.model.SmsModel;
import com.akiba.data.realm.User;
import com.akiba.data.remote.APIService;
import com.akiba.data.remote.ApiUtils;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Leyo on 17/02/2017.
 */

public class ReadSms extends ListActivity {
    private TextView mResponseTv;
    private APIService mAPIService;
    Realm realm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResponseTv = (TextView) findViewById(R.id.tv_response);
        mAPIService = ApiUtils.getAPIService();
        Realm.init(this);
        realm=Realm.getDefaultInstance();

        List<SMSData> smsList = new ArrayList<SMSData>();
        List<Message> messages = new ArrayList<Message>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        startManagingCursor(c);

        // Read the sms data and store it in the list

        if (c.moveToFirst()) {
            Integer id;
            for (int i = 0; i < c.getCount(); i++) {
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));
                String person = c.getString(c.getColumnIndex("person"));
                String date = c.getString(c.getColumnIndex("date"));
                String type = c.getString(c.getColumnIndex("type"));

                if(address.equalsIgnoreCase("m-shwari")){
                    SMSData sms= new SMSData();
                    Message message = new Message();
                    String[] separated = body.split("M-Shwari balance is Ksh.");
//                    if(count)
                  //  String[] separated2 = separated[1].split(" ");
//                    sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                    //Log.d(separated[1]);
                   // Log.d("this is my array", "arr: " + Arrays.toString(separated2));
                    sms.setBody(Arrays.toString(separated));
                    message.setFrom(address.toString());
                    message.setMessage(body.toString());
//                    message.setPerson(person.toString());
                    message.setDate(date.toString());
                    message.setType(date.toString());
                    sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                    smsList.add(sms);
                    messages.add(message);
                    realm.beginTransaction();
                        User user = realm.createObject(User.class, UUID.randomUUID().toString());
                        user.setNama(address);
                        user.setAlamat(body);
                        realm.commitTransaction();

                }

                c.moveToNext();
            }
        }
        c.close();
        String phone = "0728355429";
        sendPost(phone,messages);
        // Set smsList in the ListAdapter
        setListAdapter(new SMSListAdapter(this, smsList));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMSData sms = (SMSData) getListAdapter().getItem(position);

        Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_LONG).show();

    }

    public void sendPost(String phone, List<Message> messages) {
        Log.e("sending post", "sending post...");
        // RxJava
        mAPIService.saveMessages(new SmsModel(phone, messages)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<RegisterPostData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<RegisterPostData> registerPostData) {
                        if (registerPostData.isSuccessful()){
                            showResponse(registerPostData.body().toString());
                        }

                        System.out.println(registerPostData.code());
                        System.out.println(registerPostData.message());
                    }
                });
    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
}
