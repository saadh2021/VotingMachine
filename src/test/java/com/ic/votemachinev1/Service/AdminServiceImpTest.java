package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.DTOs.DateTimeDTO.VotingTimeDTO;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Service.Imp.AdminServiceImp;
import com.ic.votemachinev1.Service.Imp.CommonServiceImp;
import com.ic.votemachinev1.Service.Imp.EmailService;
import com.ic.votemachinev1.Service.VotingTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminServiceImpTest {

    @Mock
    private UsersRepository userRepository;

    @Mock
    private ConstituencyRepository constituencyRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private VotingTimeService votingTimeService;

    @Mock
    private CommonServiceImp commonService;

    @InjectMocks
    private AdminServiceImp adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowDashBoard() {
        Model model = mock(Model.class);

        // Mock behavior for commonServiceImp methods
        when(commonService.votingTimeEntity()).thenReturn(new VotingTimeDTO());
        when(commonService.ShowProfilePic()).thenReturn("profile_picture");


        // Call the method to be tested
        ModelAndView result = adminService.ShowDashBoard(model);

        // Assertions
        assertEquals("Admin/AdminDashboard", result.getViewName());
        verify(model).addAttribute(eq("votingTimeDTO"), any(VotingTimeDTO.class));
        verify(model).addAttribute(eq("profilePicture"), eq("profile_picture"));
    }

    // Add more test cases for other methods as needed

}

