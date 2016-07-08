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

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * 07/07/16.
 */
public class BatteryReader {

  private final static int MIN_BATTERY_LEVEL = 0;
  private final static int MAX_BATTERY_LEVEL = 100;

  private final Context context;

  public BatteryReader(Context context) {
    this.context = context;
  }

  public float getBatteryPercent() {
    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    Intent batteryStatus = context.registerReceiver(null, intentFilter);
    if (isBatteryStatusNull(batteryStatus)) {
      return MIN_BATTERY_LEVEL;
    }
    int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, MIN_BATTERY_LEVEL);
    int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, MAX_BATTERY_LEVEL);

    return level * 100f / scale;
  }

  private boolean isBatteryStatusNull(Intent batteryStatus) {
    return batteryStatus == null;
  }
}
