package org.example.booksmart.service;

import org.example.booksmart.error.InsufficientFunds;
import org.example.booksmart.error.InsufficientStock;
import org.example.booksmart.model.Buyer;
import org.example.booksmart.model.Order;
import org.example.booksmart.model.OrderProduct;
import org.example.booksmart.model.Product;
import org.example.booksmart.repository.BuyerRepository;
import org.example.booksmart.repository.OrderRepository;
import org.example.booksmart.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;


    public List<Buyer> findAll() {
        return buyerRepository.findAll();
    }

    public Buyer findByPhoneNumber(String phoneNumber) {
        return buyerRepository.findByAccount_PhoneNumber(phoneNumber);
    }

    public Buyer save(Buyer buyer) {
        buyer.getAccount().setPassword(PasswordUtil.hashPassword(buyer.getAccount().getPassword()));
        return buyerRepository.save(buyer);
    }

    public Buyer update(Buyer newBuyer) {
        Buyer existingBuyer = buyerRepository.findById(newBuyer.getId()).orElse(null);
        if (existingBuyer == null) {
            return null;
        }
        existingBuyer.getAccount().setAddress(newBuyer.getAccount().getAddress());
        existingBuyer.setAccount(newBuyer.getAccount());
        existingBuyer.getAccount().setPassword(PasswordUtil.hashPassword(newBuyer.getAccount().getPassword()));
        existingBuyer.setCreditLimit(newBuyer.getCreditLimit());
        existingBuyer.setInterests(newBuyer.getInterests());
        return buyerRepository.save(existingBuyer);
    }

    public boolean existsByEmail(String email) {
        return buyerRepository.existsByAccount_Email(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return buyerRepository.existsByAccount_PhoneNumber(phoneNumber);
    }

    public List<Product> findInterestsByBuyerId(Long buyerId) {
        List<Product> interests = buyerRepository.findInterestsByBuyerId(buyerId);
        if (interests.isEmpty()) {
            return productService.findFirstX(16);
        }
        return interests.subList(0, Math.min(interests.size(), 16));
    }

    public Buyer findByEmail(String email) {
        Buyer buyer;
        try {
            buyer = buyerRepository.findByAccount_Email(email);
        } catch (Exception e) {
            return null;
        }
        return buyer;
    }

    public boolean checkValidLoginCredentials(String email, String password) {
        Buyer buyer = findByEmail(email);
        if (buyer != null) {
            return PasswordUtil.checkPassword(password, buyer.getAccount().getPassword());
        }
        return false;

    }

    public Buyer findById(Long id) {
        return buyerRepository.findById(id).orElse(null);
    }

    public void addProductToBuyerCart(Buyer buyer, Product product, int quantity) {
        buyerRepository.addProductToCart(buyer, product, quantity);
    }

    public void removeProductFromBuyerCart(Buyer buyer, Product product) {
        buyerRepository.removeProductFromCart(buyer, product);
    }

    public int incrementBuyerCartProductQuantity(Buyer buyer, Product product) {
        return buyerRepository.incrementProductQuantity(buyer, product);
    }

    public int decrementBuyerCartProductQuantity(Buyer buyer, Product product) {
        return buyerRepository.decrementProductQuantity(buyer, product);
    }

    public void clearBuyerCart(Buyer buyer) {
        buyerRepository.clearCart(buyer);
    }

    public Map<Product, Integer> retreiveBuyerCart(Buyer buyer) {
        return buyer.getCart();
    }

    public void setBuyerCartProductQuantity(Buyer buyer, Product product, int quantity) {
        buyerRepository.setProductQuantity(buyer, product, quantity);
    }

    public void addProductToBuyerWishlist(Buyer buyer, Product product) {
        buyerRepository.addToWishlist(buyer, product);
    }

    public Buyer removeProductFromBuyerWishlist(Buyer buyer, Product product) {
        return buyerRepository.removeFromWishlist(buyer, product);
    }


    public List<Buyer> search(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return buyerRepository.findBuyersByIsDeletedFalse(pageable).getContent();
    }

    public List<Order> searchOrders(Long buyerId, int pageNumber, int pageSize) {
        Pageable pageable =  PageRequest.of(pageNumber, pageSize);
        return buyerRepository.findOrdersByBuyerIdAndIsDeletedFalse(buyerId, pageable).getContent();
    }

    @Transactional
    public boolean validateAndUpdateCart(Buyer buyer) {
        if (buyer == null || buyer.getCart() == null) return false;

        int initialCartSize = buyer.getCart().size();

        Map<Product, Integer> currentCart = buyer.getCart();
        Set<Long> productIds = currentCart.keySet().stream().map(Product::getId).collect(Collectors.toSet());
        Map<Product, Integer> currentProducts = productService.findByIdsWithQuantities(productIds);
        currentCart.entrySet().stream().filter(entry -> currentProducts.containsKey(entry.getKey())).forEach(entry -> currentProducts.put(entry.getKey(), entry.getValue()));
        buyer.clearCart();
        buyer.addCartItem(currentProducts);
        buyerRepository.save(buyer);

        return buyer.getCart().size() == initialCartSize;

    }

    @Transactional
    public boolean validateAndUpdateWishList(Buyer buyer) {
        if (buyer == null || buyer.getWishlist() == null) return false;
        int initialWishlistSize = buyer.getWishlist().size();

            Set<Product> currentWishlist = buyer.getWishlist();
            Set<Long> productIds = currentWishlist.stream().map(Product::getId).collect(Collectors.toSet());
            Set<Product> availableProductsInWishlist = new HashSet<>(productService.findByIds(productIds));
            Set<Product> itemsToRemove = currentWishlist.stream().filter(item -> !availableProductsInWishlist.contains(item)).collect(Collectors.toSet());
            buyer.removeFromWishlist(itemsToRemove);
            buyerRepository.save(buyer);

            return currentWishlist.size() == initialWishlistSize;
    }

    @Transactional
    public Buyer checkout(Buyer buyer) throws InsufficientStock, InsufficientFunds {

        buyer = buyerRepository.findById(buyer.getId()).get();
        try {
            Map<Product, Integer> currentCart = buyer.getCart();
            Order order = new Order(buyer);
            for (Map.Entry<Product, Integer> entry : currentCart.entrySet()) {
                Product product = entry.getKey();
                //--- Check if stock sufficient
                Integer productStock = product.getQuantity();
                Integer productQuantity = entry.getValue();
                if (productStock < productQuantity) {
                    throw new InsufficientStock("Transaction declined insufficient stock");
                }
                product.setQuantity(productStock - productQuantity);
                productService.update(product);
                //-- Check is funds sufficient
                BigDecimal productPrice = product.getPrice();
                BigDecimal orderPrice = productPrice.multiply(new BigDecimal(productQuantity));
                BigDecimal buyerCreditLimit = buyer.getCreditLimit();
                if (orderPrice.compareTo(buyerCreditLimit) > 0) {
                    throw new InsufficientFunds("Transaction declined insufficient funds");
                }
                buyer.setCreditLimit(buyerCreditLimit.subtract(orderPrice));

                //-- Add it to Orders
                OrderProduct orderProduct = new OrderProduct(order, entry);
                order.addOrderProduct(orderProduct);
            }
            orderService.update(order);
            buyer.clearCart();
            buyer = buyerRepository.save(buyer);

            return buyer;
        } catch (InsufficientStock | InsufficientFunds e) {
            throw e;
        }
    }
}
