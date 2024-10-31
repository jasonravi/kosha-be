package com.dreamx.kosha.helper;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.constants.TransactionType;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.model.deposit.DepositSummary;
import com.dreamx.kosha.model.deposit.DepositTransaction;
import com.dreamx.kosha.model.fimodels.Account;
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
public class DepositParser {

    public static UserFITxnDTO parse(String json, ObjectMapper objectMapper) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode accountNode = rootNode.path("Account");
            Account<DepositSummary> account = objectMapper.readValue(accountNode.toString(),
                    objectMapper.getTypeFactory().constructParametricType(Account.class, DepositSummary.class));

            final double value = Double.parseDouble(account.getSummary() == null ? "0" : account.getSummary().getCurrentBalance());
            final LocalDateTime openingDate = LocalDateTime.now();


            // Extract and log the transactions
            JsonNode transactionsNode = accountNode.get("Transactions");
            final JsonNode transactionNode = transactionsNode.get("Transaction");
            final List<DepositTransaction> fiTxns = objectMapper.readValue(transactionNode.toString(), new TypeReference<>() {
            });

            final FinancialInstrument financialInstrument = FinancialInstrument.DEPOSIT;

            final List<Transaction> entityTxns = fiTxns.stream()
                    .map(txn -> new Transaction(txn.convertTransactionTimestampToLocalDateTime(), txn.getAmount(), txn.getNarration(), TransactionType.valueOf(txn.getType()), txn.getMode(), financialInstrument))
                    .collect(Collectors.toList());

            UserFITxnDTO userFITxnDTO = new UserFITxnDTO(financialInstrument, value, openingDate, entityTxns);
//            log.info("Deposit Investment: {}", userFITxnDTO);

            return userFITxnDTO;
        } catch (IOException e) {
            log.error("Error parsing JSON", e);
        }

        return null;
    }

