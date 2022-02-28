package fr.senioradom.testApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.senioradom.testApp.dto.GPSPointDTO;
import fr.senioradom.testApp.exception.EntityNotFoundException;
import fr.senioradom.testApp.service.GPSPointService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static fr.senioradom.testApp.utils.Constants.*;
@RunWith(MockitoJUnitRunner.class)
public class GPSPointControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private ObjectWriter writer = mapper.writer();
    @Mock
    private GPSPointService service;
    @InjectMocks
    private GPSPointController controller;

    GPSPointDTO POINT_1 = GPSPointDTO.builder()
            .latitude(48.815418)
            .longitude(2.297650)
            .build();
    GPSPointDTO POINT_2 = GPSPointDTO.builder()
            .latitude(48.819259)
            .longitude(2.303504)
            .build();
    GPSPointDTO POINT_3 = GPSPointDTO.builder()
            .latitude(48.919259)
            .longitude(4.303504)
            .build();

    public static String URL_TEMPLATE= "http://localhost:8080/testAPP/v1/api/gpsPoints";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllGPSPointsRecordsWithSuccess() throws Exception {
        List<GPSPointDTO> listPoints = Stream
                                        .of(POINT_1,POINT_2,POINT_3)
                                        .collect(Collectors.toList());
        Mockito.when(service.getAllGPSPoints()).thenReturn(listPoints);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(3)))
                .andExpect(jsonPath("$[0].['latitude']",is(48.815418)))
                .andExpect(jsonPath("$[1].['latitude']",is(48.819259)))
                .andExpect(jsonPath("$[2].['latitude']",is(48.919259)));
    }

    @Test
    public  void createGPSPointWithSuccess() throws Exception {

        GPSPointDTO SENIOR_ADOM_LOCATION = GPSPointDTO.builder()
                .latitude(48.819259)
                .longitude(2.303504)
                .id(2022L)
                .build();
        String content = writer.writeValueAsString(SENIOR_ADOM_LOCATION);
        Mockito.when(service.createGPSPoint(SENIOR_ADOM_LOCATION)).thenReturn(SENIOR_ADOM_LOCATION);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.latitude",is(48.819259)))
                .andExpect(jsonPath("$.longitude",is(2.303504)))
                .andExpect(jsonPath("$.id", notNullValue()));



    }

    @Test
    public  void getGPSPointWithSuccess() throws Exception {

        GPSPointDTO SENIOR_ADOM_LOCATION = GPSPointDTO.builder()
                .latitude(48.819259)
                .longitude(2.303504)
                .id(2022L)
                .build();
        Mockito.when(service.getGPSPoint(2022L)).thenReturn(SENIOR_ADOM_LOCATION);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(URL_TEMPLATE+"/2022")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.latitude",is(48.819259)))
                .andExpect(jsonPath("$.longitude",is(2.303504)))
                .andExpect(jsonPath("$.id", is(2022)));



    }

}