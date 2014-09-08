package com.maximilianomicciullo.androidcontactlistapp.Models;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.maximilianomicciullo.androidcontactlistapp.CommonUtils.CommonUtilString;
import com.maximilianomicciullo.androidcontactlistapp.Enums.EPhones;
import com.maximilianomicciullo.androidcontactlistapp.ParserComponent.ParserContactData;
import com.maximilianomicciullo.androidcontactlistapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class ContactListActivity extends Activity {

    private List<Contact> resultContacts = new ArrayList<Contact>();
    private ParserContactData factory = new ParserContactData();

    private final String urlEndpointContact = "https://solstice.applauncher.com/external/contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        RequestQueue rq = Volley.newRequestQueue(this);

        JsonRequest jsonRequest = getJsonRequest();
        rq.add(jsonRequest);

        registerClickCallBack();
    }

    /**
     * Method who aim to obtain the necessary data hitting the endpoint.
     *
     * @return
     */
    private JsonRequest getJsonRequest() {
        return new JsonArrayRequest(urlEndpointContact,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                //Iterating all the contacts
                                for (int i = 0; i < response.length(); i++) {
                                    //Create the contact
                                    Contact contact = factory.objectToContact(response.getJSONObject(i));
                                    resultContacts.add(contact);
                                }
                                populateListView(resultContacts);
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
    }

    /**
     * Method to populate and render the contact data in the view.
     *
     * @param result
     */
    private void populateListView(List<Contact> result) {
        ArrayAdapter<Contact> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.contactsListView);
        list.setAdapter(adapter);
    }

    /**
     * MyListAdapter - It's the necessary adapter to show de contact list.
     */
    private class  MyListAdapter extends ArrayAdapter<Contact> {
        public MyListAdapter() {
            super(ContactListActivity.this, R.layout.item_view, resultContacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }

            // Find the contact to work with
            Contact currentContact = resultContacts.get(position);

            // Fill the view
            ImageView img = (ImageView) itemView.findViewById(R.id.item_icon);
            ImageLoader imgLoader = ImageLoader.getInstance();
            imgLoader.init(ImageLoaderConfiguration.createDefault(ContactListActivity.this));
            imgLoader.displayImage(currentContact.getSmallImageURL(), img);

            //Set Image minimum size
            img.setMinimumWidth(170);
            img.setMinimumHeight(170);


            // Contact Name
            TextView contactNameText = (TextView) itemView.findViewById(R.id.item_txtContactName);
            contactNameText.setText(currentContact.getName());

            // Phone
            TextView phoneText = (TextView) itemView.findViewById(R.id.item_txtPhone);

            if( CommonUtilString.checkString(currentContact.getPhone().getHome()) && CommonUtilString.checkString(currentContact.getPhone().getMobile()) && CommonUtilString.checkString(currentContact.getPhone().getWork()) ){
                phoneText.setText("-- Phone Not Available --");
            }else if( !CommonUtilString.checkString(currentContact.getPhone().getHome()) ){
                phoneText.setText(EPhones.Home.toString()+": " + currentContact.getPhone().getHome());
            }else if( !CommonUtilString.checkString(currentContact.getPhone().getMobile()) ){
                phoneText.setText(EPhones.Mobile.toString()+": " + currentContact.getPhone().getMobile());
            }else if( CommonUtilString.checkString(currentContact.getPhone().getWork()) ){
                phoneText.setText(EPhones.Work.toString()+": " + currentContact.getPhone().getWork());
            }

            return itemView;
        }
    }

    /**
     * Method to the behavior when an item is clicked.
     */
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.contactsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Contact clickedContact = resultContacts.get(position);
                Intent intent = new Intent(ContactListActivity.this, DetailActivity.class);

                // Sending clickedContact to the DetailActivity to show the info.
                intent.putExtra("contactClicked", clickedContact);
                startActivity(intent);
            }
        });
    }
}