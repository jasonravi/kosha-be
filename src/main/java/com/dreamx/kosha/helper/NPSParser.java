package com.dreamx.kosha.helper;

import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.model.equity.EquityTransaction;
import com.dreamx.kosha.model.nps.NPSSummary;
import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.model.fimodels.Account;
import com.dreamx.kosha.model.nps.Tier1SchemeTransactions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author deepika_rajani
 */
@Slf4j
public class NPSParser {

    public static UserFITxnDTO parse(String json, ObjectMapper objectMapper) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            final JsonNode accountNode = jsonNode.get("Account");

            Account account = objectMapper.readValue(accountNode.toString(),
                    objectMapper.getTypeFactory().constructParametricType(Account.class, NPSSummary.class));

            NPSSummary summary = (NPSSummary) account.getSummary();

            // Perform calculations using the summary object
            double totalValue = summary.getCurrentValue();
            String status = summary.getStatus();
            // Add more calculations as needed

            final LocalDateTime openingDate = summary.getOpeningDateAsLocalDateTime();


            // Extract and log the transactions
            JsonNode transactionsNode = accountNode.get("Transactions");
            final JsonNode tier1Node = transactionsNode.get("Tier1SchemeTransactions");
            final JsonNode tier2Node = transactionsNode.get("Tier2SchemeTransactions");
            final List<Tier1SchemeTransactions> fiTxns1 = objectMapper.readValue(tier1Node.toString(), new TypeReference<>() {
            });

            final List<Tier1SchemeTransactions> fiTxns2 = objectMapper.readValue(tier2Node.toString(), new TypeReference<>() {
            });

            final FinancialInstrument financialInstrument = FinancialInstrument.NPS;

            final List<Transaction> entityTxns1 = fiTxns1.stream()
                    .map(txn -> new Transaction(txn.convertTxnDateToLocalDateTime(), txn.getAmount(), financialInstrument))
                    .collect(Collectors.toList());

            final List<Transaction> entityTxns2 = fiTxns2.stream()
                    .map(txn -> new Transaction(txn.convertTxnDateToLocalDateTime(), txn.getAmount(), financialInstrument))
                    .collect(Collectors.toList());

            List<Transaction> entityTxns = new ArrayList<>();
            entityTxns.addAll(entityTxns1);
            entityTxns.addAll(entityTxns2);

            final UserFITxnDTO response = new UserFITxnDTO(financialInstrument, totalValue, openingDate, entityTxns);
//            log.info("NPS Investment: {}", response);

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
//                "    \"type\": \"nps\",\n" +
//                "    \"Profile\": {\n" +
//                "      \"Holders\": {\n" +
//                "        \"Holder\": {\n" +
//                "          \"name\": \"\",\n" +
//                "          \"dob\": \"2002-09-24\",\n" +
//                "          \"mobile\": \"91729391923\",\n" +
//                "          \"nominee\": \"NOT-REGISTERED\",\n" +
//                "          \"pranId\": \"\",\n" +
//                "          \"email\": \"qw@gmail.com\",\n" +
//                "          \"pan\": \"AAAPL1234C\",\n" +
//                "          \"ckycCompliance\": true\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Summary\": {\n" +
//                "      \"currentValue\": 100,\n" +
//                "      \"tier1NAVDate\": \"2004-04-12\",\n" +
//                "      \"tier2NAVDate\": \"2004-04-12\",\n" +
//                "      \"debtAssetValue\": 100,\n" +
//                "      \"equityAssetValue\": 100,\n" +
//                "      \"otherAssetValue\": 100,\n" +
//                "      \"openingDate\": \"2004-04-12\",\n" +
//                "      \"status\": \"ACTIVE\",\n" +
//                "      \"tier1Status\": \"ACTIVE\",\n" +
//                "      \"tier2Status\": \"ACTIVE\",\n" +
//                "      \"SchemeChoices\": [\n" +
//                "        {\n" +
//                "          \"allocationPercent\": \"\",\n" +
//                "          \"pfmId\": \"\",\n" +
//                "          \"pfmName\": \"\",\n" +
//                "          \"schemeId\": \"\",\n" +
//                "          \"schemeName\": \"\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"Holdings\": {\n" +
//                "        \"Tier1Holdings\": {\n" +
//                "          \"schemePreferenceType\": \"ACTIVE\",\n" +
//                "          \"freeUnits\": 20,\n" +
//                "          \"investmentCost\": 200,\n" +
//                "          \"investmentValue\": 180,\n" +
//                "          \"Tier1Holding\": {\n" +
//                "            \"amount\": 100,\n" +
//                "            \"amountInTransition\": 20,\n" +
//                "            \"blockedUnits\": 20,\n" +
//                "            \"freeUnits\": 30,\n" +
//                "            \"nav\": \"\",\n" +
//                "            \"schemeId\": \"\",\n" +
//                "            \"schemeName\": \"\",\n" +
//                "            \"totalUnits\": 30,\n" +
//                "            \"totalValueOfScheme\": 20\n" +
//                "          }\n" +
//                "        },\n" +
//                "        \"Tier2Holdings\": {\n" +
//                "          \"schemePreferenceType\": \"ACTIVE\",\n" +
//                "          \"freeUnits\": 10,\n" +
//                "          \"investmentCost\": 100,\n" +
//                "          \"investmentValue\": 190,\n" +
//                "          \"Tier2Holding\": {\n" +
//                "            \"amount\": 200,\n" +
//                "            \"amountInTransition\": 30,\n" +
//                "            \"blockedUnits\": 20,\n" +
//                "            \"freeUnits\": 10,\n" +
//                "            \"nav\": \"\",\n" +
//                "            \"schemeId\": \"\",\n" +
//                "            \"schemeName\": \"\",\n" +
//                "            \"totalUnits\": 20,\n" +
//                "            \"totalValueOfScheme\": 400\n" +
//                "          }\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Transactions\": {\n" +
//                "      \"startDate\": \"2004-04-12\",\n" +
//                "      \"endDate\": \"2004-04-12\",\n" +
//                "      \"Tier1SchemeTransactions\": [\n" +
//                "        {\n" +
//                "          \"txnId\": \"\",\n" +
//                "          \"txnDate\": \"2004-10-15\",\n" +
//                "          \"type\": \"ADJUSTMENT\",\n" +
//                "          \"schemeId\": \"\",\n" +
//                "          \"schemeName\": \"\",\n" +
//                "          \"narration\": \"\",\n" +
//                "          \"allocationPercent\": 8,\n" +
//                "          \"amount\": 100,\n" +
//                "          \"nav\": \"\",\n" +
//                "          \"units\": 10,\n" +
//                "          \"cumulativeUnits\": 14\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"Tier2SchemeTransactions\": [\n" +
//                "        {\n" +
//                "          \"txnId\": \"\",\n" +
//                "          \"txnDate\": \"2004-10-12\",\n" +
//                "          \"type\": \"ADJUSTMENT\",\n" +
//                "          \"schemeId\": \"\",\n" +
//                "          \"schemeName\": \"\",\n" +
//                "          \"narration\": \"\",\n" +
//                "          \"allocationPercent\": 8,\n" +
//                "          \"amount\": 100,\n" +
//                "          \"nav\": \"\",\n" +
//                "          \"units\": 10,\n" +
//                "          \"cumulativeUnits\": 8\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", new ObjectMapper());
//    }
}
