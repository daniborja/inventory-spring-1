package com.alex.inventorymanagement.orders.service;

import com.alex.inventorymanagement.addresses.entity.Address;
import com.alex.inventorymanagement.addresses.repository.AddressRepository;
import com.alex.inventorymanagement.common.exceptions.BadRequestException;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import com.alex.inventorymanagement.common.exceptions.UnauthorizedException;
import com.alex.inventorymanagement.orders.dto.CreateOrderRequestDto;
import com.alex.inventorymanagement.orders.dto.PaginatedOrdersResponseDto;
import com.alex.inventorymanagement.orders.entity.Order;
import com.alex.inventorymanagement.orders.entity.OrderItem;
import com.alex.inventorymanagement.orders.entity.OrderResponseDto;
import com.alex.inventorymanagement.orders.repository.OrderItemRepository;
import com.alex.inventorymanagement.orders.repository.OrderRepository;
import com.alex.inventorymanagement.products.dto.ProductResponseDto;
import com.alex.inventorymanagement.products.entity.Product;
import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import com.alex.inventorymanagement.products.repository.ProductMeasurementRepository;
import com.alex.inventorymanagement.products.repository.ProductRepository;
import com.alex.inventorymanagement.stocks.entity.Stock;
import com.alex.inventorymanagement.stocks.repository.StockRepository;
import com.alex.inventorymanagement.users.entity.Usuario;
import com.alex.inventorymanagement.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final ProductMeasurementRepository productMeasurementRepository;
    private final OrderItemRepository orderItemRepository;
    private final StockRepository stockRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public Order create(CreateOrderRequestDto orderRequestDto, String authUserEmail) {
        Usuario user = userService.findOneByEmail(authUserEmail);
        Address shippingAddress = addressRepository.findById(orderRequestDto.getShippingAddressId()).orElseThrow(
                () -> new ResourceNotFoundException("Shipping Address", "ID", orderRequestDto.getShippingAddressId())
        );

        Order order = Order.builder()
                .address(shippingAddress)
                .user(user)
                .orderItems(new ArrayList<>())
                .build();


        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderRequestDto.OrderItemDto orderItemDto : orderRequestDto.getItems()) {
            Product product = findProductById(orderItemDto.getProductId());
            ProductMeasurement productMeasurement = findProductMeasurementById(orderItemDto.getProductMeasurementId());
            Stock stock = findStockByProductMeasurementId(orderItemDto.getProductMeasurementId());

            // // validations:  TODO: ver q correspondan los productos al measurement y demas como en el UPD de product
            if (!Objects.equals(orderItemDto.getPrice(), product.getPrice())) {
                throw new UnauthorizedException("Prices do not match!");  // at time of purchase
            }
            if (stock.getQuantity() < orderItemDto.getQuantity()) {
                throw new BadRequestException("Not enough products in stock for product with ID: ".concat(product.getId().toString()));
            }

            OrderItem orderItem = OrderItem.builder()
                    .quantity(orderItemDto.getQuantity())
                    .order(order)
                    .product(product)
                    .productMeasurement(productMeasurement)
                    .priceAtPurchase(product.getPrice())
                    .build();

            // update quantity
            Long updatedQuantity = stock.getQuantity() - orderItemDto.getQuantity();
            stock.setQuantity(updatedQuantity);
            stockRepository.save(stock);


            order.getOrderItems().add(orderItem);
            orderItems.add(orderItem);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return order;
    }

    @Override
    public PaginatedOrdersResponseDto findAll(Pageable pageable) {
        Page<Order> ordersPage = orderRepository.fetchAll(pageable);
        List<Order> orders = ordersPage.getContent();
        List<OrderResponseDto> orderResponseDtoList = orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .toList();

        return PaginatedOrdersResponseDto.builder()
                .orders(orderResponseDtoList)
                .pageNumber(ordersPage.getNumber())
                .size(ordersPage.getSize())
                .totalElements(ordersPage.getTotalElements())
                .totalPages(ordersPage.getTotalPages())
                .isLastOne(ordersPage.isLast())
                .build();
    }

    @Override
    public OrderResponseDto findOne(Long id) {
        Order order = orderRepository.fetchOneById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order", "ID", id)
        );

        return modelMapper.map(order, OrderResponseDto.class);
    }


    private Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "ID", productId)
        );
    }

    private ProductMeasurement findProductMeasurementById(Long productMeasurementId) {
        return productMeasurementRepository.findById(productMeasurementId).orElseThrow(
                () -> new ResourceNotFoundException("Product Measurement", "ID", productMeasurementId)
        );
    }

    private Stock findStockByProductMeasurementId(Long productMeasurementId) {
        return stockRepository.findByProductMeasurementId(productMeasurementId).orElseThrow(
                () -> new ResourceNotFoundException("Stock", "ID", productMeasurementId)
        );
    }

}
