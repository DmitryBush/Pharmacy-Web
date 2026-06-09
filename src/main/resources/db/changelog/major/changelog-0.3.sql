--liquibase formatted sql
--changeset Bushuev:Indexes

-- Indexes for Suppliers Table
CREATE INDEX IF NOT EXISTS idx_suppliers_f_key_address_id
    ON public.suppliers(f_key_address_id);

-- Indexes for Manufacturers Table
CREATE INDEX IF NOT EXISTS idx_manufacturers_f_key_country_id
    ON public.manufacturers(f_key_country_id);

-- Indexes for Product Types Table
CREATE INDEX IF NOT EXISTS idx_product_types_parent_id
    ON public.product_types(parent_id);

-- Indexes for Products Table
CREATE INDEX IF NOT EXISTS idx_products_fk_product_manufacturer
    ON public.products(fk_product_manufacturer);

CREATE INDEX IF NOT EXISTS idx_products_f_key_product_type
    ON public.products(f_key_product_type);

CREATE INDEX IF NOT EXISTS idx_products_f_key_supplier_itn
    ON public.products(f_key_supplier_itn);

-- Indexes for Product Type Mapping Table
CREATE INDEX IF NOT EXISTS idx_product_type_mapping_category_id
    ON public.product_type_mapping(category_id);

CREATE INDEX IF NOT EXISTS idx_product_type_mapping_product_id
    ON public.product_type_mapping(product_id);

-- Indexes for Product Images Table
CREATE INDEX IF NOT EXISTS idx_product_images_f_key_product_id
    ON public.product_images(f_key_product_id);

-- Indexes for Users Table
CREATE INDEX IF NOT EXISTS idx_users_f_key_role_id
    ON public.users(f_key_role_id);

-- Indexes for Pharmacy Branches Table
CREATE INDEX IF NOT EXISTS idx_pharmacy_branches_f_key_address_id
    ON public.pharmacy_branches(f_key_address_id);

CREATE INDEX IF NOT EXISTS idx_pharmacy_branches_user_supervisor
    ON public.pharmacy_branches(user_supervisor);

-- Indexes for Orders Table
CREATE INDEX IF NOT EXISTS idx_orders_f_key_user_id
    ON public.orders(f_key_user_id);

CREATE INDEX IF NOT EXISTS idx_orders_f_key_branch_id
    ON public.orders(f_key_branch_id);

-- Indexes for Order Items Table
CREATE INDEX IF NOT EXISTS idx_order_items_f_key_product_id
    ON public.order_items(f_key_product_id);

CREATE INDEX IF NOT EXISTS idx_order_items_f_key_order_id
    ON public.order_items(f_key_order_id);

-- Indexes for Branches Opening Hours Table
CREATE INDEX IF NOT EXISTS idx_branches_opening_hours_branch_id
    ON public.branches_opening_hours(branch_id);

-- Indexes for Branch Reservation Table
CREATE INDEX IF NOT EXISTS idx_branch_reservation_f_key_branch_id
    ON public.branch_reservation(f_key_branch_id);

CREATE INDEX IF NOT EXISTS idx_branch_reservation_f_key_customer_id
    ON public.branch_reservation(f_key_user_id);

CREATE INDEX IF NOT EXISTS idx_branch_reservation_f_key_product_id
    ON public.branch_reservation(f_key_product_id);

CREATE INDEX IF NOT EXISTS idx_branch_reservation_f_key_order_id
    ON public.branch_reservation(f_key_order_id);


-- Indexes for Storage Table
CREATE INDEX IF NOT EXISTS idx_storage_f_key_branch_id
    ON public.storage(f_key_branch_id);

CREATE INDEX IF NOT EXISTS idx_storage_f_key_product_id
    ON public.storage(f_key_product_id);

-- Indexes for Cart Items Table
CREATE INDEX IF NOT EXISTS idx_cart_items_f_key_product_id
    ON public.cart_items(f_key_product_id);

-- Indexes for Transaction History Table
CREATE INDEX IF NOT EXISTS idx_transaction_history_f_key_branch_id
    ON public.transaction_history(f_key_branch_id);

CREATE INDEX IF NOT EXISTS idx_transaction_history_f_key_order_id
    ON public.transaction_history(f_key_order_id);

CREATE INDEX IF NOT EXISTS idx_transaction_history_f_key_transaction_type
    ON public.transaction_history(f_key_transaction_type);

-- Indexes for News Table
CREATE INDEX IF NOT EXISTS idx_news_f_key_type_id
    ON public.news(f_key_type_id);

-- Indexes for News Image Table
CREATE INDEX IF NOT EXISTS idx_news_image_f_key_news_id
    ON public.news_image(f_key_news_id);

