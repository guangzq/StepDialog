package com.zqg.dialogdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zqg.dialogviewpager.ZoomOutPageTransformer;
import com.zqg.dialogviewpager.StepDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepDialog.getInstance()
                        .setImages(new int[]{com.zqg.dialogviewpager.R.drawable.new_user_guide_1, com.zqg.dialogviewpager.R.drawable.new_user_guide_2, com.zqg.dialogviewpager.R.drawable.new_user_guide_3, com.zqg.dialogviewpager.R.drawable.new_user_guide_4})
                        .setCanceledOnTouchOutside(false)
                        .setPageTransformer(new ZoomOutPageTransformer())
                        .show(getFragmentManager());
            }
        });
    }
}
