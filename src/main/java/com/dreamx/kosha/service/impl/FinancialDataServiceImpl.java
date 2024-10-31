package com.dreamx.kosha.service.impl;

import com.dreamx.kosha.constants.FinancialCategory;
import com.dreamx.kosha.constants.HealthScoreCategory;
import com.dreamx.kosha.constants.TransactionType;
import com.dreamx.kosha.entity.HealthScoreDTO;
import com.dreamx.kosha.entity.InvestmentPortfolioDTO;
import com.dreamx.kosha.entity.ItemDTO;
import com.dreamx.kosha.entity.MonthlyLineChartDTO;
import com.dreamx.kosha.entity.Transaction;
import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.entity.UserFinancialInstrument;
import com.dreamx.kosha.entity.WealthDTO;
import com.dreamx.kosha.helper.CreditCardParser;
import com.dreamx.kosha.helper.DepositParser;
import com.dreamx.kosha.helper.EquityParser;
import com.dreamx.kosha.helper.MutualFundParser;
import com.dreamx.kosha.helper.NPSParser;
import com.dreamx.kosha.helper.RecurringDepositParser;
import com.dreamx.kosha.helper.TermDepositParser;
import com.dreamx.kosha.model.UserFITxnDTO;
import com.dreamx.kosha.repository.FinancialInstrumentRepository;
import com.dreamx.kosha.service.FinancialDataService;
import com.dreamx.kosha.service.HealthScoreService;
import com.dreamx.kosha.service.TransactionService;
import com.dreamx.kosha.service.UserService;
import com.dreamx.kosha.service.UserV1Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author deepika_rajani
 */
@Service
@AllArgsConstructor
@Slf4j
public class FinancialDataServiceImpl implements FinancialDataService {

    private FinancialInstrumentRepository financialInstrumentRepository;

    private HealthScoreService healthScoreService;

    private UserService userService;

    private UserV1Service userV1Service;

    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public MonthlyLineChartDTO getUserMonthlyData(Long userId) {
        // Mock data for demonstration purposes
//        List<String> categories = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
//        List<Double> assets = Arrays.asList(45000.0, 47000.0, 48000.0, 50000.0, 51000.0, 53000.0, 55000.0, 56000.0, 58000.0, 59000.0, 60000.0, 61000.0);
//        List<Double> liabilities = Arrays.asList(28000.0, 29000.0, 29500.0, 30000.0, 31000.0, 32000.0, 33000.0, 33500.0, 34000.0, 34500.0, 35000.0, 36000.0);
//        List<Double> spends = Arrays.asList(12000.0, 12500.0, 13000.0, 13500.0, 14000.0, 14500.0, 15000.0, 15500.0, 16000.0, 16500.0, 17000.0, 17500.0);
//
//        return new MonthlyLineChartDTO(categories, assets, liabilities, spends);

        // have skipped bank balance in asset
        final List<Transaction> transactions = transactionService.findByUserId(userId);

        Map<String, Double> assetsMap = new HashMap<>();
        Map<String, Double> liabilitiesMap = new HashMap<>();
        Map<String, Double> spendsMap = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");

        for (Transaction transaction : transactions) {
            log.info("Transaction: {}", transaction);

            String month = transaction.getTxnDate().format(formatter).substring(0, 3).toUpperCase();

            FinancialCategory category = transaction.getFinancialInstrument().getCategory();
            double value = transaction.getAmount();

            switch (category) {
                case ASSET:
                    assetsMap.put(month, assetsMap.getOrDefault(month, 0.0) + value);
                    break;
                case LIABILITY:
                    liabilitiesMap.put(month, liabilitiesMap.getOrDefault(month, 0.0) + value);
                    break;
                case BANK_ACCOUNT:
                    if (transaction.getType() == TransactionType.DEBIT) {
                        spendsMap.put(month, spendsMap.getOrDefault(month, 0.0) + value);
                    }
                    break;
                default:
                    break;
            }
        }

        List<String> months = Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

        List<Double> assets = new ArrayList<>();
        List<Double> liabilities = new ArrayList<>();
        List<Double> spends = new ArrayList<>();

        for (String month : months) {
            assets.add(assetsMap.getOrDefault(month, 0.0));
            liabilities.add(liabilitiesMap.getOrDefault(month, 0.0));
            spends.add(spendsMap.getOrDefault(month, 0.0));
        }

        return new MonthlyLineChartDTO(months, assets, liabilities, spends);
    }

