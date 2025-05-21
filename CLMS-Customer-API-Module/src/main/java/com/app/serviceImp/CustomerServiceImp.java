package com.app.serviceImp;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Customer;
import com.app.repository.CustomerRepository;
import com.app.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class CustomerServiceImp implements CustomerService {

	@Autowired
	private CustomerRepository cr;

	@Override
	public String saveCustomer(String cData, MultipartFile profileImage) {

		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		
		try {
			Customer value = om.readValue(cData, Customer.class);
				value.setProfilePicture(profileImage.getBytes());
				cr.save(value);
				return "Customer Data Save Successfully...!";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				 
		
		return null;
	}

	@Override
	public Customer getAllCustomerInfo(String un, String pass) {
		
		return cr.findByUserNameAndPassword(un , pass);
	}
	
	
	
}
