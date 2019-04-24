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

package com.example.jhordan.battery_dependency_injection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BatteryActivity extends AppCompatActivity implements BatteryPresenter.View {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.label_percent) TextView labelPercent;

  private Injection injection;
  private BatteryPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_battery);
    ButterKnife.bind(this);
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
    labelPercent.setText(getString(R.string.percentage, percent));
    int color = getBatteryColor(percent);
    labelPercent.setTextColor(color);
  }

  @Override public void showProjectOnGitHub() {
    Uri uri = Uri.parse(getString(R.string.git_hub));
    startActivity(new Intent(Intent.ACTION_VIEW, uri));
  }

  private void initializeToolbar() {
    setSupportActionBar(toolbar);
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
