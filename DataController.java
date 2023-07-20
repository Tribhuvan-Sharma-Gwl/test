package com.example.Rupicard;

import com.google.cloud.spring.core.GcpProjectIdProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.temporal.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class DataController {
//    @GetMapping("/")
//    public String showDataEntryForm() {
//        return "data_entry";
//    }
    private final GcpProjectIdProvider projectIdProvider;

    @Autowired
    public DataController(GcpProjectIdProvider projectIdProvider) {
        this.projectIdProvider = projectIdProvider;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @PostMapping("/submit")
    public String submitData(@RequestParam String name, @RequestParam String mobile, Model model)
            throws IOException, GeneralSecurityException {
        writeToGoogleSheets(name, mobile);
        model.addAttribute("message", "Data submitted successfully!");
        return "index";
    }

    private void writeToGoogleSheets(String name, String mobile)
            throws IOException, GeneralSecurityException {
        Sheets sheetsService = GoogleSheetsUtil.getSheetsService(projectIdProvider);
        String spreadsheetId = "YOUR_SPREADSHEET_ID"; // Replace with your Google Sheets ID
        String range = "Sheet1!A2:B2";
        List<List<Object>> values = Collections.singletonList(Arrays.asList(name, mobile));
        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values()
                .append(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .execute();
    }

}
