package com.example.bite_speed.Controller;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
            response.setSecondaryContactIds(null);
            responseDao.save(response);

            return response;
        }

        return null;
    }
}
