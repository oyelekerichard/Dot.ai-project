package com.dot.ai;

import com.dot.ai.dtos.request.TransferRequest;
import com.dot.ai.dtos.response.DotApiResponse;
import com.dot.ai.entites.Transaction;
import com.dot.ai.repositories.TransactionRepository;
import com.dot.ai.repositories.UserRepository;
import com.dot.ai.service.TransferService;
import com.dot.ai.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = DotApplicationTests.class)
@ExtendWith(MockitoExtension.class)
class DotApplicationTests {
    @InjectMocks
    private TransferService transferService;
    @InjectMocks
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TransactionRepository transactionRepository;


    @Test
    void testGetAllTransactions() {
        List<Transaction> list = new ArrayList<>();

        Transaction transaction = new Transaction("2222222", new BigDecimal(50000.0), 0.00, "0", "testing transaction", "COMPLETED", true, 10.0, "1", LocalDateTime.now());
        Transaction transaction2 = new Transaction("2222222", new BigDecimal(50000.0), 0.00, "0", "testing transaction", "COMPLETED", true, 10.0, "2", LocalDateTime.now());
        Transaction transaction3 = new Transaction("2222222", new BigDecimal(50000.0), 0.00, "0", "testing transaction", "COMPLETED", true, 10.0, "3", LocalDateTime.now());

        list.add(transaction);
        list.add(transaction2);
        list.add(transaction3);

        when(transactionRepository.findAll()).thenReturn(list);
        List<Transaction> empList = transactionRepository.findAll();
        assertEquals(3, empList.size());

        verify(transactionRepository, times(1)).findAll();

    }

}
