package ru.khrebtov.shopadminapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.khrebtov.persist.CategoryListParam;
import ru.khrebtov.persist.entity.Category;
import ru.khrebtov.shopadminapp.service.CategoryService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listPage(Model model, CategoryListParam param) {
        logger.info("Category list page requested");

        model.addAttribute("categories", categoryService.findWithFilter(param));

        return "categories";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        logger.info("New category page requested");
        model.addAttribute("category", new Category());

        return "category_form";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        logger.info("Edit category page requested");
        model.addAttribute("category", categoryService.findById(id));

        return "category_form";
    }

    @PostMapping
    public String update(@Valid Category category, BindingResult result) {
        logger.info("Saving category");

        if (result.hasErrors()) {
            return "category_form";
        }

        categoryService.save(category);

        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        logger.info("Deleting category");
        categoryService.deleteById(id);

        return "redirect:/category";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
