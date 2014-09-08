package com.maximilianomicciullo.androidcontactlistapp.Models;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.maximilianomicciullo.androidcontactlistapp.CommonUtils.CommonUtilString;
import com.maximilianomicciullo.androidcontactlistapp.ParserComponent.ParserContactData;
import com.maximilianomicciullo.androidcontactlistapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;


public class DetailActivity extends Activity {

    private ParserContactData factory = new ParserContactData();
    private Contact contactClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // getting the info of the clicked contact.
        contactClicked = (Contact) getIntent().getSerializableExtra("contactClicked");
        getJsonRequest(contactClicked);
        setNavigationButton();
    }

    /**
     * Method to get the contacts details
     * @param contact
     */
    private void getJsonRequest(Contact contact) {
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonRequest jsonRequestDetails = new JsonObjectRequest(Request.Method.GET, contact.getDetailsURL(),
                null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                ContactDetails details = factory.objectToDetails(response);
                                populateContactDetailsView(details);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DetailActivity.this, "It was an error getting the contacts details remotely !!!" + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
        rq.add(jsonRequestDetails);
    }


    /**
     * Method to populate and render the contact details in the view.
     *
     * @param details
     */
    public void populateContactDetailsView(ContactDetails details) {
        ((TextView) findViewById(R.id.txtTitleName)).setText("Name");
        ((TextView) findViewById(R.id.txtName)).setText(contactClicked.getName());

        ((TextView) findViewById(R.id.txtTitleCompany)).setText("Company");
        ((TextView) findViewById(R.id.txtCompany)).setText(contactClicked.getCompany());

        ((TextView) findViewById(R.id.txtTitlePhone)).setText("Phone");
        if( CommonUtilString.checkString(contactClicked.getPhone().getHome()) && CommonUtilString.checkString(contactClicked.getPhone().getMobile()) && CommonUtilString.checkString(contactClicked.getPhone().getWork()) ){
            ((TextView) findViewById(R.id.txtPhone)).setText("No Available Phone");
        }else if( !CommonUtilString.checkString(contactClicked.getPhone().getHome()) ){
            ((TextView) findViewById(R.id.txtPhone)).setText(contactClicked.getPhone().getHome());
        }else if( !CommonUtilString.checkString(contactClicked.getPhone().getMobile()) ){
            ((TextView) findViewById(R.id.txtPhone)).setText(contactClicked.getPhone().getMobile());
        }else if( CommonUtilString.checkString(contactClicked.getPhone().getWork()) ){
            ((TextView) findViewById(R.id.txtPhone)).setText(contactClicked.getPhone().getWork());
        }

        ((TextView) findViewById(R.id.txtTitleAddress)).setText("Address");
        ((TextView) findViewById(R.id.txtAddress)).setText(details.getAddress().getStreet());

        ((TextView) findViewById(R.id.txtTitleBirthday)).setText("Birthday");
        ((TextView) findViewById(R.id.txtBirthday)).setText(contactClicked.getBirthdate());

        ((TextView) findViewById(R.id.txtTitleEmail)).setText("Email");
        ((TextView) findViewById(R.id.txtEmail)).setText(details.getEmail());


        ImageView img = (ImageView) findViewById(R.id.contactImg);

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(details.getLargeImageURL(), img);

        //Set Image minimum size
        img.setMinimumWidth(350);
        img.setMinimumHeight(350);

        if( !details.getFavorite() ){
            ((ImageView) findViewById(R.id.starImage)).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Method who's define the behavior of the back button.
     */
    private void setNavigationButton() {
        Button button = (Button) findViewById(R.id.btnBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
