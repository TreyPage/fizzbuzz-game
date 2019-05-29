package edu.cnm.deepdive.fizzbuzz;

import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GestureDetectorCompat;
import androidx.preference.PreferenceManager;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  private ViewGroup valueContainer;
  private Random rng = new Random();
  private int value;
  private TextView valueDisplay;
  private Timer timer;
  private boolean running;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d("Trace", "Entering onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    valueDisplay = findViewById(R.id.value_display);
    valueContainer = findViewById(R.id.value_container);
    GestureDetectorCompat detector = new GestureDetectorCompat(this, new FlingListener());
    valueContainer.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (!detector.onTouchEvent(event) && event.getActionMasked() == MotionEvent.ACTION_UP) {
          valueContainer.setTranslationY(0);
          valueContainer.setTranslationX(0);
        }
        return true;
      }
    });
    Log.d("Trace", "Leaving onCreate");
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    Log.d("Trace", "Entering onCreateOptionsMenu");
    getMenuInflater().inflate(R.menu.options, menu);
    Log.d("Trace", "Leaving onCreateOptionsMenu");
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuItem play = menu.findItem(R.id.play);
    MenuItem pause = menu.findItem(R.id.pause);
    play.setEnabled(!running);
    play.setVisible(!running);
    pause.setEnabled(running);
    pause.setVisible(running);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d("Trace", "Entering onOptionsItemSelected");
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.play:
        resumeGame();
        break;
      case R.id.pause:
        pauseGame();
        break;
      case R.id.settings:
        startActivity(new Intent(this, SettingsActivity.class));
        break;
      default:
        handled = super.onOptionsItemSelected(item);
        break;
    }
    Log.d("Trace", "Leaving onOptionsItemSelected");
    return handled;
  }

  private void pauseGame() {
    running = false;

    if (timer != null) {
      timer.cancel();
      timer = null;
    }

    invalidateOptionsMenu();
    // TODO Update any necessary fields, timer, & menu.
  }

  private void resumeGame() {
    running = true;
    if (timer != null) {
      timer.cancel();
    }
    int timeLimit = PreferenceManager.getDefaultSharedPreferences(this).
        getInt(getString(R.string.time_limit_key),
            getResources().getInteger(R.integer.time_limit_default));
    updateValue();
    if (timeLimit != 0) {
      timer = new Timer();
      timer.schedule(new TimeoutTask(), timeLimit * 1000);
    }
    invalidateOptionsMenu();
    // TODO Update any necessary fields, timer, & menu.
  }

  private void updateValue() {
    int numDigits = PreferenceManager.getDefaultSharedPreferences(this).getInt
        (getString(R.string.num_digits_key),
            getResources().getInteger(R.integer.num_digits_default));
    int limit = (int) Math.pow(10, numDigits) - 1;
    value = 1 + rng.nextInt(limit);
    valueContainer.setTranslationX(0);
    valueContainer.setTranslationY(0);
    valueDisplay.setText(Integer.toString(value));
  }

  private class TimeoutTask extends TimerTask {

    @Override
    public void run() {
      Log.d("Trace", "Entering run");
      runOnUiThread(MainActivity.this::resumeGame);
      Log.d("Trace", "Leaving run");
    }
  }

  public class FlingListener extends GestureDetector.SimpleOnGestureListener {

    private float originX;
    private float originY;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      valueContainer.setTranslationX(e2.getX() - originX);
      valueContainer.setTranslationY(e2.getY() - originY);
      return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      // TODO Detect if it really is a fling, and in which direction; Update tally
      if (velocityX > 10 || velocityY > 10) {
        resumeGame(); 
      } else
        pauseGame();
      return true;
    }

    @Override
    public boolean onDown(MotionEvent evnt) {
      originX = evnt.getX();
      originY = evnt.getY();
      return true;
    }

  }
}

