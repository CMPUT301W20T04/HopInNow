package com.example.hopinnow;

import androidx.annotation.NonNull;

import com.example.hopinnow.Database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.RegisterStatusListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import java.util.Objects;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;


public class TestUserDatabaseAccessor implements RegisterStatusListener{
    public static final String TAG = "TestUserDatabaseAccessor";

    UserDatabaseAccessor userDatabaseAccessor;
    private User mockupUser() {
        User user = new User();
        user.setEmail("tester@test.ca");
        user.setName("Tester");
        user.setPassword("1234567");
        user.setPhoneNumber("14159265");
        return user;
    }
    private Rider mockupRider(User user) {
        Rider rider = (Rider)user;
        rider.setUserType(false);
        return rider;
    }
    private Driver mockupDriver(User user) {
        Driver driver = (Driver)user;
        driver.setUserType(true);
        Car car = new Car();
        car.setMake("Test Make");
        car.setModel("Test Model");
        car.setColor("Test Color");
        car.setPlateNumber("Test Plate Number");
        driver.setCar(car);
        return driver;
    }

    @Test
    public void testRegisterUser() {
        userDatabaseAccessor = new UserDatabaseAccessor();
        User user = this.mockupUser();
        // test register rider:
        Rider rider = this.mockupRider(user);
        userDatabaseAccessor.registerUser(rider, this);
        // test register driver:
        Driver driver = this.mockupDriver(user);
        userDatabaseAccessor.registerUser(driver, this);
    }

    @Override
    public void onRegisterSuccess() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Objects.requireNonNull(firebaseAuth
                .getCurrentUser())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            assertTrue(true);
                        } else {
                            fail();
                        }
                    }
                });

    }

    @Override
    public void onRegisterFailure() {

    }
}
