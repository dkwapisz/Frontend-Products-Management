package org.pk.lab3.service.model;

import org.pk.lab3.model.Product;
import org.pk.lab3.model.ProductSummary;
import org.pk.lab3.utils.AppConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class ProductService {

    // TODO Clean up and refactor methods

    private final RestTemplate restTemplate;
    private final String productApi;

    private static ProductService instance = new ProductService();

    public static synchronized ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    private ProductService() {
        restTemplate = new RestTemplate();
        setUpHttpRequestFactory();
        productApi = AppConfig.getInstance().getProductApiUrl();
    }

    public List<ProductSummary> getAllProducts() {
        ParameterizedTypeReference<List<ProductSummary>> responseType = new ParameterizedTypeReference<>() {};
        try {
            ResponseEntity<List<ProductSummary>> response = restTemplate.exchange(productApi, HttpMethod.GET, null, responseType);
            return response.getBody();
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error occurred: " + e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductDetails(String productId) {
        String apiUrl = productApi + "/" + productId;
        try {
            ResponseEntity<Product> response = restTemplate.getForEntity(apiUrl, Product.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return new Product();
            } else {
                System.out.println("HTTP Status: " + response.getStatusCode());
                return null;
            }
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error occurred: " + e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product createProduct(Product product) {
        try {
            ResponseEntity<Product> response = restTemplate.postForEntity(productApi, product, Product.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("HTTP Status: " + response.getStatusCode());
                return null;
            }
            return response.getBody();
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error occurred: " + e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateProduct(String productId, Product product) {
        try {
            String apiUrl = productApi + "/" + productId;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);

            ResponseEntity<Void> response = restTemplate.exchange(apiUrl, HttpMethod.PATCH, requestEntity, Void.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("HTTP Status: " + response.getStatusCode());
                return false;
            }
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP Status: " + e.getStatusCode());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteProduct(String productId) {
        try {
            String apiUrl = productApi + "/" + productId;
            restTemplate.delete(apiUrl);
            return true;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error occurred: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpHttpRequestFactory() {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
}
