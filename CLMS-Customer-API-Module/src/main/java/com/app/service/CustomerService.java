package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Customer;

public interface CustomerService {

	public String saveCustomer(String cData, MultipartFile profileImage);
	
	public Customer getAllCustomerInfo(String un , String pass);

	public Customer getCustomerById(Integer id);
	
	public Customer getCustomerByEnquiryId(Integer id);
	
	public List<Customer> getAllCustomer();
	
	public String updateCustomerInfo(Integer cid, String c , MultipartFile proImage);
}
