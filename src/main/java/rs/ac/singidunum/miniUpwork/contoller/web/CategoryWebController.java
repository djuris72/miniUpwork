package rs.ac.singidunum.miniUpwork.contoller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
