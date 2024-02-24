package com.usg.book;

import com.usg.book.adapter.out.api.BookISBNCheckAdapter;
import com.usg.book.adapter.out.api.ImageGcsAdapter;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationExternalApiMockingTestSupporter {

    @Mock
    protected ImageGcsAdapter imageGcsAdapter;
    @Mock
    protected BookISBNCheckAdapter bookISBNCheckAdapter;
}
