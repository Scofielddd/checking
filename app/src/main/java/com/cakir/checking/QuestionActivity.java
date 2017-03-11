package com.cakir.checking;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import checkingapi.Client;
import checkingapi.ClientMethods;
import checkingapi.model.Checkin;
import checkingapi.model.Place;
import checkingapi.model.Question;
import checkingapi.model.User;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class QuestionActivity extends AppCompatActivity {

    private long CheckinId;
    private String Quest;
    private String A;
    private String B;
    private String C;
    private String D;
    private long Id;

    private TextView textQuestion;
    private Button btnAnswer0,btnAnswer1,btnAnswer2,btnAnswer3;
    SweetAlertDialog sweet;
    ClientMethods clientMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        sweet = new SweetAlertDialog(QuestionActivity.this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CheckinId = extras.getLong("CheckinId");
            Quest = extras.getString("Quest");
            A = extras.getString("A");
            B = extras.getString("B");
            C = extras.getString("C");
            D = extras.getString("D");
            Id = extras.getLong("PlaceId");
        }

        textQuestion = (TextView)findViewById(R.id.question_text);
        btnAnswer0 = (Button)findViewById(R.id.answer0_button);
        btnAnswer1 = (Button)findViewById(R.id.answer1_button);
        btnAnswer2 = (Button)findViewById(R.id.answer2_button);
        btnAnswer3 = (Button)findViewById(R.id.answer3_button);

        textQuestion.setText(Quest);
        btnAnswer0.setText(A);
        btnAnswer1.setText(B);
        btnAnswer2.setText(C);
        btnAnswer3.setText(D);

        btnAnswer0.setTag(0);
        btnAnswer1.setTag(1);
        btnAnswer2.setTag(2);
        btnAnswer3.setTag(3);

        View.OnClickListener onClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sweet.setTitleText("");
                sweet.setContentText("Lütfen bekleyin...").changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                sweet.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
                sweet.show();

                clientMethods=new ClientMethods() {
                    @Override public void Checkins(ArrayList<Checkin> checkins) {}
                    @Override public void Login(User user) {}
                    @Override public void Register(boolean result) {}
                    @Override public void Username(boolean result) {}
                    @Override public void Email(boolean result) {}
                    @Override public void Location(ArrayList<Place> places) {}
                    @Override public void Do(Question question) {}
                    @Override
                    public void Answer(boolean result) {
                        sweet.showContentText(false);
                        sweet.setConfirmText("TAMAM");

                        sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                QuestionActivity.this.finish();
                                Intent intent=new Intent(QuestionActivity.this, CampaignActivity.class);
                                intent.putExtra("id",Id);
                                startActivity(intent);
                            }
                        });
                        if(result)
                        {
                            sweet.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            sweet.setTitleText("Cevap doğru, 100 coin kazandınız.");
                        }
                        else
                        {
                            sweet.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            sweet.setTitleText("Cevap yanlış");
                        }
                    }
                };
                Client.Answer(CheckinId,(int)view.getTag(),clientMethods);
            }
        };

        btnAnswer0.setOnClickListener(onClick);
        btnAnswer1.setOnClickListener(onClick);
        btnAnswer2.setOnClickListener(onClick);
        btnAnswer3.setOnClickListener(onClick);

    }
}
