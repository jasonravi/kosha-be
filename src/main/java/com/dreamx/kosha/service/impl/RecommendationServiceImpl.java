package com.dreamx.kosha.service.impl;

import com.dreamx.kosha.constants.FinancialRecommendation;
import com.dreamx.kosha.service.RecommendationService;
import com.dreamx.kosha.service.UserService;
import com.dreamx.kosha.constants.EmploymentType;
import com.dreamx.kosha.constants.FinancialInstrument;
import com.dreamx.kosha.constants.GoalRecommendation;
import com.dreamx.kosha.constants.Priority;
import com.dreamx.kosha.entity.RecommendationDTO;
import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.entity.UserGoalDTO;
import com.dreamx.kosha.model.GoalRecommendationDTO;
import com.dreamx.kosha.model.InstrumentDTO;
import com.dreamx.kosha.repository.FinancialInstrumentRepository;
import com.dreamx.kosha.service.GoalRecommendationService;
import com.dreamx.kosha.service.GoalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author deepika_rajani
 */
@Service
@AllArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private UserService userService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private GoalRecommendationService goalRecommendationService;

    @Autowired
    private FinancialInstrumentRepository financialInstrumentRepository;

    private static final SecureRandom random = new SecureRandom();


    @Override
    public List<FinancialRecommendation> getRecommendations(int financialHealthScore, int age, int dependents, EmploymentType employment, Priority userGoalPriority, User user) {
        List<FinancialRecommendation> recommendations = new ArrayList<>();

        if (financialHealthScore >= 0 && financialHealthScore <= 3 && age < 25 && dependents < 2 && (employment == EmploymentType.SALARIED || employment == EmploymentType.SALARIED_ACCOUNT)) {
            recommendations.add(FinancialRecommendation.EMERGENCY_FUND);
            recommendations.add(FinancialRecommendation.HEALTH_INSURANCE);
            recommendations.add(FinancialRecommendation.TERM_INSURANCE);
            recommendations.add(FinancialRecommendation.INVESTMENTS);
            recommendations.add(FinancialRecommendation.TAX_SAVING);
        } else if (financialHealthScore >= 4 && financialHealthScore <= 6 && age > 25 && age < 45 && dependents >= 2 && (employment == EmploymentType.SALARIED || employment == EmploymentType.SALARIED_ACCOUNT)) {
            recommendations.add(FinancialRecommendation.HEALTH_INSURANCE);
            recommendations.add(FinancialRecommendation.EMERGENCY_FUND);
            if (userGoalPriority == Priority.HIGH) {
                recommendations.add(FinancialRecommendation.USER_DEFINED_GOALS);
            }
            recommendations.add(FinancialRecommendation.TERM_INSURANCE);
            recommendations.add(FinancialRecommendation.TAX_SAVING);
            recommendations.add(FinancialRecommendation.INVESTMENTS);
            recommendations.add(FinancialRecommendation.SPENDS);
        } else if (financialHealthScore >= 7 && financialHealthScore <= 10 && age > 45 && dependents >= 2 && employment == EmploymentType.SELF_EMPLOYED) {
            recommendations.add(FinancialRecommendation.HEALTH_INSURANCE);
            recommendations.add(FinancialRecommendation.EMERGENCY_FUND);
            recommendations.add(FinancialRecommendation.PENSION);
            recommendations.add(FinancialRecommendation.TERM_INSURANCE);
            recommendations.add(FinancialRecommendation.USER_DEFINED_GOALS);
            recommendations.add(FinancialRecommendation.INVESTMENTS);
            recommendations.add(FinancialRecommendation.TAX_SAVING);
        } else {
            recommendations.add(FinancialRecommendation.EMERGENCY_FUND);
            recommendations.add(FinancialRecommendation.PENSION);
            recommendations.add(FinancialRecommendation.TERM_INSURANCE);
            recommendations.add(FinancialRecommendation.INVESTMENTS);
        }

        // todo check if user already have this in portfolio
        // todo return exact instruments in each category

        return recommendations;
    }

    @Override
    public RecommendationDTO getUsersRecommendations(Long userId, Boolean isUser) {
        User user = userService.getUserById(userId);
        String employmentType = user.getEmploymentType().name().replace(" ", "_");

        List<FinancialRecommendation> financialRecommendations = getRecommendations(
                user.getFinancialHealthScore() == null ? 5 : user.getFinancialHealthScore().intValue(),
                user.getAge(),
                user.getNumberOfDependents(),
                EmploymentType.valueOf(employmentType.toUpperCase(Locale.ROOT)),
                Priority.HIGH,
                user
        );
        List<FinancialInstrument> emergencyFundInstruments = new ArrayList<>();

        List<GoalRecommendationDTO> systemRecommendations = new ArrayList<>();


        for (FinancialRecommendation financialRecommendation : financialRecommendations) {
            GoalRecommendationDTO goalRecommendationDTO = new GoalRecommendationDTO();
            goalRecommendationDTO.setId(random.nextLong(1000000));
            goalRecommendationDTO.setTargetAmount(financialRecommendation.getFund());
            //goalRecommendationDTO.setAchievedAmount(getRandomNumberInRange(100000, (int) Math.round(financialRecommendation.getFund())));
            goalRecommendationDTO.setName(financialRecommendation.name());
            goalRecommendationDTO.setLogo("https://buildathon-kosha-web-client.s3.ap-south-1.amazonaws.com/goal/kosh_default_logo.png");
            List<FinancialInstrument> financialInstruments = Arrays.stream(FinancialInstrument.values())
                    .filter(instrument -> instrument.getRecommendation() == financialRecommendation)
                    .toList();

            List<InstrumentDTO> userInstrumentDTOS = new ArrayList<>();
            List<String> instruments = new ArrayList<>();

            for (FinancialInstrument financialInstrument : financialInstruments) {
                InstrumentDTO instrumentDTO = new InstrumentDTO();
                instrumentDTO.setInstrument(financialInstrument.name());
                instrumentDTO.setDescription(financialInstrument.getRecommendation().getDescription());
                instrumentDTO.setName(financialInstrument.getName());
                instruments.add(financialInstrument.name());
                userInstrumentDTOS.add(instrumentDTO);
            }
            goalRecommendationDTO.setRecommendations(userInstrumentDTOS);
//            goalRecommendationDTO.setAchievedAmount(financialInstrumentRepository.getAchieveAmount(userId, instruments));
            systemRecommendations.add(goalRecommendationDTO);

            emergencyFundInstruments.addAll(financialInstruments);
        }

        List<UserGoalDTO> userGoalDTOS = goalService.getGoal(userId);
        List<GoalRecommendationDTO> userRecommendationDTOS = new ArrayList<>();

        for (UserGoalDTO userGoalDTO : userGoalDTOS) {
            GoalRecommendation goalRecommendation = GoalRecommendation.getRecommendationByGoal(userGoalDTO.getName());
            List<FinancialInstrument> financialInstruments = goalRecommendationService.getRecommendationForGoal(goalRecommendation);
            GoalRecommendationDTO goalRecommendationDTO = new GoalRecommendationDTO();
            goalRecommendationDTO.setName(userGoalDTO.getName());

            List<InstrumentDTO> goalInstrumentDTOS = new ArrayList<>();
            List<String> instruments = new ArrayList<>();

            for (FinancialInstrument financialInstrument : financialInstruments) {
                InstrumentDTO instrumentDTO = new InstrumentDTO();
                instrumentDTO.setInstrument(financialInstrument.name());
                instrumentDTO.setDescription(financialInstrument.getRecommendation().getDescription());
                instrumentDTO.setName(financialInstrument.getName());
                instruments.add(financialInstrument.name());
                goalInstrumentDTOS.add(instrumentDTO);
            }
            goalRecommendationDTO.setRecommendations(goalInstrumentDTOS);
//            goalRecommendationDTO.setAchievedAmount(financialInstrumentRepository.getAchieveAmount(userId, instruments));
            goalRecommendationDTO.setId(userGoalDTO.getId());
            goalRecommendationDTO.setLogo(userGoalDTO.getLogo());
            goalRecommendationDTO.setDuration(userGoalDTO.getDuration());
            goalRecommendationDTO.setPriority(userGoalDTO.getPriority().name());
            goalRecommendationDTO.setDescription(userGoalDTO.getDescription());
            goalRecommendationDTO.setTargetAmount(userGoalDTO.getTargetAmount());

            userRecommendationDTOS.add(goalRecommendationDTO);
        }

        RecommendationDTO recommendation = new RecommendationDTO();

        // todo consider goal priority in allocating achieved amount

        double totalTargetAmount = userRecommendationDTOS.stream()
                .mapToDouble(r -> r.getTargetAmount() == null ? 0 : r.getTargetAmount())
                .sum();

        // Step 4: Distribute achieved amount
        for (GoalRecommendationDTO sysRecomm : userRecommendationDTOS) {
            if (totalTargetAmount > 0) { // Avoid division by zero
                double proportion = sysRecomm.getTargetAmount() / totalTargetAmount;

                final List<String> instruments = sysRecomm.getRecommendations()
                        .stream()
                        .map(InstrumentDTO::getInstrument)
                        .collect(Collectors.toList());

                final Double achievedAmount = financialInstrumentRepository.getAchieveAmount(userId, instruments);

                double allocatedAmount = achievedAmount * proportion;
                sysRecomm.setAchievedAmount(round(allocatedAmount, 2));
            }
        }
        if(isUser) {
            recommendation.setRecommendations(userRecommendationDTOS);
        } else {
            double totalTargetAmount1 = systemRecommendations.stream()
                    .mapToDouble(r -> r.getTargetAmount() == null ? 0 : r.getTargetAmount())
                    .sum();

            // Step 4: Distribute achieved amount
            for (GoalRecommendationDTO sysRecomm : systemRecommendations) {
                if (totalTargetAmount1 > 0) { // Avoid division by zero
                    double proportion = sysRecomm.getTargetAmount() / totalTargetAmount1;

                    final List<String> instruments = sysRecomm.getRecommendations()
                            .stream().map(InstrumentDTO::getInstrument)
                            .collect(Collectors.toList());

                    final Double achievedAmount = financialInstrumentRepository.getAchieveAmount(userId, instruments);

                    double allocatedAmount = achievedAmount * proportion;
                    sysRecomm.setAchievedAmount(getRandomNumberInRange(100000, 350000));
                }
            }

            // todo add user goals also in system recomm
            systemRecommendations.addAll(userRecommendationDTOS);
            recommendation.setRecommendations(systemRecommendations);
        }


        return recommendation;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private  double getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random random = new Random();
        return (double) random.nextLong((max - min) + 1) + min;
    }




}
