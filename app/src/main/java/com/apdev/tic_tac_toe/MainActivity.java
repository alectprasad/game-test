package com.apdev.tic_tac_toe;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0 = X 1 = O
    int activePlayer = 0;

    //2 = Unplayed

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void restart(View view) {
        //restart game
        recreate();
    }

    public void dropIn(View view) {

        TextView playerInfo = (TextView) findViewById(R.id.playerInfo);
        ImageView imageView = (ImageView) view;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        //find tapped cell
        int tapped = Integer.parseInt(imageView.getTag().toString());
        if (gameState[tapped] == 2) {
            gameState[tapped] = activePlayer;
            imageView.setAlpha(0f);
            if (activePlayer == 0) {
                imageView.setImageResource(R.drawable.x);
                activePlayer = 1;
                playerInfo.setText(R.string.player_o);
            } else {
                imageView.setImageResource(R.drawable.o);
                activePlayer = 0;
                playerInfo.setText(R.string.player_x);
            }
            imageView.animate().alpha(1f).setDuration(300);
            //check for win state
            boolean gameOver = false;
            for (int[] winningPosition : winningPos) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {
                    // win state
                    if (activePlayer == 0) {
                        playerInfo.setText(R.string.o_win);
                    } else {
                        playerInfo.setText(R.string.x_win);
                    }
                    for (int i = 0; i < gridLayout.getChildCount(); i++) {
                        if (gameState[i] == 2) {
                            gameState[i] = activePlayer;
                            gameOver = true;
                        }
                    }
                } else  {
                    //check for draw condition
                    boolean isDraw=true;
                    for(int i = 0; i < gridLayout.getChildCount(); i++) {
                        if (gameState[i] == 2)
                            isDraw = false;
                    }
                    if(isDraw && !gameOver)
                        playerInfo.setText(R.string.draw);

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lock orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
