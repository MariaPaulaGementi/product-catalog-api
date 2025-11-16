package product_catalog_api.product_catalog_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double preco;
    private Integer estoque;

}
