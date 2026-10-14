package com.expensetracker.api.repository;

import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.model.Transaction;
import com.expensetracker.api.model.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionSpecifications {

    public static Specification<Transaction> filters(
        int userId,
        LocalDate startDate,
        LocalDate endDate,
        CategoryType category,
        TransactionType type,
        BigDecimal minAmount,
        BigDecimal maxAmount
    ){
          return (root,query,cb)->{
              var predicates = cb.and(
                      cb.equal(root.get("user").get("id"), userId),
                      cb.isTrue(root.get("active"))
              );
              if (startDate != null) {
                  predicates = cb.and(
                          predicates,
                          cb.greaterThanOrEqualTo(
                                  root.get("transactionDate"), startDate
                          )
                  );
              }

              if (endDate != null) {
                  predicates = cb.and(
                          predicates,
                          cb.lessThanOrEqualTo(
                                  root.get("transactionDate"), endDate
                          )
                  );
              }

              if (category != null) {
                  predicates = cb.and(
                          predicates,
                          cb.equal(root.get("category"), category)
                  );
              }

              if (type != null) {
                  predicates = cb.and(
                          predicates,
                          cb.equal(root.get("type"), type)
                  );
              }

              if (minAmount != null) {
                  predicates = cb.and(
                          predicates,
                          cb.greaterThanOrEqualTo(
                                  root.get("amount"), minAmount
                          )
                  );
              }

              if (maxAmount != null) {
                  predicates = cb.and(
                          predicates,
                          cb.lessThanOrEqualTo(
                                  root.get("amount"), maxAmount
                          )
                  );
              }
              return predicates;
          };
    }
}


/*
Spring Data JPA provides these three objects when it builds the database query.

root — access entity fields
Think of root as representing one row of your Transaction entity in the query.
root.get("amount")
root.get("category")
root.get("transactionDate")
root.get("active")
root.get("user").get("id")


These expressions refer to entity attributes, not directly to Java getter calls. For example, root.get("amount") tells JPA to use the transaction's amount field in the SQL condition.




query — represents the whole query
This is the Criteria API's CriteriaQuery object. It represents the query being constructed.
You can use it to configure things such as sorting, selecting results, grouping, or distinct results.
Your current specification doesn't need to use query directly because its conditions can be built using root and cb.




cb — builds conditions
cb stands for CriteriaBuilder. It creates conditions and combines them.
cb.equal(root.get("category"), category)


This means: category must equal the category selected by the user.
Other examples:
- cb.greaterThanOrEqualTo(...) — greater than or equal to.
- cb.lessThanOrEqualTo(...) — less than or equal to.
- cb.and(...) — all conditions must be true.
- cb.isTrue(...) — a Boolean attribute must be true.
 */