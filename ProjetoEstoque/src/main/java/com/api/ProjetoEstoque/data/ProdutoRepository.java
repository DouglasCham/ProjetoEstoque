package com.api.ProjetoEstoque.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer>{
    ProdutoEntity findByCod(int cod);
}
