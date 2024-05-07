import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) throws NoSuchObjectException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productOptional = productRepo.getProductById(productId);
            if (productOptional.isEmpty()) {
                throw new NoSuchObjectException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productOptional.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), OrderStatus.PROCESSING, products);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream().filter(order -> order.orderStatus() == orderStatus).toList();
    }
}
