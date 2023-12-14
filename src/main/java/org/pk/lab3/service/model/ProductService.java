package org.pk.lab3.service.model;

import lombok.Getter;
import org.pk.lab3.model.Product;
import org.pk.lab3.model.ProductSummary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ProductService {

    // TODO Clean up and refactor methods

    private static final String API_URL = "http://localhost:8080/api/products";
    private final RestTemplate restTemplate;

    @Getter
    private static final ProductService instance = new ProductService();

    private ProductService() {
        restTemplate = new RestTemplate();
    }

    public List<ProductSummary> getAllProducts() {
        ParameterizedTypeReference<List<ProductSummary>> responseType = new ParameterizedTypeReference<>() {};
        try {
            ResponseEntity<List<ProductSummary>> response = restTemplate.exchange(API_URL, HttpMethod.GET, null, responseType);
            return response.getBody();
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error occurred: " + e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductDetails(String productId) {
        String apiUrl = API_URL + "/" + productId;
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

    public void createProduct(Product product) {
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(API_URL, product, Void.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("HTTP Status: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String updateProduct(String productId, Product product) {
        try {
            String apiUrl = API_URL + "/" + productId;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);

            ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            restTemplate.setRequestFactory(requestFactory);

            ResponseEntity<Void> response = restTemplate.exchange(apiUrl, HttpMethod.PATCH, requestEntity, Void.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("HTTP Status: " + response.getStatusCode());
                return null;
            }
            return productId;
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP Status: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteProduct(String productId) {
        try {
            String apiUrl = API_URL + "/" + productId;
            restTemplate.delete(apiUrl);
            return productId;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error occurred: " + e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
