package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.controller.web;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.model.Categoria;
import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Listar categorias
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "categorias",
                categoriaService.listarTodas()
        );

        return "categorias/lista";
    }

    // Abrir formulário de cadastro
    @GetMapping("/nova")
    public String nova(Model model) {

        model.addAttribute("categoria", new Categoria());

        return "categorias/form";
    }

    // Salvar categoria
    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("categoria") Categoria categoria,
            BindingResult resultado,
            RedirectAttributes redirectAttributes) {

        // Remove espaços antes de verificar o nome
        if (categoria.getNome() != null) {
            categoria.setNome(categoria.getNome().trim());
        }

        // Verifica se o nome já está cadastrado
        if (!resultado.hasErrors()
                && categoriaService.nomeJaExiste(categoria)) {

            resultado.rejectValue(
                    "nome",
                    "nome.duplicado",
                    "Já existe uma categoria com este nome"
            );
        }

        // Se houver erros, retorna ao formulário
        if (resultado.hasErrors()) {
            return "categorias/form";
        }

        // Salva a categoria
        categoriaService.salvar(categoria);

        // Mensagem de sucesso
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Categoria salva com sucesso!"
        );

        return "redirect:/categorias";
    }

    // Abrir formulário de edição
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {

        var categoria = categoriaService.buscarPorId(id);

        if (categoria.isEmpty()) {
            return "redirect:/categorias";
        }

        model.addAttribute("categoria", categoria.get());

        return "categorias/form";
    }

    // Excluir categoria
    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        boolean excluida = categoriaService.excluir(id);

        if (excluida) {

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Categoria excluída com sucesso!"
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Não é possível excluir esta categoria porque existem livros vinculados a ela."
            );
        }

        return "redirect:/categorias";
    }
}