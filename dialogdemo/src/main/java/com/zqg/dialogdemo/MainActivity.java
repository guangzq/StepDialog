package com.zqg.dialogdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                        .setImages(new int[]{R.drawable.new_user_guide_1, R.drawable.new_user_guide_2, R.drawable.new_user_guide_3, R.drawable.new_user_guide_4})
                        .setCanceledOnTouchOutside(true)
                        .setPageTransformer(new ZoomOutPageTransformer())
                        .setOnCancelListener(new StepDialog.OnCancelListener() {
                            @Override
                            public void onCancel(int position) {
                                Log.e("MainActivity", position + "");
                                //滑到最后一页会自动dissmiss,可以在这里做自己的操作
                            }
                        })
                        .show(getFragmentManager());
            }
        });
    }
}
