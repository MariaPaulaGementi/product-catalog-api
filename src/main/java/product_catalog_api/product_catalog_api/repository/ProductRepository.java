package product_catalog_api.product_catalog_api.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import product_catalog_api.product_catalog_api.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Repository
public class ProductRepository implements JpaRepository<Product, Long> {

}
