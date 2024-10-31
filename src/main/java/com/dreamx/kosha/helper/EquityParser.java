package com.dreamx.kosha.helper;

import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.model.equity.EquitySummary;
import com.dreamx.kosha.model.equity.EquityTransaction;
import com.dreamx.kosha.model.fimodels.Account;
import com.dreamx.kosha.model.equity.Holding;
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
public class EquityParser {

    public static UserFITxnDTO parse(String json, ObjectMapper objectMapper) {
        try {

            JsonNode jsonNode = objectMapper.readTree(json);

            final JsonNode accountNode = jsonNode.get("Account");

            Account account = objectMapper.readValue(accountNode.toString(),
                    objectMapper.getTypeFactory().constructParametricType(Account.class, EquitySummary.class));

            final EquitySummary summary = (EquitySummary) account.getSummary();

            Holding holding = summary.getInvestment().getHoldings().getHolding();

            final double currentValue = summary.getCurrentValue();  // todo check if investmentValue or this should be taken?

            // Perform calculations using the holding object
            LocalDateTime investmentDateTime = holding.getInvestmentDateTimeAsLocalDateTime();


            // Extract and log the transactions
            JsonNode transactionsNode = accountNode.get("Transactions");
            final JsonNode transactionNode = transactionsNode.get("Transaction");
            final List<EquityTransaction> fiTxns = objectMapper.readValue(transactionNode.toString(), new TypeReference<>() {
            });

            final FinancialInstrument financialInstrument = FinancialInstrument.EQUITY_SHARES;

            final List<Transaction> entityTxns = fiTxns.stream()
                    .map(txn -> new Transaction(txn.convertTransactionDateTimeToLocalDateTime(), Double.parseDouble(txn.getTradeValue()), financialInstrument))
                    .collect(Collectors.toList());

            final UserFITxnDTO response = new UserFITxnDTO(financialInstrument, currentValue, investmentDateTime, entityTxns);
//            log.info("Equity Investment: {}", response);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public static void main(String[] args) {
//        parse("{\n" +
//                "  \"Account\": {\n" +
//                "    \"linkedAccRef\": \"EQ123456789\",\n" +
//                "    \"userId\": \"ObjectId('67176c486d5ad817f74a8388')\",\n" +
//                "    \"maskedAccNumber\": \"XXXX-XXXX-XXXX-1234\",\n" +
//                "    \"version\": 1.0,\n" +
//                "    \"type\": \"equities\",\n" +
//                "    \"Profile\": {\n" +
//                "      \"Holders\": {\n" +
//                "        \"Holder\": {\n" +
//                "          \"name\": \"John Doe\",\n" +
//                "          \"dob\": \"1980-01-01\",\n" +
//                "          \"mobile\": 9876543210,\n" +
//                "          \"nominee\": \"REGISTERED\",\n" +
//                "          \"dematId\": \"DM1234567890\",\n" +
//                "          \"landline\": \"0221234567\",\n" +
//                "          \"address\": \"789 Wall Street, City\",\n" +
//                "          \"email\": \"john.doe@example.com\",\n" +
//                "          \"pan\": \"ABCDE1234F\",\n" +
//                "          \"ckycCompliance\": true\n" +
//                "        }\n" +
//                "      }\n" +
//                "    },\n" +
//                "    \"Summary\": {\n" +
//                "      \"investmentValue\": 500000,\n" +
//                "      \"currentValue\": 650000,\n" +
//                "      \"Investment\": {\n" +
//                "        \"Holdings\": {\n" +
//                "          \"Holding\": {\n" +
//                "            \"issuerName\": \"ABC Corp\",\n" +
//                "            \"isin\": \"IN1234567890\",\n" +
//                "            \"units\": \"1000\",\n" +
//                "            \"investmentDateTime\": \"2024-04-01T10:00:00\",\n" +
//                "            \"rate\": \"500\",\n" +
//                "            \"lastTradedPrice\": \"650\",\n" +
//                "            \"description\": \"Common stock of ABC Corp\"\n" +
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
//                "          \"orderId\": \"ORD001\",\n" +
//                "          \"tradeId\": \"TRADE001\",\n" +
//                "          \"companyName\": \"ABC Corp\",\n" +
//                "          \"symbol\": \"ABC\",\n" +
//                "          \"transactionDateTime\": \"2024-09-15T14:30:00\",\n" +
//                "          \"exchange\": \"NSE\",\n" +
//                "          \"isin\": \"IN1234567890\",\n" +
//                "          \"equityCategory\": \"EQUITY\",\n" +
//                "          \"instrumentType\": \"OPTIONS\",\n" +
//                "          \"optionType\": \"CALL\",\n" +
//                "          \"narration\": \"Purchased ABC Corp shares\",\n" +
//                "          \"rate\": \"600\",\n" +
//                "          \"totalCharge\": \"100\",\n" +
//                "          \"tradeValue\": \"60000\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"shareHolderEquityType\": \"COMMON-STOCK\",\n" +
//                "          \"units\": \"100\",\n" +
//                "          \"otherCharges\": \"50\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN002\",\n" +
//                "          \"orderId\": \"ORD002\",\n" +
//                "          \"tradeId\": \"TRADE002\",\n" +
//                "          \"companyName\": \"XYZ Ltd\",\n" +
//                "          \"symbol\": \"XYZ\",\n" +
//                "          \"transactionDateTime\": \"2024-08-10T12:00:00\",\n" +
//                "          \"exchange\": \"BSE\",\n" +
//                "          \"isin\": \"IN1234567891\",\n" +
//                "          \"equityCategory\": \"EQUITY\",\n" +
//                "          \"instrumentType\": \"OPTIONS\",\n" +
//                "          \"optionType\": \"PUT\",\n" +
//                "          \"narration\": \"Purchased XYZ Ltd shares\",\n" +
//                "          \"rate\": \"700\",\n" +
//                "          \"totalCharge\": \"150\",\n" +
//                "          \"tradeValue\": \"70000\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"shareHolderEquityType\": \"COMMON-STOCK\",\n" +
//                "          \"units\": \"100\",\n" +
//                "          \"otherCharges\": \"75\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN003\",\n" +
//                "          \"orderId\": \"ORD003\",\n" +
//                "          \"tradeId\": \"TRADE003\",\n" +
//                "          \"companyName\": \"LMN Inc\",\n" +
//                "          \"symbol\": \"LMN\",\n" +
//                "          \"transactionDateTime\": \"2024-07-05T11:30:00\",\n" +
//                "          \"exchange\": \"NSE\",\n" +
//                "          \"isin\": \"IN1234567892\",\n" +
//                "          \"equityCategory\": \"EQUITY\",\n" +
//                "          \"instrumentType\": \"FUTURES\",\n" +
//                "          \"narration\": \"Purchased LMN Inc shares\",\n" +
//                "          \"rate\": \"550\",\n" +
//                "          \"totalCharge\": \"120\",\n" +
//                "          \"tradeValue\": \"55000\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"shareHolderEquityType\": \"COMMON-STOCK\",\n" +
//                "          \"units\": \"100\",\n" +
//                "          \"otherCharges\": \"60\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"txnId\": \"TXN004\",\n" +
//                "          \"orderId\": \"ORD004\",\n" +
//                "          \"tradeId\": \"TRADE004\",\n" +
//                "          \"companyName\": \"DEF Ltd\",\n" +
//                "          \"symbol\": \"DEF\",\n" +
//                "          \"transactionDateTime\": \"2024-06-10T13:00:00\",\n" +
//                "          \"exchange\": \"BSE\",\n" +
//                "          \"isin\": \"IN1234567893\",\n" +
//                "          \"equityCategory\": \"EQUITY\",\n" +
//                "          \"instrumentType\": \"OPTIONS\",\n" +
//                "          \"optionType\": \"CALL\",\n" +
//                "          \"narration\": \"Purchased DEF Ltd shares\",\n" +
//                "          \"rate\": \"520\",\n" +
//                "          \"totalCharge\": \"110\",\n" +
//                "          \"tradeValue\": \"52000\",\n" +
//                "          \"type\": \"BUY\",\n" +
//                "          \"shareHolderEquityType\": \"COMMON-STOCK\",\n" +
//                "          \"units\": \"100\",\n" +
//                "          \"otherCharges\": \"55\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}\n", new ObjectMapper());
//    }
}
