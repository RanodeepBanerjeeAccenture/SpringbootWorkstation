package com.accenture.usermanagementapp.controller;

import com.accenture.usermanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Get the authenticated user from the SecurityContext
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Extract the role of the user (clean format, e.g., "USER" or "ADMIN")
        String role = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5))  // Remove the "ROLE_" prefix
                .findFirst()
                .orElse("USER");  // Default to "USER" if no role is found

        // Add the user email and role to the model
        model.addAttribute("userEmail", userDetails.getUsername());  // Assuming getUsername() gives email
        model.addAttribute("userRole", role);  // Send the role name without the "ROLE_" prefix

        // Check if the user is an admin
        if (role.equals("ADMIN")) {
            // Admins will see the admin dashboard
            model.addAttribute("users", userService.findAll()); // Get all users
            return "adminDashboard";  // Ensure this view exists in your templates folder
        }

        // Regular users will see the regular dashboard
        return "dashboard";  // Return the regular user dashboard
    }

}
