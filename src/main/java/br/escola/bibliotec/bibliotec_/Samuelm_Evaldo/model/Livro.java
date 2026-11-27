
package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Livro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 100, message = "O autor deve ter no máximo 100 caracteres")
    private String autor;

    @NotBlank(message = "O ISBN é obrigatório")
    @Pattern(regexp = "\\d{13}", message = "O ISBN deve conter exatamente 13 números")
    @Column(unique = true, length = 13, nullable = false)
    private String isbn;

    @Positive(message = "O ano deve ser positivo")
    private Integer anoPublicacao;

    @NotNull(message = "A quantidade total é obrigatória")
    @Min(value = 1, message = "A quantidade total deve ser pelo menos 1")
    private Integer quantidadeTotal;


    private Integer quantidadeDisponivel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Livro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
