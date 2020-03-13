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
public class RiderMenuActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<RiderMenuActivity> rule =
            new ActivityTestRule<>(RiderMenuActivity.class, true, true);


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
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }


    /**
     * Check menu button, my trip list view and updating user information in my profile
      * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    @Test
    public void Case1_checkMenu() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", RiderMenuActivity.class);

        solo.clickOnView(solo.getView(R.id.riderMyTrips));
        solo.assertCurrentActivity("Wrong Activity", TripListActivity.class);

        //test first item in list view, check each item in later updates
        //solo.clickInList(1);
        //solo.assertCurrentActivity("Wrong Activity", RiderMenuActivity.class);
        solo.goBack();

        //test my profile
        solo.clickOnButton("My Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);

        //test profile editing after pressing "EDIT PROFILE"
        solo.clickOnView(solo.getView(R.id.editProfileBtn));
        Thread.sleep(2000);

        solo.clearEditText(solo.getEditText(R.id.proPhoneET));
        solo.enterText(solo.getEditText(R.id.proPhoneET),"1111111");
        assertTrue(solo.waitForText("1111111",1,2000));

        solo.clearEditText(solo.getEditText(R.id.proNameET));
        solo.enterText(solo.getEditText(R.id.proNameET),"folanqi");
        assertTrue(solo.waitForText("folanqi",1,2000));

        solo.clickOnView(solo.getView(R.id.proUpdateBtn));
        assertTrue(solo.waitForText("updated!",1,2000));

        // log out
        solo.clickOnView(solo.getView(R.id.proLogoutBtn));
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
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
