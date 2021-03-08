package com.rincentral.test.services;

import com.rincentral.test.error.ConflictInputParameterException;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.storage.StorageSimulation;
import com.rincentral.test.utilities.ExternalCarToCarInfoConverter;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.utilities.FilterByParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarService {
    private ExternalCarToCarInfoConverter converter;
    private ExternalCarsApiService externalCarService;
    private FilterByParam filter;
    private StorageSimulation storage;


    @Autowired
    public CarService(ExternalCarToCarInfoConverter converter, ExternalCarsApiService externalCarService, FilterByParam filter, StorageSimulation storage) {
        this.converter = converter;
        this.externalCarService = externalCarService;
        this.filter = filter;
        this.storage = storage;
    }

    public List<? extends CarInfo> getFilteredCars(CarRequestParameters requestParameters) {
        List<? extends ExternalCar> inputList = storage.getAllCarsWithInfo();
        List<? extends CarInfo> findCarList = new ArrayList<>();

        if (requestParameters.getCountry() != null &&
                !requestParameters.getCountry().isBlank()
        ) {
            inputList = filter.filterExternalCarByCountry(inputList, requestParameters);
        }

        if (requestParameters.getSegment() != null &&
                !requestParameters.getSegment().isBlank()) {
            inputList = filter.filterExternalCarBySegment(inputList, requestParameters);
        }
        if (requestParameters.getMinEngineDisplacement() != null &&
                requestParameters.getMinEngineDisplacement() > 0) {
            inputList = filter.filterExternalCarsByMinEngineDisplacement(inputList, requestParameters);
        }

        if (requestParameters.getMinEngineHorsepower() != null &&
                requestParameters.getMinEngineHorsepower() > 0) {
            inputList = filter.filterExternalCarByMinEngineHorsepower(inputList, requestParameters);
        }

        if (requestParameters.getMinMaxSpeed() != null &&
                requestParameters.getMinMaxSpeed() > 0) {
            inputList = filter.filterExternalCarByMinMaxSpeed(inputList, requestParameters);
        }

        if (requestParameters.getYear() != null &&
                requestParameters.getYear() > 0) {
            inputList = filter.filterExternalCarByYear(inputList, requestParameters);
        }

        if (requestParameters.getBodyStyle() != null &&
                !requestParameters.getBodyStyle().isBlank()) {
            inputList = filter.filterExternalCarByBodyStyle(inputList, requestParameters);
        }

        if (requestParameters.getSearch() != null
                &&
                requestParameters.getSearch() > 0) {
            inputList = filter.filterExternalCarBySearch(inputList, requestParameters);
        }

        if (requestParameters.getIsFull() == null || requestParameters.getIsFull().equals(false)) {
            findCarList = inputList.stream()
                    .map(car -> converter.convertToCarInfo(car))
                    .collect(Collectors.toList());
        } else {
            findCarList = inputList.stream()
                    .map(car -> converter.convertToCarFullInfo((ExternalCarInfo) car))
                    .collect(Collectors.toList());
        }

        return findCarList;
    }

    public List<String> getAllFuelsType() {
        return storage.getAllCarsWithInfo().stream()
                .map(car -> car.getFuelType().name())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllBodyStyles() {
        return storage.getAllCarsWithInfo().stream()
                .map(car -> car.getBodyStyle())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllEngineType() {
        return storage.getAllCarsWithInfo().stream()
                .map(car -> car.getEngineType().name())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllWheelDrives() {
        return storage.getAllCarsWithInfo().stream()
                .map(car -> car.getWheelDriveType().name())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllGearboxType() {
        return storage.getAllCarsWithInfo().stream()
                .map(car -> car.getGearboxType().name())
                .distinct()
                .collect(Collectors.toList());
    }

    public Double getCarsAverageSpeed(MaxSpeedRequestParameters parameters) {
        double averageSpeed = -1;
        if (parameters.getBrand() != null && parameters.getModel() != null)
        {
            throw new ConflictInputParameterException("Should be only one input field" +
                    " either model or brand " + "current input \n"
                    +"Model: "
                    + parameters.getModel() + "\n" +
                    "Brand: "+ parameters.getBrand());
        }
        if (parameters.getBrand() != null &&
                (parameters.getModel() == null || parameters.getModel().isBlank()))
        {
            averageSpeed = getAllCarByBrandName(parameters.getBrand()).stream()
                    .mapToInt(car -> car.getMaxSpeed())
                    .average().orElse(-1);
        }
        if ( parameters.getModel() != null &&
                (parameters.getBrand() == null || parameters.getBrand().isBlank()))
        {
            averageSpeed=getAllCarByModelName(parameters.getModel()).stream()
                    .mapToInt(car->car.getMaxSpeed())
                    .average().orElse(-1);
        }
            return averageSpeed;
    }

    public List<ExternalCarInfo> getAllCarByBrandName(String brandName) {
        int brandId = storage.getAllBrandsStore().stream()
                .filter(brand -> brand.getTitle().equalsIgnoreCase(brandName))
                .map(brand -> brand.getId())
                .findAny().orElse(-1);

        return storage.getAllCarsWithInfo().stream()
                .filter(car -> car.getBrandId() == brandId)
                .collect(Collectors.toList());

    }

    public List<ExternalCarInfo> getAllCarByModelName(String modelName) {
        return storage.getAllCarsWithInfo().stream()
                .filter(car -> car.getModel().equalsIgnoreCase(modelName))
                .collect(Collectors.toList());
    }


}
