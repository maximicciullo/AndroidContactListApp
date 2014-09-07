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
import com.maximilianomicciullo.androidcontactlistapp.Factory.ContactFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ContactListActivity extends Activity {

    private List<Contact> myContacts = new ArrayList<Contact>();
    private List<Contact> result = new ArrayList<Contact>();
    private ContactFactory factory = new ContactFactory();

    String url = "http://cblunt.github.io/blog-android-volley/response.json";
    String urlEndpointContact = "https://solstice.applauncher.com/external/contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        populateContactList();
        populateListView();
        registerClickCallBack();

        RequestQueue rq = Volley.newRequestQueue(this);

        JsonRequest jsonRequest = new JsonArrayRequest(urlEndpointContact,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;
                            //Iterate all the contacts
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Contact contact;
                                ContactDetails details;

                                //Create the contact
                                contact = factory.objectToContact(jsonArray.getJSONObject(i));


                                //Get the details
//                                details = completeContactDetails(contact.getDetailsURL());
//                                String detailsResult = contact.getDetailsURL();
//
//                                details = factory.objectToDetails(new JSONObject(detailsResult));

//                                contact.setDetails(details);

                                result.add(contact);
                            }

                            Toast.makeText(ContactListActivity.this, "Contact List: " + result.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(ContactListActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactListActivity.this, "HUBO UN ERROR !!!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            );
        rq.add(jsonRequest);
    }


    private void populateContactList() {
        myContacts.add(new Contact("Max", "1533901999", R.drawable.person));
        myContacts.add(new Contact("Macarena", "1588114212", R.drawable.person));
        myContacts.add(new Contact("Gloria", "1578134311", R.drawable.person));
        myContacts.add(new Contact("Marta", "1578789819", R.drawable.person));
        myContacts.add(new Contact("Nico", "1541417181", R.drawable.person));
        myContacts.add(new Contact("Claudia", "1567128191", R.drawable.person));
        myContacts.add(new Contact("Coco", "1590100001", R.drawable.person));
        myContacts.add(new Contact("Vir", "1567178881", R.drawable.person));
        myContacts.add(new Contact("Fedra", "1567177776", R.drawable.person));
        myContacts.add(new Contact("Pablo", "1541417181", R.drawable.person));
        myContacts.add(new Contact("Tinto", "1567128191", R.drawable.person));
        myContacts.add(new Contact("Tita", "1590100001", R.drawable.person));
        myContacts.add(new Contact("Juancho", "1567178881", R.drawable.person));
        myContacts.add(new Contact("Lucho", "1567177776", R.drawable.person));
    }

    private void populateListView() {
        ArrayAdapter<Contact> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.contactsListView);
        list.setAdapter(adapter);
    }

    private class  MyListAdapter extends ArrayAdapter<Contact> {
        public MyListAdapter() {
            super(ContactListActivity.this, R.layout.item_view, myContacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }

            // Find the contact to work with
            Contact currentContact = myContacts.get(position);



            // Fill the view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentContact.getIconID());

            // Contact Name
            TextView contactNameText = (TextView) itemView.findViewById(R.id.item_txtContactName);
            contactNameText.setText(currentContact.getName());

            // Phone
            TextView phoneText = (TextView) itemView.findViewById(R.id.item_txtPhone);
            phoneText.setText(currentContact.getPhoneTest());

            return itemView;
        }
    }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.contactsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Contact clickedContact = myContacts.get(position);
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