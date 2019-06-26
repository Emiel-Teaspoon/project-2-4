package com.example.project24;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventActivity extends Fragment {
String naam;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_event, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView eventnaam = getView().findViewById(R.id.textView28);
        TextView eventadres = getView().findViewById(R.id.textView30);
        TextView eventbegin = getView().findViewById(R.id.textView32);
        TextView eventeinde = getView().findViewById(R.id.textView34);
        TextView eventmaker = getView().findViewById(R.id.textView36);
        TextView eventbeschrijvig = getView().findViewById(R.id.textView26);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            naam = bundle.getString("event_naam","Bart");
        }
        eventnaam.setText(naam);
        eventadres.setText("in Groningen");
        eventbegin.setText("Gister");
        eventeinde.setText("Vandaag");
        eventmaker.setText("niet jij");
        eventbeschrijvig.setText("ahsdf ahsdf hasdf hasdf asdhf asfh asjkdfh asjdf hasdf asdfasdf asdhf ahsdfjh asjdfja sdfha sdfj asdfj sadhf as" +
                "ahfsd jasdfkj ashdf ahksdfk sahdf k");




    }
}
