package celcyum.dreamshops.service.cart;

import celcyum.dreamshops.model.Cart;
import celcyum.dreamshops.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Cart cart, Long productId);
}
