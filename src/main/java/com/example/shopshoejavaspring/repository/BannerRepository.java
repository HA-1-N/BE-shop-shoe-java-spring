package com.example.shopshoejavaspring.repository;

import com.example.shopshoejavaspring.dto.banner.FilterBannerDTO;
import com.example.shopshoejavaspring.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerRepository extends JpaRepository<Banner, Long> {

}
