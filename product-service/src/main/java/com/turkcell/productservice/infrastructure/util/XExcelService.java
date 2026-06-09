package com.turkcell.productservice.infrastructure.util;

import com.turkcell.productservice.domain.service.ExcelService;
import org.springframework.stereotype.Service;

@Service
public class XExcelService implements ExcelService {
    @Override
    public String exportAsExcel(String file) {
        return "";
    }
}
