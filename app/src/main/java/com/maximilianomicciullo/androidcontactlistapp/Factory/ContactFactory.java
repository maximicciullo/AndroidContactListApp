package com.maximilianomicciullo.androidcontactlistapp.Factory;

import com.maximilianomicciullo.androidcontactlistapp.Contact;
import com.maximilianomicciullo.androidcontactlistapp.ContactAddress;
import com.maximilianomicciullo.androidcontactlistapp.ContactDetails;
import com.maximilianomicciullo.androidcontactlistapp.Phone;

import org.json.JSONObject;

/**
 * Created by maximilianomicciullo on 07/09/14.
 */
public class ContactFactory {
        public Contact objectToContact(JSONObject obj) throws Exception{
            Contact contact = new Contact();
            Phone phone = new Phone();

            if( !obj.isNull("name") ){
                contact.setName(obj.getString("name"));
            }

            if( !obj.isNull("employeeId") ) {
                contact.setEmployeeId(obj.getLong("employeeId"));
            }

            if( !obj.isNull("company") ) {
                contact.setCompany(obj.getString("company"));
            }

            if( !obj.isNull("detailsURL") ) {
                contact.setDetailsURL(obj.getString("detailsURL"));
            }

            if( !obj.isNull("smallImageURL") ) {
                contact.setSmallImageURL(obj.getString("smallImageURL"));
            }

            if( !obj.isNull("birthdate") ) {
                contact.setBirthdate(obj.getString("birthdate"));
            }

            if( !obj.isNull("phone") ) {
                JSONObject phoneObj = obj.getJSONObject("phone");

                if( !phoneObj.isNull("work") ) {
                    phone.setWork(phoneObj.getString("work"));
                }

                if( !phoneObj.isNull("home") ) {
                    phone.setHome(phoneObj.getString("home"));
                }

                if( !phoneObj.isNull("mobile") ) {
                    phone.setMobile(phoneObj.getString("mobile"));
                }
            }

            contact.setPhone(phone);

            return contact;
        }

        public ContactDetails objectToDetails(JSONObject obj) throws Exception{
            ContactDetails details = new ContactDetails();
            ContactAddress address = new ContactAddress();

            if( !obj.isNull("employeeId") ){
                details.setEmployeeId(obj.getLong("employeeId"));
            }

            if( !obj.isNull("favorite") ){
                details.setFavorite(obj.getBoolean("favorite"));
            }

            if( !obj.isNull("largeImageURL") ){
                details.setLargeImageURL(obj.getString("largeImageURL"));
            }

            if( !obj.isNull("email") ){
                details.setEmail(obj.getString("email"));
            }

            if( !obj.isNull("website") ){
                details.setWebsite(obj.getString("website"));
            }

            if( !obj.isNull("address") ){
                JSONObject addObj = obj.getJSONObject("address");

                if( !addObj.isNull("street") ){
                    address.setStreet(addObj.getString("street"));
                }

                if( !addObj.isNull("city") ){
                    address.setCity(addObj.getString("city"));
                }

                if( !addObj.isNull("state") ){
                    address.setState(addObj.getString("state"));
                }

                if( !addObj.isNull("country") ){
                    address.setCountry(addObj.getString("country"));
                }

                if( !addObj.isNull("zip") ){
                    address.setZip(addObj.getLong("zip"));
                }
                if( !addObj.isNull("latitude") ){
                    address.setLatitude(addObj.getString("latitude"));
                }
                if( !addObj.isNull("longitude") ){
                    address.setLongitude(addObj.getString("longitude"));
                }
            }

            details.setAddress(address);

            return details;
        }

}
