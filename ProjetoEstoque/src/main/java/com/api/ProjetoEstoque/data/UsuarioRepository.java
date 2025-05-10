package com.api.ProjetoEstoque.data;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer>{
    Optional<UsuarioEntity> findByUsuario(String usuario);
}
