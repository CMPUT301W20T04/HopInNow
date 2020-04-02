package com.example.hopinnow;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.hopinnow.activities.DriverMapActivity;
import com.example.hopinnow.activities.LoginActivity;
import com.example.hopinnow.activities.RiderMapActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Author: Tianyu Bai
 * UI tests on rider side activities. Robotium test framework is used.
 */
@RunWith(AndroidJUnit4.class)
public class Test4_DriverActivityTest {
    private Solo solo;


    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);


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
     *      throws all exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    /**
     * Logs in user.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    private void loginUser() throws InterruptedException {
        // Log in To Activity
        String userEmail = "driver@testing.com";
        solo.enterText((EditText)solo.getView(R.id.loginEmailEditText), userEmail);
        String userPassword = "1111111";
        solo.enterText((EditText)solo.getView(R.id.loginPassword), userPassword);
        solo.clickOnView(solo.getView(R.id.loginButton));
        Thread.sleep(2000);
    }

    /**
     * Logs out user.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    private void logoutUser() throws InterruptedException {
        solo.clickOnView(solo.getView(R.id.driverMenuBtn));
        solo.clickOnMenuItem("Log Out");
        Thread.sleep(2000);
    }

    /**
     * Logs in rider and create new request.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    private void loginRiderAddRequest() throws InterruptedException {
        // Log in To Activity
        String userEmail = "rider2@rider.com";
        solo.enterText((EditText)solo.getView(R.id.loginEmailEditText), userEmail);
        String userPassword = "1111111";
        solo.enterText((EditText)solo.getView(R.id.loginPassword), userPassword);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.loginButton));

        solo.clickOnButton("Pick Up at My Location");
        String address = solo.getView(R.id.pick_up_auto_complete).toString();

        solo.enterText((EditText) solo.getView(R.id.mock_pickUp), address);
        solo.enterText((EditText) solo.getView(R.id.mock_dropOff), address);
        solo.clickOnButton("HOP IN NOW!");
        Thread.sleep(2000);

        solo.clickOnView(solo.getView(R.id.riderMenuBtn));
        solo.clickOnMenuItem("Log Out");
        Thread.sleep(2000);
    }


    /**
     * Mock case that goes through status of a request accepted by driver.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    @Test
    public void Case1() throws InterruptedException {
        loginRiderAddRequest();
        loginUser();
        solo.assertCurrentActivity("Wrong Activity", DriverMapActivity.class);

        solo.clickOnView(solo.getView(R.id.my_loc_startup_button));
        assertTrue(solo.waitForText("requests", 1, 2000));
        solo.clickInList(1);
        assertTrue(solo.waitForText("ACCEPT", 1, 2000));

        solo.clickOnView(solo.getView(R.id.mock_next_pickUpRider));
        assertTrue(solo.waitForText("Information", 1, 2000));

        solo.clickOnView(solo.getView(R.id.PickUpRiderButton));
        assertTrue(solo.waitForText("EMERGENCY", 1, 2000));

        solo.clickOnView(solo.getView(R.id.EmergencyCall));
        solo.goBack();

        solo.clickOnView(solo.getView(R.id.dropOffRiderButton));
        if (solo.waitForText("Allow", 1, 2000)){
            solo.clickOnButton("Allow");
        } else {
            assertTrue(solo.waitForText("QR", 1, 2000));
        }

        Thread.sleep(2000);
    }


    /**
     * Close activity after each test
     * @throws Exception
     *      throws all exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
