package com.rahimian.lineortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //my views
    private TextView pone,ptwo,VS,appname;
    private Button btnagain;
    private ImageView dark;
    private GridLayout gridLayout ;
    //enum
    enum currantpl {ONE,TWO, NULL}
    currantpl currantplayer = currantpl.ONE;
    currantpl[] status = new currantpl[9];
    int [][] winstatus = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById
        gridLayout = findViewById( R.id.gridLayout);
        appname=findViewById(R.id.appname);
        VS   = findViewById(R.id.VS);
        dark = findViewById(R.id.dark);
        pone = findViewById(R.id.potxt);
        ptwo = findViewById(R.id.pttxt);
        btnagain = findViewById(R.id.btnagain);
        btnagain.setY(2000);
        pone.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
        ptwo.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
        for (int i = 0 ; i<=8 ; i++){status[i]= currantpl.NULL;}
        //BTN AGAIN !
        btnagain.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                VS.setText("VS");
                appname.setText(R.string.app_name);
                for (int i = 0 ; i<=8 ; i++){status[i]= currantpl.NULL;}
                if(currantplayer==currantpl.ONE){ ptwo.animate().translationXBy(-150); }else if (currantplayer==currantpl.TWO){currantplayer = currantpl.ONE; pone.animate().translationXBy(150); }
                VS.animate().translationYBy(50);
                pone.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
                ptwo.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
                for (int i = 0 ; i<gridLayout.getChildCount() ;i++){
                    ImageView imageView = (ImageView) gridLayout.getChildAt(i);
                    imageView.setImageResource(0);imageView.setBackgroundResource(R.color.orange);imageView.setAlpha(.5f);imageView.setClickable(true);
                }
                dark.setVisibility(View.INVISIBLE);
                btnagain.animate().translationYBy(2000).setDuration(1000);
                VS.animate().alpha(1);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void  imgIsClick (View imageview) {
        ImageView tappedImageView = (ImageView) imageview;
        int tappedimageviewtag = Integer.parseInt(tappedImageView.getTag().toString());
        tappedImageView.setRotation(0);
        tappedImageView.setAlpha(.2f);
        tappedImageView.setScaleX(-1);
        tappedImageView.setScaleY(-1);
        //log tag
        status[tappedimageviewtag] = currantplayer;
        tappedImageView.animate().alpha(1).scaleY(1).scaleX(1).setDuration(100);
        if (currantplayer == currantpl.ONE) {
            tappedImageView.setImageResource(R.drawable.lion);
            currantplayer = currantpl.TWO;
            ptwo.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
            pone.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
        } else {
            tappedImageView.setImageResource(R.drawable.tiger);
            currantplayer = currantpl.ONE;
            pone.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
            ptwo.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
        }
        tappedImageView.animate().rotation(360).setDuration(500);
        tappedImageView.setClickable(false);
        //WHO IS WINNER
        boolean isAWinner = false;
        for (int[] winner : winstatus) {
            if (status[winner[0]] == status[winner[1]] && status[winner[1]] == status[winner[2]] && status[winner[0]] != currantpl.NULL) {
                Toast.makeText(MainActivity.this, "win!", Toast.LENGTH_LONG).show();
                isAWinner = true;
                //TO uncheckable
                tappedImageView.animate().rotation(3600).setDuration(500);
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    ImageView imageView = (ImageView) gridLayout.getChildAt(i);
                    imageView.setClickable(false);
                }
                for (int i = 0; i <= 2; i++) {
                    int index = winner[i];
                    ImageView imageView = (ImageView) gridLayout.getChildAt(index);
                    imageView.setBackgroundResource(R.color.Red);
                }
                btnagain.animate().translationYBy(-2000).setDuration(1000);
                dark.setVisibility(View.VISIBLE);
                dark.animate().alpha(1).setDuration(100);

                if (currantplayer == currantpl.ONE) {
                    pone.animate().alpha(0);
                    ptwo.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).translationXBy(150).setDuration(100);
                    VS.animate().translationYBy(-50);

                } else {
                    ptwo.animate().alpha(0);
                    pone.animate().alpha(1).scaleX(1.2f).scaleY(1).translationXBy(-150).setDuration(100);
                    VS.animate().translationYBy(-50);
                }
                VS.setText(" THE WINNER IS ");
                appname.setText("");
            }
        }
        //NO WINNER
        if (!isAWinner) {
            int ok = 0;
            for (int i = 0; i <= 8; i++) {
                if (status[i] != currantpl.NULL) {
                    ok++;
                }
            }
            if (ok == 9) {
                Toast.makeText(MainActivity.this, "NO WINNER! \n TRY AGAIN", Toast.LENGTH_LONG).show();
                btnagain.animate().translationYBy(-2000).setDuration(1000);
                dark.setVisibility(View.VISIBLE);
                dark.animate().alpha(1).setDuration(100);
                pone.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100);
                ptwo.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100);
                appname.setText("");
                VS.animate().translationYBy(-50);
                VS.setText("NO WINNER! \n TRY AGAIN");
                currantplayer = currantpl.NULL;
                //TO uncheckable
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    ImageView imageView = (ImageView) gridLayout.getChildAt(i);
                    imageView.setClickable(false);
                    imageView.animate().rotation(36000);
                }
            }
        }
    }
}


