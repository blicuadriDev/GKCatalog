package com.godknows.gkcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godknows.gkcatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
