package guru.springframework.controllers;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import java.util.Collection;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(secure=false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ProductService productServiceMock;

    Product product1;
    @Before
    public void setUpProduct() throws Exception{

        product1 = new Product();
        product1.setId(1);
        product1.setProductId("235268845711068308");
        product1.setDescription("Spring Framework Guru Shirt");
        product1.setPrice(new BigDecimal("18.95"));
        product1.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");

    }
    @Test
    public void testList() throws Exception {
        assertThat(this.productServiceMock).isNotNull();
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("products"))
                .andExpect(MockMvcResultMatchers.view().name("products"))
                .andExpect(content().string(Matchers.containsString("Spring Framework Guru")))
                .andDo(print());
    }

    @Test
    public void testShowProduct() throws Exception {
        assertThat(this.productServiceMock).isNotNull();
        when(productServiceMock.getProductById(1)).thenReturn(product1);

        MvcResult result= mockMvc.perform(get("/product/{id}/", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("productshow"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("id", is(1))))
                .andExpect(model().attribute("product", hasProperty("productId", is("235268845711068308"))))
                .andExpect(model().attribute("product", hasProperty("description", is("Spring Framework Guru Shirt"))))
                .andExpect(model().attribute("product", hasProperty("price", is(new BigDecimal("18.95")))))
                .andExpect(model().attribute("product", hasProperty("imageUrl", is("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg"))))
                .andReturn();


        MockHttpServletResponse mockResponse=result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("text/html;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type", responseHeaders.iterator().next());
        String responseAsString=mockResponse.getContentAsString();
        assertTrue(responseAsString.contains("Spring Framework Guru"));

        verify(productServiceMock, times(1)).getProductById(1);
        verifyNoMoreInteractions(productServiceMock);
    }


}