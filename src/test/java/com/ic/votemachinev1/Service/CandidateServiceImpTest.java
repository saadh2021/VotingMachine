package com.ic.votemachinev1.Service;

import com.ic.votemachinev1.Repository.VotingTimeRepository;;
import com.ic.votemachinev1.Service.Imp.CommonServiceImp;
import com.ic.votemachinev1.Utils.SessionData;
import com.ic.votemachinev1.Service.Imp.CandidateServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CandidateServiceImpTest {

    @Mock
    private CommonServiceImp commonService;

    @Mock
    private SessionData sessionData;

    @Mock
    private VotingTimeRepository votingTimeRepository;

    @InjectMocks
    private CandidateServiceImp candidateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowDashBoard() {
        Model model = mock(Model.class);

        // Mock behavior for commonServiceImp methods
        when(commonService.ShowProfilePic()).thenReturn("profile_picture");

        // Mock behavior for sessionData method
        when(sessionData.getUsersEntity()).thenReturn(null);

        // Call the method to be tested
        ModelAndView result = candidateService.ShowDashBoard(model);

        // Assertions
        assertEquals("Candidate/CandidateDashboard", result.getViewName());
        verify(model).addAttribute(eq("profilePicture"), eq("profile_picture"));
    }

    // Add more test cases for other methods as needed

}
