package edu.cnm.deepdive.fizzbuzz;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.lang.annotation.Inherited;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

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
    Log.d("Trace", "Leaving onCreate");
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    Log.d("Trace", "Entering OnRestoreInstanceState");
    super.onRestoreInstanceState(savedInstanceState);
    Log.d("Trace", "Leaving OnRestoreInstanceState");
  }

  @Override
  protected void onRestart() {
    Log.d("Trace", "Entering OnRestart");
    super.onRestart();
    Log.d("Trace", "Leaving OnRestart");
  }

  @Override
  protected void onStart() {
    Log.d("Trace", "Entering onStart");
    super.onStart();
    Log.d("Trace", "Leaving onStart");
  }

  @Override
  protected void onResume() {
    Log.d("Trace", "Entering onResume");
    super.onResume();
    Log.d("Trace", "Leaving onResume");
  }

  @Override
  protected void onPause() {
    Log.d("Trace", "Entering onPause");
    super.onPause();
    Log.d("Trace", "Leaving onPause");
  }

  @Override
  protected void onStop() {
    Log.d("Trace", "Entering onStop");
    super.onStop();
    Log.d("Trace", "Leaving onStop");
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    Log.d("Trace", "Entering OnSaveInstState");
    super.onSaveInstanceState(outState);
    Log.d("Trace", "Leaving OnSaveInstState");
  }

  @Override
  protected void onDestroy() {
    Log.d("Trace", "Entering OnDestroy");
    super.onDestroy();
    Log.d("Trace", "Leaving OnDestroy");
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

    if (timer == null) {
      timer = new Timer();
      timer.schedule(new RandomValueTask(), 0, 3000); //FIXME This should read preferences.
    }

    invalidateOptionsMenu();
    // TODO Update any necessary fields, timer, & menu.
  }

  private class RandomValueTask extends TimerTask {

    private Random rng = new Random();

    @Override
    public void run() {
      Log.d("Trace", "Entering run");
      value = rng.nextInt(100); // TODO should reference preferences.
        runOnUiThread(() -> valueDisplay.setText(Integer.toString(value)));
      Log.d("Trace", "Leaving run");
    }

  }

}
