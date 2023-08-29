package com.properbooker.pdfgenerator.controller;

import com.properbooker.pdfgenerator.model.EstimationDetails;
import com.properbooker.pdfgenerator.security.TokenProvider;
import com.properbooker.pdfgenerator.service.PdfGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.repo.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    private final TokenProvider tokenProvider;
    private final PdfGeneratorService pdfGenerationService;

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody EstimationDetails details, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        token = token.replace("Bearer ", "");

        String username;

        try {
            username = tokenProvider.getUsernameFromToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

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
