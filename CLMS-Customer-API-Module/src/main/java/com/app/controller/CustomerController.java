package com.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Customer;
import com.app.service.CustomerService;



@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService cs;
	
	@Autowired
	private RestTemplate rt;
	
	@PostMapping(value="/savecustomer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> saveCustomer(@RequestPart("cJson") String cData , @RequestPart("profileImage") MultipartFile profileImage){
		
		try {
			
			MultiValueMap<String, Object> customerToEmployee = new LinkedMultiValueMap<>();
			
			ByteArrayResource imageAsResource = new ByteArrayResource(profileImage.getBytes()) {
			    @Override
			    public String getFilename() {
			        return profileImage.getOriginalFilename(); // Needed for multipart encoding
			    }
			};
			
			HttpHeaders imageHeaders = new HttpHeaders();
		    imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		    HttpEntity<ByteArrayResource> imagePart = new HttpEntity<>(imageAsResource, imageHeaders);
		    
			customerToEmployee.add("emp", cData);
		    
		    customerToEmployee.add("photo", imagePart);
			
			
			String url = "http://localhost:9090/admin/admin/saveCustomer" ;
			
			String employee = rt.postForObject(url, customerToEmployee, String.class);
			
			System.out.println(employee);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<String>(cs.saveCustomer(cData, profileImage) , HttpStatus.OK);
	}
	
	@GetMapping("/getcustomer/{un}/{pass}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("un") String un , @PathVariable("pass") String pass){
		Customer allCustomerInfo = cs.getAllCustomerInfo(un, pass);
		return new ResponseEntity<Customer>(allCustomerInfo , HttpStatus.OK);
	}
	
	
	@GetMapping("/getallcustomer")
	public ResponseEntity<List<Customer>> getAllCustomer(){
		
		return new ResponseEntity<List<Customer>>(cs.getAllCustomer() , HttpStatus.OK);
	}
	
	@GetMapping("/getcustomerbyid/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Integer id){
		Customer allCustomerInfo = cs.getCustomerById(id);
		return new ResponseEntity<Customer>(allCustomerInfo , HttpStatus.OK);
	}
	
	@GetMapping("/getcustomerbyenquiryid/{id}")
	public ResponseEntity<Customer> getCustomerByEnquiryId(@PathVariable("id") Integer id){
		Customer allCustomerInfo = cs.getCustomerByEnquiryId(id);
		return new ResponseEntity<Customer>(allCustomerInfo , HttpStatus.OK);
	}
	
	@PatchMapping("/updatecustomerinfo/{cid}")
	public ResponseEntity<String> updateCustomerInfo(@RequestPart("customerData") String c,@RequestPart("profileImage") MultipartFile proImage ,@PathVariable("cid") Integer cid){
		System.out.println(c);
		return new ResponseEntity<String>(cs.updateCustomerInfo(cid , c , proImage),HttpStatus.OK);
	}
	
}
