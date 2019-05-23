package edu.cnm.deepdive.fizzbuzz;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

  private int value;
  private TextView valueDisplay;
  private Timer timer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    valueDisplay = findViewById(R.id.value_display);
  }

  @Override
  protected void onResume() {
    super.onResume();
    timer = new Timer();
    timer.schedule(new RandomValueTask(), 0, 3000); //FIXME This should read preferences.
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.play:
        // TODO Start running game, hide play item, show pause item.
        break;
      case R.id.pause:
        // TODO Pause running game, hide pause item, show play item.
        break;
      case R.id.settings:
        startActivity(new Intent(this, SettingsActivity.class));
        break;
      default:
        handled = super.onOptionsItemSelected(item);
        break;
    }
    return handled;
  }

  private class RandomValueTask extends TimerTask {

    private Random rng = new Random();

    @Override
    public void run() {
        value = rng.nextInt(100);
        runOnUiThread(() -> valueDisplay.setText(Integer.toString(value)));
    }

  }

}
