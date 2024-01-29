package com.example.shopshoejavaspring.mapper;

import com.example.shopshoejavaspring.dto.banner.BannerDTO;
import com.example.shopshoejavaspring.entity.Banner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BannerMapper extends EntityMapper<BannerDTO, Banner>{
    List<BannerDTO> toDTOs(List<Banner> banners);
}
