package com.dreamx.kosha.helper;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.model.fimodels.Account;
import com.dreamx.kosha.model.recurringdeposit.RecurringDepositSummary;
import com.dreamx.kosha.model.recurringdeposit.RecurringDepositTransaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author deepika_rajani
 */
@Slf4j
public class RecurringDepositParser {


    public static UserFITxnDTO parse(String json, ObjectMapper objectMapper) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode accountNode = rootNode.path("Account");
            Account<RecurringDepositSummary> account = objectMapper.readValue(accountNode.toString(),
                    objectMapper.getTypeFactory().constructParametricType(Account.class, RecurringDepositSummary.class));

            final double maturityAmount = account.getSummary().getMaturityAmount(); // todo check if maturityAmount or this should be taken?


            // Extract and log the transactions
            JsonNode transactionsNode = accountNode.get("Transactions");
            final JsonNode transactionNode = transactionsNode.get("Transaction");
            final List<RecurringDepositTransaction> fiTxns = objectMapper.readValue(transactionNode.toString(), new TypeReference<>() {
            });

            final FinancialInstrument financialInstrument = FinancialInstrument.RD;

            final List<Transaction> entityTxns = fiTxns.stream()
                    .map(txn -> new Transaction(txn.convertTransactionDateTimeToLocalDateTime(), txn.getAmount(), financialInstrument))
                    .collect(Collectors.toList());

            UserFITxnDTO userFITxnDTO = new UserFITxnDTO(financialInstrument, maturityAmount, LocalDateTime.now(), entityTxns);
//            log.info("RD Investment: {}", userFITxnDTO);

            return userFITxnDTO;
        } catch (IOException e) {
            log.error("Error parsing JSON", e);
        }

        return null;
    }

