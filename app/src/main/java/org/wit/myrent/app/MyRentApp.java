package org.wit.myrent.app;


import android.app.Application;

import org.wit.myrent.models.Portfolio;

import static org.wit.android.helpers.LogHelpers.info;

public class MyRentApp extends Application {
    public Portfolio portfolio;

    @Override
    public void onCreate()
    {
        super.onCreate();
        portfolio = new Portfolio();

        info(this, "MyRent app launched");
    }
}
