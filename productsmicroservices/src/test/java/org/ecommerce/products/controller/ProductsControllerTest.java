package org.ecommerce.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.products.dto.ProductResponse;
import org.ecommerce.products.entity.Product;
import org.ecommerce.products.service.ProductsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.ecommerce.products.entity.Category.CLOTHING;
import static org.ecommerce.products.entity.Category.ELECTRONICS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductsController.class)
class ProductsControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductsService productsServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {

        product1 = Product.builder()
                .sku("000001")
                .name("name test 1")
                .description("this is product 1")
                .category(CLOTHING)
                .price(11.11f)
                .manufacturer("test manufacturer")
                .supplier("test supplier")
                .build();

        product2 = Product.builder()
                .sku("000002")
                .name("name test 2")
                .description("this is product 2")
                .category(ELECTRONICS)
                .price(22.22f)
                .manufacturer("test manufacturer 2")
                .supplier("test supplier 2")
                .build();



    }

    @AfterEach
    void tearDown() {
    }

    // methods testing

    @Test
    void greetingsTest() throws Exception{
        mockMvc.perform(get("/api/v1/products/test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo("Hello from Products DB!!!")));
    }



    @Test
    void getAllProductsTest()  throws Exception{
        //given
        ProductResponse productResponse1 = ProductResponse.builder()
                .sku(product1.getSku())
                .name(product1.getName())
                .description(product1.getDescription())
                .category(product1.getCategory())
                .price(product1.getPrice())
                .build();

        ProductResponse productResponse2 = ProductResponse.builder()
                .sku(product2.getSku())
                .name(product2.getName())
                .description(product2.getDescription())
                .category(product2.getCategory())
                .price(product2.getPrice())
                .build();

        List<ProductResponse> productResponseList = new ArrayList<ProductResponse>();
        productResponseList.add(productResponse1);
        productResponseList.add(productResponse2);
        given(productsServiceMock.getAllProducts()).willReturn(productResponseList);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/products/getAllProducts"));

        //then

        response.andExpect(status().isOk()).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(productResponseList.size())));

    }

    @Test
    void addNewProducts() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void getProductById() {
    }

    @Test
    void confirmProductBySku() {
    }
}