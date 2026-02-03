--liquibase formatted sql
--changeset Bushuev:Base_structure

CREATE TABLE IF NOT EXISTS public.addresses
(
    address_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    subject character varying(30) NOT NULL,
    district character varying(30),
    settlement character varying(30) NOT NULL,
    street character varying(30) NOT NULL,
    house character varying(5) NOT NULL,
    apartment character varying(5),
    postal_code character varying(6) NOT NULL,
    CONSTRAINT suppliers_addreses_pkey PRIMARY KEY (address_id)
);

CREATE TABLE IF NOT EXISTS public.suppliers
(
    itn character varying(12) NOT NULL,
    supplier_name character varying(50) NOT NULL,
    f_key_address_id bigint NOT NULL,
    CONSTRAINT suppliers_pkey PRIMARY KEY (itn),
    CONSTRAINT suppliers_fkey_address_id_fkey FOREIGN KEY (f_key_address_id) REFERENCES public.addresses (address_id)
);

CREATE TABLE IF NOT EXISTS public.country
(
    country_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    country character varying(50) NOT NULL,
    CONSTRAINT country_pkey PRIMARY KEY (country_id),
    CONSTRAINT country_country_key UNIQUE (country)
);

INSERT INTO country (country) VALUES
    ('Авдаленд'),
    ('Австралия'),
    ('Австрия'),
    ('Автономная земля Словакия'),
    ('Азербайджан'),
    ('Албания'),
    ('Алжир'),
    ('Американское Самоа'),
    ('Ангилья'),
    ('Ангола'),
    ('Андорра'),
    ('Антигуа и Барбуда'),
    ('Аргентина'),
    ('Армения'),
    ('Афганистан'),
    ('Багамские Острова'),
    ('Бангладеш'),
    ('Барбадос'),
    ('Бахрейн'),
    ('Беларусь'),
    ('Белиз'),
    ('Бельгия'),
    ('Бенин'),
    ('Бермудские Острова'),
    ('Болгария'),
    ('Боливия'),
    ('Бонайре, Синт-Эстатиус и Саба'),
    ('Босния и Герцеговина'),
    ('Ботсвана'),
    ('Бразилия'),
    ('Британская антарктическая территория'),
    ('Британская Гвинея'),
    ('Британская Территория в Индийском Океане'),
    ('Бруней'),
    ('Буркина-Фасо'),
    ('Бурунди'),
    ('Бутан'),
    ('Вануату'),
    ('Ватикан'),
    ('Великобритания'),
    ('Венгрия'),
    ('Венесуэла'),
    ('Вердис'),
    ('Виргинские Острова (Великобритания)'),
    ('Виргинские Острова (США)'),
    ('Восточный Тимор'),
    ('Вьетнам'),
    ('Габон'),
    ('Гаити'),
    ('Гайана'),
    ('Гамбия'),
    ('Гана'),
    ('Гватемала'),
    ('Гвинея'),
    ('Гвинея-Бисау'),
    ('Германия'),
    ('Гернси (коронное владение)'),
    ('Гибралтар'),
    ('Республика Гласьер'),
    ('Гондурас'),
    ('Государство Палестина'),
    ('Государство Центральных регионов Сомали'),
    ('Гренада'),
    ('Греция'),
    ('Грузия'),
    ('Гуам'),
    ('Свободная провинция Гуаякиль'),
    ('Дания'),
    ('Демократическая Республика Конго'),
    ('Джерси (коронное владение)'),
    ('Джибути'),
    ('Джубаленд'),
    ('Доминика'),
    ('Доминиканская Республика'),
    ('Египет'),
    ('Замбия'),
    ('Западная Сахара'),
    ('Западный Тоголенд'),
    ('Зимбабве'),
    ('Израиль'),
    ('Индия'),
    ('Индонезия'),
    ('Иордания'),
    ('Ирак'),
    ('Иран'),
    ('Иранский Курдистан'),
    ('Ирландия'),
    ('Исландия'),
    ('Испания'),
    ('Италия'),
    ('Йемен'),
    ('Кабо-Верде'),
    ('Казахстан'),
    ('Каймановы Острова'),
    ('Камбоджа'),
    ('Камерун'),
    ('Канада'),
    ('Карриаку и Малый Мартиник'),
    ('Катар'),
    ('Кенедугу (государство)'),
    ('Кения'),
    ('Республика Кипр'),
    ('Кирибати'),
    ('Китай'),
    ('Кокосовые острова'),
    ('Колумбия'),
    ('Коморы'),
    ('Республика Конго'),
    ('Корейская Народно-Демократическая Республика'),
    ('Республика Корея'),
    ('Королевство Нидерландов'),
    ('Коста-Рика'),
    ('Кот-д’Ивуар'),
    ('Куба'),
    ('Кувейт'),
    ('Острова Кука'),
    ('Курдистан'),
    ('Кыргызстан'),
    ('Кюрасао'),
    ('Лаос'),
    ('Латвия'),
    ('Лесото'),
    ('Либерия'),
    ('Ливан'),
    ('Ливия'),
    ('Листенбург'),
    ('Литва'),
    ('Лихтенштейн'),
    ('Люксембург'),
    ('Маврикий'),
    ('Мавритания'),
    ('Мадагаскар'),
    ('Малави'),
    ('Малайзия'),
    ('Мали'),
    ('Мальдивы'),
    ('Мальта'),
    ('Мальтийский орден'),
    ('Марокко'),
    ('Маршалловы Острова'),
    ('Мексика'),
    ('Мозамбик'),
    ('Молдова'),
    ('Монако'),
    ('Монголия'),
    ('Монтсеррат'),
    ('Мьянма'),
    ('Намибия'),
    ('Науру'),
    ('Непал'),
    ('Нигер'),
    ('Нигерия'),
    ('Нидерланды'),
    ('Никарагуа'),
    ('Ниуэ'),
    ('Новая Зеландия'),
    ('Новая Каледония'),
    ('Новый Судан'),
    ('Норвегия'),
    ('Остров Норфолк'),
    ('Объединённые Арабские Эмираты'),
    ('Оман'),
    ('Пакистан'),
    ('Палау'),
    ('Панама'),
    ('Папуа — Новая Гвинея'),
    ('Парагвай'),
    ('Перу'),
    ('Острова Питкэрн'),
    ('Политическая система Словении'),
    ('Польша'),
    ('Португалия'),
    ('Правительство вольного города Данцига в изгнании'),
    ('Правительство мира и единства'),
    ('Пунтленд'),
    ('Пуэрто-Рико'),
    ('Пятая Французская республика'),
    ('Республика Ангилья'),
    ('Республика Косово'),
    ('Рожава'),
    ('Остров Рождества (Австралия)'),
    ('Россия'),
    ('Руанда'),
    ('Румыния'),
    ('Сальвадор'),
    ('Самоа'),
    ('Сан-Марино'),
    ('Сан-Томе и Принсипи'),
    ('Саудовская Аравия'),
    ('Северная Македония'),
    ('Сейшельские Острова'),
    ('Сен-Мартен (владение Франции)'),
    ('Сен-Пьер и Микелон'),
    ('Сенегал'),
    ('Сент-Винсент и Гренадины'),
    ('Сент-Китс и Невис'),
    ('Сент-Люсия'),
    ('Сербия'),
    ('Сингапур'),
    ('Синт-Мартен'),
    ('Сирия'),
    ('Словакия'),
    ('Словения'),
    ('Соломоновы Острова'),
    ('Сомали'),
    ('Союзное государство'),
    ('Суверенное государство Ордена Бекташи'),
    ('Суверенное правительство Идинджи'),
    ('Судан'),
    ('Суринам'),
    ('Соединённые Штаты Америки'),
    ('Сьерра-Леоне'),
    ('Таджикистан'),
    ('Таиланд'),
    ('Танзания'),
    ('Теркс и Кайкос'),
    ('Того'),
    ('Токелау'),
    ('Тонга'),
    ('Тринидад и Тобаго'),
    ('Тувалу'),
    ('Тунис'),
    ('Турецкий Курдистан'),
    ('Туркменистан'),
    ('Турция'),
    ('Уганда'),
    ('Узбекистан'),
    ('Украина'),
    ('Уоллис и Футуна'),
    ('Уругвай'),
    ('Федеративные Штаты Микронезии'),
    ('Федерация семи общин'),
    ('Фиджи'),
    ('Филиппины'),
    ('Финляндия'),
    ('Фолклендские острова'),
    ('Франция'),
    ('Французская Полинезия'),
    ('Хиршабелле'),
    ('Хорватия'),
    ('Центральноафриканская Республика'),
    ('Чад'),
    ('Черногория'),
    ('Чехия'),
    ('Чили'),
    ('Швейцария'),
    ('Швеция'),
    ('Шри-Ланка'),
    ('Эквадор'),
    ('Экваториальная Гвинея'),
    ('Энен-Кио'),
    ('Эритрея'),
    ('Эсватини'),
    ('Эстония'),
    ('Эфиопия'),
    ('Южная Георгия и Южные Сандвичевы Острова'),
    ('Южно-Африканская Республика'),
    ('Южный Судан'),
    ('Ямайка'),
    ('Япония');