    public HealthScoreDTO getHealthScore(Long userId) {
        final List<UserFinancialInstrument> financialInstruments = financialInstrumentRepository.findByUserId(userId);

        final List<Transaction> transactions = transactionService.findByUserId(userId);

        final User user = userService.getUserById(userId);

        HealthScoreDTO response = healthScoreService.getUserHealthScore(user, financialInstruments, transactions);
        log.info("Health score calculated for user {}. Score: {}", user.getId(), response.getScore());

        final int userFinancialScoresUpdated = userV1Service.updateUserFinancialScores(user);
        log.info("Financial data updated for user {}. Rows affected: {}", user.getId(), userFinancialScoresUpdated);

        return response;

        // Mock data for demonstration purposes
//        String healthScore = "86";
//
//        return new HealthScoreDTO(healthScore, "Good");
    }

    public WealthDTO getTotalWealth(Long userId) {
        // Mock data for demonstration purposes
//        String totalWealth = "₹3.80L";
//        String assets = "₹80K";
//        String liabilities = "₹1L";
//        String spends = "₹1L";
//        return new WealthDTO(totalWealth, assets, liabilities, spends);

        List<UserFinancialInstrument> financialInstruments = financialInstrumentRepository.findByUserId(userId);

        double totalAssets = financialInstruments.stream()
                .filter(fi -> fi.getFinancialInstrument().getCategory() == FinancialCategory.ASSET || fi.getFinancialInstrument().getCategory() == FinancialCategory.BANK_ACCOUNT)
                .mapToDouble(UserFinancialInstrument::getValue)
                .sum();

        double totalLiabilities = financialInstruments.stream()
                .filter(fi -> fi.getFinancialInstrument().getCategory() == FinancialCategory.LIABILITY)
                .mapToDouble(UserFinancialInstrument::getValue)
                .sum();

        final List<Transaction> transactions = transactionService.findByUserId(userId);
        double totalSpends = transactions.stream()
                .filter(txn -> txn.getFinancialInstrument().getCategory() == FinancialCategory.BANK_ACCOUNT)
                .filter(txn -> txn.getType() == TransactionType.DEBIT)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double netWorth = totalAssets - totalLiabilities;

        return new WealthDTO(
                formatCurrency(netWorth),
                formatCurrency(totalAssets),
                formatCurrency(totalLiabilities),
                formatCurrency(totalSpends));
    }

