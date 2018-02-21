package cn.langwazi.rvhelper.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import cn.langwazi.rvhelper.adapter.OnItemClickListener;
import cn.langwazi.rvhelper.adapter.OnRequestLoadListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