CREATE TABLE IF NOT EXISTS public.manufacturers
(
    manufacturer_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    f_key_country_id bigint NOT NULL,
    manufacturer_name character varying(64) NOT NULL,
    CONSTRAINT manufacturers_pkey PRIMARY KEY (manufacturer_id),
    CONSTRAINT manufacturers_f_key_country_id_fkey FOREIGN KEY (f_key_country_id) REFERENCES public.country (country_id)
);

--changeset Bushuev:Products
CREATE TABLE IF NOT EXISTS public.product_types
(
    type_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    type character varying(64) NOT NULL,
    parent_id integer,
    CONSTRAINT product_types_pkey PRIMARY KEY (type_id),
    CONSTRAINT product_types_type_key UNIQUE (type),
    CONSTRAINT product_types_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES public.product_types (type_id)
);

CREATE TABLE IF NOT EXISTS public.products
(
    product_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    product_name character varying(128) NOT NULL,
    price numeric(10,2) NOT NULL,
    f_key_supplier_itn character varying(12) NOT NULL,
    recipe boolean NOT NULL,
    active_ingredient character varying(25),
    expiration character varying(20),
    composition text,
    indications text,
    contraindications text,
    side_effects text,
    interaction text,
    admission_course text,
    overdose text,
    release_form text,
    special_instructions text,
    storage_conditions text,
    fk_product_manufacturer bigint,
    f_key_product_type integer,
    CONSTRAINT product_pkey PRIMARY KEY (product_id),
    CONSTRAINT f_key_product_manufacturer FOREIGN KEY (fk_product_manufacturer) REFERENCES public.manufacturers (manufacturer_id),
    CONSTRAINT product_f_key_product_types_fkey FOREIGN KEY (f_key_product_type) REFERENCES public.product_types (type_id),
    CONSTRAINT product_f_key_supplier_itn_fkey FOREIGN KEY (f_key_supplier_itn) REFERENCES public.suppliers (itn)
);

