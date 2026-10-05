package celcyum.dreamshops.service.order;

import celcyum.dreamshops.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getUserOrder(Long orderId);
}
