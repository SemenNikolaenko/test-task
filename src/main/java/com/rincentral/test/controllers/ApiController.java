package com.rincentral.test.controllers;

import com.rincentral.test.error.ErrorMessage;
import com.rincentral.test.error.NoValuePresentException;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private CarService carService;

    @Autowired
    public ApiController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<List<? extends CarInfo>> getCars(CarRequestParameters requestParameters) {
        List<? extends CarInfo> list = carService.getFilteredCars(requestParameters);
        if (list.size()==0) throw new NoValuePresentException(ErrorMessage.NO_VALUE_PRESENT.name());
         return ResponseEntity.ok(list);
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<List<String>> getFuelTypes() {
        List<String> fuelTypes=carService.getAllFuelsType();
        return ResponseEntity.ok(fuelTypes);
    }

    @GetMapping("/body-styles")
    public ResponseEntity<List<String>> getBodyStyles() {
        List<String> bodyStyles=carService.getAllBodyStyles();
        return ResponseEntity.ok(bodyStyles);
    }

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        List<String> engineTypes=carService.getAllEngineType();
        return ResponseEntity.ok(engineTypes);
    }

    @GetMapping("/wheel-drives")
    public ResponseEntity<List<String>> getWheelDrives() {
        List<String> wheelDrivesTypes=carService.getAllWheelDrives();
        return ResponseEntity.ok(wheelDrivesTypes);
    }

    @GetMapping("/gearboxes")
    public ResponseEntity<List<String>> getGearboxTypes() {
        List<String> gearboxesTypes=carService.getAllGearboxType();
        return ResponseEntity.ok(gearboxesTypes);
    }

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) {
       double averageSpeed = carService.getCarsAverageSpeed(requestParameters);
       if (averageSpeed==-1) return ResponseEntity.notFound().build();
       else
           return new ResponseEntity<>(averageSpeed,HttpStatus.OK);
    }

}