CREATE TABLE IF NOT EXISTS public.product_type_mapping
(
    product_id bigint NOT NULL,
    category_id integer NOT NULL,
    is_main boolean DEFAULT true,
    CONSTRAINT product_type_mapping_pkey PRIMARY KEY (product_id, category_id),
    CONSTRAINT product_type_mapping_id_fkey FOREIGN KEY (category_id)
        REFERENCES public.product_types (type_id),
    CONSTRAINT product_type_mapping_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES public.products (product_id)
);

CREATE TABLE IF NOT EXISTS public.product_images
(
    image_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    image_path character varying(512) NOT NULL,
    f_key_product_id bigint NOT NULL,
    CONSTRAINT product_image_pkey PRIMARY KEY (image_id),
    CONSTRAINT product_image_image_path_key UNIQUE (image_path),
    CONSTRAINT product_image_f_key_product_id_fkey FOREIGN KEY (f_key_product_id)
        REFERENCES public.products (product_id)
);

--changeset Bushuev:Users
CREATE TABLE IF NOT EXISTS public.users
(
    name character varying(25) NOT NULL,
    surname character varying(25) NOT NULL,
    last_name character varying(25),
    mobile_phone character varying(18) NOT NULL,
    password character varying(256),
    CONSTRAINT users_pkey PRIMARY KEY (mobile_phone)
);

