package com.dreamx.kosha.helper;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.model.fimodels.Account;
import com.dreamx.kosha.model.termdeposit.TermDepositSummary;
import com.dreamx.kosha.model.termdeposit.TermDepositTransaction;
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
public class TermDepositParser {

    public static UserFITxnDTO parse(String json, ObjectMapper objectMapper) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode accountNode = rootNode.path("Account");
            Account<TermDepositSummary> account = objectMapper.readValue(accountNode.toString(),
                    objectMapper.getTypeFactory().constructParametricType(Account.class, TermDepositSummary.class));

            final double value = account.getSummary().getCurrentValue();  // todo check if maturityAmount or principalAmount or this should be taken?
            final LocalDateTime openingDate = account.getSummary().getOpeningDateAsLocalDateTime();


            // Extract and log the transactions
            JsonNode transactionsNode = accountNode.get("Transactions");
            final JsonNode transactionNode = transactionsNode.get("Transaction");
            final List<TermDepositTransaction> fiTxns = objectMapper.readValue(transactionNode.toString(), new TypeReference<>() {
            });

            final FinancialInstrument financialInstrument = FinancialInstrument.TD;

            final List<Transaction> entityTxns = fiTxns.stream()
                    .map(txn -> new Transaction(txn.convertTransactionDateTimeToLocalDateTime(), txn.getAmount(), financialInstrument))
                    .collect(Collectors.toList());

            UserFITxnDTO userFITxnDTO = new UserFITxnDTO(financialInstrument, value, openingDate, entityTxns);
//            log.info("TD Investment: {}", userFITxnDTO);

            return userFITxnDTO;
        } catch (IOException e) {
            log.error("Error parsing JSON", e);
        }
        return null;
    }

//    public static void main(String[] args) {
//        parse("{\n" +
//                "  \"Account\": {\n" +
//                "    \"linkedAccRef\": \"FD123456789\",\n" +
//                "    \"maskedAccNumber\": \"XXXX-XXXX-XXXX-5678\",\n" +
//                "    \"version\": \"1.1\",\n" +
//                "    \"type\": \"term_deposit\",\n" +
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
//                "      \"openingDate\": \"2024-04-01\",\n" +
//                "      \"accountType\": \"FIXED\",\n" +
//                "      \"branch\": \"Main Branch\",\n" +
//                "      \"ifsc\": \"HDFC0001234\",\n" +
//                "      \"maturityAmount\": 105000,\n" +
//                "      \"description\": \"Fixed Deposit\",\n" +
//                "      \"interestPayout\": \"HALF-YEARLY\",\n" +
//                "      \"interestRate\": \"6.5\",\n" +
//                "      \"maturityDate\": \"2027-04-01\",\n" +
//                "      \"principalAmount\": 100000,\n" +
//                "      \"tenureDays\": 0,\n" +
//                "      \"tenureMonths\": 0,\n" +
//                "      \"tenureYears\": 3,\n" +
//                "      \"interestComputation\": \"COMPOUND\",\n" +
//                "      \"compoundingFrequency\": \"HALF-YEARLY\",\n" +
//                "      \"interestPeriodicPayoutAmount\": 3250,\n" +
//                "      \"interestOnMaturity\": 5000,\n" +
//                "      \"currentValue\": 100000\n" +
//                "    },\n" +
//                "    \"Transactions\": {\n" +
//                "      \"startDate\": \"2024-04-01\",\n" +
//                "      \"endDate\": \"2024-10-01\",\n" +
//                "      \"Transaction\": [\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN001\",\n" +
//                "          \"amount\": 100000,\n" +
//                "          \"narration\": \"Fixed Deposit Initiated\",\n" +
//                "          \"type\": \"DEPOSIT\",\n" +
//                "          \"mode\": \"ONLINE\",\n" +
//                "          \"balance\": 100000,\n" +
//                "          \"transactionDateTime\": \"2024-04-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-04-01\",\n" +
//                "          \"reference\": \"FD-INIT-001\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN002\",\n" +
//                "          \"amount\": 3250,\n" +
//                "          \"narration\": \"Interest Payout - Half Yearly\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\",\n" +
//                "          \"balance\": 103250,\n" +
//                "          \"transactionDateTime\": \"2024-07-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-07-01\",\n" +
//                "          \"reference\": \"FD-INT-002\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN003\",\n" +
//                "          \"amount\": 3250,\n" +
//                "          \"narration\": \"Interest Payout - Half Yearly\",\n" +
//                "          \"type\": \"CREDIT\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\",\n" +
//                "          \"balance\": 106500,\n" +
//                "          \"transactionDateTime\": \"2024-10-01T09:00:00\",\n" +
//                "          \"valueDate\": \"2024-10-01\",\n" +
//                "          \"reference\": \"FD-INT-003\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN004\",\n" +
//                "          \"amount\": 1000,\n" +
//                "          \"narration\": \"TDS Deduction\",\n" +
//                "          \"type\": \"TDS\",\n" +
//                "          \"mode\": \"BANK_TRANSFER\",\n" +
//                "          \"balance\": 105500,\n" +
//                "          \"transactionDateTime\": \"2024-10-01T12:00:00\",\n" +
//                "          \"valueDate\": \"2024-10-01\",\n" +
//                "          \"reference\": \"FD-TDS-004\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", new ObjectMapper());
//    }
}
