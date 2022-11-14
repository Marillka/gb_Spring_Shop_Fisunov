package ru.rayumov.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rayumov.market.dtos.AddToCartDto;
import ru.rayumov.market.entities.Cart;
import ru.rayumov.market.entities.Product;
import ru.rayumov.market.repositories.CartRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Product> findAll() {
        List<Cart> cartList = cartRepository.findAll();
        List<Product> productsList = cartList.stream().map(cart -> cart.getProduct()).collect(Collectors.toList());
        return productsList;
    }

    public void deleteByID(Long id) {
        cartRepository.deleteById(id);
    }

    public void addToCart(AddToCartDto addToCartDto) {
        Product product = new Product();
        product.setTitle(addToCartDto.getTitle());
        product.setPrice(addToCartDto.getPrice());

        Cart cart = new Cart();
        cart.setProduct(product);

        cartRepository.save(cart);
    }




}
