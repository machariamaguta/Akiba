package com.example.mac.akiba;
/**
 * Created by Leyo on 17/02/2017.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akiba.data.model.RegisterPostData;
import com.akiba.data.remote.APIService;
import com.akiba.data.remote.ApiUtils;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class PostActivity extends AppCompatActivity {
    private TextView mResponseTv;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit);

        final EditText titleEt = (EditText) findViewById(R.id.et_title);
        final EditText bodyEt = (EditText) findViewById(R.id.et_body);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {
                    sendPost(title, body);
                }
            }
        });
    }

//    public void sendPost(String title, String body) {
//        Log.e("sending post", "sending post...");
//        mAPIService.savePost(title, body).enqueue(new Callback<RegisterPostData>() {
//            @Override
//            public void onResponse(Call<RegisterPostData> call, Response<RegisterPostData> response) {
//
//                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
//                    Log.i("Call", "post submitted to API." + response.body().toString());
//                }else{
//                    Log.i("Error", "Not successful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterPostData> call, Throwable t) {
//                Log.e("failed", "Unable to submit post to API.");
//            }
//        });
//    }
    public void sendPost(String title, String body) {
        Log.e("sending post", "sending post...");
        // RxJava
        mAPIService.savePost(new RegisterPostData(title, body)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
