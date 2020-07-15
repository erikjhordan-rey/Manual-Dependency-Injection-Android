/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.erikjhordanrey.dependencyinjection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import io.github.erikjhordanrey.dependencyinjection_battery.R;
import io.github.erikjhordanrey.dependencyinjection_battery.databinding.ActivityBatteryBinding;

public class BatteryActivity extends AppCompatActivity implements BatteryPresenter.View {

  private Injection injection;
  private BatteryPresenter presenter;

  private ActivityBatteryBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityBatteryBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initializeToolbar();
    initializeInjector();
    initializePresenter();
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.initialize();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.destroy();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_battery, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_github) {
      presenter.openProjectOnGitHub();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void showBatteryPercent(float percent) {
    binding.labelPercent.setText(getString(R.string.percentage, percent));
    binding.labelPercent.setTextColor(getBatteryColor(percent));
  }

  @Override public void showProjectOnGitHub() {
    Uri uri = Uri.parse(getString(R.string.git_hub));
    startActivity(new Intent(Intent.ACTION_VIEW, uri));
  }

  private void initializeToolbar() {
    setSupportActionBar(binding.toolbar);
  }

  private void initializeInjector() {
    BatteryApplication application = (BatteryApplication) getApplication();
    injection = application.getInjection();
  }

  private void initializePresenter() {
    presenter = injection.provideBatteryPresenter();
    presenter.setView(this);
  }

  private int getBatteryColor(float percent) {
    return ContextCompat.getColor(this, percent > 15f ? R.color.battery_ok : R.color.battery_low);
  }
}
