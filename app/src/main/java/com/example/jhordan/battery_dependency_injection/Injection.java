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

/**
 * 07/07/16.
 */
public class Injection {

  private final Context context;
  private BatteryReader batteryReader = null;
  private BatteryPresenter batteryPresenter = null;

  public Injection(Context context) {
    this.context = context;
  }

  protected BatteryReader createBatteryReader() {
    return new BatteryReader(provideContext());
  }

  protected BatteryPresenter createBatteryPresenter() {
    return new BatteryPresenter(provideBatteryReader());
  }

  /***** Public provide methods *****/

  public Context provideContext() {
    return context;
  }

  public BatteryReader provideBatteryReader() {
    if (batteryReader == null) {
      batteryReader = createBatteryReader();
    }
    return batteryReader;
  }

  public BatteryPresenter provideBatteryPresenter() {
    if (batteryPresenter == null) {
      batteryPresenter = createBatteryPresenter();
    }
    return batteryPresenter;
  }
}
