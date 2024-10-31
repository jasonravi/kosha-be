package com.dreamx.kosha.controller;


import com.dreamx.kosha.entity.UserGoalDTO;
import com.dreamx.kosha.model.UserGoalResponseDTO;
import com.dreamx.kosha.service.GoalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/kosha/user/goal")
public class GoalController {

    private GoalService goalService;

    @GetMapping("{id}")
    public ResponseEntity<List<UserGoalDTO>> getGoal(@PathVariable("id") Long userId){
        List<UserGoalDTO> goals = goalService.getGoal(userId);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<UserGoalResponseDTO> createGoal(@PathVariable("id") Long userId, @RequestParam(name = "handle", required = false) String handle, @RequestBody UserGoalDTO userGoalDTO) throws JsonProcessingException {
        String result = null;
        if(null != handle) {
            result = handle.replace("\"", "");
        }

        UserGoalResponseDTO userGoalResponseDTO = goalService.saveGoal(userId, result, userGoalDTO);
        return new ResponseEntity<>(userGoalResponseDTO, HttpStatus.CREATED);
    }
}
