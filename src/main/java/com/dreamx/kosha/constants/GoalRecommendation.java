package com.dreamx.kosha.constants;

public enum GoalRecommendation {

    BUYING_HOME("Recommendation for saving or taking a loan to buy / buying / purchase a home or house or villa or flat or shop or land pr ghar or jhopadi or jhuggi or jhopri / mkana / makaan "),
    TRAVEL("Recommendation for planning and saving for travel / honeymoon / trip / tour / holiday / vacation "),
    WANT_TO_TRAVEL("Recommendation for planning and saving for travel / honeymoon / trip / tour / holiday / vacation "),
    WANT_TO_GO_TRAVEL("Recommendation for planning and saving for  travel or go to foreign or other places like city , country , waterpark , music , honneymoon , trip , tour travel holiday vacation"),
    STARTING_BUSINESS("Recommendation for financial plan / planning for start / starting a business dhandha vyapar"),
    BUYING_CAR("Recommendation for saving or taking a loan to buy / buying / purchase a car bus truck jcb bmw van"),
    BUYING_MOTORCYCLE("Recommendation for saving or taking a loan to buy / buying  / purchase a motorcycle or cycle ."),
    BUY_GADGET("Recommendation for buy / buying  gadget ."),
    EDUCATION("Recommendation for saving for education."),
    PAYING_OFF_DEBT("Recommendation for paying off debt efficiently ."),
    SAVING_FOR_WEDDING("Recommendation for wedding / wedding / shadi / engagement expenses plan / planning ."),
    BUILD_ASSETS("Recommendation for build / building assest / assests"),
    SAVING_FOR_RETIREMENT("Recommendation for retirement plan / planning ."),
    CHARITY_DONATIONS("Recommendation for planning charitable donations ."),
    REAL_ESTATE_INVESTMENT("Recommendation for investing in real estate ."),
    LUXURY_PURCHASE("Recommendation for planning and saving for luxury purchases ."),
    VENTURE_CAPITAL("Recommendation for venture capital or startup investment ."),
    HOLIDAY_FUND("Recommendation for building a holiday or vacation fund ."),
    RENOVATE_HOME("Recommendation for plan / planning to renovation or renovate or upgrade home / reconstruction / redecoration "),
    EMERGENCY_FUND("Recommendation for planning to arrange fund for emergency , critical,  fund"),
    PERSONAL_SAVING("Recommendation for planning to save money , paisa , rokra , rokda , for future"),
    INSURANCE("Recommendation for opening insurance "),
    DEFAULT("No specific financial recommendation at the moment ."),
    NONE("No specific financial recommendation at the moment .");

    private final String description;

    // Constructor to initialize the description
    GoalRecommendation(String description) {
        this.description = description;
    }

    // Getter to access the description
    public String getDescription() {
        return description;
    }

    // Method to get GoalRecommendation based on a goal string
    public static GoalRecommendation getRecommendationByGoal(String goal) {
        int maxMatchCount = 0;
        GoalRecommendation bestRecommendation = NONE;

        // Split the goal string into words
        String[] goalWords = goal != null ? goal.toLowerCase().split("\\s+") : new String[0];

        // Check each recommendation
        for (GoalRecommendation recommendation : GoalRecommendation.values()) {
            int matchCount = 0;
            String[] descriptionWords = recommendation.getDescription().toLowerCase().split("\\s+");

            // Count matches for each word in the goal
            for (String goalWord : goalWords) {
                for (String descriptionWord : descriptionWords) {
                    if (goalWord.toLowerCase().equals(descriptionWord.toLowerCase())) {
                        matchCount++;
                    }
                }
            }

            // Update the best recommendation if this one has more matches
            if (matchCount > maxMatchCount) {
                maxMatchCount = matchCount;
                bestRecommendation = recommendation;
            }
        }

        return bestRecommendation; // Return the recommendation with the most matches
    }
}
