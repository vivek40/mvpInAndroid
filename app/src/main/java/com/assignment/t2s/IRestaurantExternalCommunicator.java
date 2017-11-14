package com.assignment.t2s;

import com.assignment.t2s.model.RestaurantData;

/**
 * Created by vivek on 10/11/17.
 */

public interface IRestaurantExternalCommunicator {
    void onSuccess(RestaurantData restaurantData);
    void onFailure(String errorMessage);
}
