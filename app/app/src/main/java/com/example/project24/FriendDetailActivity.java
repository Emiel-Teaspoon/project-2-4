package com.example.project24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FriendDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        Intent intent = getIntent();
        String friend = intent.getStringExtra(FriendsActivity.EXTRA_FRIEND);

        // TODO Implement JSONArrayRequest for this friend
        // TODO Add the request to the queue

        TextView textView = findViewById(R.id.friendName);
        textView.setText(friend);
    }
}
