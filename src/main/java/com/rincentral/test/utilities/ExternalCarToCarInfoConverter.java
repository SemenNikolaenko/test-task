package com.rincentral.test.utilities;

import com.rincentral.test.models.BodyCharacteristics;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.EngineCharacteristics;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.storage.StorageSimulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExternalCarToCarInfoConverter {
    private final StorageSimulation storage;

    @Autowired
    public ExternalCarToCarInfoConverter(StorageSimulation storage) {
        this.storage = storage;
    }


    public CarInfo convertToCarInfo(ExternalCar externalCar) {
        return new CarInfo(externalCar.getId(),
                externalCar.getSegment(),
                getBrandNameById(externalCar.getBrandId()),
                externalCar.getModel(),
                getCountryByCarId(externalCar.getBrandId()),
                externalCar.getGeneration(),
                externalCar.getModification()
        );
    }

    public CarInfo convertToCarFullInfo(ExternalCarInfo externalCar) {
        EngineCharacteristics engine = getEngineCharacteristicsByCarId(externalCar.getId());
        BodyCharacteristics body = getBodyCharacteristicsByCarId(externalCar.getId());
        return new CarFullInfo(externalCar.getId(),
                externalCar.getSegment(),
                getBrandNameById(externalCar.getBrandId()),
                externalCar.getModel(),
                getCountryByCarId(externalCar.getBrandId()),
                externalCar.getGeneration(),
                externalCar.getModification(),
                engine,
                body
        );
    }

    public int[] getDateInfo(String date) {
        int[] yearRange = new int[2];
        String[] stringYears = date.split("-");
        yearRange[0] = Integer.parseInt(stringYears[0]);
        yearRange[1] = (stringYears[1].equals("present")) ? 2021 : Integer.parseInt(stringYears[1]);
        return yearRange;
    }

    public BodyCharacteristics getBodyCharacteristicsByCarId(Integer carId) {
        ExternalCarInfo car = storage.getAllCarsWithInfo().stream().filter(e -> e.getId().equals(carId)).findFirst().get();
        return new BodyCharacteristics(car.getBodyLength(), car.getBodyWidth(), car.getBodyHeight(), car.getBodyStyle());
    }

    public EngineCharacteristics getEngineCharacteristicsByCarId(Integer carId) {
        ExternalCarInfo car = storage.getAllCarsWithInfo().stream().filter(e -> e.getId().equals(carId)).findFirst().get();
        return new EngineCharacteristics(car.getFuelType(), car.getEngineType(), car.getEngineDisplacement(), car.getHp());
    }

    public String getCountryByCarId(Integer brandId) {
        return storage.getAllBrandsStore().stream().filter(e -> e.getId().equals(brandId)).findFirst().get().getCountry();
    }

    public String getBrandNameById(Integer brandId) {
        return storage.getAllBrandsStore().stream().filter(e -> e.getId().equals(brandId)).findFirst().get().getTitle();
    }


}
