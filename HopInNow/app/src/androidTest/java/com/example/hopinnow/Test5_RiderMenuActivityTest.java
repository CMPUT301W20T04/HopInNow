package com.example.hopinnow;

import android.app.Activity;
import android.provider.ContactsContract;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.hopinnow.activities.LoginActivity;
import com.example.hopinnow.activities.ProfileActivity;
import com.example.hopinnow.activities.RiderMapActivity;

import com.example.hopinnow.activities.RiderMenuActivity;
import com.example.hopinnow.activities.TripListActivity;
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
 * UI tests on rider menu activities. Robotium test framework is used.
 */
@RunWith(AndroidJUnit4.class)
public class Test5_RiderMenuActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<RiderMapActivity> rule =
            new ActivityTestRule<>(RiderMapActivity.class, true, true);


    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(),
                rule.getActivity());
    }

    /**
     * Logs in user.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    private void loginUser() throws InterruptedException {
        // Log in To Activity
        String userEmail = "rider2@rider.com";
        solo.enterText((EditText)solo.getView(R.id.loginEmailEditText), userEmail);
        String userPassword = "1111111";
        solo.enterText((EditText)solo.getView(R.id.loginPassword), userPassword);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.loginButton));

        Thread.sleep(2000);
    }



    /**
     * Check menu button, my trip list view and updating user information in my profile
      * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    @Test
    public void Case1_checkMenu() throws InterruptedException {
        Thread.sleep(2000);

        solo.clickOnView(solo.getView(R.id.driverMenuBtn));
        solo.clickOnMenuItem("Log Out");
        Thread.sleep(2000);

        loginUser();

        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);
        solo.clickOnView(solo.getView(R.id.riderMenuBtn));
        assertTrue(solo.waitForText("My Profile",1,2000));

        //test my profile
        solo.clickOnMenuItem("My Profile");
        Thread.sleep(2000);
        assertTrue(solo.waitForText("Profile",1,2000));


        //test profile editing after pressing "EDIT PROFILE"
        solo.clickOnView(solo.getView(R.id.editProfileBtn));
        Thread.sleep(2000);

        EditText phoneET = (EditText) solo.getView(R.id.proPhoneET);
        solo.clearEditText(phoneET);
        solo.enterText(phoneET,"1111112");
        assertTrue(solo.waitForText("1111112",1,2000));

        EditText nameET = (EditText) solo.getView(R.id.proNameET);
        solo.clearEditText(nameET);
        solo.enterText(nameET,"v");
        assertTrue(solo.waitForText("v",1,2000));

        solo.clickOnView(solo.getView(R.id.proUpdateBtn));
        assertTrue(solo.waitForText("updated!",1,2000));

        // log out
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.riderMenuBtn));
        solo.clickOnMenuItem("Log Out");
    }


    /**
     * Close activity after each test
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
