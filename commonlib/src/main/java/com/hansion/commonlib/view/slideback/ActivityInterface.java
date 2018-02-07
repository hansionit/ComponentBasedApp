package com.hansion.commonlib.view.slideback;

import android.app.Application;

/**
 *Created by zouxianbin on 2017/6/19.
 */
interface ActivityInterface {
    /**
     * Set the callback for activity lifecycle
     *
     * @param callbacks callbacks
     */
    void setActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callbacks);
}
