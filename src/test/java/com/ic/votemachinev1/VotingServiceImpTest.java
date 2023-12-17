package com.ic.votemachinev1;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.Repository.ConstituencyRepository;
import com.ic.votemachinev1.Repository.PartiesRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.CommonService;
import com.ic.votemachinev1.Service.Imp.EmailService;
import com.ic.votemachinev1.Service.Imp.VoterServiceImp;
import com.ic.votemachinev1.Utils.SessionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VoterServiceImpTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private PartiesRepository partiesRepository;

    @Mock
    private ConstituencyRepository constituencyRepository;

    @Mock
    private SessionData sessionData;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private VotingTimeRepository votingTimeRepository;

    @Mock
    private CommonService commonService;

    @InjectMocks
    private VoterServiceImp voterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllApprovedCandidates() {
        // Mock behavior for commonService
        when(commonService.ListAllApprovedCandidate()).thenReturn(Collections.emptyList());

        // Mock behavior for model
        Model model = mock(Model.class);

        // Call the method to be tested
        String result = voterService.ListAllApprovedCandidates(model);

        // Assertions
        assertEquals("Voter/CastVoteDashBoard", result);
        verify(model, times(1)).addAttribute(eq("approvedCandidate"), anyList());
        verify(model, times(1)).addAttribute(eq("SessionData"), any());
        verify(model, times(1)).addAttribute(eq("votingTimeDTO"), any());
        verify(model, times(1)).addAttribute(eq("userDetails"), any());
    }

    // Add more test cases for other methods as needed

}
