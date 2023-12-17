package com.ic.votemachinev1.Service;

import com.cloudinary.Cloudinary;
import com.ic.votemachinev1.DTOs.Voter.VoterDTOToFetch;
import com.ic.votemachinev1.Repository.PartiesRepository;
import com.ic.votemachinev1.Repository.UsersRepository;
import com.ic.votemachinev1.Repository.VotingTimeRepository;
import com.ic.votemachinev1.Service.Imp.CommonServiceImp;
import com.ic.votemachinev1.Service.Imp.PartiesServiceImp;
import com.ic.votemachinev1.Utils.SessionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommonServiceImpTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private SessionData sessionData;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private VotingTimeRepository votingTimeRepository;

    @Mock
    private PartiesRepository partiesRepository;

    @Mock
    private PartiesServiceImp partiesServiceImp;

    @InjectMocks
    private CommonServiceImp commonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllVoters() {
        // Mock behavior for usersRepository
        when(usersRepository.findByRole(any())).thenReturn(Collections.emptyList());

        // Call the method to be tested
        List<VoterDTOToFetch> result = commonService.ListAllVoters();

        // Assertions
        assertEquals(Collections.emptyList(), result);
    }

    // Add more test cases for other methods as needed

}
