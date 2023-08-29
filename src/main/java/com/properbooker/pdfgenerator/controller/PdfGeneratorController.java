package com.properbooker.pdfgenerator.controller;

import com.properbooker.pdfgenerator.model.EstimationDetails;
import com.properbooker.pdfgenerator.service.PdfGeneratorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.repo.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@RestController
@RequestMapping("/pdfgenerator")
@RequiredArgsConstructor
@Data
public class PdfGeneratorController {

    private final PdfGeneratorService pdfGenerationService;

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody EstimationDetails details) {
        try {
            byte[] pdfBytes = pdfGenerationService.generatePdfFromJson(details);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=estimation.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
