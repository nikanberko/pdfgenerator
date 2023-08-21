package com.properbooker.pdfgenerator.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EstimationDetails {
    private String name;
    private String apartment;
    private String idNumber;
    private Integer numberOfGuests;
    private Integer numberOfStays;
    private String arrivalDate;
    private String endDate;
    private Double discount;  // Changed type from BigDecimal to Double
    private String issuer;
    private String issueDate;
    private Double price;
    private String invoiceNumber; // Changed type from Integer to String
    private String telephone;
    private String email;
    private String issuerId;
}
