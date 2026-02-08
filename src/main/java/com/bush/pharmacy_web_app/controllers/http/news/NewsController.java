package com.bush.pharmacy_web_app.controllers.http.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    @GetMapping("/{slug}")
    public String getNews(Model model, @PathVariable String slug) {
        model.addAttribute("newsSlug", slug);
        return "/news/news";
    }
}
