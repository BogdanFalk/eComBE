package com.eCom.eCom.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.eCom.eCom.entities.Product;
import com.eCom.eCom.repositories.ProductRepository;
import com.querydsl.core.types.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductController {

  private final ProductRepository productRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/products")
  public Page<Product> findAllProducts() {
    Pageable pageable = PageRequest.of(0, 99);
    return productRepository.findAll(pageable);
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/productByName")
  public Product findByName(@RequestParam("name") String name) {
    return productRepository.findByName(name);
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/getProductsPerPage")
  public Page<Product> findAllProductsPerPage(@RequestParam String numberOfProductsPerPage,
      @RequestParam String numberOfPage) {
    Pageable pageable = PageRequest.of(Integer.valueOf(numberOfPage), Integer.valueOf(numberOfProductsPerPage));
    return productRepository.findAll(pageable);
  }

  public int count(String search) {
    return jdbcTemplate.queryForObject("SELECT count(*) FROM Product WHERE name LIKE '%" + search + "%'",
        Integer.class);
  }

  public int countFiltered(String search, String addIsOnSale, String addIsInStock, String addIsNew) {
    return jdbcTemplate.queryForObject(
        "SELECT count(*) FROM Product WHERE name LIKE '%" + search + "%'" + addIsOnSale + addIsInStock + addIsNew,
        Integer.class);
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/getProductsWhichContain")
  public Page<Product> findAllProductsWhichContain(@RequestParam String search,
      @RequestParam String numberOfProductsPerPage, @RequestParam String numberOfPage) {
    Pageable pageable = PageRequest.of(Integer.valueOf(numberOfPage), Integer.valueOf(numberOfProductsPerPage));
    List<Product> products = jdbcTemplate.query("SELECT * FROM Product WHERE name LIKE '%" + search + "%' LIMIT "
        + pageable.getPageSize() + " OFFSET " + pageable.getOffset(), (rs, rowNum) -> (mapProductResult(rs)));
    return new PageImpl<Product>(products, pageable, count(search));
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/getProductsWhichContainFiltered")
  public Page<Product> findAllProductsWhichContainFiltered(@RequestParam String search,
      @RequestParam String numberOfProductsPerPage, @RequestParam String numberOfPage, @RequestParam String isInStock,
      @RequestParam String isNew, @RequestParam String isOnSale) {
    Pageable pageable = PageRequest.of(Integer.valueOf(numberOfPage), Integer.valueOf(numberOfProductsPerPage));

    String addIsOnSale = Integer.valueOf(isOnSale) == 1 ? " AND isOnSale = 1 " : "";
    String addIsInStock = Integer.valueOf(isInStock) == 1 ? " AND isInStock = 1 " : "";
    String addIsNew = Integer.valueOf(isNew) == 1 ? " AND isNew = 1 " : "";

    String queryString = "SELECT * FROM Product WHERE name LIKE '%" + search + "%'" + addIsOnSale + addIsInStock
        + addIsNew + "LIMIT " + Integer.toString(pageable.getPageSize()) + " OFFSET "
        + Long.toString(pageable.getOffset());

    List<Product> products = jdbcTemplate.query(queryString, (rs, rowNum) -> (mapProductResult(rs)));
    return new PageImpl<Product>(products, pageable, countFiltered(search, addIsOnSale, addIsInStock, addIsNew));
  }

  private Product mapProductResult(final ResultSet rs) throws SQLException {
    Product product = new Product();
    product.setName(rs.getString("name"));
    product.setCategory(rs.getString("category"));
    product.setPrice(rs.getFloat("price"));
    product.setPublishedOn(rs.getString("publishedOn"));
    product.setIsOnSale(rs.getInt("isOnSale"));
    product.setIsNew(rs.getInt("isNew"));
    product.setIsInStock(rs.getInt("isInStock"));
    return product;
  }

  @PostMapping("/products/addProduct")
  @ResponseStatus(HttpStatus.OK)
  public String addProduct(@RequestBody Product product) {
    if (product == null || product.getName() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is invalid");
    }
    if (product.getName().compareTo("") == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is empty");
    }
    jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?,?,?)", product.getName(), product.getCategory(),
        product.getIsInStock(), product.getIsNew(), product.getIsOnSale(), product.getPrice(),
        product.getPublishedOn());
    return "Product Added";
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  void onIllegalArgumentException(IllegalArgumentException exception) {

  }
}
