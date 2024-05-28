package com.webfluxcourse.productservice.service;

import com.webfluxcourse.productservice.dto.ProductDto;
import com.webfluxcourse.productservice.repository.ProductRepository;
import com.webfluxcourse.productservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<ProductDto> getAll() {
        return productRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getById(String id) {
        return productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> dto) {
        return dto.map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::insert)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> dtoMono) {
        return productRepository.findById(id)
                .flatMap(product -> dtoMono.map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

    public Flux<ProductDto> getInPriceRange(int min, int max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }
}
