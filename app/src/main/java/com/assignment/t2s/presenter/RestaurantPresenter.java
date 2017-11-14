package com.assignment.t2s.presenter;

import android.content.Context;

import com.assignment.t2s.utils.NetworkHelper;
import com.assignment.t2s.R;
import com.assignment.t2s.IRestaurantExternalCommunicator;
import com.assignment.t2s.model.RestaurantData;
import com.assignment.t2s.utils.RestaurantFetcher;
import com.assignment.t2s.IRestaurantView;
import com.assignment.t2s.views.RestaurantViewClass;

/**
 * Created by vivek on 08/11/17.
 */

public class RestaurantPresenter {

    private IRestaurantView restaurantView;

    public RestaurantPresenter(RestaurantViewClass restaurantView){
        this.restaurantView = restaurantView;
        this.restaurantView.setRestaurantPresenter(this);

    }

    /**
     * Function to hit the API and retrieve the response
     * @param storeID is the parameter to be passed in the API
     */
    public void getRestaurantDetails(Context mContext, String storeID){

        if (NetworkHelper.isNetworkAvailable(mContext)) {

            RestaurantFetcher restaurantFetcher = new RestaurantFetcher();
            restaurantFetcher.getRestaurantDetails(mContext, storeID, new IRestaurantExternalCommunicator() {
                @Override
                public void onSuccess(RestaurantData restaurantData) {
                    restaurantView.getRestaurantDetails(restaurantData);
                }

                @Override
                public void onFailure(String errorMessage) {
                    restaurantView.errorMessage(errorMessage);
                }
            });

        }else
            restaurantView.errorMessage(mContext.getString(R.string.internet_check));
    }

}
