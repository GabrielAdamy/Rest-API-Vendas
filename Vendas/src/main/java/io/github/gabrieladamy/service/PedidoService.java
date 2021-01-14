package io.github.gabrieladamy.service;

import io.github.gabrieladamy.domain.entity.Pedido;
import io.github.gabrieladamy.domain.enums.StatusPedido;
import io.github.gabrieladamy.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );

    Optional <Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
