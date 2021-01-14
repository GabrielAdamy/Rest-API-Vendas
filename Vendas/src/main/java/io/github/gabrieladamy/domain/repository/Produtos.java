package io.github.gabrieladamy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.gabrieladamy.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto,Integer> {
}
