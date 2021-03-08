package com.rincentral.test.utilities;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.services.CarService;
import com.rincentral.test.storage.StorageSimulation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FilterByParam {

    private StorageSimulation storage;
    private ExternalCarToCarInfoConverter converter;

    @Autowired
    public FilterByParam(StorageSimulation storage, ExternalCarToCarInfoConverter converter) {
        this.storage = storage;
        this.converter = converter;
    }

    public List<Integer> getBrandIdByCountry(String brandName){
     return    storage.getAllBrandsStore().stream()
                .filter(brand->brand.getCountry().equalsIgnoreCase(brandName))
                .map(brand->brand.getId())
                .collect(Collectors.toList());
    }

    public List<? extends ExternalCar> filterExternalCarByCountry(List<? extends ExternalCar> cars, CarRequestParameters params) {
            List<Integer> brandIdList = getBrandIdByCountry(params.getCountry());
        return cars.stream()
                .filter(car->brandIdList.contains(car.getBrandId()))
                .collect(Collectors.toList());



    }

    public List<? extends ExternalCar> filterExternalCarBySegment(List<? extends ExternalCar> cars, CarRequestParameters params) {
        return cars.stream()
                .filter(car -> car.getSegment().equalsIgnoreCase(params.getSegment()))
                .collect(Collectors.toList());
    }

    public List<? extends ExternalCar> filterExternalCarsByMinEngineDisplacement(List<? extends ExternalCar> cars, CarRequestParameters params) {
        try {
            return cars.stream()
                    .map(car -> (ExternalCarInfo) car)
                    .filter(car -> car.getEngineDisplacement() >= params.getMinEngineDisplacement())
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public List<? extends ExternalCar> filterExternalCarByMinEngineHorsepower(List<? extends ExternalCar> cars, CarRequestParameters params) {
        try {
            return cars.stream()
                    .map(car -> (ExternalCarInfo) car)
                    .filter(car -> car.getHp() >= params.getMinEngineHorsepower())
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public List<? extends ExternalCar> filterExternalCarByMinMaxSpeed(List<? extends ExternalCar> cars, CarRequestParameters params) {
        try {
            return cars.stream()
                    .map(car -> (ExternalCarInfo) car)
                    .filter(car -> car.getMaxSpeed() >= params.getMinMaxSpeed())
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public List<? extends ExternalCar> filterExternalCarBySearch(List<? extends ExternalCar> cars, CarRequestParameters params) {
        return cars.stream()
                .sorted(Comparator.comparing(ExternalCar::getModel).thenComparing(ExternalCar::getGeneration).thenComparing(ExternalCar::getModification))
                .limit(params.getSearch())
                .collect(Collectors.toList());
    }

    public List<? extends CarInfo> filterExternalCarByIsFull(List<? extends ExternalCar> cars, CarRequestParameters params, CarService carService) {
        return cars.stream()
                .map(car -> (ExternalCarInfo) car)
                .map(car -> converter.convertToCarFullInfo(car))
                .collect(Collectors.toList());
    }

    public List<? extends ExternalCar> filterExternalCarByYear(List<? extends ExternalCar> cars, CarRequestParameters params) {
        return cars.stream()
                .map(car -> (ExternalCarInfo) car)
                .filter(car ->params.getYear()>=converter.getDateInfo(car.getYearsRange())[0] && params.getYear()<=converter.getDateInfo(car.getYearsRange())[1])
                .collect(Collectors.toList());
    }

    public List<? extends ExternalCar> filterExternalCarByBodyStyle(List<? extends ExternalCar> cars, CarRequestParameters params) {
        return cars.stream()
                .map(car -> (ExternalCarInfo) car)
                .filter(car -> car.getBodyStyle().contains(params.getBodyStyle()))
                .collect(Collectors.toList());
    }
}
