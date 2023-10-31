package com.dot.ai.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TransactionsRepository {

//    @PersistenceContext
//    private EntityManager em;
//
//    private final EntityManagerFactory emf;
//
//    @Autowired
//    public TransactionsRepository(EntityManagerFactory entityManagerFactory) {
//        this.emf = entityManagerFactory;
//    }
//
//    public List<Transaction> findTransactionSummaryByDay(String fromDate, String toDate, String userId, String status) {
//
//        String qry = "select * FROM work_order WHERE create_time BETWEEN CAST('%s' AS DATE) "
//                + "AND DATE_ADD(CAST('%s' AS DATE), INTERVAL 1 day) ";
//
//        if (!status.equals("") && !status.isEmpty()) {
//            qry.concat(" and status = " + status);
//        }
//        if (!userId.equals("") && !userId.isEmpty()) {
//            qry.concat(" and user_id = " + status);
//        }
//        Query q = em.createNativeQuery(String.format(qry, fromDate, toDate), Transaction.class);
//        return q.getResultList();
//    }
}
