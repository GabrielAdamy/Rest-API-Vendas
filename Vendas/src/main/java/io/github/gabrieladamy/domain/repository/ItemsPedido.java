package io.github.gabrieladamy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.gabrieladamy.domain.entity.ItemPedido;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
