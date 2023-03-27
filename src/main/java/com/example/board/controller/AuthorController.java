package com.example.board.controller;

import com.example.board.domain.Author;
import com.example.board.domain.Role;
import com.example.board.service.AuthorService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors/new")
    public String createForm(){
        return "authors/createAuthorForm";
    }
//    회원가입시 많은 데이터들이 넘어올때는 post방식을
    @PostMapping("/authors/new")
    public String create(AuthorPostForm authorPostForm){

        Author author = Author.builder()
                .password(authorPostForm.getPassword())
                .name(authorPostForm.getName())
                .email(authorPostForm.getEmail())
                .build();
        authorService.create(author);
        return "redirect:/";
    }


    //화면에다가 db에서 조회한 값을 넘겨주려면 어떻게?!
    @GetMapping("/authors")
    public String authorList(Model model){
//        key, value 값으로 넘겨줘야한다.
        model.addAttribute("authors", authorService.findAll());
        return "authors/authorList";
    }

    @GetMapping("authors/findById")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String findById(@RequestParam(value="id")Long id, Model model) throws Exception {
        try {
            model.addAttribute("author", authorService.findById(id).orElseThrow(Exception::new));
        }catch (Exception e){
             throw new EntityNotFoundException("postList EntityNotFoundException " + e.getMessage());
        }

        return "authors/authorDetail";
    }

}
