package com.goliva.coinbird;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.goliva.coinbird.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity {

    private ActivityGameBinding binding;
    private MediaPlayer mediaPlayer;

    private boolean touchControl = false;
    private boolean beginControl = false;

    private Runnable runnable, runnable2;
    private Handler handler, handler2;

    // Positions
    private int birdX, enemy1X, enemy2X, enemy3X, coin1X, coin2X;
    private int birdY, enemy1Y, enemy2Y, enemy3Y, coin1Y, coin2Y;

    // Dimensions of screen
    private int screenWidth;
    private int screenHeight;

    // Remaining lives
    int hearts = 3;

    // Points
    int score = 0;

    double difficultyLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean status = getIntent().getBooleanExtra("status", false);
        if (!status) {
            mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.audio_for_game);
            mediaPlayer.start();
        }

        binding.tvStartInfo.setVisibility(View.INVISIBLE);

        binding.btnMedium.setOnClickListener(view -> {
            setupGameVisibilities();
            difficultyLevel = 1.0;
            playGame();
        });

        binding.btnHard.setOnClickListener(view -> {
            setupGameVisibilities();
            difficultyLevel = 0.8;
            playGame();
        });

        binding.btnNoMercy.setOnClickListener(view -> {
            setupGameVisibilities();
            difficultyLevel = 0.4;
            playGame();
        });
    }

    private void setupGameVisibilities() {
        binding.btnMedium.setVisibility(View.INVISIBLE);
        binding.btnHard.setVisibility(View.INVISIBLE);
        binding.btnNoMercy.setVisibility(View.INVISIBLE);
        binding.tvStartInfo.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void playGame() {
        binding.constraintLayout.setOnTouchListener((view, motionEvent) -> {
            binding.tvStartInfo.setVisibility(View.INVISIBLE);

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                touchControl = true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                touchControl = false;
            }

            if (!beginControl) {
                beginControl = true;

                screenWidth = (int) binding.constraintLayout.getWidth();
                screenHeight = (int) binding.constraintLayout.getHeight();

                birdX = (int) binding.ivBird.getX();
                birdY = (int) binding.ivBird.getY();

                handler = new Handler();
                runnable = () -> {
                    moveToBird();
                    enemyControl();
                    collisionControl();
                };
                handler.post(runnable);
            } else {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    touchControl = true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    touchControl = false;
                }
            }

            return true;
        });
    }

    private void moveToBird() {
        if (touchControl) {
            birdY = birdY - (screenWidth / 100);
        } else {
            birdY = birdY + (screenWidth / 100);
        }

        if (birdY <= 0) {
            birdY = 0;
        }

        if (birdY >= (screenHeight - binding.ivBird.getHeight())) {
            birdY = screenHeight - binding.ivBird.getHeight();
        }

        binding.ivBird.setY(birdY);
    }

    private void enemyControl() {
        binding.ivEnemy1.setVisibility(View.VISIBLE);
        binding.ivEnemy2.setVisibility(View.VISIBLE);
        binding.ivEnemy3.setVisibility(View.VISIBLE);
        binding.ivCoin1.setVisibility(View.VISIBLE);
        binding.ivCoin2.setVisibility(View.VISIBLE);

        double percentage = 1.0;

        if (score >= 50 && score < 100) {
            percentage = .8;
        }
        if (score >= 100 && score < 150) {
            percentage = .7;
        }
        if (score >= 150) {
            percentage = .6;
        }

        enemy1X = enemy1X - (screenWidth / (int) (150 * percentage * difficultyLevel));

        if (enemy1X < 0) {
            enemy1X = screenWidth + 200;
            enemy1Y = (int) Math.floor(Math.random() * screenHeight);

            if (enemy1Y <= 0) {
                enemy1Y = 0;
            }

            if (enemy1Y >= (screenHeight - binding.ivEnemy1.getHeight())) {
                enemy1Y = screenHeight - binding.ivEnemy1.getHeight();
            }
        }

        binding.ivEnemy1.setX(enemy1X);
        binding.ivEnemy1.setY(enemy1Y);

        enemy2X = enemy2X - (screenWidth / (int) (140 * percentage * difficultyLevel));

        if (enemy2X < 0) {
            enemy2X = screenWidth + 200;
            enemy2Y = (int) Math.floor(Math.random() * screenHeight);

            if (enemy2Y <= 0) {
                enemy2Y = 0;
            }

            if (enemy2Y >= (screenHeight - binding.ivEnemy2.getHeight())) {
                enemy2Y = screenHeight - binding.ivEnemy2.getHeight();
            }
        }

        binding.ivEnemy2.setX(enemy2X);
        binding.ivEnemy2.setY(enemy2Y);

        enemy3X = enemy3X - (screenWidth / (int) (130 * percentage * difficultyLevel));

        if (enemy3X < 0) {
            enemy3X = screenWidth + 200;
            enemy3Y = (int) Math.floor(Math.random() * screenHeight);

            if (enemy3Y <= 0) {
                enemy3Y = 0;
            }

            if (enemy3Y >= (screenHeight - binding.ivEnemy3.getHeight())) {
                enemy3Y = screenHeight - binding.ivEnemy3.getHeight();
            }
        }

        binding.ivEnemy3.setX(enemy3X);
        binding.ivEnemy3.setY(enemy3Y);

        coin1X = coin1X - (screenWidth / 120);

        if (coin1X < 0) {
            coin1X = screenWidth + 200;
            coin1Y = (int) Math.floor(Math.random() * screenHeight);

            if (coin1Y <= 0) {
                coin1Y = 0;
            }

            if (coin1Y >= (screenHeight - binding.ivCoin1.getHeight())) {
                coin1Y = screenHeight - binding.ivCoin1.getHeight();
            }
        }

        binding.ivCoin1.setX(coin1X);
        binding.ivCoin1.setY(coin1Y);

        coin2X = coin2X - (screenWidth / 110);

        if (coin2X < 0) {
            coin2X = screenWidth + 200;
            // Ensure coins are not too close together
            do {
                coin2Y = (int) Math.floor(Math.random() * screenHeight);
            } while (coin2Y <= coin1Y + 200 && coin2Y >= coin1Y - 200);


            if (coin2Y <= 0) {
                coin2Y = 0;
            }

            if (coin2Y >= (screenHeight - binding.ivCoin2.getHeight())) {
                coin2Y = screenHeight - binding.ivCoin2.getHeight();
            }
        }

        binding.ivCoin2.setX(coin2X);
        binding.ivCoin2.setY(coin2Y);
    }

    @SuppressLint("SetTextI18n")
    private void collisionControl() {
        int centerEnemy1X = enemy1X + binding.ivEnemy1.getWidth() / 2;
        int centerEnemy1Y = enemy1Y + binding.ivEnemy1.getHeight() / 2;

        if (centerEnemy1X >= birdX
            && centerEnemy1X <= (birdX + binding.ivBird.getWidth())
            && centerEnemy1Y >= birdY
            && centerEnemy1Y <= (birdY + binding.ivBird.getHeight())) {
            enemy1X = screenWidth + 200;
            hearts--;
        }

        int centerEnemy2X = enemy2X + binding.ivEnemy2.getWidth() / 2;
        int centerEnemy2Y = enemy2Y + binding.ivEnemy2.getHeight() / 2;

        if (centerEnemy2X >= birdX
                && centerEnemy2X <= (birdX + binding.ivBird.getWidth())
                && centerEnemy2Y >= birdY
                && centerEnemy2Y <= (birdY + binding.ivBird.getHeight())) {
            enemy2X = screenWidth + 200;
            hearts--;
        }

        int centerEnemy3X = enemy3X + binding.ivEnemy3.getWidth() / 2;
        int centerEnemy3Y = enemy3Y + binding.ivEnemy3.getHeight() / 2;

        if (centerEnemy3X >= birdX
                && centerEnemy3X <= (birdX + binding.ivBird.getWidth())
                && centerEnemy3Y >= birdY
                && centerEnemy3Y <= (birdY + binding.ivBird.getHeight())) {
            enemy3X = screenWidth + 200;
            hearts--;
        }

        int centerCoin1X = coin1X + binding.ivCoin1.getWidth() / 2;
        int centerCoin1Y = coin1Y + binding.ivCoin1.getHeight() / 2;

        if (centerCoin1X >= birdX
                && centerCoin1X <= (birdX + binding.ivBird.getWidth())
                && centerCoin1Y >= birdY
                && centerCoin1Y <= (birdY + binding.ivBird.getHeight())) {
            coin1X = screenWidth + 200;
            score += 10;
            binding.tvScore.setText("" + score);
        }

        int centerCoin2X = coin2X + binding.ivCoin2.getWidth() / 2;
        int centerCoin2Y = coin2Y + binding.ivCoin2.getHeight() / 2;

        if (centerCoin2X >= birdX
                && centerCoin2X <= (birdX + binding.ivBird.getWidth())
                && centerCoin2Y >= birdY
                && centerCoin2Y <= (birdY + binding.ivBird.getHeight())) {
            coin2X = screenWidth + 200;
            score += 10;
            binding.tvScore.setText("" + score);
        }

        if (hearts > 0 && score < 200) {
            if (hearts == 2) {
                binding.ivHeartRight.setImageResource(R.drawable.favorite_grey);
            }
            if (hearts == 1) {
                binding.ivHeartMid.setImageResource(R.drawable.favorite_grey);
            }
            handler.postDelayed(runnable, 20);
        } else if (score >= 200) {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
            }
            handler.removeCallbacks(runnable);
            binding.constraintLayout.setEnabled(false);
            binding.tvStartInfo.setVisibility(View.VISIBLE);
            binding.tvStartInfo.setText("Congratulations. You won the game!");
            binding.ivEnemy1.setVisibility(View.INVISIBLE);
            binding.ivEnemy2.setVisibility(View.INVISIBLE);
            binding.ivEnemy3.setVisibility(View.INVISIBLE);
            binding.ivCoin1.setVisibility(View.INVISIBLE);
            binding.ivCoin2.setVisibility(View.INVISIBLE);

            handler2 = new Handler();
            runnable2 = () -> {
                birdX = birdX + (screenWidth / 300);
                binding.ivBird.setX(birdX);
                binding.ivBird.setY(screenHeight / 2f);
                if (birdX <= screenWidth) {
                    handler2.postDelayed(runnable2, 20);
                } else {
                    handler2.removeCallbacks(runnable2);

                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                }
            };
            handler2.post(runnable2);
        } else if (hearts == 0) {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
            }
            handler.removeCallbacks(runnable);
            binding.ivHeartLeft.setImageResource(R.drawable.favorite_grey);
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }
}