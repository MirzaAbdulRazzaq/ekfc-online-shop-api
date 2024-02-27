package com.ekfc.onlinestore.InterfaceRepos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekfc.onlinestore.Models.products.products;

public interface productRepo extends JpaRepository<products, Integer> {
    List<products> findAllByCategory(String category);
}
