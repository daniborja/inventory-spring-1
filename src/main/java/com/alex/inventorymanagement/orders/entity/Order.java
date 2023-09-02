package com.alex.inventorymanagement.orders.entity;

import com.alex.inventorymanagement.addresses.entity.Address;
import com.alex.inventorymanagement.users.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_order")   // in postgresql and jpa/hibernate order is reserved
//@Where(clause = "deleted = false") // filtra los deleted para todos los Select
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // calculated in back
    @Column
    private BigDecimal tax;
    @Column
    protected BigDecimal subtotal;
    @Column
    private BigDecimal totalAmount;

    // provided by payment platform in back
    private String transactionId;  // set by payment platform after pay
    private boolean isPaid;        // validate with payment platform

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user_order_ref")
    private Usuario user;       // SII crea aqui la FK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    @JsonBackReference("address_order_ref")
    private Address address;        // SII crea la FK aqui

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonManagedReference("order_orderitem_ref")
    private List<OrderItem> orderItems;


    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
