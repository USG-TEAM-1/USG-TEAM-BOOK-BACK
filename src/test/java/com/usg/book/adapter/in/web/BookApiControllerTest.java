package com.usg.book.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("책 등록 API 테스트")
    void registerBookTest() throws Exception {
        // given
        String bookName = "bookName";
        Integer bookRealPrice = 100;
        String author = "author";
        String publisher = "publisher";
        String bookPostName = "bookPostName";
        String bookComment = "bookComment";
        Integer bookPrice = 10;
        String isbn = "isbn";
        String jwt = "Bearer " + MockJWTGenerator.generateToken("email");

        // when
        ResultActions perform = mockMvc.perform(multipart("/api/book")
                .file("images[0]", new byte[10])
                .param("bookName", bookName)
                .param("bookRealPrice", bookRealPrice.toString())
                .param("author", author)
                .param("publisher", publisher)
                .param("bookComment", bookComment)
                .param("bookPostName", bookPostName)
                .param("bookPrice", bookPrice.toString())
                .param("isbn", isbn)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", jwt));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("책 등록이 완료되었습니다."));
    }
}
