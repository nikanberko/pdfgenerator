package com.properbooker.pdfgenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.properbooker.pdfgenerator.model.EstimationDetails;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {
    private final ObjectMapper objectMapper;

    public byte[] generatePdfFromJson(EstimationDetails details) throws Exception {
        JasperReport jasperReport = compileReport();

        String json = objectMapper.writeValueAsString(details);

        JsonDataSource dataSource = new JsonDataSource(new ByteArrayInputStream(json.getBytes()));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", details.getName());
        parameters.put("apartment", details.getApartment());
        parameters.put("idNumber", details.getIdNumber());
        parameters.put("numberOfGuests", details.getNumberOfGuests());
        parameters.put("numberOfStays", details.getNumberOfStays());
        parameters.put("arrivalDate", details.getArrivalDate());
        parameters.put("endDate", details.getEndDate());
        parameters.put("discount", details.getDiscount());
        parameters.put("issuer", details.getIssuer());
        parameters.put("issueDate", details.getIssueDate());
        parameters.put("price", details.getPrice());
        parameters.put("totalPrice", (double) Math.round(details.getPrice()*details.getNumberOfStays()*(1-details.getDiscount()*0.01)*1.25 * 100)/100);
        parameters.put("invoiceNumber", details.getInvoiceNumber());
        parameters.put("telephone", details.getTelephone());
        parameters.put("email", details.getEmail());
        parameters.put("issuerId", details.getIssuerId());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperReport compileReport() throws JRException {
        String jrxmlPath = "src/main/resources/templates/pdfestimation.jrxml";
        return JasperCompileManager.compileReport(jrxmlPath);
    }
}