    @Override
    public List<InvestmentPortfolioDTO> getInvestmentPortfolio(Long userId) {
        // Mock data for demonstration purposes
//        List<ItemDTO> assets = Arrays.asList(
//                new ItemDTO("House", 500000.0),
//                new ItemDTO("Car", 30000.0)
//        );
//
//        List<ItemDTO> liabilities = Arrays.asList(
//                new ItemDTO("Mortgage", 200000.0),
//                new ItemDTO("Car Loan", 15000.0)
//        );
//
//        List<ItemDTO> spends = Arrays.asList(
//                new ItemDTO("Groceries", 500.0),
//                new ItemDTO("Utilities", 200.0)
//        );
//
//        return Arrays.asList(
//                new InvestmentPortfolioDTO(FinancialCategory.ASSET.getDisplayName(), "₹80K", assets),
//                new InvestmentPortfolioDTO(FinancialCategory.LIABILITY.getDisplayName(), "₹1L", liabilities),
//                new InvestmentPortfolioDTO(FinancialCategory.EXPENSE.getDisplayName(), "₹1L", spends)
//        );


        List<UserFinancialInstrument> financialInstruments = financialInstrumentRepository.findByUserId(userId);

        List<ItemDTO> assets = financialInstruments.stream()
                .filter(fi -> fi.getFinancialInstrument().getCategory() == FinancialCategory.ASSET)
                .map(fi -> new ItemDTO(fi.getFinancialInstrument().getName(), fi.getValue()))
                .collect(Collectors.toList());

        List<ItemDTO> liabilities = financialInstruments.stream()
                .filter(fi -> fi.getFinancialInstrument().getCategory() == FinancialCategory.LIABILITY)
                .map(fi -> new ItemDTO(fi.getFinancialInstrument().getName(), fi.getValue()))
                .collect(Collectors.toList());

        final List<Transaction> transactions = transactionService.findByUserId(userId);

        List<ItemDTO> spends = transactions.stream()
                .filter(txn -> txn.getFinancialInstrument().getCategory() == FinancialCategory.BANK_ACCOUNT)
                .filter(txn -> txn.getType() == TransactionType.DEBIT)
                .map(fi -> new ItemDTO(fi.getDescription(), fi.getAmount()))
                .collect(Collectors.toList());

        double totalAssets = assets.stream().mapToDouble(ItemDTO::getValue).sum();
        double totalLiabilities = liabilities.stream().mapToDouble(ItemDTO::getValue).sum();
        double totalSpends = spends.stream().mapToDouble(ItemDTO::getValue).sum();

        String assetFormatted = formatCurrency(totalAssets);
        String liabilitiesFormatted = formatCurrency(totalLiabilities);
        String spendsFormatted = formatCurrency(totalSpends);

        InvestmentPortfolioDTO assetPortfolio = new InvestmentPortfolioDTO(FinancialCategory.ASSET.getDisplayName(), assetFormatted, assets);
        InvestmentPortfolioDTO liabilitiesPortfolio = new InvestmentPortfolioDTO(FinancialCategory.LIABILITY.getDisplayName(), liabilitiesFormatted, liabilities);
        InvestmentPortfolioDTO spendsPortfolio = new InvestmentPortfolioDTO(FinancialCategory.EXPENSE.getDisplayName(), spendsFormatted, spends);

        return Arrays.asList(assetPortfolio, liabilitiesPortfolio, spendsPortfolio);
    }

    private String formatCurrency(double value) {
        if (value >= 1_00_000) {
            return "₹" + new DecimalFormat("#.##").format(value / 1_00_000) + "L";
        } else if (value >= 1_000) {
            return "₹" + new DecimalFormat("#.##").format(value / 1_000) + "K";
        } else {
            return "₹" + new DecimalFormat("#.##").format(value);
        }
    }

