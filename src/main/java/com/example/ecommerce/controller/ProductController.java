package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/menu")
    public String getAllProducts(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "menu";
    }

    @GetMapping("/create/product")
    public String createProduct(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);
        return "create_product";
    }

    @PostMapping("/create/product")
    public String updateProduct(@ModelAttribute("product") ProductDto productDto) {
        productService.save(productDto);
        return "redirect:/create/product";
    }

    @GetMapping("/menu/{productId}/edit")
    public String editProduct(@PathVariable("productId") Long productId, Model model) {
        ProductDto product = productService.findById(productId);
        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping("/edit/product/{productId}")
    public String updateEditedProduct(@ModelAttribute("product") ProductDto productDto, @PathVariable("productId") Long productId) {
        productDto.setId(productId);
        productService.save(productDto);
        return "redirect:/menu/" + productDto.getCategory().toLowerCase();
    }

    @GetMapping("/menu/{productId}/delete")
    public String editDonut(@PathVariable("productId") Long productId) {
        ProductDto productDto = productService.findById(productId);
        productService.delete(productDto);
        return "redirect:/menu/" + productDto.getCategory().toLowerCase();
    }

    @GetMapping("/menu/donuts")
    public String findDonuts(Model model) {
        List<ProductDto> products = productService.findDonuts();
        model.addAttribute("products", products);
        model.addAttribute("category", "Donuts");
        model.addAttribute("category_text", "Sweet life");
        return "category_show";
    }

    @GetMapping("/menu/coffee")
    public String findCoffee(Model model) {
        List<ProductDto> products = productService.findCoffee();
        model.addAttribute("products", products);
        model.addAttribute("category", "Coffee");
        model.addAttribute("category_text", "Start the day with joy");
        return "category_show";
    }

    @GetMapping("/menu/drinks")
    public String findDrinks(Model model) {
        List<ProductDto> products = productService.findDrinks();
        model.addAttribute("products", products);
        model.addAttribute("category", "Drinks");
        model.addAttribute("category_text", "Fresh drinks");
        return "category_show";
    }

    @GetMapping("/product/view/{productId}")
    public String viewProduct(@PathVariable("productId") Long productId, Model model) {
        ProductDto productDto = productService.findById(productId);
        model.addAttribute("product", productDto);
        return "product_view";
    }

    @GetMapping("/cashier")
    public String getCashierPage(Model model) {
        List<ProductDto> productDtos = productService.getAllProducts();
        model.addAttribute("products", productDtos);
        return "cashier_page";
    }

//    @PostMapping("/cart/find")
//    @ResponseBody
//    public ResponseEntity<?> findProduct(@RequestParam String code) {
//        try {
//            ProductDto productDto = productService.findByName(code);
//            if (productDto == null) {
//                return ResponseEntity.ok("NotFound");
//            }
//            return ResponseEntity.ok(productDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred: " + e.getMessage());
//        }
//    }

    @GetMapping("/cart/find/{productName}")
    public String getProuctView(@PathVariable("productName") String productName) {
        ProductDto productDto = productService.findByName(productName);
        return "redirect:/product/view/" + productDto.getId();
    }
}
