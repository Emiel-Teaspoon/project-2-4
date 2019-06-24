package com.example.project24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView eventnaam = findViewById(R.id.textView28);
        TextView eventadres = findViewById(R.id.textView30);
        TextView eventbegin = findViewById(R.id.textView32);
        TextView eventeinde = findViewById(R.id.textView34);
        TextView eventmaker = findViewById(R.id.textView36);
        TextView eventbeschrijvig = findViewById(R.id.textView26);


        Intent intent = getIntent();
        String naam = intent.getStringExtra(FriendsEventsActivity.EXTRA_EVENTNAAM);
        eventnaam.setText(naam);
        eventadres.setText("in Groningen");
        eventbegin.setText("Gister");
        eventeinde.setText("Vandaag");
        eventmaker.setText("niet jij");
        eventbeschrijvig.setText("ahsdf ahsdf hasdf hasdf asdhf asfh asjkdfh asjdf hasdf asdfasdf asdhf ahsdfjh asjdfja sdfha sdfj asdfj sadhf as" +
                "ahfsd jasdfkj ashdf ahksdfk sahdf k");




    }
}
