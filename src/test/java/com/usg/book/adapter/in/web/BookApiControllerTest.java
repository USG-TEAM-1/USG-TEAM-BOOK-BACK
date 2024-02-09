package com.usg.book.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usg.book.adapter.in.web.dto.BookRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Long memberId = 1L;
        String bookName = "bookName";
        String bookComment = "bookComment";
        String bookPostName = "bookPostName";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookRegisterRequest request = createBookRegisterRequest(memberId, bookName, bookComment, bookPostName, bookPrice, isbn);

        // when
        ResultActions perform = mockMvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("책 등록이 완료되었습니다."));
    }

    private BookRegisterRequest createBookRegisterRequest(Long memberId,
                                                          String bookName,
                                                          String bookComment,
                                                          String bookPostName,
                                                          Integer bookPrice,
                                                          String isbn) {
        return BookRegisterRequest
                .builder()
                .memberId(memberId)
                .bookName(bookName)
                .bookComment(bookComment)
                .bookPostName(bookPostName)
                .bookPrice(bookPrice)
                .isbn(isbn)
                .build();
    }
}
