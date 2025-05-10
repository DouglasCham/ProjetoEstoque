package com.api.ProjetoEstoque.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Component
@Table(name = "produtos")
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "O código é obrigatório.")
    @Min(value = 81010000, message = "O código precisa ser válido.")
    @Column(unique = true)
    private int cod;
    
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    
    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    private int quantidade;
    
    @NotBlank(message = "O endereço é obrigatório.")
    private String endereco;
    
    private String descricao;
}