CREATE TABLE IF NOT EXISTS public.roles
(
    role_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    role_name character varying(25) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (role_id),
    CONSTRAINT roles_role_name_key UNIQUE (role_name)
);

INSERT INTO roles (role_name) VALUES ('ADMIN'), ('OPERATOR'), ('CUSTOMER');

CREATE TABLE IF NOT EXISTS public.user_roles
(
    id_user character varying(18) NOT NULL,
    id_role integer NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (id_user, id_role),
    CONSTRAINT user_roles_id_role_fkey FOREIGN KEY (id_role) REFERENCES public.roles (role_id),
    CONSTRAINT user_roles_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.users (mobile_phone)
);

--changeset Bushuev:Pharmacy_branch
CREATE TABLE IF NOT EXISTS public.pharmacy_branches
(
    branch_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    f_key_address_id bigint NOT NULL,
    name character varying(32),
    warehouse_limitation integer,
    branch_phone character varying(18),
    user_supervisor character varying(18),
    is_active boolean NOT NULL DEFAULT true,
    CONSTRAINT pharmacy_branches_pkey PRIMARY KEY (branch_id),
    CONSTRAINT pharmacy_branches_f_key_address_id_fkey FOREIGN KEY (f_key_address_id) REFERENCES public.addresses (address_id),
    CONSTRAINT pharmacy_branches_user_supervisor_fkey FOREIGN KEY (user_supervisor) REFERENCES public.users (mobile_phone)
);

--changeset Bushuev:Orders
CREATE TABLE IF NOT EXISTS public.orders
(
    order_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    status_order smallint,
    date timestamp with time zone NOT NULL,
    f_key_user_id character varying(15) NOT NULL,
    f_key_branch_id bigint NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (order_id),
    CONSTRAINT orders_f_key_branch_id_fkey FOREIGN KEY (f_key_branch_id) REFERENCES public.pharmacy_branches (branch_id),
    CONSTRAINT orders_f_key_user_id_fkey FOREIGN KEY (f_key_user_id) REFERENCES public.users (mobile_phone)
);

CREATE TABLE IF NOT EXISTS public.order_items
(
    obj_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    obj_amount integer DEFAULT 1,
    obj_price numeric(10,2) NOT NULL,
    f_key_product_id bigint NOT NULL,
    f_key_order_id bigint NOT NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (obj_id),
    CONSTRAINT order_items_f_key_product_id_fkey FOREIGN KEY (f_key_product_id) REFERENCES public.products (product_id),
    CONSTRAINT order_items_f_key_order_id_fkey FOREIGN KEY (f_key_order_id) REFERENCES public.orders (order_id)
);

