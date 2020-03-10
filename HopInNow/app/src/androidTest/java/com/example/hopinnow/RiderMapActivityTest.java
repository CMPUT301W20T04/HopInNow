package com.example.hopinnow;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.content.ComponentName;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hopinnow.activities.RiderMapActivity;

import com.example.hopinnow.activities.RiderMenuActivity;
import com.example.hopinnow.activities.RiderWaitingDriverFragment;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used
 */
@RunWith(AndroidJUnit4.class)
public class RiderMapActivityTest{
    private Solo solo;

    @Rule
    public ActivityTestRule<RiderMapActivity> riderMapActivityRule =
            new ActivityTestRule<>(RiderMapActivity.class, true, true);



    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                riderMapActivityRule.getActivity());
    }


    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = riderMapActivityRule.getActivity();
    }


    /**
     * Checks auto complete fragments
     */
    @Test
    public void checkAutoCompleteFragment() {
        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);

        //TODO drop down list on click
        solo.enterText((EditText) solo.getView(R.id.pick_up_auto_complete), "Hub Edmonton");
        solo.enterText((EditText) solo.getView(R.id.drop_off_auto_complete), "Cab Edmonton");
        assertTrue(solo.waitForText("Hub Edmonton", 1, 2000));
        assertTrue(solo.waitForText("Cab Edmonton", 1, 2000));

        solo.clearEditText((EditText) solo.getView(R.id.pick_up_auto_complete));
        solo.clearEditText((EditText) solo.getView(R.id.drop_off_auto_complete));
        assertFalse(solo.searchText("Edmonton"));
        assertFalse(solo.searchText("Edmonton"));

        //solo.clickOnView(solo.getView(R.id.riderMenuBtn));
        //solo.clickOnView(solo.getView(R.id.));
    }


    /**
     *
     */
    @Test
    public void waitingDriverOfferFragment(){
        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);
        solo.clickOnButton("HOP IN NOW!");
        assertTrue(solo.waitForText("Time Elapsed", 1, 2000));
        String fare = String.valueOf((EditText) solo.getView(R.id.fare_amount));

        solo.clickOnButton("+1$");
        solo.waitForText("Edmonton", 1, 2000);

        // Get MainActivity to access its variables and methods.
        MainActivity activity = (MainActivity) solo.getCurrentActivity();
        final ListView cityList = activity.cityList; // Get the listview
        String city = (String) cityList.getItemAtPosition(0); // Get item from first position
        assertEquals("Edmonton", city);
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