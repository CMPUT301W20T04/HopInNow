package com.example.hopinnow;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.hopinnow.activities.DriverMapActivity;
import com.example.hopinnow.activities.ProfileActivity;
import com.example.hopinnow.activities.RegisterActivity;
import com.example.hopinnow.activities.RegisterVehicleInfoActivity;
import com.example.hopinnow.activities.RiderMapActivity;

import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Author: Tianyu Bai
 * UI tests on register activities. Robotium test framework is used.
 */
@RunWith(AndroidJUnit4.class)
public class Test1_RegisterActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<RegisterActivity> rule =
            new ActivityTestRule<>(RegisterActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),
                rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    /**
     * Tests general user registration and vehicle registration by creating driver account.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    @Test
    public void Case1_registerDriver() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);

        // test name
        solo.enterText((EditText)solo.getView(R.id.regNameEditText),"Lupin the Third");
        assertTrue(solo.waitForText("Lupin the Third", 1, 2000));

        // test email
        solo.enterText((EditText)solo.getView(R.id.regEmailEditText),"lupin@third");
        assertTrue(solo.waitForText("lupin@third", 1, 2000));

        // test phone number
        solo.enterText((EditText)solo.getView(R.id.regPhoneNum),"11111111");
        assertTrue(solo.waitForText("11111111", 1, 2000));

        // test first password entry
        solo.enterText((EditText)solo.getView(R.id.regPassword),"111111");
        assertTrue(solo.waitForText("111111", 1, 2000));

        // test second password entry
        solo.enterText((EditText)solo.getView(R.id.reRegPassword2),"111111");
        assertTrue(solo.waitForText("111111", 1, 2000));

        // test password length
        solo.clickOnButton("Register");
        assertTrue(solo.waitForText("Length", 1, 2000));

        // test password length when passwords differ
        solo.enterText((EditText)solo.getView(R.id.regPassword),"1");
        solo.clickOnButton("Register");
        assertTrue(solo.waitForText("Length", 1, 2000));

        // test inconsistent passwords
        solo.clearEditText((EditText)solo.getView(R.id.reRegPassword2));
        solo.enterText((EditText)solo.getView(R.id.reRegPassword2),"1111112");
        solo.clickOnButton("Register");
        assertTrue(solo.waitForText("Passwords do not match!",
                1, 2000));

        //test registering
        solo.clearEditText((EditText)solo.getView(R.id.reRegPassword2));
        solo.enterText((EditText)solo.getView(R.id.reRegPassword2),"1111111");

        // switch to driver mode account registering
        solo.clickOnView(solo.getView(R.id.driver_rider_switch));
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", RegisterVehicleInfoActivity.class);

        // test vehicle info entry
        solo.enterText((EditText)solo.getView(R.id.vehMakeEt),"Auburn");
        assertTrue(solo.waitForText("Auburn",1,2000));
        solo.enterText((EditText)solo.getView(R.id.vehModelEt),"Speedster");
        assertTrue(solo.waitForText("Speedster",1,2000));
        solo.enterText((EditText)solo.getView(R.id.vehColorEt),"Cream");
        assertTrue(solo.waitForText("Cream",1,2000));
        solo.enterText((EditText)solo.getView(R.id.vehPlateEt),"1111111");
        assertTrue(solo.waitForText("1111111",1,2000));

        // test creating driver account
        solo.clickOnButton("finish");
        //assertFalse(solo.waitForText("finish",1,2000));
    }


    /**
     * Tests creating rider account.
     * @throws InterruptedException
     *           throws exception if thread is interrupted
     */
    @Test
    public void Case2_registerRider() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);

        solo.enterText((EditText)solo.getView(R.id.regNameEditText),"Lupin the Third");
        solo.enterText((EditText)solo.getView(R.id.regEmailEditText),"lupin@third");
        solo.enterText((EditText)solo.getView(R.id.regPhoneNum),"11111111");
        solo.enterText((EditText)solo.getView(R.id.regPassword),"1111111");
        solo.enterText((EditText)solo.getView(R.id.reRegPassword2),"1111111");


        solo.clickOnButton("Register");
        Thread.sleep(2000);
        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);

        solo.clickOnView(solo.getView(R.id.riderMenuBtn));
        solo.clickOnButton("My Profile");
        Thread.sleep(2000);

        //solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        //solo.clickOnView(solo.getView(R.id.proLogoutBtn));
    }





    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
