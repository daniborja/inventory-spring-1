package com.alex.inventorymanagement.orders.entity;

import com.alex.inventorymanagement.addresses.entity.Address;
import com.alex.inventorymanagement.users.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.Set;


@Data
@Entity
@Table(name = "_order")   // in postgresql and jpa/hibernate order is reserved
@Where(clause = "deleted = false") // filtra los deleted para todos los Select
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal tax;
    @Column(nullable = false)
    protected BigDecimal subtotal;
    @Column(nullable = false)
    private BigDecimal totalAmount;

    private String transactionId;  // set by payment platform after pay
    private boolean isPaid;        // validate with payment platform


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("user_order_ref")
    private Usuario user;       // SII crea aqui la FK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @JsonBackReference("address_order_ref")
    private Address address;        // SII crea la FK aqui

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonManagedReference("order_orderitem_ref")
    private Set<OrderItem> orderItems;

}
