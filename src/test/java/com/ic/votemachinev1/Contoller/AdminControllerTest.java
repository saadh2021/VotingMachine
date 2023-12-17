package com.ic.votemachinev1.Contoller;

import com.ic.votemachinev1.Controller.AdminController;
import com.ic.votemachinev1.DTOs.Admin.ConstituencyDTO;
import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.Service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowLoginPage() {
        assertEquals("login", adminController.showLoginPage());
    }

    @Test
    void testApprove() {
        Model model = mock(Model.class);
        Long cnic = 123L;

        doNothing().when(adminService).ApproveCandidate(any(Model.class), eq(cnic));

        String result = adminController.Approve(model, cnic);

        assertEquals("Admin/ApprovedCandidateSuccessForAdmin", result);
        verify(adminService, times(1)).ApproveCandidate(model, cnic);
    }

    // Add more test cases for other methods as needed

}
