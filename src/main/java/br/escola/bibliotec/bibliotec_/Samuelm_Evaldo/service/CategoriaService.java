package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.service;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model.Categoria;
import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.repository.CategoriaRepository;
import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;

    public CategoriaService(
            CategoriaRepository categoriaRepository,
            LivroRepository livroRepository) {

        this.categoriaRepository = categoriaRepository;
        this.livroRepository = livroRepository;
    }

    // Listar categorias
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Salvar categoria
    public Categoria salvar(Categoria categoria) {
        categoria.setNome(categoria.getNome().trim());
        return categoriaRepository.save(categoria);
    }

    // Buscar categoria pelo ID
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    // Verificar se o nome já existe
    public boolean nomeJaExiste(Categoria categoria) {

        Optional<Categoria> existente =
                categoriaRepository.findByNomeIgnoreCase(
                        categoria.getNome().trim()
                );

        return existente.isPresent()
                && !existente.get().getId().equals(categoria.getId());
    }

    // Verificar se existem livros vinculados à categoria
    public boolean possuiLivros(Long id) {
        return livroRepository.possuiLivrosNaCategoria(id);
    }

    // Excluir categoria somente se não possuir livros
    public boolean excluir(Long id) {

        // Verifica se existem livros associados
        if (possuiLivros(id)) {
            return false;
        }

        // Exclui se estiver vazia
        categoriaRepository.deleteById(id);

        return true;
    }

}