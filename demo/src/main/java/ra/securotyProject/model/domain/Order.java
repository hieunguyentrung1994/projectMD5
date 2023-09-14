package ra.securotyProject.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="date_order")
    private Date orderDate;

    @ManyToMany
    @JoinTable(name = "StatusOrder_role"
            ,joinColumns = @JoinColumn(name = "order_id")
            ,inverseJoinColumns = @JoinColumn(name="roleOrder_id"))
    private Set<RoleOrder> statusOrders;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;
}

