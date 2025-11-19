package com.example.baitap1.controller;

import com.example.baitap1.model.Product;
import com.example.baitap1.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/index";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Product product = productService.findById(id);
        if (product == null) {
            redirect.addFlashAttribute("error", "Product not found!");
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product/view";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirect) {
        if (product.getId() == null) {
            product.setId(productService.generateId());
        }
        productService.save(product);
        redirect.addFlashAttribute("success", "Product added successfully!");
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Product product = productService.findById(id);
        if (product == null) {
            redirect.addFlashAttribute("error", "Product not found!");
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product/update";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product, RedirectAttributes redirect) {
        productService.update(product.getId(), product);
        redirect.addFlashAttribute("success", "Product updated successfully!");
        return "redirect:/products";
    }

    // Xóa sản phẩm: hiển thị form xác nhận
    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Product product = productService.findById(id);
        if (product == null) {
            redirect.addFlashAttribute("error", "Product not found!");
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product/delete"; // form delete riêng
    }

    // Xử lý xóa khi submit form
    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute Product product, RedirectAttributes redirect) {
        Product p = productService.findById(product.getId());
        if (p != null) {
            productService.delete(product.getId());
            redirect.addFlashAttribute("success", "Product deleted successfully!");
        } else {
            redirect.addFlashAttribute("error", "Product not found!");
        }
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        model.addAttribute("products", productService.searchByName(keyword));
        return "product/index";
    }
}