--changeset Bushuev:Branch_storage
CREATE TABLE IF NOT EXISTS public.branches_opening_hours
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    branch_id bigint NOT NULL,
    day_of_week smallint NOT NULL,
    open_time time without time zone NOT NULL,
    close_time time without time zone NOT NULL,
    CONSTRAINT branches_opening_hours_pkey PRIMARY KEY (id),
    CONSTRAINT branches_opening_hours_branch_id_day_of_week_key UNIQUE (branch_id, day_of_week),
    CONSTRAINT branches_opening_hours_branch_id_fkey FOREIGN KEY (branch_id)
        REFERENCES public.pharmacy_branches (branch_id),
    CONSTRAINT branches_opening_hours_day_of_week_check CHECK (day_of_week >= 0 AND day_of_week <= 6)
);

CREATE TABLE IF NOT EXISTS public.branch_reservation
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    amount integer NOT NULL,
    reserved_at timestamp with time zone NOT NULL,
    expires_at timestamp with time zone NOT NULL,
    f_key_product_id bigint NOT NULL,
    f_key_branch_id bigint NOT NULL,
    f_key_user_id character varying(15) NOT NULL,
    f_key_order_id bigint,
    CONSTRAINT branch_reservation_pkey PRIMARY KEY (id),
    CONSTRAINT branch_reservation_f_key_branch_id_fkey FOREIGN KEY (f_key_branch_id)
        REFERENCES public.pharmacy_branches (branch_id),
    CONSTRAINT branch_reservation_f_key_customer_id_fkey FOREIGN KEY (f_key_user_id)
        REFERENCES public.users (mobile_phone),
    CONSTRAINT branch_reservation_f_key_product_id_fkey FOREIGN KEY (f_key_product_id)
        REFERENCES public.products (product_id),
    CONSTRAINT branch_reservation_f_key_order_id_fkey FOREIGN KEY (f_key_order_id)
        REFERENCES public.orders (order_id),
    CONSTRAINT branch_reservation_amount_check CHECK (amount > 0),
    CONSTRAINT branch_reservation_check CHECK (expires_at > reserved_at)
);

CREATE TABLE IF NOT EXISTS public.branch_user_assignment
(
    user_id character varying(18) NOT NULL,
    branch_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    CONSTRAINT branch_user_assignment_pkey PRIMARY KEY (user_id, branch_id),
    CONSTRAINT branch_user_assignment_branch_id_fkey FOREIGN KEY (branch_id)
        REFERENCES public.pharmacy_branches (branch_id),
    CONSTRAINT branch_user_assignment_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (mobile_phone)
);

CREATE TABLE IF NOT EXISTS public.storage
(
    record_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    f_key_product_id bigint NOT NULL,
    f_key_branch_id bigint NOT NULL,
    amount integer DEFAULT 0,
    CONSTRAINT storage_pkey PRIMARY KEY (record_id),
    CONSTRAINT storage_f_key_product_id_f_key_branch_id_key UNIQUE (f_key_product_id, f_key_branch_id),
    CONSTRAINT storage_f_key_branch_id_fkey FOREIGN KEY (f_key_branch_id) REFERENCES public.pharmacy_branches (branch_id),
    CONSTRAINT storage_f_key_product_id_fkey FOREIGN KEY (f_key_product_id) REFERENCES public.products (product_id)
);

--changeset Bushuev:Cart
CREATE TABLE IF NOT EXISTS public.carts
(
    cart_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    f_key_user_id character varying(15) NOT NULL,
    CONSTRAINT carts_pkey PRIMARY KEY (cart_id),
    CONSTRAINT carts_f_key_user_id_key UNIQUE (f_key_user_id),
    CONSTRAINT carts_f_key_user_id_fkey FOREIGN KEY (f_key_user_id)
        REFERENCES public.users (mobile_phone)
);

