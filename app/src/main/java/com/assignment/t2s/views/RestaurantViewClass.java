package com.assignment.t2s.views;

import com.assignment.t2s.IRestaurantView;
import com.assignment.t2s.IStoreIDListener;
import com.assignment.t2s.R;
import com.assignment.t2s.model.RestaurantData;
import com.assignment.t2s.ui.RestaurantHomeActivity;
import com.assignment.t2s.presenter.RestaurantPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vivek on 10/11/17.
 */

public class RestaurantViewClass implements IRestaurantView, IStoreIDListener {

    private RestaurantHomeActivity mContext;
    private RestaurantPresenter restaurantPresenter;

    public RestaurantViewClass(RestaurantHomeActivity context){
        this.mContext = context;
    }

    @Override
    public void setRestaurantPresenter(RestaurantPresenter restaurantPresenter) {
        this.restaurantPresenter = restaurantPresenter;
    }

    @Override
    public void getRestaurantDetails(RestaurantData restaurantData) {
        if(restaurantData != null){
            mContext.hideErrorMessage();
            mContext.hideProgressLoader();
            mContext.setRestaurantName(restaurantData.getRestName());
            mContext.setRestaurantAddress(restaurantData.getRestAddress());

            parseRestaurantStatus(restaurantData.getRestTimings());
        }

    }

    @Override
    public void errorMessage(String errorMessage) {
        if(errorMessage != null) {

            mContext.hideProgressLoader();
            mContext.showErrorMessage(errorMessage);
        }
    }



    /**
     * Function to parse the timing json and determine
     * whether the restaurant is open/close
     * @param restTimings is the json object as string for timings
     */
    private void parseRestaurantStatus(String restTimings){
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());
        String current = timeFormat.format(calendar.getTime());

        try {
            JSONObject jsonObject = new JSONObject(restTimings);
            JSONArray jsonArray = jsonObject.getJSONArray(weekDay);

            if(jsonArray != null){

                Date startTime = timeFormat.parse(jsonArray.getString(0));
                Date endTime = timeFormat.parse(jsonArray.getString(1));
                Date currentTime = timeFormat.parse(current);

                if(startTime.before(currentTime) && currentTime.before(endTime)) {

                    String text = mContext.getString(R.string.open_now) + " - " + timeFormat.format(startTime) + " to " +
                            timeFormat.format(endTime) + "(" + weekDay + ")";

                    mContext.setRestaurantStatus(true, text);


                }else {
                    String text = mContext.getString(R.string.close_now) + " - " + timeFormat.format(startTime) + " to " +
                            timeFormat.format(endTime) + "(" + weekDay + ")";

                    mContext.setRestaurantStatus(false, text);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStoreIDEntered(String storeID) {
        if(restaurantPresenter != null) {
            mContext.showProgressLoader();
            mContext.resetValues();
            restaurantPresenter.getRestaurantDetails(mContext, storeID);
        }

    }
}
