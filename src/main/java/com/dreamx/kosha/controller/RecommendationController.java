package com.dreamx.kosha.controller;

/**
 * @author deepika_rajani
 */

import com.dreamx.kosha.service.RecommendationService;
import com.dreamx.kosha.entity.RecommendationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/kosha/recommendations")
public class RecommendationController {

    private RecommendationService recommendationService;

    @GetMapping("{id}")
    public ResponseEntity<RecommendationDTO> getUsersRecommendation(@PathVariable("id") Long userId, @RequestParam(name = "user") Boolean user) {
        final RecommendationDTO usersRecommendations = recommendationService.getUsersRecommendations(userId, user);
        return new ResponseEntity<>(usersRecommendations, HttpStatus.OK);
    }
}
