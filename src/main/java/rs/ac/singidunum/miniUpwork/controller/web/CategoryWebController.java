package rs.ac.singidunum.miniUpwork.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.ac.singidunum.miniUpwork.model.Category;
import rs.ac.singidunum.miniUpwork.service.CategoryService;

@Controller
@RequestMapping("/web/categories")
public class CategoryWebController {

    private final CategoryService categoryService;

    public CategoryWebController(
            CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "categories",
                categoryService.findAll());

        return "categories/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "category",
                new Category());

        return "categories/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Category category) {

        categoryService.save(category);

        return "redirect:/web/categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "category",
                categoryService.findById(id));

        return "categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute Category category) {

        categoryService.update(id, category);

        return "redirect:/web/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        categoryService.delete(id);

        return "redirect:/web/categories";
    }
}
