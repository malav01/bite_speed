package com.example.bite_speed.Controller;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bite_speed.dao.ContactDao;
import com.example.bite_speed.dao.EmailDao;
import com.example.bite_speed.dao.PhoneDao;
import com.example.bite_speed.dao.ResponseDao;
import com.example.bite_speed.dto.Contact;
import com.example.bite_speed.dto.Email;
import com.example.bite_speed.dto.Phone;
import com.example.bite_speed.dto.Request;
import com.example.bite_speed.dto.Response;

@RestController
public class handleControl {
    @Autowired
    public ContactDao contactDao;

    @Autowired
    public PhoneDao phoneDao;

    @Autowired
    public EmailDao emailDao;

    @Autowired
    public ResponseDao responseDao;

    @PostMapping(path = "/identify")
    public Response handlRequest(@RequestBody Request input){
        String phoneNumber = input.getPhoneNumber();
        String email = input.getEmail();

        // case - 1 : new user        
        if(!phoneDao.existsById(phoneNumber) && !emailDao.existsById(email)){
            Contact contact = new Contact();
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(email);
            contact.setLinkedId(null);
            contact.setLinkPrecedence("primary");
            contact.setCreatedAt(ZonedDateTime.now());
            contact.setDeletedAt(null);
            contact.setUpdatedAt(ZonedDateTime.now());
            contactDao.save(contact);

            Email emailObj = new Email();
            emailObj.setContactid(contact);
            emailObj.setEmail(email);
            emailDao.save(emailObj);

            Phone phone = new Phone();
            phone.setContactid(contact);
            phone.setPhoneNumber(phoneNumber);
            phoneDao.save(phone);

            Response response = new Response();
            List<String> emails = new ArrayList<>();
            emails.add(email); 
            response.setEmails(emails);
            List<String> phones = new ArrayList<>();
            phones.add(phoneNumber); 
            response.setPhoneNumbers(phones);
            response.setPrimaryContatctId(contact.getId());
            responseDao.save(response);

            return response;
        }

        // case - 2 : email present
        else if(!phoneDao.existsById(phoneNumber) && emailDao.existsById(email)){
            Optional<Email> emailObj = emailDao.findById(email);
            Email email2 = emailObj.get();
            Contact id = email2.getContactid();
            Phone phone = new Phone();
            phone.setContactid(id);
            phone.setPhoneNumber(phoneNumber);
            phoneDao.save(phone);

            Contact contact = new Contact();
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(email);
            contact.setLinkedId(id.getId());
            contact.setLinkPrecedence("secondary");
            contact.setCreatedAt(ZonedDateTime.now());
            contact.setDeletedAt(null);
            contact.setUpdatedAt(ZonedDateTime.now());
            contact = contactDao.save(contact);

            Optional<Response> responseObj = responseDao.findById(id.getId());
            Response response = responseObj.get();
            response.getPhoneNumbers().add(phoneNumber);
            response.getSecondaryContactIds().add(contact.getId());
            responseDao.save(response);

            return response;
        }

        // case - 3 : phone no present
        else if(phoneDao.existsById(phoneNumber) && !emailDao.existsById(email)){
            Optional<Phone> phoneOptional = phoneDao.findById(phoneNumber);
            Phone phone = phoneOptional.get();
            Contact id = phone.getContactid();
            Email email2 = new Email();
            email2.setContactid(id);
            email2.setEmail(email);
            emailDao.save(email2);

            Contact contact = new Contact();
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(email);
            contact.setLinkedId(id.getId());
            contact.setLinkPrecedence("secondary");
            contact.setCreatedAt(ZonedDateTime.now());
            contact.setDeletedAt(null);
            contact.setUpdatedAt(ZonedDateTime.now());
            contact = contactDao.save(contact);

            Optional<Response> responseObj = responseDao.findById(id.getId());
            Response response = responseObj.get();
            response.getEmails().add(email);
            response.getSecondaryContactIds().add(contact.getId());
            responseDao.save(response);

            return response;
        }

        // case - 4 : one of them is null

        // case - 5 : both present
        else{
            Optional<Email> emailOptional = emailDao.findById(email);
            Email emailObj = emailOptional.get();
            Contact id1 = emailObj.getContactid();

            Optional<Phone> phoneOptional = phoneDao.findById(phoneNumber);
            Phone phone = phoneOptional.get();
            Contact id2 = phone.getContactid();

            if(id1.getId()>id2.getId()){
                emailOptional = emailDao.findById(id1.getEmail());
                Email email2 = emailOptional.get();
                email2.setContactid(id2);
                emailDao.save(email2);

                phoneOptional = phoneDao.findById(id1.getPhoneNumber());
                phone = phoneOptional.get();
                phone.setContactid(id2);
                phoneDao.save(phone);
                
                Optional<Response> responseObj = responseDao.findById(id1.getId());
                Response response1 = responseObj.get();

                responseObj = responseDao.findById(id2.getId());
                Response response2 = responseObj.get();

                response2.getEmails().addAll(response1.getEmails());
                response2.getPhoneNumbers().addAll(response1.getPhoneNumbers());
                response2.getSecondaryContactIds().add(response1.getPrimaryContatctId());
                response2.getSecondaryContactIds().addAll(response1.getSecondaryContactIds());
                responseDao.save(response2);
                responseDao.delete(response1);

                return response2;
            }

            else{
                emailOptional = emailDao.findById(id2.getEmail());
                Email email2 = emailOptional.get();
                email2.setContactid(id1);
                emailDao.save(email2);

                phoneOptional = phoneDao.findById(id2.getPhoneNumber());
                phone = phoneOptional.get();
                phone.setContactid(id1);
                phoneDao.save(phone);

                Optional<Response> responseObj = responseDao.findById(id2.getId());
                Response response1 = responseObj.get();

                responseObj = responseDao.findById(id1.getId());
                Response response2 = responseObj.get();

                response2.getEmails().addAll(response1.getEmails());
                response2.getPhoneNumbers().addAll(response1.getPhoneNumbers());
                response2.getSecondaryContactIds().add(response1.getPrimaryContatctId());
                response2.getSecondaryContactIds().addAll(response1.getSecondaryContactIds());
                responseDao.save(response2);
                responseDao.delete(response1);

                return response2;
            }
        }
    }
}
