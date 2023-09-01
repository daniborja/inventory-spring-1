-- Drop function (stored procedure)
DROP FUNCTION get_products_with_details();

-- Create function (stored procedure)
CREATE OR REPLACE FUNCTION get_products_with_details()
RETURNS TABLE (
    id bigint,
    title character varying(255),
    sku character varying(255),
    description text,
    price numeric,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    deleted boolean,
    category_id bigint,
    category_name character varying(255),
    measurement_id bigint,
    measurement_type character varying(255),
    measurement_value character varying(255),
    image_id bigint,
    image_url character varying(255),
    stock_quantity bigint
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.id,
        p.title,
        p.sku,
        p.description,
        p.price,
        p.created_at,
        p.updated_at,
        p.deleted,
        p.category_id,
        c.name AS category_name,
        pm.id AS measurement_id,
        pm.measurement_type,
        pm.measurement_value,
        pi.id AS image_id,
        pi.image_url,
        s.quantity AS stock_quantity
    FROM product p
    LEFT JOIN category c ON c.id = p.category_id
    LEFT JOIN product_measurement pm ON pm.product_id = p.id
    LEFT JOIN product_image pi ON pi.product_id = p.id
    LEFT JOIN stock s ON s.product_id = p.id
    WHERE p.deleted = false;
END;
$$ LANGUAGE plpgsql;


-- Calling stored procedure
select * from get_products_with_details();