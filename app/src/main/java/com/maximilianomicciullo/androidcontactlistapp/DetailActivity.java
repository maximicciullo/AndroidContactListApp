package com.maximilianomicciullo.androidcontactlistapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.maximilianomicciullo.androidcontactlistapp.Enums.EPhones;
import com.maximilianomicciullo.androidcontactlistapp.Factory.ContactFactory;
import com.maximilianomicciullo.androidcontactlistapp.Utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.util.List;


public class DetailActivity extends Activity {

    private ContactFactory factory = new ContactFactory();
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


    private void getJsonRequest(Contact contact) {
        RequestQueue rq = Volley.newRequestQueue(this);
        final JSONObject[] detailJsonObject = new JSONObject[1];
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
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
        rq.add(jsonRequestDetails);

    }

    public void populateContactDetailsView(ContactDetails details) {
        ((TextView) findViewById(R.id.txtTitleName)).setText("Name");
        ((TextView) findViewById(R.id.txtName)).setText(contactClicked.getName());

        ((TextView) findViewById(R.id.txtTitleCompany)).setText("Company");
        ((TextView) findViewById(R.id.txtCompany)).setText(contactClicked.getCompany());

        ((TextView) findViewById(R.id.txtTitlePhone)).setText("Phone");
        if( StringUtils.checkString(contactClicked.getPhone().getHome()) && StringUtils.checkString(contactClicked.getPhone().getMobile()) && StringUtils.checkString(contactClicked.getPhone().getWork()) ){
            ((TextView) findViewById(R.id.txtPhone)).setText("No Available Phone");
        }else if( !StringUtils.checkString(contactClicked.getPhone().getHome()) ){
            ((TextView) findViewById(R.id.txtPhone)).setText(contactClicked.getPhone().getHome());
        }else if( !StringUtils.checkString(contactClicked.getPhone().getMobile()) ){
            ((TextView) findViewById(R.id.txtPhone)).setText(contactClicked.getPhone().getMobile());
        }else if( StringUtils.checkString(contactClicked.getPhone().getWork()) ){
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
