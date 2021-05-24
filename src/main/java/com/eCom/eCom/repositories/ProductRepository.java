package com.eCom.eCom.repositories;

import java.util.List;

import com.eCom.eCom.entities.Product;
import com.eCom.eCom.entities.WebsiteUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
  Product findByName(@Param("name") String name);

  @Override
  @RestResource(exported = false)
  void delete(Product entity);

  @Override
  @RestResource(exported = false)
  void deleteAll();

  @Override
  @RestResource(exported = false)
  void deleteAll(Iterable<? extends Product> entities);

  @Override
  @RestResource(exported = false)
  void deleteById(Long aLong);
}
