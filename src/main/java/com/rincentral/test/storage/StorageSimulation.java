package com.rincentral.test.storage;

import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.services.ExternalCarsApiService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Getter
@NoArgsConstructor
public class StorageSimulation {
    private List<ExternalCar> allCarsStore;
    private List<ExternalBrand> allBrandsStore;
    private List<ExternalCarInfo> allCarsWithInfo;
    private ExternalCarsApiService apiService;

    @Autowired
    public StorageSimulation(ExternalCarsApiService apiService) {
        this.apiService = apiService;
    }

    @PostConstruct
    public void postConstruct() {
        allCarsStore = apiService.loadAllCars().stream().collect(Collectors.toList());
        allBrandsStore = apiService.loadAllBrands().stream().distinct().collect(Collectors.toList());
        allCarsWithInfo = allCarsStore.stream()
                .map(e -> apiService.loadCarInformationById(e.getId()))
                .distinct()
                .collect(Collectors.toList());
    }


}
