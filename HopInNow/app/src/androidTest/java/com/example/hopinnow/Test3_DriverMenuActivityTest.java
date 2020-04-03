package com.example.hopinnow;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.hopinnow.activities.DriverMapActivity;
import com.example.hopinnow.activities.LoginActivity;
import com.example.hopinnow.activities.RiderMapActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;

public class Test3_DriverMenuActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);


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
        Thread.sleep(2000);

        String userEmail = "test@driver.com";
        solo.enterText((EditText)solo.getView(R.id.loginEmailEditText), userEmail);
        String userPassword = "1111111";
        solo.enterText((EditText)solo.getView(R.id.loginPassword), userPassword);
        solo.clickOnView(solo.getView(R.id.loginButton));

        solo.assertCurrentActivity("Wrong Activity", DriverMapActivity.class);
        solo.clickOnView(solo.getView(R.id.driverMenuBtn));
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
        solo.enterText(phoneET,"2222222");
        assertTrue(solo.waitForText("2222222",1,2000));

        EditText nameET = (EditText) solo.getView(R.id.proNameET);
        solo.clearEditText(nameET);
        solo.enterText(nameET,"Testing Driver");
        assertTrue(solo.waitForText("Testing Driver",1,2000));

        solo.clickOnView(solo.getView(R.id.proUpdateBtn));
        Thread.sleep(3000);
        solo.goBack();
        Thread.sleep(2000);

        //test editing my car
        solo.clickOnMenuItem("Car Information");
        solo.clearEditText((EditText) solo.getView(R.id.vehicleMakeEditText));
        solo.enterText((EditText) solo.getView(R.id.vehicleMakeEditText),"BMW");
        assertTrue(solo.waitForText("BMW",1,2000));
        solo.clearEditText((EditText) solo.getView(R.id.vehicleModelEditText));
        solo.enterText((EditText) solo.getView(R.id.vehicleModelEditText),"X6");
        assertTrue(solo.waitForText("X6",1,2000));
        solo.clearEditText((EditText) solo.getView(R.id.vehicleColorEditText));
        solo.enterText((EditText) solo.getView(R.id.vehicleColorEditText),"Red");
        assertTrue(solo.waitForText("Red",1,2000));
        solo.clearEditText((EditText) solo.getView(R.id.vehiclePlateEditText));
        solo.enterText((EditText) solo.getView(R.id.vehiclePlateEditText),"2222222");
        assertTrue(solo.waitForText("2222222",1,2000));
        solo.clickOnView(solo.getView(R.id.vehicleUpdateBtn));
        assertTrue(solo.waitForText("updated!",1,2000));
        solo.goBack();

        //test my trips
        solo.clickOnMenuItem("My Trips");
        assertTrue(solo.waitForText("History",1,2000));
        solo.goBack();

        //test offline
        solo.clickOnMenuItem("Offline");
        assertTrue(solo.waitForText("SEARCH",1,2000));

        // log out
        solo.clickOnView(solo.getView(R.id.driverMenuBtn));
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
