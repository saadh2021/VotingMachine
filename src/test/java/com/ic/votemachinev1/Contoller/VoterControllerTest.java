package com.ic.votemachinev1.Contoller;

import com.ic.votemachinev1.Controller.VoterController;
import com.ic.votemachinev1.DTOs.Candidate.AllCandidateOrPartyDTO;
import com.ic.votemachinev1.Service.VoterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VoterControllerTest {

    @Mock
    private VoterService voterService;

    @InjectMocks
    private VoterController voterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowDashBoard() {
        Model model = mock(Model.class);
        ModelAndView expectedModelAndView = new ModelAndView("voterDashboard");

        when(voterService.ShowDashBoard(any(Model.class))).thenReturn(expectedModelAndView);

        ModelAndView result = voterController.ShowDashBoard(model);

        assertEquals(expectedModelAndView, result);
        verify(voterService, times(1)).ShowDashBoard(model);
    }

    @Test
    void testListCandidates() {
        Model model = mock(Model.class);
        String expectedViewName = "candidatesList";

        when(voterService.listCandidates(any(Model.class))).thenReturn(expectedViewName);

        String result = voterController.listCandidates(model);

        assertEquals(expectedViewName, result);
        verify(voterService, times(1)).listCandidates(model);
    }

    @Test
    void testListCandidatesByCnic() {
        Model model = mock(Model.class);
        Long search = 123L;
        String expectedViewName = "candidatesList";

        when(voterService.listCandidates(any(Model.class), eq(search))).thenReturn(expectedViewName);

        String result = voterController.listCandidates(model, search);

        assertEquals(expectedViewName, result);
        verify(voterService, times(1)).listCandidates(model, search);
    }



    @Test
    void testUpdateVoterToBeCandidate() {
        AllCandidateOrPartyDTO voterToCandidateDto = new AllCandidateOrPartyDTO();
        String expectedRedirectUrl = "redirect:/Voter/SendApprovalRequest/123";

        when(voterService.UpdateVoterToBeCandidate(any(AllCandidateOrPartyDTO.class))).thenReturn(123L);

        String result = voterController.UpdateVoterToBeCandidate(voterToCandidateDto);

        assertEquals(expectedRedirectUrl, result);
        verify(voterService, times(1)).UpdateVoterToBeCandidate(voterToCandidateDto);
    }

    @Test
    void testSendApprovalRequest() {
        Long cnic = 456L;
        String expectedViewName = "/Voter/ApprovedCandidateSuccessForVoter";

        doNothing().when(voterService).approveCandidate(cnic);

        String result = voterController.SendApprovalRequest(cnic);

        assertEquals(expectedViewName, result);
        verify(voterService, times(1)).approveCandidate(cnic);
    }

    @Test
    void testListAllApprovedCandidates() {
        Model model = mock(Model.class);
        String expectedViewName = "approvedCandidatesList";

        when(voterService.ListAllApprovedCandidates(any(Model.class))).thenReturn(expectedViewName);

        String result = voterController.ListAllApprovedCandidates(model);

        assertEquals(expectedViewName, result);
        verify(voterService, times(1)).ListAllApprovedCandidates(model);
    }

    @Test
    void testSaveCastedVote() {
        Model model = mock(Model.class);
        Long partyID = 789L;
        String expectedViewName = "voteCastedSuccess";

        when(voterService.SaveCastedVote(eq(partyID), any(Model.class))).thenReturn(expectedViewName);

        String result = voterController.SaveCastedVote(partyID, model);

        assertEquals(expectedViewName, result);
        verify(voterService, times(1)).SaveCastedVote(partyID, model);
    }

    @Test
    void testElectionResults() {
        Model model = mock(Model.class);
        int page = 1;
        int size = 10;
        String expectedViewName = "electionResults";

        when(voterService.ElectionResults(eq(page), eq(size), any(Model.class))).thenReturn(expectedViewName);

        String result = voterController.ElectionResults(page, size, model);

        assertEquals(expectedViewName, result);
        verify(voterService, times(1)).ElectionResults(page, size, model);
    }

    @Test
    void testResultsByPartyNames() {
        Model model = mock(Model.class);
        String search = "PartyA";
        int page = 1;
        int size = 10;
        String expectedViewName = "partyResults";

        when(voterService.ResultsByPartyNames(any(Model.class), eq(search), eq(page), eq(size))).thenReturn(expectedViewName);

        String result = voterController.ResultsByPartyNames(model, search, page, size);

        assertEquals(expectedViewName, result);
        verify(voterService, times(1)).ResultsByPartyNames(model, search, page, size);
    }

}