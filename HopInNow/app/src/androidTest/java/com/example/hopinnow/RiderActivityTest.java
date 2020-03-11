package com.example.hopinnow;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.content.ComponentName;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
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
public class RiderActivityTest{
    private Solo solo;
    private String userEmail = "folanqi123@ualberta.ca";
    private String userPassword = "12345678";

    //TODO drop down list on click
    //TODO toast message partial string check?
    //TODO hanging up phone

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
     * - login()
     * - addRequest()
     * - completePayment()
     * - cancelRequest()
     * - confirmRating
     * - noRating
     * - cancelRequestOnWaitingDriverOffer
     * - cancelRequestOn
     */

    private void loginUser() throws InterruptedException {
        // Log in To Activity
        //TODO MDZZ FRAGMENT DOES NOT ACCEPT ENTERTEXT
        solo.enterText((EditText)solo.getView(R.id.loginEmailEditText), userEmail);
        solo.enterText((EditText)solo.getView(R.id.loginPassword), userPassword);
        solo.goBack();
        solo.clickOnButton("LOGIN");
    }

    private void addNewRequest() throws InterruptedException {

        solo.clickOnButton("HOP IN NOW!");
        Thread.sleep(2000);
    }

    private  void checkPayment() throws InterruptedException {

        solo.clickOnButton("10%");
        solo.clickOnButton("$ SUM");
        assertTrue(solo.waitForText("2.71", 1, 2000));

        solo.clickOnButton("15%");
        solo.clickOnButton("$ SUM");
        assertTrue(solo.waitForText("2.83", 1, 2000));

        solo.clickOnButton("20%");
        solo.clickOnButton("$ SUM");
        assertTrue(solo.waitForText("2.95", 1, 2000));

        Button other = (Button) solo.getView(R.id.rider_payment_else);
        solo.clickOnView(solo.getView(R.id.rider_payment_other_editText));
        solo.enterText((EditText)solo.getView(R.id.rider_payment_other_editText),"10");
        solo.clickOnButton("$ SUM");
        assertTrue(solo.waitForText("2.71", 1, 2000));

        solo.clickOnButton("CONFIRM");
        assertTrue(solo.waitForText("Rating", 1, 2000));

        //check qr image popping
    }


    /**
     * Checks auto complete fragment and menu button.
     */
    @Test
    public void Case1() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        loginUser();

        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);

        solo.clickOnView(solo.getView(R.id.pick_up_auto_complete));
        solo.enterText((EditText) solo.getView(R.id.pick_up_auto_complete), "Hub Edmonton");
        solo.enterText((EditText) solo.getView(R.id.drop_off_auto_complete), "Cab Edmonton");
        assertTrue(solo.waitForText("Hub Edmonton", 1, 2000));
        assertTrue(solo.waitForText("Cab Edmonton", 1, 2000));

        solo.clearEditText((EditText) solo.getView(R.id.pick_up_auto_complete));
        solo.clearEditText((EditText) solo.getView(R.id.drop_off_auto_complete));
        assertFalse(solo.searchText("Edmonton"));
        assertFalse(solo.searchText("Edmonton"));

        View fab = solo.getView(R.id.riderMenuBtn);
        solo.clickOnView(fab);
        assertTrue(solo.waitForText("Menu", 1, 2000));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);
    }


    /**
     * waitingDriverOfferFragmentCancel
     */
    @Test
    public void Case2() throws InterruptedException {
        addNewRequest();
        assertTrue(solo.waitForText("Time Elapsed", 1, 2000));
        double fare = Double.parseDouble(String.valueOf(solo.getView(R.id.fare_amount)));

        solo.clickOnButton("+1$");
        String newFare = Double.toString(fare+1);
        assertTrue(solo.waitForText(newFare, 1, 2000));

        solo.clickOnButton("-1$");
        assertTrue(solo.waitForText(Double.toString(fare), 1, 2000));

        solo.clickOnButton("Cancel The Request");
        assertTrue(solo.waitForText("HOP IN NOW!", 1, 2000));
    }

    /**
     * waitingDriverFragmentCancel
     */
    @Test
    public void Case3() throws InterruptedException {
        addNewRequest();
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Would you like to accept this offer?",
                1, 2000));

        Button emailBtn = (Button) solo.getView(R.id.rider_offer_email_button);
        solo.clickOnView(emailBtn);
        assertTrue(solo.waitForText("Share",1,2000));
        solo.goBack();

        Button phoneBtn = (Button) solo.getView(R.id.rider_offer_email_button);
        solo.clickOnView(phoneBtn);
        assertTrue(solo.waitForText("Keypad",1,2000));
        solo.goBack();

        EditText driverName = (EditText) solo.getView(R.id.rider_driver_offer_name);
        solo.clickOnView(driverName);
        assertFalse(solo.waitForText("Would you like to accept this offer?",
                1,2000));
        solo.goBack();

        solo.clickOnButton("DECLINE");
        assertFalse(solo.waitForText("Time Elapsed:",
                1,2000));
        solo.clickOnButton(">");
        solo.clickOnButton("ACCEPT");
        solo.clickOnButton("CANCEL REQUEST");
        assertTrue(solo.waitForText("HOP IN NOW!",
                1,2000));
    }

    /**
     * noRating
     */
    @Test
    public void Case4() throws InterruptedException {
        addNewRequest();
        solo.clickOnButton(">");
        solo.clickOnButton("ACCEPT");
        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Now, we are heading to ...",
                1,2000));

        solo.clickOnButton("EMERGENCY CALL");
        assertTrue(solo.waitForText("Keypad",1,2000));
        solo.goBack();

        solo.clickOnButton(">");
        assertTrue(solo.waitForText("Is this ride complete?",
                1,2000));

        solo.clickOnButton("NO");
        assertFalse(solo.waitForText("Is this ride complete?",
                1,2000));

        solo.clickOnButton(">");
        solo.clickOnButton("EMERGENCY CALL");
        assertTrue(solo.waitForText("Keypad",1,2000));
        solo.goBack();

        solo.clickOnButton("YES");
        assertTrue(solo.waitForText("Payment",1,2000));

        checkPayment();

        solo.setProgressBar(0,4);
        solo.clickOnButton("CANCEL");
        assertFalse(solo.waitForText("Rating",1,2000));
    }

    /**
     * yesRating
     */
    @Test
    public void Case5() throws InterruptedException {
        addNewRequest();
        solo.clickOnButton(">");
        solo.clickOnButton("ACCEPT");
        solo.clickOnButton(">");
        solo.clickOnButton(">");
        solo.clickOnButton("YES");

        solo.clickOnButton("SUBMIT");
        assertFalse(solo.waitForText("Rating",1,2000));
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