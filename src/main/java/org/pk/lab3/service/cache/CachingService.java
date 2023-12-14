package org.pk.lab3.service.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.pk.lab3.model.Product;
import org.pk.lab3.model.ProductSummary;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CachingService {

    private static CachingService instance = new CachingService();
    private final Cache<String, Product> productDetailsCache;
    private final Cache<String, List<ProductSummary>> productSummaryListCache;

    public static synchronized CachingService getInstance() {
        if (instance == null) {
            instance = new CachingService();
        }
        return instance;
    }

    private CachingService() {
        productDetailsCache = Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        productSummaryListCache = Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(1)
                .build();
    }

    public List<ProductSummary> getProductSummaryListCache(String cacheKey) {
        return productSummaryListCache.getIfPresent(cacheKey);
    }

    public void addProductSummaryListToCache(String cacheKey, List<ProductSummary> products) {
        productSummaryListCache.put(cacheKey, products);
    }

    public Product getProductDetailsCache(String cacheKey) {
        return productDetailsCache.getIfPresent(cacheKey);
    }

    public void addProductDetailsToCache(String cacheKey, Product product) {
        productDetailsCache.put(cacheKey, product);
    }

    public void invalidateProductDetailsCacheByKey(String cacheKey) {
        productDetailsCache.invalidate(cacheKey);
    }

    public void invalidateProductSummaryListCache() {
        productSummaryListCache.invalidateAll();
    }
}