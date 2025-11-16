package product_catalog_api.product_catalog_api.controller;


import org.springframework.web.bind.annotation.*;
import product_catalog_api.product_catalog_api.model.Product;
import product_catalog_api.product_catalog_api.repository.ProductRepository;

import java.util.List;
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> list() {
        return repository.findAll();
    }


    @GetMapping("/{id}")
    public Product searchId(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @PostMapping
    public Product save(@RequestBody Product produto) {
        return repository.save(produto);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product produtoAtualizado) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setNome(produtoAtualizado.getNome());
        product.setDescricao(produtoAtualizado.getDescricao());
        product.setPreco(produtoAtualizado.getPreco());
        product.setEstoque(produtoAtualizado.getEstoque());
        return repository.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
