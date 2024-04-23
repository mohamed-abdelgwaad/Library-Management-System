package com.maids.Library_Management_System;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.maids.Library_Management_System.Controllers.BorrowingController;
import com.maids.Library_Management_System.Models.BorrowingRecord;
import com.maids.Library_Management_System.Repositories.RoleRepository;
import com.maids.Library_Management_System.Services.BorrowingService;

@WithMockUser(username="admin", roles={"USER", "ADMIN"})
@WebMvcTest(BorrowingController.class)
public class BorrowingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingService borrowingService;
	@MockBean
	private RoleRepository roleRepository;
	
    @Test
    public void testBorrowBook_Success() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;
        BorrowingRecord record = new BorrowingRecord();

        given(borrowingService.borrowBook(anyLong(), anyLong())).willReturn(Optional.of(record));

        mockMvc.perform(post("/user/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Book borrowed successfully"));
    }
    @Test

    public void testReturnBook_Success() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;
        BorrowingRecord record = new BorrowingRecord();


        given(borrowingService.returnBook(anyLong(), anyLong())).willReturn(Optional.of(record));

        mockMvc.perform(put("/user/api/return/{bookId}/patron/{patronId}", bookId, patronId)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Book returned successfully"));
    }

}
