package com.nishantwrp.institutemanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ToastService {
    public void displayErrorToast(Model model, String message) {
        model.addAttribute("errorToast", message);
    }

    public void displaySuccessToast(Model model, String message) {
        model.addAttribute("successToast", message);
    }
}
