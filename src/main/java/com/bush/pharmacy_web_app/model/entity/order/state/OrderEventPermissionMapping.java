package com.bush.pharmacy_web_app.model.entity.order.state;

import com.bush.pharmacy_web_app.model.entity.user.role.RoleType;
import org.springframework.security.core.GrantedAuthority;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class OrderEventPermissionMapping {
    private static final Map<RoleType, Set<OrderEvent>> PERMISSION_MAPPING = Map.of(
            RoleType.ROLE_CUSTOMER, EnumSet.of(
                    OrderEvent.ORDER_CANCELLED_BY_USER,
                    OrderEvent.RETURN_REQUESTED_BY_USER
            ),
            RoleType.ROLE_OPERATOR, EnumSet.of(
                    OrderEvent.DECORATED_ORDER,
                    OrderEvent.SELLER_ISSUE,
                    OrderEvent.WAREHOUSE_ISSUE,
                    OrderEvent.DELIVERED_ORDER,
                    OrderEvent.OPERATOR_COMPLETES_ORDER,
                    OrderEvent.OPERATOR_CANCELS_ORDER,
                    OrderEvent.OPERATOR_REFUNDS_ORDER,
                    OrderEvent.RETURN_DISAPPROVED,
                    OrderEvent.RETURN_APPROVED,
                    OrderEvent.ORDER_RETURNED_BY_USER,
                    OrderEvent.SHIPPED_RETURN
            ),
            RoleType.ROLE_LOGISTICS, EnumSet.of(
                    OrderEvent.SHIPPED_ORDER,
                    OrderEvent.LOGISTIC_ISSUE,
                    OrderEvent.DELIVERED_RETURN
            ),
            RoleType.ROLE_ADMIN, EnumSet.allOf(OrderEvent.class)
    );

    public static boolean canOrderEventProcessed(Set<GrantedAuthority> authorities, OrderEvent event) {
        for (GrantedAuthority authority : authorities) {
            if (PERMISSION_MAPPING.get(RoleType.valueOf(authority.getAuthority())).contains(event)) {
                return true;
            }
        }
        return false;
    }
}
