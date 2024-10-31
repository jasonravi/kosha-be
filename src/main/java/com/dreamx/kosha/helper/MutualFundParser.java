package com.dreamx.kosha.helper;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.constants.TransactionType;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.model.deposit.DepositTransaction;
import com.dreamx.kosha.model.fimodels.Account;
import com.dreamx.kosha.model.mutualfund.MutualFundSummary;
import com.dreamx.kosha.model.mutualfund.MutualFundTransaction;
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
public class MutualFundParser {

    public static UserFITxnDTO parse(String json, ObjectMapper objectMapper) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            final JsonNode accountNode = jsonNode.get("Account");

            Account<MutualFundSummary> account = objectMapper.readValue(accountNode.toString(),
                    objectMapper.getTypeFactory().constructParametricType(Account.class, MutualFundSummary.class));

            MutualFundSummary summary = account.getSummary();

            // Perform calculations using the summary object
            double totalValue = summary.getCurrentValue();  // todo investmentValue or this?
            // Add more calculations as needed


            // Extract and log the transactions
            JsonNode transactionsNode = accountNode.get("Transactions");
            final JsonNode transactionNode = transactionsNode.get("Transaction");
            final List<MutualFundTransaction> fiTxns = objectMapper.readValue(transactionNode.toString(), new TypeReference<>() {
            });

            final FinancialInstrument financialInstrument = FinancialInstrument.MUTUAL_FUND_UNITS;

            final List<Transaction> entityTxns = fiTxns.stream()
                    .map(txn -> new Transaction(txn.convertExecutionDateToLocalDateTime(), Double.parseDouble(txn.getAmount()), financialInstrument))
                    .collect(Collectors.toList());

