package com.nishantwrp.institutemanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ToastService {
    public void displayErrorToast(Model model, String message) {
        model.addAttribute("errorToast", message);
    }

    public void displaySuccessToast(Model model, String message) {
        model.addAttribute("successToast", message);
    }

    public void redirectWithErrorToast(RedirectAttributes attributes, String message) {
        attributes.addFlashAttribute("errorToast", message);
    }

    public void redirectWithSuccessToast(RedirectAttributes attributes, String message) {
        attributes.addFlashAttribute("successToast", message);
    }
}
