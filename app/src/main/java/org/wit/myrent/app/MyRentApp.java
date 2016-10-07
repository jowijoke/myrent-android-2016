package org.wit.myrent.app;


import android.app.Application;

import org.wit.myrent.models.Portfolio;
import org.wit.myrent.models.PortfolioSerializer;

import static org.wit.android.helpers.LogHelpers.info;

public class MyRentApp extends Application {
    public Portfolio portfolio;
    private static final String FILENAME = "portfolio.json";
    protected static MyRentApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(serializer);

        info(this, "MyRent app launched");
    }

    public static MyRentApp getApp() {
        return app;

    }
}
