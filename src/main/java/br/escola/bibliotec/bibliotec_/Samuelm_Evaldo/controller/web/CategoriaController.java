
package br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.controller.web;

import br.escola.bibliotec.bibliotec_.Samuelm_Evaldo.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias",
                categoriaService.listarTodas());

        return "categorias/lista";
    }
}