
package com.coffee.coffeehouse;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coffee.coffeehouse.CoffeeHouseController;

/**Integration Test  to test application started correctly
 * @author Akhil
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationStartUpTest {

    @Autowired
    private  CoffeeHouseController controller;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}