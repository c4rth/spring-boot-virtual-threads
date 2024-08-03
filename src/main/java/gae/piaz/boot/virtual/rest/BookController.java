package gae.piaz.boot.virtual.rest;

import gae.piaz.boot.virtual.domain.Book;
import gae.piaz.boot.virtual.domain.dto.BookDTO;
import gae.piaz.boot.virtual.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Generated by Springboot-3layer-Generator at Jan 6, 2021, 8:30:41 PM
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAll() {
        UUID uuid = UUID.randomUUID();
        log.info("getAll() {} running", uuid);

        ResponseEntity<List<BookDTO>> list = ResponseEntity.ok(this.bookRepository.findAll().stream().
                map((e) -> new BookDTO(e.getBookId(),e.getAuthor(),e.getIsbn(),e.getTitle(),e.getYear())).collect(Collectors.toList()));

        log.info("getAll() {} executed", uuid);
        return list;
    }

    @GetMapping(value = "/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllSimple() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO dto) {
        UUID uuid = UUID.randomUUID();
        log.info("saveBook() {} running", uuid);

        Book e = new Book();
        e.setBookId(dto.bookId());
        e.setIsbn(dto.isbn());
        e.setTitle(dto.title());
        e.setAuthor(dto.author());
        e.setYear(dto.year());
        e = bookRepository.save(e);

        ResponseEntity<BookDTO> resp = ResponseEntity.ok(new BookDTO(e.getBookId(),e.getAuthor(),e.getIsbn(),e.getTitle(),e.getYear()));

        log.info("saveBook() {} ", uuid);
        return resp;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO dto) {
        UUID uuid = UUID.randomUUID();
        log.info("updateBook() {} running", uuid);

        Book e = this.bookRepository.findByIsbn(dto.isbn());
        e.setAuthor(dto.author());
        e = this.bookRepository.save(e);

        BookDTO bookDto = new BookDTO(e.getBookId(),e.getAuthor(),e.getIsbn(),e.getTitle(),e.getYear());

        log.info("updateBook() {} executed", uuid);
        return ResponseEntity.ok(bookDto);
    }


}