package com.optimagrowth.license.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.config.ServiceConf;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.service.client.OrganizationDiscoveryClient;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import com.optimagrowth.license.service.client.OrganizationRestTemplateClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LicenseService {
    @Autowired
    MessageSource messages;
    
    @Autowired
    LicenseRepository licenseRepository;
    
    @Autowired
    ServiceConf config;
    
    
    private void randomlyRunLong() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3)
            sleep();
    }

    private void sleep() throws TimeoutException {
        try {
            log.info("sleep() is called");
            Thread.sleep(5000);
            throw new java.util.concurrent.TimeoutException();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private int count = 0;
    
    @CircuitBreaker(name = "licenseService", fallbackMethod = "getLicenseByOrganizationFallback")
    public List<License> getLicenseByOrganization(String organizationId) throws TimeoutException {
    	// 서킷 브레이크의 동작을 쉽게 확인할 수 있도록 수정
    	
    	// randomlyRunLong();
    	
    	count ++;
    	log.info(">>> getLicenseByOrganization() is called ... " + count);
    	try {
    		Thread.sleep(3000);
    		throw new java.util.concurrent.TimeoutException();
    	} catch (InterruptedException e) {}
    	
    	log.info(">>> findByOrganizationId() call ... " + count);   		// 호출되지 않음 	
    	return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> getLicenseByOrganizationFallback(String organizationId, Throwable t) {
    	log.error(">>> " + t.getMessage());
    	
    	List<License> fallbackList = new ArrayList<>();
    	License license = new License();
    	license.setLicenseId("0000-0000-0000");
    	license.setOrganizationId(organizationId);
    	license.setProductName("Sorry no licensing information currently available");
    	fallbackList.add(license);
    	return fallbackList;
    }
    
    
    public License getLicense(String licenseId, String organizationId) {
    	License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license == null) {
            throw new IllegalArgumentException(
                String.format(
                    messages.getMessage("license.search.error.message", null, null), 
                    licenseId, organizationId));
            
        }        
        return license.withComment(config.getProperty());
    }
    
    public License getLicense(String licenseId, String organizationId, String clientType) {
    	License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license == null) {
            throw new IllegalArgumentException(
                String.format(
                    messages.getMessage("license.search.error.message", null, null), 
                    licenseId, organizationId));
            
        }        
        
        // 조직 서비스에서 조직 정보를 조회
        Organization organization = retriveOganizationInfo(organizationId, clientType);
        if (organization != null) {
        	license.setOrganizationName(organization.getName());
        	license.setContactName(organization.getContactName());
        	license.setContactEmail(organization.getContactEmail());
        	license.setContactPhone(organization.getContactPhone());
        }
        
        // 조직 정보가 포함된 라이센스 정보를 반환
        return license.withComment(config.getProperty());
    }
    
    
    @Autowired
    OrganizationFeignClient organizationFeignClient;
    
    @Autowired
    OrganizationRestTemplateClient organizationRestTemplateClient;
    
    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;
    
    // clientType에 따라 지정된 HTTP 클라이언트 라이브러리를 이용해서 조직 정보를 조회 
    private Organization retriveOganizationInfo(String organizationId, String clientType) {
    	Organization organization = null;
    	
    	switch(clientType) {
    	case "feign":
    		log.info(">>> Feign을 이용한 조직 정보 조회");
    		organization = organizationFeignClient.getOrganization(organizationId);
    		break;
    	case "rest":
    		log.info(">>> RestTemplate을 이용한 조직 정보 조회");
    		organization = organizationRestTemplateClient.getOrganization(organizationId);
    		break;
    	case "discovery":
    		log.info(">>> DiscoveryClient를 이용한 조직 정보 조회");
    		organization = organizationDiscoveryClient.getOrganization(organizationId);
    		break;
    		
    	}
    	
    	return organization;
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }
    
    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }
    
    public String deleteLicense(String licenseId) {
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        
        String responseMessage = null;
        responseMessage 
            = String.format(messages.getMessage("license.delete.message", null, null), 
                licenseId);
        return responseMessage;
    }
}