CREATE TABLE IF NOT EXISTS public.cart_items
(
    obj_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    obj_amount integer DEFAULT 0,
    f_key_product_id bigint NOT NULL,
    f_key_cart_id bigint,
    CONSTRAINT shopping_carts_pkey PRIMARY KEY (obj_id),
    CONSTRAINT cart_items_f_key_cart_id_fkey FOREIGN KEY (f_key_cart_id)
        REFERENCES public.carts (cart_id),
    CONSTRAINT shopping_carts_f_key_product_id_fkey FOREIGN KEY (f_key_product_id)
        REFERENCES public.products (product_id)
);

--changeset Bushuev:Daily products
CREATE TABLE IF NOT EXISTS public.daily_featured_products
(
    id smallint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT daily_featured_products_pkey PRIMARY KEY (id),
    CONSTRAINT daily_featured_products_product_id_key UNIQUE (product_id),
    CONSTRAINT daily_featured_products_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES public.products (product_id)
);

CREATE TABLE IF NOT EXISTS public.daily_featured_products_changelog
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    created_at timestamp with time zone DEFAULT now(),
    CONSTRAINT daily_featured_products_changelog_pkey PRIMARY KEY (id)
);

--changeset Bushuev:Transactions
CREATE TABLE IF NOT EXISTS public.transaction_types
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    transaction_name character varying(25) NOT NULL,
    CONSTRAINT transaction_types_pkey PRIMARY KEY (id),
    CONSTRAINT transaction_types_transaction_name_key UNIQUE (transaction_name)
);

CREATE TABLE IF NOT EXISTS public.transaction_history
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    completed_at timestamp with time zone NOT NULL,
    f_key_transaction_type integer NOT NULL,
    f_key_branch_id bigint NOT NULL,
    f_key_order_id bigint,
    CONSTRAINT transaction_history_pkey PRIMARY KEY (id),
    CONSTRAINT transaction_history_f_key_branch_id_fkey FOREIGN KEY (f_key_branch_id)
        REFERENCES public.pharmacy_branches (branch_id),
    CONSTRAINT transaction_history_f_key_order_id_fkey FOREIGN KEY (f_key_order_id)
        REFERENCES public.orders (order_id),
    CONSTRAINT transaction_history_f_key_transaction_type_fkey FOREIGN KEY (f_key_transaction_type)
        REFERENCES public.transaction_types (id)
);

CREATE TABLE IF NOT EXISTS public.transaction_items
(
    f_key_product_id bigint NOT NULL,
    f_key_transaction_id bigint NOT NULL,
    amount integer NOT NULL,
    price numeric(10,2) NOT NULL,
    CONSTRAINT transaction_items_pkey PRIMARY KEY (f_key_product_id, f_key_transaction_id),
    CONSTRAINT transaction_items_f_key_product_id_fkey FOREIGN KEY (f_key_product_id)
        REFERENCES public.products (product_id),
    CONSTRAINT transaction_items_f_key_transaction_id_fkey FOREIGN KEY (f_key_transaction_id)
        REFERENCES public.transaction_history (id)
);

--changeset Bushuev:News
CREATE TABLE IF NOT EXISTS public.news_type
(
    type character varying(25) NOT NULL,
    CONSTRAINT news_type_pkey PRIMARY KEY (type)
);

CREATE TABLE IF NOT EXISTS public.news
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    slug character varying(64) NOT NULL,
    created_time timestamp with time zone NOT NULL,
    title character varying(255) NOT NULL,
    body text NOT NULL,
    f_key_type character varying(25),
    CONSTRAINT news_pkey PRIMARY KEY (id),
    CONSTRAINT news_slug_key UNIQUE (slug),
    CONSTRAINT news_f_key_type_fkey FOREIGN KEY (f_key_type)
        REFERENCES public.news_type (type)
);

CREATE TABLE IF NOT EXISTS public.news_image
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    image_work_path character varying(4096) NOT NULL,
    f_key_news_id bigint,
    CONSTRAINT p_key_id PRIMARY KEY (id),
    CONSTRAINT news_image_f_key_news_id_fkey FOREIGN KEY (f_key_news_id)
        REFERENCES public.news (id)
);