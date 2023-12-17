package com.ic.votemachinev1.Contoller;

import com.ic.votemachinev1.Controller.CandidateController;
import com.ic.votemachinev1.Service.CandidateService;
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

class CandidateControllerTest {

    @Mock
    private CandidateService candidateService;

    @InjectMocks
    private CandidateController candidateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowDashBoard() {
        Model model = mock(Model.class);
        ModelAndView expectedModelAndView = new ModelAndView("candidateDashboard");

        when(candidateService.ShowDashBoard(any(Model.class))).thenReturn(expectedModelAndView);

        ModelAndView result = candidateController.ShowDashBoard(model);

        assertEquals(expectedModelAndView, result);
        verify(candidateService, times(1)).ShowDashBoard(model);
    }

    @Test
    void testListVoters() {
        Model model = mock(Model.class);
        String expectedViewName = "votersList";

        when(candidateService.listVoters(any(Model.class))).thenReturn(expectedViewName);

        String result = candidateController.listVoters(model);

        assertEquals(expectedViewName, result);
        verify(candidateService, times(1)).listVoters(model);
    }

    @Test
    void testVoterByCNIC() {
        Model model = mock(Model.class);
        Long search = 123L;
        String expectedViewName = "voterDetails";

        when(candidateService.VoterByCNIC(any(Model.class), eq(search))).thenReturn(expectedViewName);

        String result = candidateController.VoterByCNIC(model, search);

        assertEquals(expectedViewName, result);
        verify(candidateService, times(1)).VoterByCNIC(model, search);
    }

    @Test
    void testListAllApprovedCandidates() {
        Model model = mock(Model.class);
        String expectedViewName = "approvedCandidatesList";

        when(candidateService.ListAllApprovedCandidates(any(Model.class))).thenReturn(expectedViewName);

        String result = candidateController.ListAllApprovedCandidates(model);

        assertEquals(expectedViewName, result);
        verify(candidateService, times(1)).ListAllApprovedCandidates(model);
    }

    @Test
    void testSaveCastedVote() {
        Model model = mock(Model.class);
        Long partyID = 456L;
        String expectedViewName = "voteCastedSuccess";

        when(candidateService.SaveCastedVote(eq(partyID), any(Model.class))).thenReturn(expectedViewName);

        String result = candidateController.SaveCastedVote(partyID, model);

        assertEquals(expectedViewName, result);
        verify(candidateService, times(1)).SaveCastedVote(partyID, model);
    }

    @Test
    void testElectionResults() {
        Model model = mock(Model.class);
        int page = 1;
        int size = 10;
        String expectedViewName = "electionResults";

        when(candidateService.ElectionResults(eq(page), eq(size), any(Model.class))).thenReturn(expectedViewName);

        String result = candidateController.ElectionResults(page, size, model);

        assertEquals(expectedViewName, result);
        verify(candidateService, times(1)).ElectionResults(page, size, model);
    }

    @Test
    void testResultsByPartyNames() {
        Model model = mock(Model.class);
        String search = "PartyA";
        int page = 1;
        int size = 10;
        String expectedViewName = "partyResults";

        when(candidateService.ResultsByPartyNames(any(Model.class), eq(search), eq(page), eq(size))).thenReturn(expectedViewName);

        String result = candidateController.ResultsByPartyNames(model, search, page, size);

        assertEquals(expectedViewName, result);
        verify(candidateService, times(1)).ResultsByPartyNames(model, search, page, size);
    }

    @Test
    void testLogout() {
        String expectedViewName = "logout";

        when(candidateService.Logout()).thenReturn(expectedViewName);

        String result = candidateController.Logout();

        assertEquals(expectedViewName, result);
        verify(candidateService, times(1)).Logout();
    }
}
