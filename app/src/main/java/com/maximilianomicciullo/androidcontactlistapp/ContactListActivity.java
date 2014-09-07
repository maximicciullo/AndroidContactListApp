package com.maximilianomicciullo.androidcontactlistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.maximilianomicciullo.androidcontactlistapp.Enums.EPhones;
import com.maximilianomicciullo.androidcontactlistapp.Factory.ContactFactory;
import com.maximilianomicciullo.androidcontactlistapp.Utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ContactListActivity extends Activity {

    private List<Contact> result = new ArrayList<Contact>();
    private ContactFactory factory = new ContactFactory();

    String urlEndpointContact = "https://solstice.applauncher.com/external/contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        RequestQueue rq = Volley.newRequestQueue(this);

        JsonRequest jsonRequest = new JsonArrayRequest(urlEndpointContact,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Iterating all the contacts
                            for (int i = 0; i < response.length(); i++) {
                                Contact contact;
                                ContactDetails details;

                                //Create the contact
                                contact = factory.objectToContact(response.getJSONObject(i));


                                //Get the details
//                                details = completeContactDetails(contact.getDetailsURL());
//                                String detailsResult = contact.getDetailsURL();
//
//                                details = factory.objectToDetails(new JSONObject(detailsResult));

//                                contact.setDetails(details);

                                result.add(contact);
                            }
                            populateListView(result);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactListActivity.this, "It was an error getting the contacts remotely !!!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            );
        rq.add(jsonRequest);

        registerClickCallBack();
    }

    private void populateListView(List<Contact> result) {
        System.out.println("LISTA DE RESULT EN POPULATE LIST VIEW: " + result);
        ArrayAdapter<Contact> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.contactsListView);
        list.setAdapter(adapter);
    }

    private class  MyListAdapter extends ArrayAdapter<Contact> {
        public MyListAdapter() {
            super(ContactListActivity.this, R.layout.item_view, result);
            System.out.println("LISTA DE RESULT: " + result);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }

            // Find the contact to work with
            Contact currentContact = result.get(position);

            // Fill the view
            ImageView img = (ImageView) itemView.findViewById(R.id.item_icon);
            ImageLoader imgLoader = ImageLoader.getInstance();
            imgLoader.init(ImageLoaderConfiguration.createDefault(ContactListActivity.this));
            imgLoader.displayImage(currentContact.getSmallImageURL(), img);

            //Set Image minimum size
            img.setMinimumWidth(100);
            img.setMinimumHeight(100);


            // Contact Name
            TextView contactNameText = (TextView) itemView.findViewById(R.id.item_txtContactName);
            contactNameText.setText(currentContact.getName());

            // Phone
            TextView phoneText = (TextView) itemView.findViewById(R.id.item_txtPhone);

            if( StringUtils.checkString(currentContact.getPhone().getHome()) && StringUtils.checkString(currentContact.getPhone().getMobile()) && StringUtils.checkString(currentContact.getPhone().getWork()) ){
                phoneText.setText("-- No Available Phone --");
            }else if( !StringUtils.checkString(currentContact.getPhone().getHome()) ){
                phoneText.setText(currentContact.getPhone().getHome() + "("+ EPhones.Home.toString()+")");
            }else if( !StringUtils.checkString(currentContact.getPhone().getMobile()) ){
                phoneText.setText(currentContact.getPhone().getMobile() + "("+ EPhones.Mobile.toString()+")");
            }else if( StringUtils.checkString(currentContact.getPhone().getWork()) ){
                phoneText.setText(currentContact.getPhone().getWork() + "("+EPhones.Work.toString()+")");
            }

            return itemView;
        }
    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.contactsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Contact clickedContact = result.get(position);
                String message = "You clicked position " + position
                        + " Which is contact name " + clickedContact.getName();
                Toast.makeText(ContactListActivity.this, message, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ContactListActivity.this, DetailActivity.class);

                // Sending clickedContact to the DetailActivity to show the info.
                intent.putExtra("contactClicked", clickedContact);
                startActivity(intent);
//                startActivity(new Intent(ContactListActivity.this, DetailActivity.class));
            }
        });
    }
}