package me.rebi.contactsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.rebi.contactsdemo.adapter.RecyAdapter;
import me.rebi.contactsdemo.bean.Contact;
import me.rebi.contactsdemo.widget.pulltorefresh.PullToRefreshLayout;

public class PushActivity extends AppCompatActivity {
    private static final String TAG = "PushActivity111";
    private PullToRefreshLayout pull;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "111onCreate: ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        pull= (PullToRefreshLayout) this.findViewById(R.id.pull);
        rv= (RecyclerView) this.findViewById(R.id.rv);
        List<Contact> contacts=new ArrayList<>();
        for (int i=0;i<30;i++){
            Contact contact =new Contact("0","1","我是"+i,"oo");
            contacts.add(contact);
        }
        RecyAdapter adapter=new RecyAdapter(contacts);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pull.refreshEnd();
            }
        });

    }
}
