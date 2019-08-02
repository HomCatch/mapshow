package com.xiaohe.mapshow.modules.common;

import com.xiaohe.mapshow.modules.cities.entity.Cities;
import com.xiaohe.mapshow.modules.cities.service.CitiesService;
import com.xiaohe.mapshow.modules.provinces.entity.Provinces;
import com.xiaohe.mapshow.modules.provinces.service.ProvincesService;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2019/3/29 17:42
 */
@RestController
@RequestMapping(value = "/area")
public class AreaController {
    private final ProvincesService provincesService;
    private final CitiesService citiesService;

    @Autowired
    public AreaController(ProvincesService provincesService, CitiesService citiesService) {
        this.provincesService = provincesService;
        this.citiesService = citiesService;
    }

    @GetMapping("/provinces")
    public Result getAllProvince() {
        CommonResult commonResult = new CommonResult();
        List<Provinces> all = provincesService.findList();
        commonResult.setDatas(all);
        commonResult.ok();
        return commonResult;
    }

    @GetMapping("/cities")
    public Result getAllCitiesByProvince(@RequestParam(value = "provinceId") String provinceId) {
        CommonResult commonResult = new CommonResult();
        List<Cities> all = citiesService.findByProvinceId(provinceId);
        commonResult.setDatas(all);
        commonResult.ok();
        return commonResult;
    }
}
