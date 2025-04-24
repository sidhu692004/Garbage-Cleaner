package com.example.kachraseth;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeactivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private static final String TAG = "HomeActivity";

    private GestureDetector gestureDetector;
    private FrameLayout fragmentContainer;
    private int currentFragmentIndex = 0;
    private boolean isSwipeLeft = true;

    private final Fragment[] fragments = new Fragment[]{
            new HomeFragment(),
            new SellScrapFragment(),
            new RateListFragment(),
            new ProfileFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        VideoView videoView = findViewById(R.id.videoBackground);

        // वीडियो का पथ (res/raw/background_video.mp4)
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.background_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true); // वीडियो को लूप में चलाएं
                mediaPlayer.setVolume(0f, 0f); // वीडियो को म्यूट करें
                videoView.start(); // वीडियो प्ले करें
            }
        });
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        fragmentManager = getSupportFragmentManager();

        // Load Default Fragment
        loadFragment(fragments[currentFragmentIndex], false);

        // Initialize Gesture Detector
        gestureDetector = new GestureDetector(this, new GestureListener());
        fragmentContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showBottomNavigation();
                }
                return false;
            }
        });

        // Bottom Navigation Click Listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                int previousIndex = currentFragmentIndex;

                if (id == R.id.nav_home) {
                    currentFragmentIndex = 0;
                } else if (id == R.id.nav_sell) {
                    currentFragmentIndex = 1;
                } else if (id == R.id.nav_rate) {
                    currentFragmentIndex = 2;
                } else if (id == R.id.nav_profile) {
                    currentFragmentIndex = 3;
                }

                // Check swipe direction based on index change
                isSwipeLeft = currentFragmentIndex > previousIndex;

                loadFragment(fragments[currentFragmentIndex], true);
                return true;
            }
        });
    }

    // Override dispatchTouchEvent to detect gestures globally
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    // Load Fragment with Animation
    private boolean loadFragment(Fragment fragment, boolean withAnimation) {
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            if (withAnimation) {
                if (isSwipeLeft) {
                    //                    transaction.setCustomAnimations(android.R.anim.slide_in_right, android.R.anim.slide_out_left);

                } else {
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                }
            }

            transaction.replace(R.id.fragmentContainer, fragment).commit();

            // Hide bottom navigation after switching fragment
            hideBottomNavigation();

            return true;
        }
        return false;
    }

    // Gesture Listener for Swipe Detection
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    // Swipe Right (Previous Fragment)
                    if (currentFragmentIndex > 0) {
                        isSwipeLeft = false;
                        currentFragmentIndex--;
                        loadFragment(fragments[currentFragmentIndex], true);
                        updateBottomNavSelection(currentFragmentIndex);
                    }
                } else {
                    // Swipe Left (Next Fragment)
                    if (currentFragmentIndex < fragments.length - 1) {
                        isSwipeLeft = true;
                        currentFragmentIndex++;
                        loadFragment(fragments[currentFragmentIndex], true);
                        updateBottomNavSelection(currentFragmentIndex);
                    }
                }
                return true;
            }
            return false;
        }
    }

    // Hide Bottom Navigation with Fade Animation
    private void hideBottomNavigation() {
        if (bottomNavigationView.getVisibility() == View.VISIBLE) {
            Animation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setDuration(500);
            bottomNavigationView.startAnimation(fadeOut);
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    // Show Bottom Navigation with Fade Animation
    private void showBottomNavigation() {
        if (bottomNavigationView.getVisibility() == View.GONE) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setDuration(500);
            bottomNavigationView.startAnimation(fadeIn);
        }
    }



    // Update Bottom Navigation Selection
    private void updateBottomNavSelection(int index) {
        int itemId;
        switch (index) {
            case 0:
                itemId = R.id.nav_home;
                break;
            case 1:
                itemId = R.id.nav_sell;
                break;
            case 2:
                itemId = R.id.nav_rate;
                break;
            case 3:
                itemId = R.id.nav_profile;
                break;
            default:
                itemId = R.id.nav_home;
                break;
        }
        bottomNavigationView.setSelectedItemId(itemId);
    }
}
