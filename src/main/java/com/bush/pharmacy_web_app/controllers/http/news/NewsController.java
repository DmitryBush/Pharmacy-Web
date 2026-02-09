package com.bush.pharmacy_web_app.controllers.http.news;

import com.bush.pharmacy_web_app.service.news.NewsService;
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
    private final NewsService newsService;

    @GetMapping
    public String getNews(Model model) {
        return "/news/news-list";
    }

    @GetMapping("/{slug}")
    public String getNewsBySlug(Model model, @PathVariable String slug) {
        model.addAttribute("newsSlug", slug);
        model.addAttribute("newsTitle", newsService.getNewsBySlug(slug).title());
        return "/news/news";
    }
}
