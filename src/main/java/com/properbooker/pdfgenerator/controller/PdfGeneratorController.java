package com.properbooker.pdfgenerator.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pdfgenerator")
@RequiredArgsConstructor
@Data
public class PdfGeneratorController {




    @PostMapping("/generateestimation")
    @ApiOperation(value = "${ApartmentController.addApartment}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied")})
    public ResponseEntity<ResponseMessage> generateEstimation(
            @ApiParam("Generate PDF estimation") @RequestBody @Valid EstimationDetails estimationDetails, HttpServletRequest request
    ) throws RuntimeException {
        return;
    }
}
