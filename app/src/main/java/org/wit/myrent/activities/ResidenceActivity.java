package org.wit.myrent.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import org.wit.android.helpers.ContactHelper;
import org.wit.myrent.R;
import org.wit.myrent.app.MyRentApp;
import org.wit.myrent.models.Portfolio;
import org.wit.myrent.models.Residence;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.wit.android.helpers.ContactHelper.sendEmail;
import static org.wit.android.helpers.IntentHelper.navigateUp;
import static org.wit.android.helpers.IntentHelper.selectContact;


public class ResidenceActivity extends AppCompatActivity implements TextWatcher,CompoundButton.OnCheckedChangeListener, View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private EditText  geolocation;
    private Residence residence;
    private CheckBox rented;
    private Button dateButton;
    private Portfolio portfolio;
    private String emailAddress = "";
    private static final int REQUEST_CONTACT = 1;
    private Button tenantButton;
    private Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        geolocation = (EditText) findViewById(R.id.geolocation);
        residence = new Residence();

        // Register a TextWatcher in the EditText geolocation object
        geolocation.addTextChangedListener(this);

        dateButton  = (Button)   findViewById(R.id.registration_date);
        dateButton  .setOnClickListener(this);
        rented      = (CheckBox) findViewById(R.id.isrented);

        tenantButton = (Button) findViewById(R.id.tenant);
        reportButton = (Button) findViewById(R.id.residence_reportButton);

        MyRentApp app = (MyRentApp) getApplication();
        portfolio = app.portfolio;

        Long resId = (Long) getIntent().getExtras().getSerializable("RESIDENCE_ID");
        residence = portfolio.getResidence(resId);
        if (residence != null)
        {
            updateControls(residence);
        }
    }



    public void updateControls(Residence residence)
    {
        geolocation.setText(residence.geolocation);
        rented.setOnCheckedChangeListener(this);
        dateButton.setText(residence.getDateString());
        tenantButton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
    }




    @Override
    public void beforeTextChanged(CharSequence c, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence c, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        residence.setGeolocation(editable.toString());

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.i(this.getClass().getSimpleName(), "rented Checked");
        residence.rented = isChecked;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.registration_date      : Calendar c = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog (this, this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                break;
            case R.id.tenant:
                selectContact(this, REQUEST_CONTACT);
                break;
            case R.id.residence_reportButton:
                sendEmail(this, emailAddress,
                        getString(R.string.residence_report_subject), residence.getResidenceReport(this));
                break;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
        residence.date = date.getTime();
        dateButton.setText(residence.getDateString());
    }

    @Override
    public void onPause() {
        super.onPause();
        portfolio.saveResidences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUp(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONTACT:
                String name = ContactHelper.getContact(this, data);
                emailAddress = ContactHelper.getEmail(this, data);
                tenantButton.setText(name + " : " + emailAddress);
                residence.tenant = name;
                break;
        }
    }

}
