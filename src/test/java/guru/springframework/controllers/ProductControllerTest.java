package guru.springframework.controllers;

import guru.springframework.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
//@SpringBootTest
public class ProductControllerTest {
    private MockMvc mockMvc;
 /*   private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }*/
 @MockBean
 private ProductService productService;
/*
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }*/

    @Before
    public void setUp() {
        ProductController productController= new ProductController();
        productController.setProductService(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }


    @Test
    public void testList() throws Exception {

        assertThat(this.productService).isNotNull();
      mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.view().name("products"));
               /*   .andExpect(MockMvcResultMatchers.model().attributeExists("products"));
               .andExpect(MockMvcResultMatchers.model().attribute("books",
                        Matchers.is(Matchers.empty())));*/

    }

 /*   @Test
    public void testShowProduct() throws Exception {

    }

    @Test
    public void testEdit() throws Exception {

    }

    @Test
    public void testNewProduct() throws Exception {

    }

    @Test
    public void testSaveProduct() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }*/
}