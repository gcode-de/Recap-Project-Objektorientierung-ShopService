import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.rmi.NoSuchObjectException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() throws NoSuchObjectException {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", OrderStatus.PROCESSING,  List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertEquals(expected.orderStatus(), actual.orderStatus());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_throw() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productIds = List.of("1", "2");

        //WHEN

        //THEN
        assertThrows(NoSuchObjectException.class, () -> shopService.addOrder(productIds));
    }

    @Test
    void getOrdersByStatusTest () throws NoSuchObjectException {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Order testOrder = shopService.addOrder(productsIds);

        //WHEN
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.PROCESSING);
        //THEN
       List<Order> expected = List.of(testOrder);
       assertEquals(expected, actual);
    }
}
