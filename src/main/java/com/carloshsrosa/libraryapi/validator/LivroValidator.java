package com.carloshsrosa.libraryapi.validator;

import com.carloshsrosa.libraryapi.exceptions.CampoInvalidoException;
import com.carloshsrosa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carloshsrosa.libraryapi.model.Livro;
import com.carloshsrosa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LivroValidator {

    @Autowired
    private LivroRepository repository;

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    public boolean validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de " +
                    ANO_EXIGENCIA_PRECO + ", o preço é obrigatório!");
        }
        var livroExistente =  repository.findByIsbn(livro.getIsbn());
        return livroExistente.isPresent();
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    public boolean existeLivroComIsbn(Livro livro) {
        var livroExistente =  repository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null) {
            return livroExistente.isPresent();
        }
        return livroExistente
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}