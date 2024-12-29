package com.foundationProject.bookstore.controller.view;

import com.foundationProject.bookstore.constants.ViewControllerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/view/auth")
@Tag(name = "Authentication View", description = "authentication view")
public class AuthenticationViewController {

    @Operation(summary = "Login", description = "Login Screen")
    @GetMapping("/login")
    public String login(Model model) {
        return ViewControllerConstants.CUSTOMER_TEMPLATES_DIR + "/login";
    }

    @Operation(summary = "Register", description = "Register Screen")
    @GetMapping("/register")
    public String register(Model model) {
        return ViewControllerConstants.CUSTOMER_TEMPLATES_DIR + "/register";
    }

}
