package com.app.serviceImp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Customer;
import com.app.enums.CustomerEmailStatus;
import com.app.repository.CustomerRepository;
import com.app.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class CustomerServiceImp implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImp.class);

	@Override
	public String saveCustomer(String cData, MultipartFile profileImage) {

		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		
		try {
			Customer value = om.readValue(cData, Customer.class);
				value.setProfilePicture(profileImage.getBytes());
				value.setEmailStatus(CustomerEmailStatus.NOT_VERIFIED);
				customerRepository.save(value);
				return "Customer Data Save Successfully...!";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error("Customer is not save because something went wrong : "+e.getMessage());
		} catch (IOException e) {
			log.error("Customer is not save because something went wrong : "+e.getMessage());
			e.printStackTrace();
		}
				
		return null;
	}

	
	@Override
	public Customer getAllCustomerInfo(String un, String pass) {
		
		log.info(" Customer Login Successfully...!");
		return customerRepository.findByUserNameAndPassword(un , pass);
	}

	@Override
	public Customer getCustomerById(Integer id) {
		
		Optional<Customer> byId = customerRepository.findById(id);
		return byId.get();
	}

	@Override
	public Customer getCustomerByEnquiryId(Integer id) {
		
		return customerRepository.findByLeEnquiryId(id);
	}
	
	@Override
	public String updateCustomerInfo(Integer cid, String c , MultipartFile proImage) {
		Optional<Customer> findById = customerRepository.findById(cid);
		if(findById.isPresent()) {
			Customer customer = findById.get();
			
			try {
				ObjectMapper om = new ObjectMapper();
				om.registerModule(new JavaTimeModule());
				Customer readValue = om.readValue(c,Customer.class);
				
				customer.setProfilePicture(proImage.getBytes());
				customer.setDateOfBirth(readValue.getDateOfBirth());
				customer.setAge(readValue.getAge());
				customer.setGender(readValue.getGender());
				customer.setState(readValue.getState());
				customer.setCustomerContactNumber(readValue.getCustomerContactNumber());
				customer.setCustomerAlternateNumber(readValue.getCustomerAlternateNumber());
				customer.setCustomerPermanentAddress(readValue.getCustomerPermanentAddress());
				customer.setCustomerCity(readValue.getCustomerCity());
				customer.setCustomerPincode(readValue.getCustomerPincode());
				customer.setAadharNo(readValue.getAadharNo());
				customer.setPanCardNo(readValue.getPanCardNo());
				
				customerRepository.save(customer);
				log.info("Customer Details has been Updated Successfully...! : "+customer.getCustomerId());
				return "Customer Details Update Successfully...!";
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				log.error("Customer Details is not Updated Something Went Wrong "+e.getMessage());
			} catch ( IOException e) {
				e.printStackTrace();
				log.error("Customer Details is not Updated Something Went Wrong "+e.getMessage());
			}
			
			
		}
		return null;
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}
	
}
