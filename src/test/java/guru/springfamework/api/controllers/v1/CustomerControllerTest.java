package guru.springfamework.api.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static guru.springfamework.api.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Darcy Xian  22/11/20  5:51 pm      spring5-mvc-rest
 */

public class CustomerControllerTest {

    public static final String FIRSTNAME = "JOE";
    public static final String LASTNAME = "LING";

    @Mock
    CustomerService customerService;

    @InjectMocks
     CustomerController customerController;

    MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        // given

       List<CustomerDTO> customerDTOS = Arrays.asList(new CustomerDTO(),new CustomerDTO(), new CustomerDTO());
        when(customerService.findAllCustomers()).thenReturn(customerDTOS);

        // then
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerListDTOS", hasSize(3)));

    }

    @Test
    public void getCustomerById() throws Exception {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setId(1L);

        when(customerService.findCustomerById(1L)).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/1")
               .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));
    }
//    @Test
//    public void createNewCustomer() throws Exception {
//        // given
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setFirstname(FIRSTNAME);
//        customerDTO.setLastname(LASTNAME);
//
//        CustomerDTO returnDTO = new CustomerDTO();
//        returnDTO.setFirstname(customerDTO.getFirstname());
//        returnDTO.setLastname(customerDTO.getLastname());
//        returnDTO.setCustomerUrl("/api/v1/customers/1");
//
//        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);
//
//        mockMvc.perform(post("/api/v1/customers/")
//                    .contentType(MediaType.APPLICATION_JSON)
//                     // 把一个对象转化成json   利用jackson的jar包
//                    .contentType(asJsonString(customerDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstname",equalTo("Fred")));
//               // .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
//
//    }

    @Test
    public void testUpdateCustomer() throws Exception{
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(1l);
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(),any(CustomerDTO.class))).thenReturn(returnDTO);

        // when then
        mockMvc.perform(put("/api/v1/customers/1")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(asJsonString(customerDTO)))
                         .andExpect(status().isCreated())
                         .andExpect(jsonPath("$.firstname",equalTo(FIRSTNAME)));


    }



}