//    public static void main(String[] args) {
//        parse("{\n" +
//                "  \"Account\": {\n" +
//                "    \"type\": \"SAVINGS\",\n" +
//                "    \"maskedAccNumber\": \"XXXX-XXXX-XXXX-5678\",\n" +
//                "    \"version\": \"1.1\",\n" +
//                "    \"linkedAccRef\": \"ACC123456789\",\n" +
//                "    \"Profile\": {\n" +
//                "      \"Holders\": {\n" +
//                "        \"type\": \"JOINT\",\n" +
//                "        \"Holder\": {\n" +
//                "          \"name\": \"John Doe\",\n" +
//                "          \"dob\": \"1985-06-15\",\n" +
//                "          \"mobile\": \"91729391923\",\n" +
//                "          \"nominee\": \"NOT-REGISTERED\",\n" +
//                "          \"email\": \"john.doe@example.com\",\n" +
//                "          \"pan\": \"AAAPL1234C\",\n" +
//                "          \"ckycCompliance\": true\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Summary\": {\n" +
//                "      \"currentBalance\": \"50000.00\",\n" +
//                "      \"currency\": \"INR\",\n" +
//                "      \"ifscCode\": \"HDFC0001234\",\n" +
//                "      \"micrCode\": \"400240001\",\n" +
//                "      \"openingDate\": \"2024-04-01\",\n" +
//                "      \"status\": \"ACTIVE\"\n" +
//                "    },\n" +
//                "    \"Transactions\": {\n" +
//                "      \"startDate\": \"2024-04-01\",\n" +
//                "      \"endDate\": \"2024-10-01\",\n" +
//                "      \"Transaction\": [\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN001\",\n" +
//                "          \"amount\": 50000,\n" +
//                "          \"currentBalance\": 100000,\n" +
//                "          \"transactionTimestamp\": \"2024-04-01T09:00:00\",\n" +
//                "          \"narration\": \"Monthly Salary Credit\",\n" +
//                "          \"reference\": \"SAL12345\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN002\",\n" +
//                "          \"amount\": 1500,\n" +
//                "          \"currentBalance\": 98500,\n" +
//                "          \"transactionTimestamp\": \"2024-04-05T12:00:00\",\n" +
//                "          \"narration\": \"Electricity Bill Payment\",\n" +
//                "          \"reference\": \"UTIL001\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"UPI\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN003\",\n" +
//                "          \"amount\": 3000,\n" +
//                "          \"currentBalance\": 95500,\n" +
//                "          \"transactionTimestamp\": \"2024-04-10T15:30:00\",\n" +
//                "          \"narration\": \"Grocery Shopping\",\n" +
//                "          \"reference\": \"SPEND001\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"CARD\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN004\",\n" +
//                "          \"amount\": 20000,\n" +
//                "          \"currentBalance\": 115500,\n" +
//                "          \"transactionTimestamp\": \"2024-05-01T09:00:00\",\n" +
//                "          \"narration\": \"Monthly Salary Credit\",\n" +
//                "          \"reference\": \"SAL12346\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN005\",\n" +
//                "          \"amount\": 1200,\n" +
//                "          \"currentBalance\": 114300,\n" +
//                "          \"transactionTimestamp\": \"2024-05-05T12:30:00\",\n" +
//                "          \"narration\": \"Water Bill Payment\",\n" +
//                "          \"reference\": \"UTIL002\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"UPI\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN006\",\n" +
//                "          \"amount\": 5000,\n" +
//                "          \"currentBalance\": 109300,\n" +
//                "          \"transactionTimestamp\": \"2024-05-20T18:00:00\",\n" +
//                "          \"narration\": \"Online Shopping\",\n" +
//                "          \"reference\": \"SPEND002\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"CARD\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN007\",\n" +
//                "          \"amount\": 50000,\n" +
//                "          \"currentBalance\": 159300,\n" +
//                "          \"transactionTimestamp\": \"2024-06-01T09:00:00\",\n" +
//                "          \"narration\": \"Monthly Salary Credit\",\n" +
//                "          \"reference\": \"SAL12347\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN008\",\n" +
//                "          \"amount\": 1800,\n" +
//                "          \"currentBalance\": 157500,\n" +
//                "          \"transactionTimestamp\": \"2024-06-10T13:00:00\",\n" +
//                "          \"narration\": \"Gas Bill Payment\",\n" +
//                "          \"reference\": \"UTIL003\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"UPI\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN009\",\n" +
//                "          \"amount\": 2500,\n" +
//                "          \"currentBalance\": 155000,\n" +
//                "          \"transactionTimestamp\": \"2024-06-25T14:30:00\",\n" +
//                "          \"narration\": \"Restaurant\",\n" +
//                "          \"reference\": \"SPEND003\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"CARD\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN010\",\n" +
//                "          \"amount\": 60000,\n" +
//                "          \"currentBalance\": 215000,\n" +
//                "          \"transactionTimestamp\": \"2024-07-01T09:00:00\",\n" +
//                "          \"narration\": \"Monthly Salary Credit\",\n" +
//                "          \"reference\": \"SAL12348\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN011\",\n" +
//                "          \"amount\": 1300,\n" +
//                "          \"currentBalance\": 213700,\n" +
//                "          \"transactionTimestamp\": \"2024-07-05T11:00:00\",\n" +
//                "          \"narration\": \"Internet Bill Payment\",\n" +
//                "          \"reference\": \"UTIL004\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"UPI\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN012\",\n" +
//                "          \"amount\": 4500,\n" +
//                "          \"currentBalance\": 209200,\n" +
//                "          \"transactionTimestamp\": \"2024-07-15T10:00:00\",\n" +
//                "          \"narration\": \"Clothing Purchase\",\n" +
//                "          \"reference\": \"SPEND004\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"CARD\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN013\",\n" +
//                "          \"amount\": 65000,\n" +
//                "          \"currentBalance\": 274200,\n" +
//                "          \"transactionTimestamp\": \"2024-08-01T09:00:00\",\n" +
//                "          \"narration\": \"Monthly Salary Credit\",\n" +
//                "          \"reference\": \"SAL12349\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN014\",\n" +
//                "          \"amount\": 1700,\n" +
//                "          \"currentBalance\": 272500,\n" +
//                "          \"transactionTimestamp\": \"2024-08-05T12:00:00\",\n" +
//                "          \"narration\": \"Electricity Bill Payment\",\n" +
//                "          \"reference\": \"UTIL005\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"UPI\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN015\",\n" +
//                "          \"amount\": 6000,\n" +
//                "          \"currentBalance\": 266500,\n" +
//                "          \"transactionTimestamp\": \"2024-08-20T14:00:00\",\n" +
//                "          \"narration\": \"Hotel Stay\",\n" +
//                "          \"reference\": \"SPEND005\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"CARD\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN016\",\n" +
//                "          \"amount\": 70000,\n" +
//                "          \"currentBalance\": 336500,\n" +
//                "          \"transactionTimestamp\": \"2024-09-01T09:00:00\",\n" +
//                "          \"narration\": \"Monthly Salary Credit\",\n" +
//                "          \"reference\": \"SAL12350\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN017\",\n" +
//                "          \"amount\": 2000,\n" +
//                "          \"currentBalance\": 334500,\n" +
//                "          \"transactionTimestamp\": \"2024-09-10T13:00:00\",\n" +
//                "          \"narration\": \"Mobile Bill Payment\",\n" +
//                "          \"reference\": \"UTIL006\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"UPI\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN018\",\n" +
//                "          \"amount\": 5500,\n" +
//                "          \"currentBalance\": 329000,\n" +
//                "          \"transactionTimestamp\": \"2024-09-25T17:00:00\",\n" +
//                "          \"narration\": \"Electronics Purchase\",\n" +
//                "          \"reference\": \"SPEND006\",\n" +
//                "          \"type\": \"DEBIT\",\n" +
//                "          \"mode\": \"CARD\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", new ObjectMapper());
//    }
}
