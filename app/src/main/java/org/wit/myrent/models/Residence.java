package org.wit.myrent.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.wit.myrent.R;

import java.util.Date;
import java.util.Random;

public class Residence {
    public Long id;
    public Long date;
    public String tenant;

    //a latitude longitude pair
    //example "52.4566,-6.5444"
    public String geolocation;
    public boolean rented;
    private static final String JSON_TENANT = "tenant";
    private static final String JSON_ID = "id";
    private static final String JSON_GEOLOCATION = "geolocation";
    private static final String JSON_DATE = "date";
    private static final String JSON_RENTED = "rented";
    public double zoom;//zoom level of accompanying map
    private static final String JSON_ZOOM = "zoom"; //map zoom level

    public Residence()
    {
        id = unsignedLong();
        id = new Random().nextLong();
        date = new Date().getTime();
        geolocation = "52.253456,-7.187162";
        zoom = 16.0f;
        tenant = ": none presently";
    }

    /**
     * Generate a long greater than zero
     *
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }

    public Residence(JSONObject json) throws JSONException {
        id = json.getLong(JSON_ID);
        geolocation = json.getString(JSON_GEOLOCATION);
        date = json.getLong(JSON_DATE);
        zoom = json.getDouble(JSON_ZOOM);
        rented = json.getBoolean(JSON_RENTED);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Long.toString(id));
        json.put(JSON_GEOLOCATION, geolocation);
        json.put(JSON_DATE, date);
        json.put(JSON_RENTED, rented);
        json.put(JSON_ZOOM, zoom);
        json.put(JSON_TENANT, tenant);
        return json;
    }

    public String getResidenceReport(Context context) {
        String rentedString = "";
        if (rented) {
            rentedString = context.getString(R.string.residence_report_rented);
        } else {
            rentedString = context.getString(R.string.residence_report_not_rented);
        }

        String prospectiveTenant = tenant;
        if (tenant == null) {
            prospectiveTenant = context.getString(R.string.residence_report_nobody_interested);
        } else {
            prospectiveTenant = context.getString(R.string.residence_report_prospective_tenant, tenant);
        }
        String report = "Location " + geolocation + " Date: " + dateString() + " " + rentedString + " " + prospectiveTenant;
        return report;

    }

    public void setGeolocation(String geolocation)
    {
        this.geolocation = geolocation;
    }

    public String getGeolocation()
    {
        return geolocation;
    }

    public String getDateString() {
        return "Registered:" + dateString();
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }
}
