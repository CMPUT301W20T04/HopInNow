package com.example.hopinnow;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.content.ComponentName;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hopinnow.activities.LoginActivity;
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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


/**
 * Author: Tianyu Bai
 * UI tests on rider side activities. Robotium test framework is used.
 */
@RunWith(AndroidJUnit4.class)
public class RiderActivityTest{
    private Solo solo;
    private String userEmail = "folanqi123@ualberta.ca";
    private String userPassword = "12345678";

    //TODO fragment
    //TODO toast message check
    //TODO hanging up phone / driver calling
    //TODO email cannot go back
    //TODO QR image popping

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
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    /**
     * Logs in user.
     * @throws InterruptedException
     */
    private void loginUser() throws InterruptedException {
        // Log in To Activity
        //TODO MDZZ FRAGMENT DOES NOT ACCEPT ENTERTEXT
        solo.enterText((EditText)solo.getView(R.id.loginEmailEditText), userEmail);
        solo.enterText((EditText)solo.getView(R.id.loginPassword), userPassword);
        solo.goBack();
        solo.clickOnButton("LOGIN");
        Thread.sleep(2000);
    }


    /**
     * Add new request.
     * @throws InterruptedException
     */
    private void addNewRequest() throws InterruptedException {
        solo.enterText((EditText) solo.getView(R.id.mock_pickUp), "Hub Edmonton");
        solo.enterText((EditText) solo.getView(R.id.mock_dropOff), "Lister Edmonton");
        Thread.sleep(2000);
        solo.clickOnButton("HOP IN NOW!");
        //Thread.sleep(2000);
    }


    /**
     * Checks all tips option.
     * @throws InterruptedException
     */
    private  void checkPayment() throws InterruptedException {
        solo.clickOnButton("10%");
        solo.clickOnView(solo.getView(R.id.rider_payment_calculate));
        assertTrue(solo.waitForText("2.71", 1, 2000));

        solo.clickOnButton("15%");
        solo.clickOnView(solo.getView(R.id.rider_payment_calculate));
        assertTrue(solo.waitForText("2.83", 1, 2000));

        solo.clickOnButton("20%");
        solo.clickOnView(solo.getView(R.id.rider_payment_calculate));
        assertTrue(solo.waitForText("2.95", 1, 2000));

        Button other = (Button) solo.getView(R.id.rider_payment_else);
        solo.clickOnView(solo.getView(R.id.rider_payment_else));
        solo.clickOnView(solo.getView(R.id.rider_payment_other_editText));
        solo.enterText((EditText)solo.getView(R.id.rider_payment_other_editText),"10");
        solo.clickOnView(solo.getView(R.id.rider_payment_calculate));
        assertTrue(solo.waitForText("2.71", 1, 3000));

        solo.clickOnButton("CONFIRM");
        assertTrue(solo.waitForText("Rating", 1, 2000));

        //check qr image popping
    }