//    public static void main(String[] args) {
//        parse("{\n" +
//                "  \"Account\": {\n" +
//                "    \"linkedAccRef\": \"RD123456789\",\n" +
//                "    \"maskedAccNumber\": \"XXXX-XXXX-XXXX-5678\",\n" +
//                "    \"version\": \"1.1\",\n" +
//                "    \"type\": \"recurring_deposit\",\n" +
//                "    \"Profile\": {\n" +
//                "      \"Holders\": {\n" +
//                "        \"type\": \"JOINT\",\n" +
//                "        \"Holder\": {\n" +
//                "          \"name\": \"John Doe\",\n" +
//                "          \"dob\": \"2002-09-24\",\n" +
//                "          \"mobile\": \"91729391923\",\n" +
//                "          \"nominee\": \"NOT-REGISTERED\",\n" +
//                "          \"email\": \"qw@gmail.com\",\n" +
//                "          \"pan\": \"AAAPL1234C\",\n" +
//                "          \"ckycCompliance\": true\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Summary\": {\n" +
//                "      \"accountType\": \"FIXED\",\n" +
//                "      \"openingDate\": \"2024-04-01\",\n" +
//                "      \"branch\": \"Main Branch\",\n" +
//                "      \"ifsc\": \"HDFC0001234\",\n" +
//                "      \"maturityAmount\": 120000,\n" +
//                "      \"maturityDate\": \"2027-04-01\",\n" +
//                "      \"description\": \"Recurring Deposit\",\n" +
//                "      \"interestPayout\": \"HALF-YEARLY\",\n" +
//                "      \"interestRate\": \"6.0\",\n" +
//                "      \"principalAmount\": 20000,\n" +
//                "      \"tenureDays\": 0,\n" +
//                "      \"tenureMonths\": 0,\n" +
//                "      \"tenureYears\": 3,\n" +
//                "      \"recurringAmount\": 5000,\n" +
//                "      \"recurringDepositDay\": \"01\",\n" +
//                "      \"interestComputation\": \"COMPOUND\",\n" +
//                "      \"compoundingFrequency\": \"HALF-YEARLY\",\n" +
//                "      \"interestPeriodicPayoutAmount\": 600,\n" +
//                "      \"interestOnMaturity\": 5000,\n" +
//                "      \"currentValue\": 30000\n" +
//                "    },\n" +
//                "    \"Transactions\": {\n" +
//                "      \"startDate\": \"2024-04-01\",\n" +
//                "      \"endDate\": \"2024-10-01\",\n" +
//                "      \"Transaction\": [\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN001\",\n" +
//                "          \"amount\": 5000,\n" +
//                "          \"narration\": \"Recurring Deposit Installment\",\n" +
//                "          \"type\": \"DEPOSIT\",\n" +
//                "          \"mode\": \"ONLINE\",\n" +
//                "          \"balance\": 5000,\n" +
//                "          \"transactionDateTime\": \"2024-04-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-04-01\",\n" +
//                "          \"reference\": \"RD-DEP-001\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN002\",\n" +
//                "          \"amount\": 5000,\n" +
//                "          \"narration\": \"Recurring Deposit Installment\",\n" +
//                "          \"type\": \"DEPOSIT\",\n" +
//                "          \"mode\": \"ONLINE\",\n" +
//                "          \"balance\": 10000,\n" +
//                "          \"transactionDateTime\": \"2024-05-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-05-01\",\n" +
//                "          \"reference\": \"RD-DEP-002\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN003\",\n" +
//                "          \"amount\": 5000,\n" +
//                "          \"narration\": \"Recurring Deposit Installment\",\n" +
//                "          \"type\": \"DEPOSIT\",\n" +
//                "          \"mode\": \"ONLINE\",\n" +
//                "          \"balance\": 15000,\n" +
//                "          \"transactionDateTime\": \"2024-06-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-06-01\",\n" +
//                "          \"reference\": \"RD-DEP-003\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN004\",\n" +
//                "          \"amount\": 5000,\n" +
//                "          \"narration\": \"Recurring Deposit Installment\",\n" +
//                "          \"type\": \"DEPOSIT\",\n" +
//                "          \"mode\": \"ONLINE\",\n" +
//                "          \"balance\": 20000,\n" +
//                "          \"transactionDateTime\": \"2024-07-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-07-01\",\n" +
//                "          \"reference\": \"RD-DEP-004\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN005\",\n" +
//                "          \"amount\": 5000,\n" +
//                "          \"narration\": \"Recurring Deposit Installment\",\n" +
//                "          \"type\": \"DEPOSIT\",\n" +
//                "          \"mode\": \"ONLINE\",\n" +
//                "          \"balance\": 25000,\n" +
//                "          \"transactionDateTime\": \"2024-08-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-08-01\",\n" +
//                "          \"reference\": \"RD-DEP-005\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN006\",\n" +
//                "          \"amount\": 5000,\n" +
//                "          \"narration\": \"Recurring Deposit Installment\",\n" +
//                "          \"type\": \"DEPOSIT\",\n" +
//                "          \"mode\": \"ONLINE\",\n" +
//                "          \"balance\": 30000,\n" +
//                "          \"transactionDateTime\": \"2024-09-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-09-01\",\n" +
//                "          \"reference\": \"RD-DEP-006\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN007\",\n" +
//                "          \"amount\": 600,\n" +
//                "          \"narration\": \"Interest Credit - Half Yearly\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\",\n" +
//                "          \"balance\": 30600,\n" +
//                "          \"transactionDateTime\": \"2024-10-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-10-01\",\n" +
//                "          \"reference\": \"RD-INT-007\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN008\",\n" +
//                "          \"amount\": 300,\n" +
//                "          \"narration\": \"TDS Deduction\",\n" +
//                "          \"type\": \"TDS\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\",\n" +
//                "          \"balance\": 30300,\n" +
//                "          \"transactionDateTime\": \"2024-10-01T12:00:00\",\n" +
//                "          \"valueDate\": \"2024-10-01\",\n" +
//                "          \"reference\": \"RD-TDS-008\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", new ObjectMapper());
//    }
}
