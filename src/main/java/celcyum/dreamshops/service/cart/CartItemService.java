package celcyum.dreamshops.service.cart;

import celcyum.dreamshops.exceptions.ResourcesNotFoundException;
import celcyum.dreamshops.model.Cart;
import celcyum.dreamshops.model.CartItem;
import celcyum.dreamshops.model.Product;
import celcyum.dreamshops.repository.CartItemRepository;
import celcyum.dreamshops.repository.CartRepository;
import celcyum.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        Cart cart = getCart(cartId);
        // 2. Get the product
        Product product = productService.getProductById(productId);
        // 3. Check if the product already in the cart
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            // 4. If Yes, then increase the quantity with the requested quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // 5. If No, then initiate a new CartItem entry
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cart.getCartItems().add(cartItem);
        }

        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
        updateCartTotal(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        CartItem cartItem = getCartItem(cart, productId);
        cart.getCartItems().remove(cartItem);
        updateCartTotal(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = getCart(cartId);
        CartItem cartItem = getCartItem(cart, productId);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        updateCartTotal(cart);
    }

    private Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourcesNotFoundException("Cart not found"));
    }

    @Override
    public CartItem getCartItem(Cart cart, Long productId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourcesNotFoundException("Item not found"));
    }

    private void updateCartTotal(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
        cartRepository.save(cart);
    }
}