    /**
     * Checks menu button.
     * Auto complete fragment testing is currently in question and mocks are used.
     */
    @Test
    public void Case1() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        loginUser();

        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);

        solo.enterText((EditText) solo.getView(R.id.mock_pickUp), "Hub Edmonton");
        solo.enterText((EditText) solo.getView(R.id.mock_dropOff), "Lister Edmonton");
        //solo.enterText((EditText) solo.getView(R.id.pick_up_auto_complete), "Hub Edmonton");
        //solo.enterText((EditText) solo.getView(R.id.drop_off_auto_complete), "Lister Edmonton");
        //assertTrue(solo.waitForText("Hub Edmonton", 1, 2000));
        //assertTrue(solo.waitForText("Cab Edmonton", 1, 2000));

        //solo.clearEditText((EditText) solo.getView(R.id.pick_up_auto_complete));
        //solo.clearEditText((EditText) solo.getView(R.id.drop_off_auto_complete));
        //assertFalse(solo.searchText("Edmonton"));
        //assertFalse(solo.searchText("Edmonton"));

        View fab = solo.getView(R.id.riderMenuBtn);
        solo.clickOnView(fab);
        assertTrue(solo.waitForText("Menu", 1, 2000));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);

        Thread.sleep(2000);
    }


    /**
     * Tests request cancelling when user is waiting for driver offer.
     * @throws InterruptedException
     * @throws NumberFormatException
     */
    @Test
    public void Case2() throws InterruptedException, NumberFormatException {
        loginUser();
        addNewRequest();
        assertTrue(solo.waitForText("Time Elapsed:", 1, 2000));
        //double fare = Double.parseDouble(String.valueOf(solo.getView(R.id.fare_amount)));

        solo.clickOnView(solo.getView(R.id.add_money));
        //String newFare = Double.toString(fare+1);
        assertTrue(solo.waitForText("3.68", 1, 2000));

        solo.clickOnView(solo.getView(R.id.reduce_money));
        assertTrue(solo.waitForText("2.68", 1, 2000));

        solo.clickOnButton("CANCEL REQUEST");
        assertTrue(solo.waitForText("HOP IN NOW!", 1, 2000));

        Thread.sleep(2000);
    }

    /**
     * Tests request cancelling when rider is waiting for driver pick up.
     * @throws InterruptedException
     */
    @Test
    public void Case3() throws InterruptedException {
        loginUser();
        addNewRequest();
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Would you like to accept this offer?",
                1, 2000));

        //TODO emergency call works, not driver calling
        //Tests calling and emailing driver
        /*Button emailBtn = (Button) solo.getView(R.id.rider_offer_email_button);
        solo.clickOnView(emailBtn);
        solo.goBack();
        Button phoneBtn = (Button) solo.getView(R.id.rider_offer_call_button);
        solo.clickOnView(phoneBtn);
        //assertTrue(solo.waitForText("Keypad",1,2000));
        solo.goBack();*/

        //Tests Dialog
        TextView driverName = (TextView) solo.getView(R.id.rider_driver_offer_name);
        solo.clickOnView(driverName);
        assertTrue(solo.waitForText("Car:",1,2000));

        /*Button emailBtn = (Button) solo.getView(R.id.rider_offer_email_button);
        solo.clickOnView(emailBtn);
        assertFalse(solo.waitForText("Would you like to accept this offer?",1,2000));
        solo.goBack();
        Button phoneDialogBtn = (Button) solo.getView(R.id.dialog_call_button);
        solo.clickOnView(phoneDialogBtn);
        //assertTrue(solo.waitForText("Keypad",1,2000));
        solo.goBack();*/

        solo.goBack();

        //Declining driver offer
        solo.clickOnButton("DECLINE");
        assertTrue(solo.waitForText("Time Elapsed:",
                1,2000));
        solo.clickOnButton(">");

        //Accepting driver offer then cancelling request
        solo.clickOnButton("ACCEPT");
        solo.clickOnButton("CANCEL REQUEST");
        assertTrue(solo.waitForText("HOP IN NOW!",
                1,2000));
    }

    /**
     * Test fragments of rider picked up, rider confirming arriving at drop off location, payment
     *      actions,and completing ride without rating
     * @throws InterruptedException
     */
    @Test
    public void Case4() throws InterruptedException {
        loginUser();
        addNewRequest();
        solo.clickOnButton(">");
        solo.clickOnButton("ACCEPT");
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Now, we are heading to ...",
                1,2000));

        //Tests Dialog
        TextView driverName = (TextView) solo.getView(R.id.rider_pickedup_driver_name);
        solo.clickOnView(driverName);
        assertTrue(solo.waitForText("Car:",1,2000));
        solo.goBack();

        solo.clickOnButton("EMERGENCY CALL");
        //assertFalse(solo.waitForText("Now, we are heading to ...",1,2000));
        solo.goBack();

        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Is this ride complete?",
                1,2000));

        solo.clickOnButton("NO");
        assertFalse(solo.waitForText("Is this ride complete?",
                1,2000));

        solo.clickOnButton(">");
        solo.clickOnButton("EMERGENCY CALL");
        //assertTrue(solo.waitForText("Is this ride complete?",1,2000));
        solo.goBack();
        Thread.sleep(2000);

        solo.clickOnButton("YES");
        assertTrue(solo.waitForText("Payment",1,2000));

        //test tips and payment
        checkPayment();

        //test completing trip without rating
        solo.setProgressBar(0,8);
        solo.clickOnView(solo.getView(R.id.dialog_rating_cancel));
        assertTrue(solo.waitForText("HOP IN NOW!",1,2000));
    }

    /**
     * Complete ride by submitting a rating.
     * @throws InterruptedException
     */
    @Test
    public void Case5() throws InterruptedException {
        loginUser();
        addNewRequest();
        solo.clickOnButton(">");
        solo.clickOnButton("ACCEPT");
        solo.clickOnButton(">");
        solo.clickOnButton(">");
        solo.clickOnButton("YES");
        solo.clickOnButton("CONFIRM");

        solo.clickOnView(solo.getView(R.id.dialog_rating_submit));
        assertTrue(solo.waitForText("Rating", 1, 2000));

        solo.setProgressBar(0,8);
        solo.clickOnView(solo.getView(R.id.dialog_rating_submit));
        assertTrue(solo.waitForText("HOP IN NOW!",1,2000));
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