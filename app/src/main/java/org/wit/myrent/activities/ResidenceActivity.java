package org.wit.myrent.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.wit.myrent.R;
import org.wit.myrent.models.Residence;


public class ResidenceActivity extends Activity implements TextWatcher,CompoundButton.OnCheckedChangeListener {

    private EditText  geolocation;
    private Residence residence;
    private CheckBox rented;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence);

        geolocation = (EditText) findViewById(R.id.geolocation);
        residence = new Residence();

        // Register a TextWatcher in the EditText geolocation object
        geolocation.addTextChangedListener(this);

        dateButton  = (Button)   findViewById(R.id.registration_date);
        dateButton .setEnabled(false);
        rented      = (CheckBox) findViewById(R.id.isrented);
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
}
