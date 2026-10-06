package celcyum.dreamshops.service.order;

import celcyum.dreamshops.dto.OrderDto;
import celcyum.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
