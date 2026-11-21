package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.repository;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c WHERE LOWER(TRIM(c.nome)) = LOWER(TRIM(:nome))")
    Optional<Categoria> findByNomeIgnoreCase(@Param("nome") String nome);

}