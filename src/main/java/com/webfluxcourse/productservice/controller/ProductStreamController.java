package com.webfluxcourse.productservice.controller;

import com.webfluxcourse.productservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductStreamController {
    @Autowired
    private Flux<ProductDto> flux;

    @GetMapping(value = "stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductsUpdate(@PathVariable int maxPrice) {
        return flux
                .filter(dto -> dto.getPrice() <= maxPrice);
    }
}
