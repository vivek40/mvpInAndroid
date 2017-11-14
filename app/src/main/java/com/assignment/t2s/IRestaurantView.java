package com.assignment.t2s;

import com.assignment.t2s.model.RestaurantData;
import com.assignment.t2s.presenter.RestaurantPresenter;

/**
 * Created by vivek on 08/11/17.
 */

public interface IRestaurantView {

    void getRestaurantDetails(RestaurantData restaurantData);
    void errorMessage(String message);

    void setRestaurantPresenter(RestaurantPresenter restaurantPresenter);
}
