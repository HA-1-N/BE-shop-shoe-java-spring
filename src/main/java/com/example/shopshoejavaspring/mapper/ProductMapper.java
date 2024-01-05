package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.product.GetIdNameProductDTO;
import com.example.shopshoejavaspring.dto.product.ProductDTO;
import com.example.shopshoejavaspring.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, ProductQuantityMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    List<ProductDTO> toProductDTOs(List<Product> products);

    ProductDTO toDto(Product product);

    List<GetIdNameProductDTO> toGetIdNameProductDTOs(List<Product> products);
}
