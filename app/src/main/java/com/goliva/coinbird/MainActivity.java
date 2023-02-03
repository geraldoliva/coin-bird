package com.goliva.coinbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.goliva.coinbird.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MediaPlayer mediaPlayer;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_animation);
        binding.bird.setAnimation(animation);
        binding.enemy1.setAnimation(animation);
        binding.enemy2.setAnimation(animation);
        binding.enemy3.setAnimation(animation);
        binding.coin.setAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.audio_for_game);
        mediaPlayer.start();

        binding.volume.setOnClickListener(view -> {
            if (!status) {
                mediaPlayer.setVolume(0, 0);
                binding.volume.setImageResource(R.drawable.volume_off);
                status = true;
            } else {
                mediaPlayer.setVolume(1, 1);
                binding.volume.setImageResource(R.drawable.volume_up);
                status = false;
            }
        });

        binding.buttonStart.setOnClickListener(view -> {
            mediaPlayer.reset();
            binding.volume.setImageResource(R.drawable.volume_up);

            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("status", status);
            startActivity(intent);
        });
    }
}