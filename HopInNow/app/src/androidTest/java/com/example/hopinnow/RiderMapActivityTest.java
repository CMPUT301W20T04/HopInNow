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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
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
        solo = new Solo(getInstrumentation(),
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
        solo.enterText((EditText) solo.getView(R.id.pick_up_auto_complete), "Hub Edmonton");
        solo.enterText((EditText) solo.getView(R.id.drop_off_auto_complete), "Cab Edmonton");

        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);
        solo.clickOnButton("HOP IN NOW!");
        assertTrue(solo.waitForText("Time Elapsed", 1, 2000));
        Double fare = Double.valueOf(String.valueOf(solo.getView(R.id.fare_amount)));

        solo.clickOnButton("+1$");
        String newFare = Double.toString(fare+1);
        assertTrue(solo.waitForText(newFare, 1, 2000));

        solo.clickOnButton("-1$");
        assertTrue(solo.waitForText(Double.toString(fare), 1, 2000));

        solo.clickOnButton("Cancel The Request");
        assertTrue(solo.waitForText("HOP IN NOW!", 1, 2000));

        solo.enterText((EditText) solo.getView(R.id.pick_up_auto_complete), "Hub Edmonton");
        solo.enterText((EditText) solo.getView(R.id.drop_off_auto_complete), "Cab Edmonton");
        solo.clickOnButton("HOP IN NOW!");


    }

    /**
     *
     */
    @Test
    public void driverOfferFragment(){
        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Would you like to accept this offer?", 1, 2000));

        //solo.clickOnEditText(R.id.rider_driver_offer_name);
        //assertFalse(solo.waitForText("Accept", 1, 2000));
        //click elsewhere to close alert dialog

        solo.clickOnButton("DECLINE");
        assertTrue(solo.waitForText("Time Elapsed", 1, 2000));

        solo.clickOnButton(">");
    }

    /**
     *
     */
    @Test
    public void waitingPickUpFragment(){
        solo.clickOnButton("ACCEPT");
        assertTrue(solo.waitForText("Driver's on the way! Just a moment ...", 1, 2000));

        //TODO solo.clickOnEditText(R.id.rider_driver_offer_name);
        //TODO assertFalse(solo.waitForText("Accept", 1, 2000));
        //TODO click elsewhere to close alert dialog

        solo.clickOnButton("CANCEL REQUEST");
        assertTrue(solo.waitForText("HOP IN NOW!", 1, 2000));

        // TODO test phone


        // TODO test email

        waitingDriverOfferFragment();
        driverOfferFragment();
    }

    /**
     *
     */
    @Test
    public void pickedUpFragment(){
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Now, we are heading to ...", 1, 2000));

        // TODO emergency call
    }

    /**
     *
     */
    @Test
    public void confirmDropOffFragment(){
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Is this ride complete?", 1, 2000));

        solo.clickOnButton("NO");
        assertTrue(solo.waitForText("Now, we are heading to ...", 1, 2000));

        solo.clickOnButton("YES");
        assertTrue(solo.waitForText("Payment", 1, 2000));

        // TODO emergency call
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