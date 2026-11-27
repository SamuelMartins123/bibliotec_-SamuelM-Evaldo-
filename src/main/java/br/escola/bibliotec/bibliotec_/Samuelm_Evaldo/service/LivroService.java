package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.service;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model.Livro;
import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    // Listar livros em ordem alfabética
    public List<Livro> listarTodos() {
        return livroRepository.findAllByOrderByTituloAsc();
    }

    // Buscar livros pelo título
    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
    }

    // Buscar livro pelo ID
    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow();
    }

    // Verificar se o ISBN já pertence a outro livro
    public boolean isbnDuplicado(Livro livro) {

        return livroRepository.findByIsbn(livro.getIsbn())
                .map(livroEncontrado ->
                        !livroEncontrado.getId().equals(livro.getId()))
                .orElse(false);
    }

    // Salvar ou atualizar livro
    public Livro salvar(Livro livro) {

        livro.setTitulo(livro.getTitulo().trim());
        livro.setAutor(livro.getAutor().trim());
        livro.setIsbn(livro.getIsbn().trim());

        // Cadastro novo
        if (livro.getId() == null) {

            livro.setQuantidadeDisponivel(
                    livro.getQuantidadeTotal()
            );

        } else {

            // Buscar dados atuais do livro
            Livro atual = livroRepository.findById(livro.getId())
                    .orElseThrow();

            // Calcular quantos exemplares estão emprestados
            int emprestados = atual.getQuantidadeTotal()
                    - atual.getQuantidadeDisponivel();

            // Atualizar quantidade disponível
            livro.setQuantidadeDisponivel(
                    livro.getQuantidadeTotal() - emprestados
            );
        }

        return livroRepository.save(livro);
    }
}