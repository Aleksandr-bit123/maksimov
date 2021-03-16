package com.diploma.maksimov.restcontroller;

import com.diploma.maksimov.api.LicensesApi;
import com.diploma.maksimov.dto.LicensePlatform;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicensesApiImpl implements LicensesApi {
    @Override
    public ResponseEntity<LicensePlatform> showLicenseById(Integer licenseId) {
        return null;
    }
}
