package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.product.GetIdNameProductDTO;
import com.example.shopshoejavaspring.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetIdNameProductMapper extends EntityMapper<GetIdNameProductDTO, Product> {
}
