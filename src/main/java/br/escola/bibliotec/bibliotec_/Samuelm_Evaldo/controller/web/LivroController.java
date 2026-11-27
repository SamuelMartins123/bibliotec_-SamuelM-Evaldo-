
package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.controller.web;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model.Livro;
import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.service.LivroService;
import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final CategoriaService categoriaService;

    public LivroController(
            LivroService livroService,
            CategoriaService categoriaService) {

        this.livroService = livroService;
        this.categoriaService = categoriaService;
    }

    // Listar livros
    // Listar e pesquisar livros
    @GetMapping
    public String listar(
            @RequestParam(required = false) String busca,
            Model model) {

        if (busca == null || busca.isBlank()) {

            model.addAttribute(
                    "livros",
                    livroService.listarTodos()
            );

        } else {

            model.addAttribute(
                    "livros",
                    livroService.buscarPorTitulo(busca.trim())
            );
        }

        model.addAttribute("busca", busca);

        return "livros/lista";
    }

    // Abrir formulário de cadastro
    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("livro", new Livro());

        formulario(model);

        return "livros/form";
    }

    // Abrir formulário de edição
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {

        Livro livro = livroService.buscarPorId(id);

        model.addAttribute("livro", livro);

        formulario(model);

        return "livros/form";
    }

    // Salvar ou atualizar livro
    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("livro") Livro livro,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Verifica se a categoria foi selecionada
        if (livro.getCategoria() == null
                || livro.getCategoria().getId() == null) {

            resultado.rejectValue(
                    "categoria.id",
                    "categoria.obrigatoria",
                    "Selecione uma categoria"
            );
        }

        // Verifica se o ISBN já está cadastrado
        if (livro.getIsbn() != null
                && !livro.getIsbn().isBlank()
                && livroService.isbnDuplicado(livro)) {

            resultado.rejectValue(
                    "isbn",
                    "isbn.duplicado",
                    "Este ISBN já está cadastrado em outro livro."
            );
        }

        // Se houver erros, retorna ao formulário
        if (resultado.hasErrors()) {

            formulario(model);

            return "livros/form";
        }

        // Salva ou atualiza o livro
        livroService.salvar(livro);

        // Mensagem dependendo da operação
        if (livro.getId() == null) {

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Livro cadastrado com sucesso!"
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Livro atualizado com sucesso!"
            );
        }

        return "redirect:/livros";
    }

    // Carregar categorias no formulário
    private void formulario(Model model) {

        model.addAttribute(
                "categorias",
                categoriaService.listarTodas()
        );
    }
}