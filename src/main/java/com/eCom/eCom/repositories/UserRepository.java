package com.eCom.eCom.repositories;

import java.util.List;

import com.eCom.eCom.entities.WebsiteUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<WebsiteUser, Long> {
  Page<WebsiteUser> findByEmail(@Param("email") String email, Pageable pageable);

  @Override
  @RestResource(exported = false)
  void delete(WebsiteUser entity);

  @Override
  @RestResource(exported = false)
  void deleteAll();

  @Override
  @RestResource(exported = false)
  void deleteAll(Iterable<? extends WebsiteUser> entities);

  @Override
  @RestResource(exported = false)
  void deleteById(Long aLong);
}
