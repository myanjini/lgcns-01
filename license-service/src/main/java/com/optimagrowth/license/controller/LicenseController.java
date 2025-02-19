package com.optimagrowth.license.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;

    @GetMapping(value="/")
    public List<License> getLicense(@PathVariable("organizationId") String organizationId) throws TimeoutException {
    	return licenseService.getLicenseByOrganization(organizationId);
    }    
    
    @GetMapping(value = "/{licenseId}/{clientType}")
    public License getLicenseWithClient(
    		@PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId, 
            @PathVariable("clientType") String clientType) {
    	License license = licenseService.getLicense(licenseId, organizationId, clientType);
    	return license;
    }
        
    
    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        License license = licenseService.getLicense(licenseId, organizationId);
        
        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId()))
                    .withSelfRel(),
                linkTo(methodOn(LicenseController.class).updateLicense(organizationId, license))
                    .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).createLicense(organizationId, license, null))
                    .withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, license.getLicenseId()))
                    .withRel("deleteLicense")
            );
        
        return ResponseEntity.ok(license);
    }
    
    @PutMapping
    public ResponseEntity<License> updateLicense(
            @PathVariable("organizationId") String organizationId, 
            @RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }
    
    @PostMapping
    public ResponseEntity<License> createLicense(
            @PathVariable("organizationId") String organizationId, 
            @RequestBody License request, 
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.createLicense(request));
    }
    
    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId, 
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }    
}
