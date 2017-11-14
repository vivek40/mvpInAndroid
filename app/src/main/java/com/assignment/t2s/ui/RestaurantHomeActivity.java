package com.assignment.t2s.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assignment.t2s.R;
import com.assignment.t2s.presenter.RestaurantPresenter;
import com.assignment.t2s.views.RestaurantViewClass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantHomeActivity extends AppCompatActivity {

    @BindView(R.id.name_textview)
    TextView restaurantNameView;

    @BindView(R.id.address_textView)
    TextView restaurantAddressView;

    @BindView(R.id.status_textview)
    TextView restaurantStatusView;

    @BindView(R.id.error_textview)
    TextView errorMessage;

    @BindView(R.id.progressBar)
    ProgressBar loadingProgress;

    RestaurantPresenter restaurantPresenter;

    RestaurantViewClass restaurantView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        restaurantView = new RestaurantViewClass(this);
        restaurantPresenter = new RestaurantPresenter(restaurantView);
        restaurantView.setRestaurantPresenter(restaurantPresenter);

    }

    /**
     * Function to hide the progressLoader
     */
    public void hideProgressLoader(){
        this.loadingProgress.setVisibility(View.GONE);
    }

    /**
     * Function to show the progressLoader
     */
    public void showProgressLoader(){
        this.loadingProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Function to hide error message
     */
    public void hideErrorMessage(){
        this.errorMessage.setVisibility(View.GONE);
    }

    /**
     * Function to display the error message
     * @param errorMessage is the actual message
     */
    public void showErrorMessage(String errorMessage){
        this.errorMessage.setVisibility(View.VISIBLE);
        this.errorMessage.setText(errorMessage);
    }

    /**
     * Function to set the name of the restaurant
     * @param restaurantName
     */
    public void setRestaurantName(String restaurantName){
        this.restaurantNameView.setText(restaurantName);
    }

    /**
     * Function to set the address of the restaurant
     * @param restaurantAddress
     */
    public void setRestaurantAddress(String restaurantAddress){
        this.restaurantAddressView.setText(restaurantAddress);
    }

    /**
     * Function to set the status of the restaurant
     * @param isOpen
     * @param restaurantStatus
     */
    public void setRestaurantStatus(boolean isOpen, String restaurantStatus){

        SpannableString spannableString = new SpannableString(restaurantStatus);
        ForegroundColorSpan fcs;
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        if(isOpen)
            fcs = new ForegroundColorSpan(Color.rgb(00, 100, 00));
        else
            fcs = new ForegroundColorSpan(Color.rgb(100, 00, 00));

        int endSize = isOpen ? getString(R.string.open_now).length() : getString(R.string.close_now).length();

        spannableString.setSpan(fcs, 0, endSize, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(bss, 0, endSize, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        restaurantStatusView.setText(spannableString);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {

            StoreIDFragment storeIDFragment = new StoreIDFragment();
            storeIDFragment.setProceedClickListener(restaurantView);
            storeIDFragment.show(getSupportFragmentManager(), RestaurantHomeActivity.class.getName());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to reset the textfield values to empty
     */
    public void resetValues(){
        restaurantNameView.setText("");
        restaurantAddressView.setText("");
        restaurantStatusView.setText("");
    }
}