    @Override
    public void processUserFinancialData(Long userId) {
        try {
            // read from files
            List<String> userFinDataList = readDataForRandomBucket();

            // parse & collect all financial data
            final List<UserFITxnDTO> fiTxnDTOS = parseFIData(userFinDataList);

            final List<UserFinancialInstrument> userFinancialInstruments = convertToUserFinancialInstruments(userId, fiTxnDTOS);

            final List<Transaction> transactions = convertToTransactions(fiTxnDTOS);

            // insert FIs
            financialInstrumentRepository.insertUserFinancialInstrument(userFinancialInstruments);

            // insert transactions
            transactionService.insertTransaction(userId, transactions);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Transaction> convertToTransactions(List<UserFITxnDTO> fiTxnDTOS) {
        return fiTxnDTOS.stream()
                .flatMap(fiTxnDTO -> fiTxnDTO.getTransactions().stream())
                .collect(Collectors.toList());
    }

    private List<UserFITxnDTO> parseFIData(List<String> userFinDataList) throws JsonProcessingException {
        List<UserFITxnDTO> fiTxnDTOList = new ArrayList<>();

        for (String finData : userFinDataList) {
            JsonNode jsonNode = objectMapper.readTree(finData);
            String accountType = jsonNode.get("Account").get("type").asText();
            log.info("Account type: {}", accountType);

            UserFITxnDTO fiTxnDTO = null;

            switch (accountType) {
                case "credit_card":
                    fiTxnDTO = CreditCardParser.parse(finData, objectMapper);
                    break;
                case "equities":
                    fiTxnDTO = EquityParser.parse(finData, objectMapper);
                    break;
                case "nps":
                    fiTxnDTO = NPSParser.parse(finData, objectMapper);
                    break;
                case "mutualfunds":
                    fiTxnDTO = MutualFundParser.parse(finData, objectMapper);
                    break;
                case "deposit":
                    fiTxnDTO = DepositParser.parse(finData, objectMapper);
                    break;
                case "recurring_deposit":
                    fiTxnDTO = RecurringDepositParser.parse(finData, objectMapper);
                    break;
                case "term_deposit":
                    fiTxnDTO = TermDepositParser.parse(finData, objectMapper);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown account type: " + accountType);
            }

            fiTxnDTO.setMeta(finData);

            fiTxnDTOList.add(fiTxnDTO);

        }

        return fiTxnDTOList;
    }

    private List<UserFinancialInstrument> convertToUserFinancialInstruments(Long userId, List<UserFITxnDTO> fiTxnDTOList) {
        List<UserFinancialInstrument> userFinancialInstruments = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixMonthsAgo = now.minus(6, ChronoUnit.MONTHS);

        for (UserFITxnDTO fiTxnDTO : fiTxnDTOList) {
            UserFinancialInstrument ufi = new UserFinancialInstrument();
            ufi.setFinancialInstrument(fiTxnDTO.getName());
            ufi.setValue(fiTxnDTO.getValue());
            ufi.setUser(new User(userId));
            ufi.setMeta(fiTxnDTO.getMeta());
            ufi.setDateRangeFrom(sixMonthsAgo);
            ufi.setDateRangeTo(now);
            ufi.setTransactionTime(fiTxnDTO.getTxnTime());

            userFinancialInstruments.add(ufi);
        }

        return userFinancialInstruments;
    }


    private Path getFolderPath(String randomFolder) throws IOException, URISyntaxException {
        // Try to load the resource from the filesystem if available (for IntelliJ)
        try {
            URL resourceUrl = HealthScoreServiceImpl.class.getClassLoader()
                    .getResource("test_data/" + randomFolder);
            if (resourceUrl != null) {
                Path folderPath = Paths.get(resourceUrl.toURI());
                if (Files.exists(folderPath)) {
                    return folderPath;
                }
            }
        } catch (NullPointerException | URISyntaxException e) {
            // Ignore and fallback to using resource stream
        }

        // Fallback to JAR-compatible solution
        InputStream resourceStream = HealthScoreServiceImpl.class.getClassLoader()
                .getResourceAsStream("test_data/" + randomFolder);

        if (resourceStream == null) {
            throw new FileNotFoundException("Resource not found: test_data/" + randomFolder);
        }

        // Create a temporary directory and extract the resource from the JAR
        Path tempDir = Files.createTempDirectory("tempResource");
        Path tempFile = tempDir.resolve(randomFolder);
        Files.copy(resourceStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        resourceStream.close();

        return tempFile;
    }

    private List<String> readDataForRandomBucket() {
        try {
            // Get a random folder
            final String randomFolder = "score_7_10_1"; //getRandomScore() + "_1";
            log.info("Random folder: {}", randomFolder);

            // Read the files from the folder
            Path folderPath = getFolderPath(randomFolder);

            List<String> data = new ArrayList<>();

            // Read the files
            Files.walk(folderPath)
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        try {
                            // Handle files inside JAR with InputStream
                            InputStream fileStream = HealthScoreServiceImpl.class.getClassLoader()
                                    .getResourceAsStream("test_data/" + randomFolder + "/" + filePath.getFileName());
                            if (fileStream != null) {
                                String fileData = new String(fileStream.readAllBytes());
                                data.add(fileData);
                                fileStream.close();
                            } else {
                                // Handle files on filesystem
                                String fileData = Files.readString(filePath);
                                data.add(fileData);
                            }
                        } catch (IOException e) {
                            log.error("Error reading file: " + filePath, e);
                        }
                    });

//            log.info("Files data: " + data);
            return data;
        } catch (IOException e) {
            log.error("Error reading file", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    private String getRandomScore() {
        Random random = new Random();
        int category = random.nextInt(3);
        switch (category) {
            case 0:
                return "score_0_3"; // 0-3
            case 1:
            case 2:
                return "score_7_10";
            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }
    }

}