            final UserFITxnDTO response = new UserFITxnDTO(financialInstrument, totalValue, LocalDateTime.now(), entityTxns);
//            log.info("MF Investment: {}", response);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public static void main(String[] args) {
//        parse("{\n" +
//                "  \"Account\": {\n" +
//                "    \"linkedAccRef\": \"MF123456789\",\n" +
//                "    \"userId\": \"ObjectId('67176c486d5ad817f74a8388')\",\n" +
//                "    \"maskedAccNumber\": \"XXXX-XXXX-XXXX-6789\",\n" +
//                "    \"version\": 1.2,\n" +
//                "    \"type\": \"mutualfunds\",\n" +
//                "    \"Profile\": {\n" +
//                "      \"Holders\": {\n" +
//                "        \"Holder\": {\n" +
//                "          \"name\": \"Alice Smith\",\n" +
//                "          \"dob\": \"1985-05-15\",\n" +
//                "          \"mobile\": 9876543210,\n" +
//                "          \"nominee\": \"REGISTERED\",\n" +
//                "          \"dematId\": \"DM1234567890\",\n" +
//                "          \"landline\": \"0221234567\",\n" +
//                "          \"address\": \"456 Market Street, City\",\n" +
//                "          \"email\": \"alice.smith@example.com\",\n" +
//                "          \"pan\": \"ABCDE1234F\",\n" +
//                "          \"ckycCompliance\": true\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Summary\": {\n" +
//                "      \"investmentValue\": 100000,\n" +
//                "      \"currentValue\": 150000,\n" +
//                "      \"Investment\": {\n" +
//                "        \"Holdings\": {\n" +
//                "          \"Holding\": {\n" +
//                "            \"amc\": \"XYZ Asset Management\",\n" +
//                "            \"registrar\": \"CAMS\",\n" +
//                "            \"schemeCode\": \"XYZ123\",\n" +
//                "            \"isin\": \"IN1234567890\",\n" +
//                "            \"ucc\": \"UCC00123\",\n" +
//                "            \"amfiCode\": \"AMFI001\",\n" +
//                "            \"folioNo\": \"FOLIO12345\",\n" +
//                "            \"dividendType\": \"Cash\",\n" +
//                "            \"FatcaStatus\": \"Yes\",\n" +
//                "            \"mode\": \"DEMAT\",\n" +
//                "            \"units\": \"1000\",\n" +
//                "            \"closingUnits\": \"950\",\n" +
//                "            \"lienUnits\": \"0\",\n" +
//                "            \"rate\": \"100\",\n" +
//                "            \"nav\": \"150\",\n" +
//                "            \"lockingUnits\": \"0\"\n" +
//                "          }\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Transactions\": {\n" +
//                "      \"startDate\": \"2024-04-01\",\n" +
//                "      \"endDate\": \"2024-10-10\",\n" +
//                "      \"Transaction\": [\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN001\",\n" +
//                "          \"amc\": \"XYZ Asset Management\",\n" +
//                "          \"registrar\": \"CAMS\",\n" +
//                "          \"schemeCode\": \"XYZ123\",\n" +
//                "          \"schemePlan\": \"DIRECT\",\n" +
//                "          \"isin\": \"IN1234567890\",\n" +
//                "          \"amfiCode\": \"AMFI001\",\n" +
//                "          \"fundType\": \"EQUITY\",\n" +
//                "          \"schemeOption\": \"REINVEST\",\n" +
//                "          \"schemeTypes\": \"EQUITY_SCHEMES\",\n" +
//                "          \"schemeCategory\": \"MULTI_CAP_FUND\",\n" +
//                "          \"ucc\": \"UCC00123\",\n" +
//                "          \"amount\": \"15000\",\n" +
//                "          \"closingUnits\": \"1000\",\n" +
//                "          \"lienUnits\": \"0\",\n" +
//                "          \"nav\": \"150\",\n" +
//                "          \"navDate\": \"2024-10-01\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"orderDate\": \"2024-09-30\",\n" +
//                "          \"executionDate\": \"2024-10-01\",\n" +
//                "          \"lock-inFlag\": \"NO\",\n" +
//                "          \"lock-inDays\": \"0\",\n" +
//                "          \"mode\": \"DEMAT\",\n" +
//                "          \"narration\": \"Purchase of units\"\n" +
//                "        },        {\n" +
//                "          \"txnId\": \"TXN001\",\n" +
//                "          \"amc\": \"XYZ Asset Management\",\n" +
//                "          \"registrar\": \"CAMS\",\n" +
//                "          \"schemeCode\": \"XYZ123\",\n" +
//                "          \"schemePlan\": \"DIRECT\",\n" +
//                "          \"isin\": \"IN1234567890\",\n" +
//                "          \"amfiCode\": \"AMFI001\",\n" +
//                "          \"fundType\": \"EQUITY\",\n" +
//                "          \"schemeOption\": \"REINVEST\",\n" +
//                "          \"schemeTypes\": \"EQUITY_SCHEMES\",\n" +
//                "          \"schemeCategory\": \"MULTI_CAP_FUND\",\n" +
//                "          \"ucc\": \"UCC00123\",\n" +
//                "          \"amount\": \"15000\",\n" +
//                "          \"closingUnits\": \"1000\",\n" +
//                "          \"lienUnits\": \"0\",\n" +
//                "          \"nav\": \"150\",\n" +
//                "          \"navDate\": \"2024-10-01\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"orderDate\": \"2024-09-30\",\n" +
//                "          \"executionDate\": \"2024-09-01\",\n" +
//                "          \"lock-inFlag\": \"NO\",\n" +
//                "          \"lock-inDays\": \"0\",\n" +
//                "          \"mode\": \"DEMAT\",\n" +
//                "          \"narration\": \"Purchase of units\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN002\",\n" +
//                "          \"amc\": \"XYZ Asset Management\",\n" +
//                "          \"registrar\": \"CAMS\",\n" +
//                "          \"schemeCode\": \"XYZ123\",\n" +
//                "          \"schemePlan\": \"DIRECT\",\n" +
//                "          \"isin\": \"IN1234567890\",\n" +
//                "          \"amfiCode\": \"AMFI001\",\n" +
//                "          \"fundType\": \"EQUITY\",\n" +
//                "          \"schemeOption\": \"REINVEST\",\n" +
//                "          \"schemeTypes\": \"EQUITY_SCHEMES\",\n" +
//                "          \"schemeCategory\": \"MULTI_CAP_FUND\",\n" +
//                "          \"ucc\": \"UCC00123\",\n" +
//                "          \"amount\": \"20000\",\n" +
//                "          \"closingUnits\": \"1050\",\n" +
//                "          \"lienUnits\": \"0\",\n" +
//                "          \"nav\": \"140\",\n" +
//                "          \"navDate\": \"2024-08-10\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"orderDate\": \"2024-08-09\",\n" +
//                "          \"executionDate\": \"2024-08-10\",\n" +
//                "          \"lock-inFlag\": \"NO\",\n" +
//                "          \"lock-inDays\": \"0\",\n" +
//                "          \"mode\": \"DEMAT\",\n" +
//                "          \"narration\": \"Purchase of units\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN003\",\n" +
//                "          \"amc\": \"XYZ Asset Management\",\n" +
//                "          \"registrar\": \"CAMS\",\n" +
//                "          \"schemeCode\": \"XYZ123\",\n" +
//                "          \"schemePlan\": \"DIRECT\",\n" +
//                "          \"isin\": \"IN1234567890\",\n" +
//                "          \"amfiCode\": \"AMFI001\",\n" +
//                "          \"fundType\": \"EQUITY\",\n" +
//                "          \"schemeOption\": \"REINVEST\",\n" +
//                "          \"schemeTypes\": \"EQUITY_SCHEMES\",\n" +
//                "          \"schemeCategory\": \"MULTI_CAP_FUND\",\n" +
//                "          \"ucc\": \"UCC00123\",\n" +
//                "          \"amount\": \"10000\",\n" +
//                "          \"closingUnits\": \"1020\",\n" +
//                "          \"lienUnits\": \"0\",\n" +
//                "          \"nav\": \"135\",\n" +
//                "          \"navDate\": \"2024-07-20\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"orderDate\": \"2024-07-19\",\n" +
//                "          \"executionDate\": \"2024-07-20\",\n" +
//                "          \"lock-inFlag\": \"NO\",\n" +
//                "          \"lock-inDays\": \"0\",\n" +
//                "          \"mode\": \"DEMAT\",\n" +
//                "          \"narration\": \"Purchase of units\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN004\",\n" +
//                "          \"amc\": \"XYZ Asset Management\",\n" +
//                "          \"registrar\": \"CAMS\",\n" +
//                "          \"schemeCode\": \"XYZ123\",\n" +
//                "          \"schemePlan\": \"DIRECT\",\n" +
//                "          \"isin\": \"IN1234567890\",\n" +
//                "          \"amfiCode\": \"AMFI001\",\n" +
//                "          \"fundType\": \"EQUITY\",\n" +
//                "          \"schemeOption\": \"REINVEST\",\n" +
//                "          \"schemeTypes\": \"EQUITY_SCHEMES\",\n" +
//                "          \"schemeCategory\": \"MULTI_CAP_FUND\",\n" +
//                "          \"ucc\": \"UCC00123\",\n" +
//                "          \"amount\": \"5000\",\n" +
//                "          \"closingUnits\": \"990\",\n" +
//                "          \"lienUnits\": \"0\",\n" +
//                "          \"nav\": \"130\",\n" +
//                "          \"navDate\": \"2024-06-10\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"orderDate\": \"2024-06-09\",\n" +
//                "          \"executionDate\": \"2024-06-10\",\n" +
//                "          \"lock-inFlag\": \"NO\",\n" +
//                "          \"lock-inDays\": \"0\",\n" +
//                "          \"mode\": \"DEMAT\",\n" +
//                "          \"narration\": \"Purchase of units\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", new ObjectMapper());
//    }
}
