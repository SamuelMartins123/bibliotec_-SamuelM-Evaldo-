package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.repository;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Listar livros em ordem alfabética
    List<Livro> findAllByOrderByTituloAsc();

    // Buscar livro pelo ISBN
    Optional<Livro> findByIsbn(String isbn);

    // Buscar livros pelo título
    List<Livro> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    // Verificar se existem livros vinculados à categoria
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Livro l WHERE l.categoria.id = :categoriaId")
    boolean possuiLivrosNaCategoria(
            @Param("categoriaId") Long categoriaId
    );

}