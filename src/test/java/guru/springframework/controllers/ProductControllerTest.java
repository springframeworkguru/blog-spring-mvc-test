package guru.springframework.controllers;

import guru.springframework.services.ProductService;
import org.hamcrest.Matchers;
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
public class ProductControllerTest {
  private MockMvc mockMvc;
   @MockBean
   private ProductService productService;

    @Before
    public void setUp() {
        ProductController productController= new ProductController();
        /*Calling this method results in javax.servlet.ServletException: Circular view path [products]: would dispatch back to the current handler URL [/products] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)*/
       // productController.setProductService(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }


    @Test
    public void testList() throws Exception {

        assertThat(this.productService).isNotNull();
      mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.view().name("products"))
                 .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
               .andExpect(MockMvcResultMatchers.model().attribute("products",
                        Matchers.is(Matchers.empty())));

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