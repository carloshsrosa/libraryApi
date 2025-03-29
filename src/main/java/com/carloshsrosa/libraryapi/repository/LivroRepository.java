package com.carloshsrosa.libraryapi.repository;

import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    boolean existsByAutor(Optional<Autor> autor);

    Optional<Livro> findByIsbn(String isbn);
}
