package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopinnow.R;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.ProgressbarDialog;

import java.util.Locale;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements UserProfileStatusListener {
    // establish the TAG of this activity:
    public static final String TAG = "ProfileActivity";
    // declare database accessor:
    private UserDatabaseAccessor userDatabaseAccessor;
    // Global User object:
    private User currentUser;
    // UI Components:
    private EditText name;
    private EditText phoneNumber;
    private TextView email;
    private TextView deposit;
    private TextView userType;
    private Button editBtn;
    private Button updateBtn;
    private Button logoutButton;
    // alert progress dialog:
    private ProgressbarDialog progressbarDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // init the userDatabaseAccessor:
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        // check the login status:
        if (!this.userDatabaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        // UI init, button listeners are written in the onStart() method
        this.name = findViewById(R.id.proNameET);
        this.name.setEnabled(false);
        this.email = findViewById(R.id.proEmailTxt);
        this.phoneNumber = findViewById(R.id.proPhoneET);
        this.phoneNumber.setEnabled(false);
        this.deposit = findViewById(R.id.proDeposit);
        this.userType = findViewById(R.id.proUserType);
        this.editBtn = findViewById(R.id.editProfileBtn);
        this.updateBtn = findViewById(R.id.proUpdateBtn);
        this.updateBtn.setEnabled(false);
        this.updateBtn.setVisibility(View.INVISIBLE);
        this.logoutButton = findViewById(R.id.proLogoutBtn);
        // alert progress dialog:
        ViewGroup viewGroup = findViewById(R.id.activity_profile);
        progressbarDialog = new ProgressbarDialog(ProfileActivity.this, viewGroup);
        progressbarDialog.startProgressbarDialog();
        // retrieve the current user information
        Intent intent = this.getIntent();
        this.currentUser = (User)intent.getSerializableExtra("UserObject");
        if (this.currentUser == null) {
            this.userDatabaseAccessor.getUserProfile(this);
        } else {
            // set all text fields according to the retreived user object:
            this.name.setText(Objects.requireNonNull(currentUser).getName());
            this.email.setText(currentUser.getEmail());
            this.phoneNumber.setText(currentUser.getPhoneNumber());
            this.deposit.setText(
                    String.format(Locale.CANADA, "%.2f", currentUser.getDeposit()));
            if (this.currentUser.isUserType()) {    // if true, then the user is driver
                this.userType.setText(R.string.usertype_driver);
            } else {    // or else, the user is a rider
                this.userType.setText(R.string.usertype_rider);
            }
            this.progressbarDialog.dismissDialog();
        }
    }
    private boolean verifyFields() {
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        // actions when edit button is clicked:
        this.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                phoneNumber.setEnabled(true);
                editBtn.setEnabled(false);
                editBtn.setVisibility(View.INVISIBLE);
                updateBtn.setEnabled(true);
                updateBtn.setVisibility(View.VISIBLE);
            }
        });
        // actions when update button is clicked:
        this.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!verifyFields()) {
                    return;
                }
                // alert progress dialog:
                ViewGroup viewGroup = findViewById(R.id.activity_profile);
                progressbarDialog = new ProgressbarDialog(ProfileActivity.this, viewGroup);
                progressbarDialog.startProgressbarDialog();
                // access database:
                currentUser.setName(name.getText().toString());
                currentUser.setPhoneNumber(phoneNumber.getText().toString());
                userDatabaseAccessor.updateUserProfile(currentUser, ProfileActivity.this);
            }
        });
        // actions when logout button is clicked:
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDatabaseAccessor.logoutUser();
                // go to the login activity again:
                Toast.makeText(getApplicationContext(),
                        "You are Logged out!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }



    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.currentUser = user;
        // set all text fields according to the retreived user object:
        this.name.setText(Objects.requireNonNull(currentUser).getName());
        this.email.setText(currentUser.getEmail());
        this.phoneNumber.setText(currentUser.getPhoneNumber());
        this.deposit.setText(
                String.format(Locale.CANADA, "%.2f", currentUser.getDeposit()));
        if (user.isUserType()) {    // if true, then the user is driver
            this.userType.setText(R.string.usertype_driver);
        } else {    // or else, the user is a rider
            this.userType.setText(R.string.usertype_rider);
        }
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onProfileRetrieveFailure() {
        this.progressbarDialog.dismissDialog();
        Toast.makeText(getApplicationContext(),
                "Info retrieve failed, check network connection.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProfileUpdateSuccess(User user) {
        this.currentUser = user;
        // set all text fields according to the retreived user object:
        this.name.setText(Objects.requireNonNull(currentUser).getName());
        this.email.setText(currentUser.getEmail());
        this.phoneNumber.setText(currentUser.getPhoneNumber());
        name.setEnabled(false);
        phoneNumber.setEnabled(false);
        editBtn.setEnabled(true);
        editBtn.setVisibility(View.VISIBLE);
        updateBtn.setEnabled(false);
        updateBtn.setVisibility(View.INVISIBLE);
        this.progressbarDialog.dismissDialog();
        Toast.makeText(getApplicationContext(),
                "Your info is updated!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProfileUpdateFailure() {
        this.progressbarDialog.dismissDialog();
        Toast.makeText(getApplicationContext(),
                "Update failed, check network connection.", Toast.LENGTH_LONG).show();
    }
}
