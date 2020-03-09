package com.example.hopinnow;

import android.app.Activity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hopinnow.activities.RiderMapActivity;
import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest{
    private Solo solo;
    @Rule
    public ActivityTestRule<RiderMapActivity> rule =
            new ActivityTestRule<>(RiderMapActivity.class, true, true);


    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
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
     * Checks locations added to the two search location fragments.
     */
    @Test
    public void checkList(){
        solo.assertCurrentActivity("Wrong Activity", RiderMapActivity.class);
        solo.enterText((EditText) solo.getView(R.id.pick_up_auto_complete), "Edmonton");
        solo.enterText((EditText) solo.getView(R.id.pick_up_auto_complete), "Edmonton");
        assertTrue(solo.waitForText("Hub Edmonton", 1, 2000));
        assertTrue(solo.waitForText("Cab Edmonton", 1, 2000));

        solo.clickOnButton("HOP IN NOW!");

        solo.clearEditText((EditText) solo.getView(R.id.editText_name)); //Clear the EditText
        assertTrue(solo.waitForText("Edmonton", 1, 2000));
        solo.clickOnButton("ClEAR ALL"); 
        assertFalse(solo.searchText("Edmonton"));
    }


    /**
     * Check button "hop in now" for creating new request and switch fragment.
     */
    @Test
    public void checkCiyListItem(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("ADD CITY");
        solo.enterText((EditText) solo.getView(R.id.editText_name), "Edmonton");
        solo.clickOnButton("CONFIRM");
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