package com.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Customer;

public interface CustomerService {

	public String saveCustomer(String cData, MultipartFile profileImage);
	
	public Customer getAllCustomerInfo(String un , String pass);
}
