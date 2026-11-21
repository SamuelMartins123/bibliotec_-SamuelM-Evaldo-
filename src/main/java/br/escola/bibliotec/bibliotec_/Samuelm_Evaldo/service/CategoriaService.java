package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.service;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model.Categoria;
import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria salvar(Categoria categoria) {
        categoria.setNome(categoria.getNome().trim());
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public boolean nomeJaExiste(Categoria categoria) {

        Optional<Categoria> existente =
                categoriaRepository.findByNomeIgnoreCase(categoria.getNome().trim());

        return existente.isPresent()
                && !existente.get().getId().equals(categoria.getId());
    }

    public void excluir(Long id) {
        categoriaRepository.deleteById(id);
    }

}