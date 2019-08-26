package com.isaacmurtagh.connect3alpha;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // red = 0, yellow = 1, none = 2
    public int playerTurn = 0;
    public int[] positions = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    public boolean inGame = true;
    int redScore = 0, yellowScore = 0;

    public int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};


    public void dropIn(View view) {

        ImageView token = (ImageView) view;
        int selectedPos = Integer.parseInt(token.getTag().toString());

        if (inGame && positions[selectedPos] == 2) {
            positions[selectedPos] = playerTurn;

            if (playerTurn == 0) {
                token.setImageResource(R.drawable.red);
            } else {
                token.setImageResource(R.drawable.yellow);
            }

            token.setAlpha(1f);
            token.setTranslationY(-1000f);
            token.animate().translationYBy(1000f).setDuration(300);

            if (checkWin()) {
                inGame = false;
                displayWin(true);
            } else if(checkDraw()){
                displayWin(false);
            }

            if (playerTurn == 0) { playerTurn = 1; } else { playerTurn = 0; }
            updateTurnToken();
        }

    }

    public boolean checkWin() {

        for (int[] winningPos : winningPositions) {
            if (positions[winningPos[0]] == positions[winningPos[1]] &&
                    positions[winningPos[1]] == positions[winningPos[2]] &&
                    positions[winningPos[0]] != 2) {
                return true;
            }
        }
        return false;
    }


    public boolean checkDraw() {
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == 2) {
                return false;
            }
        }
        return true;
    }


    public void displayWin(boolean winnable) {

        TextView winMessage = findViewById(R.id.winnerTextView);
        LinearLayout winDisplay = findViewById(R.id.winDisplayLayout);

        winDisplay.bringToFront();
        if (winnable) {
            if (playerTurn == 0) {
                winMessage.setTextColor(Color.RED);
                winMessage.setText("RED WON");
                redScore++;
            } else {
                winMessage.setTextColor(Color.YELLOW);
                winMessage.setText("YELLOW WON");
                yellowScore++;
            }
        } else {
            winMessage.setTextColor(Color.WHITE);
            winMessage.setText("DRAW");
        }

        updateScore();

        winDisplay.setVisibility(View.VISIBLE);


    }

    public void updateTurnToken() {
        ImageView turnToken = findViewById(R.id.turnTokenImageView);

        if (playerTurn == 0) {
            turnToken.setImageResource(R.drawable.red);
        } else {
            turnToken.setImageResource(R.drawable.yellow);
        }
    }

    public void updateScore() {
        TextView redScoreTextView = findViewById(R.id.redScoreTextView);
        TextView yellowScoreTextView = findViewById(R.id.yellowScoreTextView);

        redScoreTextView.setText(String.valueOf(redScore));
        yellowScoreTextView.setText(String.valueOf(yellowScore));

    }

    public void playAgain(View view) {
        System.out.println("Done");

        LinearLayout winDisplay = (LinearLayout) findViewById(R.id.winDisplayLayout);
        winDisplay.setVisibility(View.INVISIBLE);

        android.support.v7.widget.GridLayout grid = findViewById(R.id.gameGrid);


        for (int i = 0; i < grid.getChildCount(); i++) {
            ImageView token = (ImageView) grid.getChildAt(i);
            token.setAlpha(0f);
            positions[i] = 2;
        }

        inGame = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
