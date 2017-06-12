package com.tefah.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.tefah.bakingapp.pojo.Step;

public class StepDisplayActivity extends AppCompatActivity {

    Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_display);

        Intent intent  = getIntent();
        step = (Step) intent.getSerializableExtra("step");
        Bundle args = new Bundle();
        args.putSerializable("step", step);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragmentContainer,stepDetailFragment).commit();

    }

}
