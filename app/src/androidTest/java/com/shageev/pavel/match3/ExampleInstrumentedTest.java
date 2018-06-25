package com.shageev.pavel.match3;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.shageev.pavel.match3", appContext.getPackageName());
    }

    @Test
    public void resourseManager() {
        Context ctx = InstrumentationRegistry.getTargetContext();

        ResourceManager rm = ResourceManager.getInstance();
        rm.loadImages(ctx.getResources());
        rm.scaleImages(50);
        assertEquals(true, rm.tileImages.size() > 0);
    }

}
