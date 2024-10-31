package com.dreamx.kosha.helper;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.model.creditcard.CreditCardSummary;
import com.dreamx.kosha.model.creditcard.CreditCardTransaction;
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
public class CreditCardParser {

    public static UserFITxnDTO parse(String json, ObjectMapper objectMapper) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            final JsonNode accountNode = jsonNode.get("Account");

            Account account = objectMapper.readValue(accountNode.toString(),
                    objectMapper.getTypeFactory().constructParametricType(Account.class, CreditCardSummary.class));

            final CreditCardSummary creditCardSummary = (CreditCardSummary) account.getSummary();

            // Extract and log the total due amount and due date
            double totalDueAmount = Double.parseDouble(creditCardSummary.getTotalDueAmount());
            LocalDateTime dueDate = creditCardSummary.getDueDateAsLocalDateTime();

            // Extract and log the transactions
            JsonNode transactionsNode = accountNode.get("Transactions");
            final JsonNode transactionNode = transactionsNode.get("Transaction");
            final List<CreditCardTransaction> creditCardTransactions = objectMapper.readValue(transactionNode.toString(), new TypeReference<>() {
            });

            final FinancialInstrument financialInstrument = FinancialInstrument.CC;

            final List<Transaction> transactions = creditCardTransactions.stream()
                    .map(ccTxn -> new Transaction(ccTxn.convertTxnDateToLocalDateTime(), ccTxn.getAmount(), financialInstrument))
                    .collect(Collectors.toList());

            final UserFITxnDTO response = new UserFITxnDTO(financialInstrument, totalDueAmount, dueDate, transactions);
//            log.info("CC Investment: {}", response);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        parse("{\n" +
//                "  \"Account\": {\n" +
//                "    \"linkedAccRef\": \"\",\n" +
//                "    \"userId\": \"ObjectId('67176c486d5ad817f74a8388')\",\n" +
//                "    \"maskedAccNumber\": \"\",\n" +
//                "    \"version\": \"1.0\",\n" +
//                "    \"type\": \"credit_card\",\n" +
//                "    \"Profile\": {\n" +
//                "      \"Holders\": {\n" +
//                "        \"Holder\": {\n" +
//                "          \"name\": \"\",\n" +
//                "          \"dob\": \"2002-09-24\",\n" +
//                "          \"mobile\": \"8879254212\",\n" +
//                "          \"nominee\": \"NOT-REGISTERED\",\n" +
//                "          \"email\": \"qw@gmail.com\",\n" +
//                "          \"pan\": \"AAAPL1234C\",\n" +
//                "          \"ckycCompliance\": true,\n" +
//                "          \"Cards\": {\n" +
//                "            \"Card\": {\n" +
//                "              \"cardType\": \"RUPAY\",\n" +
//                "              \"primary\": \"YES\",\n" +
//                "              \"issuedDate\": \"2004-04-12\",\n" +
//                "              \"maskedCardNumber\": \"\"\n" +
//                "            }\n" +
//                "          }\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Summary\": {\n" +
//                "      \"currentDue\": 3000,\n" +
//                "      \"lastStatementDate\": \"2004-04-12\",\n" +
//                "      \"dueDate\": \"2004-04-12\",\n" +
//                "      \"previousDueAmount\": 100,\n" +
//                "      \"totalDueAmount\": 2000,\n" +
//                "      \"minDueAmount\": 200,\n" +
//                "      \"creditLimit\": 3000,\n" +
//                "      \"cashLimit\": 400,\n" +
//                "      \"availableCredit\": 3000,\n" +
//                "      \"loyaltyPoints\": 20,\n" +
//                "      \"financeCharges\": 50\n" +
//                "    },\n" +
//                "    \"Transactions\": {\n" +
//                "      \"startDate\": \"2024-04-12\",\n" +
//                "      \"endDate\": \"2024-10-12\",\n" +
//                "      \"Transaction\": [\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN001\",\n" +
//                "          \"txnType\": \"DEBIT\",\n" +
//                "          \"txnDate\": \"2024-09-12\",\n" +
//                "          \"amount\": 200,\n" +
//                "          \"valueDate\": \"2024-09-12\",\n" +
//                "          \"narration\": \"Online Shopping\",\n" +
//                "          \"statementDate\": \"2024-09-13\",\n" +
//                "          \"mcc\": \"5411\",\n" +
//                "          \"maskedCardNumber\": \"XXXX-XXXX-XXXX-1234\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN002\",\n" +
//                "          \"txnType\": \"DEBIT\",\n" +
//                "          \"txnDate\": \"2024-08-15\",\n" +
//                "          \"amount\": 150,\n" +
//                "          \"valueDate\": \"2024-08-15\",\n" +
//                "          \"narration\": \"Grocery Purchase\",\n" +
//                "          \"statementDate\": \"2024-08-16\",\n" +
//                "          \"mcc\": \"5311\",\n" +
//                "          \"maskedCardNumber\": \"XXXX-XXXX-XXXX-1234\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN003\",\n" +
//                "          \"txnType\": \"DEBIT\",\n" +
//                "          \"txnDate\": \"2024-07-10\",\n" +
//                "          \"amount\": 500,\n" +
//                "          \"valueDate\": \"2024-07-10\",\n" +
//                "          \"narration\": \"Fuel Payment\",\n" +
//                "          \"statementDate\": \"2024-07-11\",\n" +
//                "          \"mcc\": \"5541\",\n" +
//                "          \"maskedCardNumber\": \"XXXX-XXXX-XXXX-1234\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN004\",\n" +
//                "          \"txnType\": \"DEBIT\",\n" +
//                "          \"txnDate\": \"2024-06-05\",\n" +
//                "          \"amount\": 120,\n" +
//                "          \"valueDate\": \"2024-06-05\",\n" +
//                "          \"narration\": \"Restaurant Bill\",\n" +
//                "          \"statementDate\": \"2024-06-06\",\n" +
//                "          \"mcc\": \"5812\",\n" +
//                "          \"maskedCardNumber\": \"XXXX-XXXX-XXXX-1234\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN005\",\n" +
//                "          \"txnType\": \"DEBIT\",\n" +
//                "          \"txnDate\": \"2024-05-18\",\n" +
//                "          \"amount\": 250,\n" +
//                "          \"valueDate\": \"2024-05-18\",\n" +
//                "          \"narration\": \"Utility Bill Payment\",\n" +
//                "          \"statementDate\": \"2024-05-19\",\n" +
//                "          \"mcc\": \"4900\",\n" +
//                "          \"maskedCardNumber\": \"XXXX-XXXX-XXXX-1234\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN006\",\n" +
//                "          \"txnType\": \"DEBIT\",\n" +
//                "          \"txnDate\": \"2024-04-25\",\n" +
//                "          \"amount\": 300,\n" +
//                "          \"valueDate\": \"2024-04-25\",\n" +
//                "          \"narration\": \"Hotel Booking\",\n" +
//                "          \"statementDate\": \"2024-04-26\",\n" +
//                "          \"mcc\": \"7011\",\n" +
//                "          \"maskedCardNumber\": \"XXXX-XXXX-XXXX-1234\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", new ObjectMapper());
//    }
}
