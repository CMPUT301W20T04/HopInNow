package com.example.hopinnow;

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hopinnow.activities.LoginActivity;
import com.example.hopinnow.activities.RiderMapActivity;

import com.google.android.gms.maps.model.Marker;
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
public class Test4_RiderActivityTest {
    private Solo solo;

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
        //TODO MDZZ FRAGMENT DOES NOT ACCEPT ENTERTEXT
        String userEmail = "folanqi123@ualberta.ca";
        solo.enterText((EditText)solo.getView(R.id.loginEmailEditText), userEmail);
        String userPassword = "12345678";
        solo.enterText((EditText)solo.getView(R.id.loginPassword), userPassword);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.loginButton));

        Thread.sleep(2000);
    }

    /**
     * Logs out user.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    private void logoutUser() throws InterruptedException {

        solo.clickOnView(solo.getView(R.id.riderMenuBtn));
        solo.clickOnView(solo.getView(R.id.riderMyProfile));
        solo.clickOnView(solo.getView(R.id.proLogoutBtn));

        Thread.sleep(2000);
    }


    /**
     * Add new request.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    private void addNewRequest() throws InterruptedException {
        solo.enterText((EditText) solo.getView(R.id.mock_pickUp), "Hub Edmonton");
        solo.enterText((EditText) solo.getView(R.id.mock_dropOff), "Lister Edmonton");
        Thread.sleep(2000);
        solo.clickOnButton("HOP IN NOW!");
        Thread.sleep(2000);
    }


    /**
     * Checks all tips option.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
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

        solo.clickOnView(solo.getView(R.id.rider_payment_else));
        solo.enterText((EditText)solo.getView(R.id.rider_payment_other_editText),"10");
        solo.clickOnView(solo.getView(R.id.rider_payment_calculate));
        assertTrue(solo.waitForText("2.71", 1, 3000));

        solo.clickOnButton("CONFIRM");
        assertTrue(solo.waitForText("Rating", 1, 2000));

        //check qr image popping

        Thread.sleep(2000);
    }




    /**
     * Checks menu button.
     * Auto complete fragment testing is currently in question and mocks are used.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     *
     *      todo: autocompletefragment cannot be entered with text
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

        logoutUser();

        Thread.sleep(2000);
    }


    /**
     * Tests request cancelling when user is waiting for driver offer.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     * @throws NumberFormatException
     *      throws exception if string is converted to a number
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

        logoutUser();

        Thread.sleep(2000);
    }

    /**
     * Tests request cancelling when rider is waiting for driver pick up.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
     */
    @Test
    public void Case3() throws InterruptedException {
        loginUser();
        addNewRequest();
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Would you like to accept this offer?",
                1, 2000));

        //Tests Dialog
        TextView driverName = (TextView) solo.getView(R.id.rider_driver_offer_name);
        solo.clickOnView(driverName);
        assertTrue(solo.waitForText("Car:",1,2000));
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

        logoutUser();

        Thread.sleep(2000);
    }

    /**
     * Test fragments of rider picked up, rider confirming arriving at drop off location, payment
     *      actions,and completing ride without rating
     * @throws InterruptedException
     *       throws exception if thread is interrupted
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

        logoutUser();

        Thread.sleep(2000);
    }

    /**
     * Complete ride by submitting a rating.
     * @throws InterruptedException
     *      throws exception if thread is interrupted
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