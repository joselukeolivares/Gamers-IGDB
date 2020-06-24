package com.example.myapplicationstyle.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplicationstyle.R;

public class Screenshots extends AppCompatActivity {

    FragmentManager fragmentManager=getSupportFragmentManager();
    Screenshot_fragment screenshot_fragment=new Screenshot_fragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenshots);

        if(savedInstanceState!=null){

        }else{
            
        }

    }
}
