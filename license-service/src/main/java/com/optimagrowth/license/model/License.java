package com.optimagrowth.license.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "licenses")
public class License extends RepresentationModel<License> {
    // private int id;
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    
    private String description;
    
    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    
    @Column(name = "comment")
    private String comment;
    
    @Transient
    private String organizationName;
    
    @Transient
    private String contactName;
    
    @Transient
    private String contactEmail;
    
    @Transient
    private String contactPhone;    
    
    public License withComment(String comment) {
        this.setComment(comment);
        return this;
    }    
}
