package com.jedu.re_kos.Register_Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.jedu.re_kos.Adapter.ViewPageAdapter;
import com.jedu.re_kos.R;

public class OnboardingActivity extends AppCompatActivity {

    ViewPager SlideViewPager;
    LinearLayout DotLayout;
    Button back,next,skip;

    TextView[] dots;
    ViewPageAdapter viewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(OnboardingActivity.this, R.color.biru_navbar));

        back = findViewById(R.id.buttonBack);
        next = findViewById(R.id.buttonNext);
        skip = findViewById(R.id.buttonSkip);

        // Sembunyikan tombol back saat pertama kali
        back.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getitem(0)>0) {
                    SlideViewPager.setCurrentItem(getitem(-1), true);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getitem(0) < viewPageAdapter.getCount() - 1) {
                    SlideViewPager.setCurrentItem(getitem(1), true);
                } else {
                    Intent i = new Intent(OnboardingActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnboardingActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        SlideViewPager = (ViewPager) findViewById(R.id.ViewPager);
        DotLayout = (LinearLayout) findViewById(R.id.dotslide);

        viewPageAdapter = new ViewPageAdapter(this);

        SlideViewPager.setAdapter(viewPageAdapter);

        setUpindicator(0);
        SlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpindicator(int position){
        dots = new TextView[3];
        DotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white, getApplicationContext().getTheme()));
            DotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.biru_navbar, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0){
                back.setVisibility(View.VISIBLE);
            }else {
                back.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){
        return SlideViewPager.getCurrentItem() + i;
    }

}