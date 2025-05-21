package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Customer;
import com.app.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService cs;
	
	@RequestMapping("/savecustomer")
	public ResponseEntity<String> saveCustomer(@RequestPart("cJson") String cData , @RequestPart("profileImage") MultipartFile profileImage){
		
		return new ResponseEntity<String>(cs.saveCustomer(cData, profileImage) , HttpStatus.OK);
	}
	
	@RequestMapping("/getcustomer/{un}/{pass}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("un") String un , @PathVariable("pass") String pass){
		Customer allCustomerInfo = cs.getAllCustomerInfo(un, pass);
		return new ResponseEntity<Customer>(allCustomerInfo , HttpStatus.OK);
	}
	
}
