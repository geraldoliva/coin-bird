package com.goliva.coinbird;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.goliva.coinbird.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    private ActivityResultBinding binding;
    private int score;
    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        score = getIntent().getIntExtra("score", 0);
        binding.tvMyScore.setText("Your score: " + score);

        sharedPreferences = this.getSharedPreferences("Score", Context.MODE_PRIVATE);
        int highestScore = sharedPreferences.getInt("highestScore", 0);

        if (score >= 200) {
            binding.tvResultInfo.setText("You won the Game!");
            binding.tvHighestScore.setText("Highest Score: " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        } else if (score >= highestScore) {
            binding.tvResultInfo.setText("Sorry, you lost the game.");
            binding.tvHighestScore.setText("Highest Score: " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        } else {
            binding.tvResultInfo.setText("Sorry, you lost the game.");
            binding.tvHighestScore.setText("Highest Score: " + highestScore);
        }

        binding.buttonAgain.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Coin Bird");
        builder.setMessage("Are you sure you want to quit the game?");
        builder.setCancelable(false);
        builder.setNegativeButton("Quit Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}