package com.assignment.t2s.utils;

import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.assignment.t2s.IRestaurantExternalCommunicator;
import com.assignment.t2s.model.RestaurantData;
import com.assignment.t2s.utils.NetworkHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vivek on 08/11/17.
 */

public class RestaurantFetcher {

    public RestaurantFetcher() {

    }

    /**
     * Function to make the API call and return the result
     * @param mContext is the activity context
     * @param storeID is the storeID of the restaurant
     * @param externalCommunicator is the callback object for the API call
     */
    public void getRestaurantDetails(Context mContext, String storeID, final IRestaurantExternalCommunicator externalCommunicator){

        RequestQueue queue = NetworkHelper.getRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(NetworkHelper.SERVER_URL + NetworkHelper.SERVER_GET_RESTAURANT + storeID,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("message")) {
                    try {
                        externalCommunicator.onFailure(response.getString("message"));
                    }catch (JSONException e){
                        e.printStackTrace();
                        externalCommunicator.onFailure("Some parsing error has encountered");
                    }
                }else {
                    RestaurantData restaurantData = populateResturantData(response);
                    if (restaurantData != null)
                        externalCommunicator.onSuccess(restaurantData);
                    else
                        externalCommunicator.onFailure("Some parsing error has encountered");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                externalCommunicator.onFailure(error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s","api","testapi!");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    /**
     * Function that parse the response and stores in the local modal
     * @param jsonObject is the response json
     * @return the local data modal RestaurantData object
     */
    private RestaurantData populateResturantData(JSONObject jsonObject){
        try{
            RestaurantData restaurantData = new RestaurantData();
            restaurantData.setRestName(jsonObject.getString("host"));

            JSONObject pageObject = jsonObject.getJSONObject("pages");
            JSONObject contactObject = pageObject.getJSONObject("contact");
            JSONObject addressObject = contactObject.getJSONObject("address");

            restaurantData.setRestAddress(addressObject.getString("address1")+","+
                    addressObject.getString("address2")+","+addressObject.getString("address3")+","+addressObject.getString("postcode"));
            restaurantData.setRestLocation(addressObject.getString("lat")+","+addressObject.getString("long"));
            restaurantData.setRestPhoneNo(addressObject.getString("phone"));
            restaurantData.setRestTimings(contactObject.getJSONObject("opentimes").toString());


            return restaurantData;

        }catch (JSONException e){
            e.printStackTrace();

        }

        return null;
    }

}
